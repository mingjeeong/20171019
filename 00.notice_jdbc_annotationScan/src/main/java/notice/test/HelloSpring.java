package notice.test;

import org.springframework.stereotype.Component;

@Component("hello")
public class HelloSpring {
	public void print() {
		System.out.println("HelloSpring....");
	}
}
