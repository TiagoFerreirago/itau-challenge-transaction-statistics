package com.Itauchallenge.transaction.statistics.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Itauchallenge.transaction.statistics.dto.StatisticDto;
import com.Itauchallenge.transaction.statistics.service.TransactionService;


@RestController
@RequestMapping("/statistic")
public class StatisticController {

	private TransactionService transactionService;
	
	public StatisticController(TransactionService transactionService) {
		
		this.transactionService = transactionService;
	}
	@GetMapping
	public ResponseEntity<StatisticDto>getStatistics(@RequestParam(value = "seconds", defaultValue = "60") int seconds){
		
		ResponseEntity<StatisticDto> statistics = transactionService.getStatistics(seconds);
		return statistics;
	}
}
