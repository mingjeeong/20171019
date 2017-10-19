package notice.model.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import notice.model.dao.LoginDao;
import notice.model.dao.NoticeDao;
@Component("loginService")
public class LoginService {
	
	@Resource(name="loginDao")
	private LoginDao loginDao;
	
   public LoginService() {
	   
   }
   
   public  LoginService(LoginDao loginDao) {
	   this.loginDao = loginDao;
   }
	
	public boolean login(String id,String pw) {
		return loginDao.loginCheck(id, pw);
	}
}
