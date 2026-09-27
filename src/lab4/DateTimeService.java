package lab4;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeService {
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd MM yyyy");
	private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH mm ss");

	public static String processCommand(String command) {
		LocalDateTime now = LocalDateTime.now();
		switch (command.toUpperCase()) {
		case "DATE":
			return now.format(DATE_FORMAT);
		case "TIME":
			return now.format(TIME_FORMAT);
		case "DATETIME":
			return now.format(DATE_FORMAT) + " " + now.format(TIME_FORMAT);
		default:
			return "INVALID_COMMAND";
		}
	}
}
