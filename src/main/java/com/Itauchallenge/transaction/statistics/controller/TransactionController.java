package com.Itauchallenge.transaction.statistics.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Itauchallenge.transaction.statistics.dto.TransactionDto;
import com.Itauchallenge.transaction.statistics.service.TransactionService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/transaction")
public class TransactionController {

	private TransactionService transactionService;
	
	public TransactionController(TransactionService transactionService) {
		this.transactionService =transactionService;
	}
	@PostMapping
	public ResponseEntity<Void> receiveTransaction(@Valid @RequestBody TransactionDto transaction){
		
		transactionService.createTransaction(transaction);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	@DeleteMapping
	public ResponseEntity<Void> removeAllTransactions(){
		
		transactionService.deleteAllTransactions();
		return ResponseEntity.ok().build();
	}
}
