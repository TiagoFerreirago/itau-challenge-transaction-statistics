package com.Itauchallenge.transaction.statistics.service;

import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Itauchallenge.transaction.statistics.dto.StatisticDto;
import com.Itauchallenge.transaction.statistics.dto.TransactionDto;
import io.micrometer.core.annotation.Timed;

@Service
public class TransactionService {
	
	private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

	private Queue<TransactionDto> transactionQueue = new ConcurrentLinkedQueue<>();
	
	public ResponseEntity<Void> createTransaction(TransactionDto transaction){
		
		
			if (transaction == null || transaction.getValue() == null || transaction.getDateTime() == null) {
			    return ResponseEntity.unprocessableEntity().build();
			}
			
			if(transaction.getDateTime().isAfter(OffsetDateTime.now()) || transaction.getValue() < 0) {
				return ResponseEntity.unprocessableEntity().build();
			}
				
			logger.info("Transação recebida com sucesso: valor={}, data={}",transaction.getValue(),transaction.getDateTime());

			transactionQueue.offer(transaction);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		
	}
	
	public ResponseEntity<Void> deleteAllTransactions(){
		
		
			logger.info("Todas as transações foram excluídas com sucesso");
			transactionQueue.clear();
			
			return ResponseEntity.ok().build();
	}
	
	@Timed(value = "execution.time")
	public ResponseEntity<StatisticDto> getStatistics(int seconds) {
		
			if (seconds <= 0) {
				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
			}
			
			logger.info("Retornando todas as estatísticas");	
			DoubleSummaryStatistics  summaryStatistics = transactionQueue.stream()
					.filter(t -> !t.getDateTime().isBefore(OffsetDateTime.now()
					.minusSeconds(seconds))).mapToDouble(TransactionDto::getValue).summaryStatistics();
			StatisticDto dtoStatistics = new StatisticDto(summaryStatistics);
			return ResponseEntity.ok().body(dtoStatistics);
	}
		
}
