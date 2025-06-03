package com.Itauchallenge.transaction.statistics.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Itauchallenge.transaction.statistics.dto.StatisticDto;
import com.Itauchallenge.transaction.statistics.service.TransactionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/statistic")
@Tag(name = "Statistic", description = "Rest endpoint to return statistics")
public class StatisticController {

	private TransactionService transactionService;
		
	public StatisticController(TransactionService transactionService) {
		
		this.transactionService = transactionService;
	}
	@GetMapping
	@Operation(summary = "Get transaction statistics", description = "Returns statistical data (sum, average, min, max, count) for transactions received in the last N seconds.",
	tags = {"Statistic"},responses = {@ApiResponse(description = "OK", responseCode = "200", content = @Content),
				 @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
				 @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
				 @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)})
	public ResponseEntity<StatisticDto> getStatistics(@RequestParam(value = "seconds", defaultValue = "60") int seconds){
		
		ResponseEntity<StatisticDto> statistics = transactionService.getStatistics(seconds);
		return statistics;
	}
}
