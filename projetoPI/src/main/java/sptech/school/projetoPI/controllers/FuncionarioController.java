package sptech.school.projetoPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.entities.Agendamento;
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
        return null;
    }

    @GetMapping
    public ResponseEntity<List<Funcionario>> getAllFuncionarios() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> getFuncionarioById(@PathVariable Integer id) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> updateFuncionarioById(@RequestBody Funcionario funcionario, @PathVariable Integer id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFuncionarioById(@PathVariable Integer id) {
        return null;
    }
}
