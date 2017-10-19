package notice.model.dao;

import java.util.ArrayList;

import notice.model.dto.NoticeDto;

public interface Dao {

	public void noticeInsert(NoticeDto dto);
	public int noticeUpdate(NoticeDto dto);
	public ArrayList<NoticeDto> noticeList();
	public NoticeDto noticeView(int num);
	public void noticeDelete(int num);
	
}


