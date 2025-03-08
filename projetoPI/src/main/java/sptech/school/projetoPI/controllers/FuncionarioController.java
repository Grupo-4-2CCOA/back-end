package sptech.school.projetoPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.entities.Funcionario;
import sptech.school.projetoPI.repositories.FuncionarioRepository;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @PostMapping
    public ResponseEntity<Funcionario> signFuncionario(@RequestBody Funcionario funcionario) {
        if(funcionario == null) ResponseEntity.status(400).build();

        if(
            funcionarioRepository.existsByCpf(funcionario.getCpf()) ||
            funcionarioRepository.existsByEmail(funcionario.getEmail())
        ) {
            return ResponseEntity.status(409).build();
        }

        funcionario.setId(null);
        return ResponseEntity.status(201).body(funcionarioRepository.save(funcionario));
    }

    @GetMapping
    public ResponseEntity<List<Funcionario>> getAllFuncionarios() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();

        if(funcionarios.isEmpty()) return ResponseEntity.status(204).build();

        return ResponseEntity.status(200).body(funcionarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> getFuncionarioById(@PathVariable Integer id) {
        return ResponseEntity.of(funcionarioRepository.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> updateFuncionarioById(@RequestBody Funcionario funcionario, @PathVariable Integer id) {
        if(!funcionarioRepository.existsById(id)) return ResponseEntity.status(404).build();
        if(funcionario == null) return ResponseEntity.status(400).build();

        funcionario.setId(id);
        return ResponseEntity.status(200).body(funcionario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFuncionarioById(@PathVariable Integer id) {
        if(!funcionarioRepository.existsById(id)) return ResponseEntity.status(404).build();

        return ResponseEntity.status(204).build();
    }
}
