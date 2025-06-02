package com.Itauchallenge.transaction.statistics.dto;

import java.time.OffsetDateTime;
import java.util.Objects;

import com.Itauchallenge.transaction.statistics.model.Transaction;

import jakarta.validation.constraints.NotNull;

public class TransactionDto {

	@NotNull
	private Double value;
	@NotNull
	private OffsetDateTime dateTime;
	
	public TransactionDto() {}
	
	public TransactionDto(Double value, OffsetDateTime dateTime) {
		this.value = value;
		this.dateTime = dateTime;
	}
	
	public TransactionDto(Transaction transaction) {
		this.value = transaction.getValue();
		this.dateTime = transaction.getDateTime();
	}

	public Double getValue() {
		return value;
	}


	public OffsetDateTime getDateTime() {
		return dateTime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateTime, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransactionDto other = (TransactionDto) obj;
		return Objects.equals(dateTime, other.dateTime) && Objects.equals(value, other.value);
	}
	
	
}
