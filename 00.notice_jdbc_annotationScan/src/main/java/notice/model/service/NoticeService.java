package notice.model.service;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import notice.model.dao.NoticeDao;
import notice.model.dto.NoticeDto;

@Component("noticeService")
public class NoticeService {
	
	@Resource(name="noticeDao")
	private NoticeDao noticeDao;
	
	
	public NoticeService() {
		
	}
	public NoticeService(NoticeDao noticeDao) {
		this.noticeDao = noticeDao;
	}
	
	public ArrayList<NoticeDto> showList(){
		return noticeDao.noticeList();
	}
}
