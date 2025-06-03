package com.Itauchallenge.transaction.statistics.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.OffsetDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.Itauchallenge.transaction.statistics.dto.StatisticDto;
import com.Itauchallenge.transaction.statistics.dto.TransactionDto;
import com.Itauchallenge.transaction.statistics.exception.CustomizedBadRequestException;
import com.Itauchallenge.transaction.statistics.mock.MockTransaction;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class TestTransaction {

	MockTransaction input;
	
	@InjectMocks
	private TransactionService service;
	
	@BeforeEach
	void setUp() throws Exception {
		input = new MockTransaction();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreateTransaction() throws Exception {
		
		TransactionDto transaction = input.mockTransactionDto();
		ResponseEntity<Void> response = service.createTransaction(transaction);
		
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	
	@Test
	void testGetStatistic() throws Exception {
	   
		service.deleteAllTransactions();
		service.createTransaction(new TransactionDto(10.0, OffsetDateTime.now().minusSeconds(45)));
		service.createTransaction(new TransactionDto(15.0, OffsetDateTime.now().minusSeconds(30)));
	    service.createTransaction(new TransactionDto(35.0, OffsetDateTime.now().minusSeconds(30)));

		ResponseEntity<StatisticDto> response = service.getStatistics(60);
		
		assertNotNull(response.getBody());
		assertEquals(3, response.getBody().getCount());
		assertEquals(60.0, response.getBody().getSum());
	    assertEquals(10.0, response.getBody().getMin());
	    assertEquals(35.0, response.getBody().getMax());
	    assertEquals(20.0, response.getBody().getAvg());
	}
	
	@Test
    void testConcurrentTransactions() throws Exception {
		service.deleteAllTransactions();
		int numThreads = 100;
		
		ExecutorService executor = Executors.newFixedThreadPool(numThreads);
		
		for(int i = 0 ; i < numThreads; i++) {
			executor.execute(() -> {
				try {
					service.createTransaction(new TransactionDto(30.0, OffsetDateTime.now().minusSeconds(5)));
				} catch (Exception e) {
					fail("Erro ao criar transação em concorrência: " + e.getMessage());
				}
			});
		}
		
		executor.shutdown();
		assertTrue(executor.awaitTermination(10, TimeUnit.SECONDS));
		
		ResponseEntity<StatisticDto> response = service.getStatistics(60);
		assertNotNull(response.getBody(), "O corpo da resposta não deve ser nulo.");
		assertEquals(numThreads, response.getBody().getCount(), "Número esperado de transações.");
		assertEquals(30 * numThreads, response.getBody().getSum(), "Soma correta dos valores das transações.");
		assertEquals(30, response.getBody().getMax(), "O valor máximo deve ser 30.0.");
		assertEquals(30, response.getBody().getMin(), "O valor mínimo deve ser 30.0.");
		assertEquals(30, response.getBody().getAvg(), "a média deve ser 30.0");
		
	}
	
	@Test
	void testDeleteAllTransactions() throws Exception {
		
		service.createTransaction(new TransactionDto(20.0, OffsetDateTime.now().minusSeconds(20)));
		service.createTransaction(new TransactionDto(40.0, OffsetDateTime.now().minusSeconds(40)));
		
		ResponseEntity<Void> response = service.deleteAllTransactions();
		ResponseEntity<StatisticDto> stats = service.getStatistics(60);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(0 , stats.getBody().getCount());
		
	}
	
	@Test
	void testExpiredTransactionNotIncluded() throws Exception {
	    service.deleteAllTransactions();
	    service.createTransaction(new TransactionDto(30.0, OffsetDateTime.now().minusSeconds(61)));
	    ResponseEntity<StatisticDto> stats = service.getStatistics(60);
	    assertEquals(0, stats.getBody().getCount());
	}

	@Test
	void testCreateTransactionWhenNull() throws Exception {
		

		ResponseEntity<Void> response = service.createTransaction(null);
	    
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());

	}
	
	@Test
	void testGetStatisticWhenNegativeSeconds() throws Exception {
		

		Exception exception = assertThrows(CustomizedBadRequestException.class, () -> {
		
			service.getStatistics(-5);
		});
		
		String exceptionActual = "Não é permitido segundos negativos ou zero";
		String exceptionExpected = exception.getMessage();
	
		assertEquals(exceptionActual, exceptionExpected);
	}
	
	@Test
	void testStatisticsWhenNoTransactions() throws Exception {
		
		service.deleteAllTransactions();
		ResponseEntity<StatisticDto> statistic = service.getStatistics(60);
		
		assertEquals(0, statistic.getBody().getCount());
		assertEquals(0.0, statistic.getBody().getAvg());
		assertEquals(0.0, statistic.getBody().getMax());
		assertEquals(0.0, statistic.getBody().getMin());
		assertEquals(0.0, statistic.getBody().getSum());
	}
	
	@Test
	void testBoundaryTransaction() throws Exception  {
		
		service.deleteAllTransactions();
		service.createTransaction(new TransactionDto(30.0, OffsetDateTime.now().minusSeconds(59)));
		
		ResponseEntity<StatisticDto> statistic =  service.getStatistics(60);
		
		assertEquals(1, statistic.getBody().getCount(), "A transação no limite deve ser contada");
		
	}


}
