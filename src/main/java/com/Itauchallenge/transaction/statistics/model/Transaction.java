package com.Itauchallenge.transaction.statistics.model;

import java.time.OffsetDateTime;
import java.util.Objects;

public class Transaction {

	private Double value;
	private OffsetDateTime dateTime;
	
	public Transaction(Double value, OffsetDateTime dateTime) {
		this.value = value;
		this.dateTime = dateTime;
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
		Transaction other = (Transaction) obj;
		return Objects.equals(dateTime, other.dateTime) && Objects.equals(value, other.value);
	}
	
}
