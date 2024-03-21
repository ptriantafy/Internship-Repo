package gr.knowledge.internship.introduction.entity;

import java.math.BigDecimal;

public enum BonusBySeason {
	WINTER("winter", new BigDecimal("1.3")), SPRING("spring", new BigDecimal("0.6")),
	SUMMER("summer", new BigDecimal("0.7")), AUTUMN("autumn", new BigDecimal("0.4"));

	private final BigDecimal rate;
	private final String season;

	BonusBySeason(String season, BigDecimal rate) {
		this.season = season;
		this.rate = rate;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public String getSeason() {
		return season;
	}

}
