package sptech.school.projetoPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.entities.Pagamento;
import sptech.school.projetoPI.repositories.PagamentoRepository;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @PostMapping
    public ResponseEntity<Pagamento> signPagamento(@RequestBody Pagamento pagamento) {
        if(pagamento == null) ResponseEntity.status(400).build();

//        if(
//                pagamentoRepository.existsByCpf(funcionario.getCpf()) ||
//                pagamento.existsByEmail(funcionario.getEmail())
//        ) {
//            return ResponseEntity.status(409).build();
//        }

        pagamento.setId(null);
        return ResponseEntity.status(201).body(pagamentoRepository.save(pagamento));
    }

    @GetMapping
    public ResponseEntity<List<Pagamento>> getAllPagamentos() {
        List<Pagamento> pagamentos = pagamentoRepository.findAll();

        if(pagamentos.isEmpty()) return ResponseEntity.status(204).build();

        return ResponseEntity.status(200).body(pagamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pagamento> getPagamentoById(@PathVariable Integer id) {
        return ResponseEntity.of(pagamentoRepository.findById(id));
    }
}
