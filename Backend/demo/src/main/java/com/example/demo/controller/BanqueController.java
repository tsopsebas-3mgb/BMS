package com.example.demo.controller;

import com.example.demo.model.Compte;
import com.example.demo.model.LoginReq;
import com.example.demo.model.LoginReturn;
import com.example.demo.service.Banque;
import com.example.demo.model.Transaction;
import com.example.demo.model.TransactionReq;
import com.example.demo.model.Depot;
import com.example.demo.model.Retrait;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/banque")
public class BanqueController {

    private final Banque banque;

    public BanqueController(Banque banque) {
        this.banque = banque;
    }

	@GetMapping("/comptes/{id}/transactions")
public ResponseEntity<?> getTransactions(@PathVariable String id) {
    Compte compte = banque.getCompteById(id);
    if (compte == null) {
        return ResponseEntity.status(404).body("Account not found");
    }
    return ResponseEntity.ok(compte.getTransactions());
}


	@PostMapping("/comptes/{id}/transactions/initiate")
public ResponseEntity<?> initiateTransaction(@PathVariable String id, @RequestBody TransactionReq req) {
    try {
        Transaction transaction = banque.initiateTransaction(req.getAccountNumber(),req.getDestinatorAccountNumber(), req.getAmount(), req.getType());
        return ResponseEntity.ok(transaction);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.status(500).body("Transaction failed");
    }
}

    @PostMapping("/comptes/login")
    public ResponseEntity<?> login(@RequestBody LoginReq req) {
        LoginReturn result = banque.logIn(req.getNum(), req.getTel(), req.getPass());

        if (result.getLog() == false) {
            return ResponseEntity.status(401).body("Account not found");
        }
            return ResponseEntity.ok(result);
    }

        @PostMapping("/comptes/login/{num}")
public ResponseEntity<Compte> fetchData(@RequestBody LoginReturn result, @PathVariable String num) {
    // Vérifie si le login est valide
    if (!result.getLog()) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    // Vérifie que l'accountNumber du body correspond bien à l'URL
    if (!num.equals(result.getAccountNumber())) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // Récupère le compte
    Compte c = banque.getCompteById(num);
    if (c == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    return ResponseEntity.ok(c);
}



    @PostMapping("/comptes")
    public ResponseEntity<Compte> creerCompte(@RequestBody Compte compte) {
        Compte c = banque.creerCompte(
                compte.getNom(),
                compte.getNiu(),
                compte.getTelephone(),
                compte.getType(),
                compte.getPassword()
        );

        return ResponseEntity.ok(c);
    }

    @GetMapping("/comptes")
    public ResponseEntity<List<Compte>> getComptes() {
        return ResponseEntity.ok(banque.getComptes());
    }
}