package sptech.school.projetoPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.entities.Agendamento;
import sptech.school.projetoPI.entities.Servico;
import sptech.school.projetoPI.repositories.ServicoRepository;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoRepository servicoRepository;

    @PostMapping
    public ResponseEntity<Servico> signServico(@RequestBody Servico servico) {
        return null;
    }

    @GetMapping
    public ResponseEntity<List<Servico>> getAllServicos() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servico> getServicoById(@PathVariable Integer id) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Agendamento> updateServicoById(@RequestBody Servico servico, @PathVariable Integer id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServicoById(@PathVariable Integer id) {
        return null;
    }
}
