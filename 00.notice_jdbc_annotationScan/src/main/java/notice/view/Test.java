package notice.view;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import notice.model.dao.FactoryDao;
import notice.model.dao.LoginDao;
import notice.model.dao.NoticeDao;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		ApplicationContext context = new ClassPathXmlApplicationContext("jdbc-beans-config.xml");
		FactoryDao factory = context.getBean("FactoryDao", FactoryDao.class);
		System.out.println(">>>>>"+factory);
		
		LoginDao login = context.getBean("loginDao",LoginDao.class);
		System.out.println(login);
		System.out.println(login);
		NoticeDao notice = context.getBean("noticeDao",NoticeDao.class);
		System.out.println(notice);
		System.out.println(notice);
		
	
	}

}
