package notice.model.dao;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static void main(String[] args) {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("annotationScan.xml");
		FactoryDao factory = context.getBean("factoryDao", FactoryDao.class);
		System.out.println(factory);
		
		
//		UtilityDB util = context.getBean("util", UtilityDB.class);
//		util.printMessage("aaa");
	}
}
