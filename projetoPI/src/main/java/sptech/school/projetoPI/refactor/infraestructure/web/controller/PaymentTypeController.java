package sptech.school.projetoPI.refactor.infraestructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.payment_type.GetPaymentTypeResponseDto;

@RestController
@RequestMapping("/metodos-de-pagamento")
@RequiredArgsConstructor
public class PaymentTypeController {

  @GetMapping("/{id}")
  public ResponseEntity<GetPaymentTypeResponseDto> getPaymentType(@PathVariable Integer id) {

      if (id == 1) {
        GetPaymentTypeResponseDto getPaymentTypeResponseDto = new GetPaymentTypeResponseDto(
                id,
                "PIX",
                "sexta-feira, PlayTV"
        );

          return ResponseEntity.status(200).body(getPaymentTypeResponseDto);
      } else if (id == 2) {
          GetPaymentTypeResponseDto getPaymentTypeResponseDto = new GetPaymentTypeResponseDto(
                  id,
                  "Crédito",
                  "tá devendo?"
          );

          return ResponseEntity.status(200).body(getPaymentTypeResponseDto);
      } else if (id == 3) {
          GetPaymentTypeResponseDto getPaymentTypeResponseDto = new GetPaymentTypeResponseDto(
                  id,
                  "Débito",
                  "boa, testou tudo"
          );

          return ResponseEntity.status(200).body(getPaymentTypeResponseDto);
      } else {
          return ResponseEntity.status(400).build();
      }


  }

}
