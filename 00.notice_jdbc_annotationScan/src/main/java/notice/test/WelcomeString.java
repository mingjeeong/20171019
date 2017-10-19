package notice.test;

import org.springframework.stereotype.Component;

@Component("welcome")
public class WelcomeString {
	public void print() {
		System.out.println("HelloSpring....");
	}
}
