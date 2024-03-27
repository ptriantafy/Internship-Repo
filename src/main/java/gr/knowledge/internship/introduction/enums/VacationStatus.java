package gr.knowledge.internship.introduction.enums;

public enum VacationStatus {
	PENDING, APPROVED, REJECTED;

	/**
	 * Retrieves the enum value from the given string.
	 *
	 * @param status the string representation of the enum value
	 * @return the corresponding VacationStatus enum
	 */
	public static VacationStatus resolveOfEum(String status) {
		return VacationStatus.valueOf(status.toUpperCase());
	}
}
