package notice.test;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static void main(String[] args) {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("annotationScan.xml");
		HelloSpring hello = context.getBean("HelloSpring", HelloSpring.class);
		WelcomeString welcome = context.getBean("welcome", WelcomeString.class);
		
		System.out.println(hello);
		System.out.println(welcome);
		
	}
}
