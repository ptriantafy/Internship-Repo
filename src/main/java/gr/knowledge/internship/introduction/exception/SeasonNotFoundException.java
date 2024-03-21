package gr.knowledge.internship.introduction.exception;

import jakarta.persistence.EntityNotFoundException;

@SuppressWarnings("serial")
public class SeasonNotFoundException extends EntityNotFoundException {
	public SeasonNotFoundException(String givenSeason) {
		super("Season: " + givenSeason + " not found. Available seasons: winter, spring, summer, autumn,");
	}
}
