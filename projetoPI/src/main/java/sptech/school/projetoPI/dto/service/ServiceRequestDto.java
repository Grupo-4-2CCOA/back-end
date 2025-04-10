package sptech.school.projetoPI.dto.service;

import jakarta.validation.constraints.*;

public class ServiceRequestDto {
    @Size(max = 80)
    @NotBlank(message = "Preencha o tipo do serviço")
    private String type;

    @Positive(message = "Preço deve ser positivo")
    @DecimalMax(value = "999.99", message = "Preço muito alto")
    @NotNull(message = "Preencha o valor do serviço")
    private Double price;

    @Positive
    @DecimalMax(value = "999.99", message = "Desconto muito alto")
    @NotNull(message = "Preencha o valor do desconto")
    private Double discount;

    @Size(max = 255, message = "Descrição muito longa")
    private String description;

    @Size(max = 255, message = "URL de imagem muito longa")
    private String image;

    @Positive(message = "ID inválido para Categoria")
    private Integer category;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }
}
