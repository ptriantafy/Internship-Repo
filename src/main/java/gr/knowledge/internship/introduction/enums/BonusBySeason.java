package gr.knowledge.internship.introduction.enums;

import java.math.BigDecimal;

import gr.knowledge.internship.introduction.exception.SeasonNotFoundException;

public enum BonusBySeason {
	AUTUMN("autumn", new BigDecimal("0.4")), SPRING("spring", new BigDecimal("0.6")),
	SUMMER("summer", new BigDecimal("0.7")), WINTER("winter", new BigDecimal("1.3"));

	/**
	 * Resolves the BonusBySeason enum from a given string input.
	 *
	 * @param input the input string
	 * @return the corresponding BonusBySeason enum
	 * @throws SeasonNotFoundException if the input does not match any season
	 */
	public static BonusBySeason resolveOfEnum(String input) {
		for (BonusBySeason value : BonusBySeason.values()) {
			try {
				if (input.toUpperCase().equals(value.name())) {
					return value;
				}
			} catch (NullPointerException npe) {
				throw new SeasonNotFoundException(input);
			}
		}
		throw new SeasonNotFoundException(input);
	}

	private final BigDecimal rate;

	private final String season;

	/**
	 * Constructor for BonusBySeason enum.
	 *
	 * @param season the season
	 * @param rate   the bonus rate
	 */
	BonusBySeason(String season, BigDecimal rate) {
		this.season = season;
		this.rate = rate;
	}

	/**
	 * Retrieves the bonus rate.
	 *
	 * @return the bonus rate
	 */
	public BigDecimal getRate() {
		return rate;
	}

	/**
	 * Retrieves the season.
	 *
	 * @return the season
	 */
	public String getSeason() {
		return season;
	}
}
