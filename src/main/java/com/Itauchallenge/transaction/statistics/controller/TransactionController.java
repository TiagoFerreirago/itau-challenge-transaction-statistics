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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/transaction")
@Tag(name = "Transaction", description = "REST endpoints for creating and deleting transactions")
public class TransactionController {

	private TransactionService transactionService;
	
	public TransactionController(TransactionService transactionService) {
		this.transactionService =transactionService;
	}
	@PostMapping
	@Operation(summary = "Add a new transaction", description = "Receives a transaction and stores it for further statistical analysis.", tags = {"Transaction"},
	responses = {@ApiResponse(description = "Created", responseCode = "201", content = @Content(schema = @Schema(implementation = TransactionDto.class))),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
				@ApiResponse(description = "Unprocessable Entity", responseCode = "422", content = @Content),
				@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)})
	public ResponseEntity<Void> createTransaction(@Valid @RequestBody TransactionDto transaction) throws Exception{
		
		transactionService.createTransaction(transaction);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@DeleteMapping
	@Operation(summary = "Delete all transactions", description = "Removes all stored transactions from memory.", tags = {"Transaction"},
	responses = { @ApiResponse(description = "OK", responseCode = "200", content = @Content),
				  @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
				  @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
				  @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)})
	public ResponseEntity<Void> deleteAllTransactions() throws Exception{
		
		transactionService.deleteAllTransactions();
		return ResponseEntity.ok().build();
	}
}
