package notice.model.dao;

import org.springframework.stereotype.Component;

@Component("util")
public class UtilityDB {
	public void printMessage(String message) {
		System.out.println("Utility#printMessage(): " + message);
	}
}
