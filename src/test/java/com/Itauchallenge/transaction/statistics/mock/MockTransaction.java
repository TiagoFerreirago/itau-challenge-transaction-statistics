package com.Itauchallenge.transaction.statistics.mock;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import com.Itauchallenge.transaction.statistics.dto.TransactionDto;
import com.Itauchallenge.transaction.statistics.model.Transaction;

public class MockTransaction {

	public Transaction mockTransaction() {
		
		Transaction transaction = new Transaction(20.0, OffsetDateTime.of(LocalDateTime.of(2025, 6, 15, 8, 30, 50), ZoneOffset.UTC));
		return transaction;
	}
	
public TransactionDto mockTransactionDto() {
		
	TransactionDto transactionDto = new TransactionDto(20.0, OffsetDateTime.of(LocalDateTime.of(2025, 5, 10, 18, 50, 10), ZoneOffset.UTC));
		return transactionDto;
	}
}
