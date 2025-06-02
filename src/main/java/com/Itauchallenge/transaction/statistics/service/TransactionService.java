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

@Service
public class TransactionService {
	
	private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

	private Queue<TransactionDto> transactionQueue = new ConcurrentLinkedQueue<>();
	
	public ResponseEntity<Void> createTransaction(TransactionDto transaction){
		
		try {
			logger.info("Transanção recebida com sucesso: valor={}, data={}",transaction.getValue(),transaction.getDateTime());
			if(transaction.getDateTime().isAfter(OffsetDateTime.now()) || transaction.getValue() < 0) {
				return ResponseEntity.unprocessableEntity().build();
			}
			transactionQueue.offer(transaction);
			return ResponseEntity.status(HttpStatus.CREATED).build();

		}
		catch(Exception e) {
			logger.error("Erro ao receber uma transação: valor={}, data={}",transaction.getValue(),transaction.getDateTime(),e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	public ResponseEntity<Void> deleteAllTransactions(){
		
		try {
			logger.info("Todas as transações foram excluídas com sucesso");
			transactionQueue.clear();
			return ResponseEntity.ok().build();
		}
		catch(Exception e) {
			logger.error("Erro ao excluir as transações", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	public ResponseEntity<StatisticDto> getStatistics(int seconds) {
		
		try {
			logger.info("As estatísticas foram executadas");
			DoubleSummaryStatistics  summaryStatistics = transactionQueue.stream().filter(t -> t.getDateTime().isAfter(OffsetDateTime.now().minusSeconds(seconds))).mapToDouble(TransactionDto::getValue).summaryStatistics();
			StatisticDto dtoStatistics = new StatisticDto(summaryStatistics);
			return ResponseEntity.ok().body(dtoStatistics);
		}
		catch(Exception e) {
			logger.error("Erro ao executar as estatísticas",e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
	
}
