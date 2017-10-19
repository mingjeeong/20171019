package notice.controller;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import notice.model.dao.LoginDao;
import notice.model.dao.NoticeDao;
import notice.model.dto.NoticeDto;
@Component("controller")
public class Controller {

	@Autowired
	private LoginDao loginDao;
	
	@Resource(name="noticeDao")
	private NoticeDao noticeDao;
	
	public Controller() {
	}

	public LoginDao getLoginDao() {
		return loginDao;
	}

	public void setLoginDao(LoginDao loginDao) {
		this.loginDao = loginDao;
	}
	
	public void setNoticeDao(NoticeDao noticeDao) {
		this.noticeDao=noticeDao;
	}
	
	

	public String deleteAction(int num) {
		
		String msg = null;
		try {
			noticeDao.noticeDelete(num);

            msg = "삭제처리 되었습니다.";
		
        }catch(Exception e){
			msg="삭제시 오류 발생했습니다.";
		}
		return msg;
	}
	
	 public ArrayList<NoticeDto> listAction() {
		  ArrayList<NoticeDto> notice = null;

		  try{
				notice = noticeDao.noticeList();
			}catch(Exception e){
			}
		  return notice;
		}
	
	 public String loginAction(String user, String pass) {

		  String msg=null;
			try{
	            //boolean check=new LoginDAO().loginCheck(user,pass);
	            boolean check = loginDao.loginCheck(user, pass);
					if(check) {
					msg= "로그인 되었습니다.";
	                // result.jsp view 이름을 직접적으로 명시하면 view 관리가힘들다. 이름을 변경해서 써라-> struts-config.xml 에 정의
					// ActionForward
				}

			}catch(Exception e){
				msg= "error";
			}
			return msg;
		}
	 
		public NoticeDto modifyAction(int num) {
			NoticeDto n = null;

			try {
				n = noticeDao.noticeView(num);
				// nextPage="notice/NoticeView.jsp";
			} catch (Exception e) {
			}
			return n;
		}
		
		public String saveAction(String writer, String title, String cont) {

			try{
				NoticeDto dto = new NoticeDto(0,writer,null,title,cont);
				
	  			noticeDao.noticeInsert(dto);
				return "정상적으로 저장 되었습니다.";
	        }catch(Exception e){
				return "error";
			}
		}
		
		  public NoticeDto updateAction(NoticeDto dto) {

			  NoticeDto nb = null;
				try{
		  			nb = new NoticeDto();
		  			
		  			if(noticeDao.noticeUpdate(dto)==1) {
		  				return nb;	  				
		  			}
		        }catch(Exception e){
				}
				return nb;
		}     	
		  
		  public NoticeDto viewAction (int num) {
				NoticeDto n = null;
			  try{
		  			n = noticeDao.noticeView(num);
				}catch(Exception e){
				}
			  return n;
			}
}
