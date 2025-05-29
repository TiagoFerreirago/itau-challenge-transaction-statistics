package com.Itauchallenge.transaction.statistics.dto;

import java.util.DoubleSummaryStatistics;
import java.util.Objects;

public class StatisticsDto {

	private Long count;
	private Double sum;
	private Double avg;
	private Double min;
	private Double max;
	
	public StatisticsDto(DoubleSummaryStatistics statistics) {
		this.count = statistics.getCount();
		this.sum = statistics.getSum();
		this.avg = statistics.getAverage();
		this.min = statistics.getMin();
		this.max = statistics.getMax();
	}

	public Long getCount() {
		return count;
	}

	public Double getSum() {
		return sum;
	}

	public Double getAvg() {
		return avg;
	}

	public Double getMin() {
		return min;
	}

	public Double getMax() {
		return max;
	}

	@Override
	public int hashCode() {
		return Objects.hash(avg, count, max, min, sum);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StatisticsDto other = (StatisticsDto) obj;
		return Objects.equals(avg, other.avg) && Objects.equals(count, other.count) && Objects.equals(max, other.max)
				&& Objects.equals(min, other.min) && Objects.equals(sum, other.sum);
	}
	
	
}
