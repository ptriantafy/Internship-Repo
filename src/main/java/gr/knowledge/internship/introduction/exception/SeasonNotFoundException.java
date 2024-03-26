package gr.knowledge.internship.introduction.exception;

@SuppressWarnings("serial")
public class SeasonNotFoundException extends RuntimeException {
	public SeasonNotFoundException(String givenSeason) {
		super("Season: " + givenSeason + " not found. Available seasons: winter, spring, summer, autumn,");
	}
}
