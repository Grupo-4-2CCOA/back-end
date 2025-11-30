import json
import pika
import os
import mysql.connector
from rich.console import Console
from rich.json import JSON
from email.message import EmailMessage
import smtplib

RABBITMQ_URL = os.environ.get("RABBITMQ_URL", "amqp://guest:guest@localhost:5672/")
GMAIL_PASSWORD = os.environ.get("GMAIL_PASSWORD", "PASSWORD_NULL")
QUEUE_NAME = "beauty-barreto.queue"

print(GMAIL_PASSWORD)

DB_CONFIG = {
    "host": os.environ.get("DB_HOST", "localhost"),
    "user": os.environ.get("DB_USER", "infra"),
    "password": os.environ.get("DB_PASSWORD", "infra"),
    "database": os.environ.get("DB_NAME", "grupo4")
}

console = Console()

def salvar_feedback(data):
    try:
        conn = mysql.connector.connect(**DB_CONFIG)
        cursor = conn.cursor()

        cursor.execute("""
            INSERT INTO feedback (
                rating, comment, fk_schedule
            ) VALUES (%s, %s, %s)
        """, (
            data["rating"],
            data.get("comment", ""),
            data["scheduleId"]
        ))
        conn.commit()
        feedback_id = cursor.lastrowid

        cursor.execute("""
            SELECT u.name, u.email
            FROM schedule s
            JOIN user u ON u.id = s.fk_client
            WHERE s.id = %s
        """, [data["scheduleId"]])
        cliente = cursor.fetchone()

        if cliente:
            nome_cliente, email_cliente = cliente
            enviar_email_agradecimento(
                destinatario=email_cliente,
                nome_cliente=nome_cliente,
                rating=data["rating"],
                comment=data.get("comment", "")
            )

        return feedback_id

    except mysql.connector.Error as err:
        console.print(f"[red] Erro ao salvar feedback no MySQL:[/red] {err}")
        return None
    finally:
        if conn.is_connected():
            cursor.close()
            conn.close()


def enviar_email_agradecimento(destinatario, nome_cliente, rating, comment):
    try:
        print(">>> [1] Entrou na função enviar_email_agradecimento")

        msg = EmailMessage()
        msg["Subject"] = "Obrigado pelo seu feedback! 💬"
        msg["From"] = "Beauty Barreto"
        msg["To"] = destinatario

        print(">>> [2] Headers configurados")

        comentario_html = ""
        if comment and comment.strip():
            comentario_html = f"""
                <p><strong>📝 Comentário:</strong> “{comment}”</p>
            """

        corpo = f"""
        <html>
        <body>
            <p>Olá {nome_cliente},</p>
            <p>Obrigado por avaliar seu atendimento no <strong> Espaço Vilma Barreto</strong>!</p>
            <p><strong>⭐ Avaliação:</strong> {rating} estrelas<br>
            {comentario_html}
            <p>Ficamos felizes com sua opinião. Esperamos vê-lo novamente em breve!</p>
            <p>Com carinho,<br>Equipe Beauty Barreto</p>
        </body>
        </html>
        """

        msg.set_content("Obrigado pelo seu feedback!")
        msg.add_alternative(corpo, subtype="html")

        print(">>> [3] Corpo do e-mail configurado")

        print(">>> [4] Tentando conectar ao SMTP...")

        with smtplib.SMTP("smtp.gmail.com", 587) as smtp:
            print(">>> [5] Conectado ao servidor SMTP")

            smtp.starttls()
            print(">>> [6] TLS iniciado")

            smtp.login("beautybarreto01@gmail.com", GMAIL_PASSWORD)
            print(">>> [7] Login realizado com sucesso")

            smtp.send_message(msg)
            print(">>> [8] E-MAIL ENVIADO COM SUCESSO!!!")

    except Exception as e:
        print("!!! ERRO NO ENVIO DE E-MAIL:", e)

def on_message(channel, method, properties, body):
    try:
        data = json.loads(body.decode())
        console.rule("[bold green] Novo feedback recebido")
        console.print(JSON.from_data(data))

        feedback_id = salvar_feedback(data)
        if feedback_id:
            console.print(f"[bold blue] Feedback salvo com ID {feedback_id}.")
        else:
            console.print("[red] Falha ao salvar feedback.")

    except json.JSONDecodeError:
        console.print(f"[red] Erro ao decodificar JSON:[/red] {body}")
    finally:
        channel.basic_ack(delivery_tag=method.delivery_tag)

def main():
    params = pika.URLParameters(RABBITMQ_URL)
    connection = pika.BlockingConnection(params)
    channel = connection.channel()
    channel.queue_declare(queue=QUEUE_NAME, durable=True)

    console.print(f" Aguardando feedbacks da fila [bold cyan]{QUEUE_NAME}[/bold cyan]...\n")
    channel.basic_consume(queue=QUEUE_NAME, on_message_callback=on_message)

    try:
        channel.start_consuming()
    except KeyboardInterrupt:
        console.print("\n🛑 Interrompido pelo usuário.")
        channel.stop_consuming()
    finally:
        connection.close()

if __name__ == "__main__":
    main()