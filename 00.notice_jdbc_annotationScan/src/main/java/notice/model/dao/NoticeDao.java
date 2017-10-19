/* 
        Singleton Pattern을 적용 :
        1.  Singleton instance에 대한 멤버변수 private static
        2.  getInstance() : Singleton public static 메서드 제공
        3.  생성자 private
*/

package notice.model.dao;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.*;
import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import notice.model.dto.NoticeDto;

import javax.annotation.Resource;
import javax.naming.InitialContext;
/**
 *<pre>
 * Database의 Notice 정보를 접근하기위한 CRUD처리 기능을 담고있는 클래스
 *</pre>
 *
 */
@Component("noticeDao")
public class NoticeDao { // implements Dao {
	
	@Resource(name="factoryDao")
	private FactoryDao factory ;
    
    /* 
        Singleton Pattern을 적용 :
        1.  Singleton instance에 대한 멤버변수 private static
        2.  getInstance() : Singleton public static 메서드 제공
        3.  생성자 private
    */
    // 1.
    private static NoticeDao dao;

    // 2.
    public static NoticeDao getInstance() {
        if (dao == null) {
            dao = new NoticeDao();
        }
        return dao;
    }
    public static NoticeDao getInstance(FactoryDao factory) {
    	  if (dao == null) {
              dao = new NoticeDao(factory);
          }
		return dao;
    	
    	
    }
    
    public void setFactory(FactoryDao factory) {
    	this.factory=factory;
    }

    /**
    * Default constructor
    * DataBase 연동을 위해 DataSource 객체를 검색합니다.
    */
    // 3.
	private NoticeDao() {
	
	}

    public NoticeDao(FactoryDao factory) {
    	this.factory = factory;
	}
	/**
    * 공지사항 정보를 DB에 입력합니다.
    * 공지사항 입력을 위해서 기존에 저장된 공지사항의 번호 중 가장 큰값을 구합니다.
    * 가장 큰값에 1을 더해 공지사항의 번호를 구하고, 공지사항 레코드가 존재하지 않을 경우 번호를 1로 설정합니다.
    * 구해진 번호와 매개변수로 입력된 id, title, content, 그리고 오늘날짜를 구하여 Notice 테이블에 한 레코드를 추가합니다.
    * @param writer 작성자
    * @param title  제목
    * @param content  공지사항 내용
    * @return void
    */
	public void noticeInsert(NoticeDto dto){
		Statement stmt = null;
		PreparedStatement pstmt = null;
		Connection con = null;
		try {
			int seqnum;
			con = factory.getConnection();
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT max(num) FROM notice");
			if(rs.next())   {
				seqnum = rs.getInt(1) + 1;
			}else{
				seqnum = 1;
		}
		pstmt = con.prepareStatement("INSERT INTO notice VALUES(?,?,?,?,?)");
		pstmt.setInt(1, seqnum);
		pstmt.setString(2, dto.getWriter());
		Date dt=new Date();
		SimpleDateFormat sd=new SimpleDateFormat();
		String date=sd.getDateInstance().format(dt);
		pstmt.setString(3, date);
		pstmt.setString(4,dto.getTitle());
 		pstmt.setString(5, dto.getContent());
		pstmt.executeUpdate();
		}catch(Exception e)     {
			e.printStackTrace();
		}finally{
			try { 
				if(stmt != null)stmt.close();
				if(pstmt != null)pstmt.close(); 
				if(con != null)con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 공지사항 내용을 수정합니다
	 * @param num 글번호
	 * @param content 내용
	 */
	public int noticeUpdate(NoticeDto dto){
		PreparedStatement pstmt = null;
		Connection con = null;
		try {
			con = factory.getConnection();
			pstmt = con.prepareStatement("update notice set title=?, cont=? where num=?");
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setInt(3, dto.getNum());
			return pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try { 
				if(pstmt != null)pstmt.close(); 
				if(con != null)con.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	
/**
* DB를 연동하여 공지사항 정보를 Notice 객체에 저장하고 객체들의 목록을 ArrayList 형태로 리턴합니다.
* @return ArrayList : 공지사항 정보 목록
*/
	public ArrayList<NoticeDto> noticeList(){
		Connection con = null;
		ArrayList<NoticeDto> arr = new ArrayList<>();
		Statement stmt = null;
		try {
    		con = factory.getConnection();
			stmt = con.createStatement();
			String query = "SELECT num,title,writer,indate FROM Notice ORDER BY num asc";
			//SELECT num,title,writer,indate FROM Notice ORDER BY num asc
			ResultSet myResult = stmt.executeQuery(query);

			while (myResult.next()) {
				NoticeDto n=new NoticeDto();
				n.setNum(myResult.getInt("num"));
				n.setTitle(myResult.getString("title"));
				n.setWriter(myResult.getString("writer"));
				n.setInDate(myResult.getString("inDate"));
				arr.add(n);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally { 
			try { 
				if(stmt != null)stmt.close();
				if(con != null)con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		 return arr; 
	}

    /**
    * 매개변수로 전달된 번호의 공지사항 정보를 검색하여 Notice 형태로 리턴합니다.
    * @param num 검색하고자 하는 공지사항 번호
    * @return Notice : 검색된 공지사항정보(Notice)
    */
	public NoticeDto noticeView(int num){
		Connection con = null;
		Statement stmt=null;
		NoticeDto n = new NoticeDto();
		try {
    		con = factory.getConnection();
			stmt = con.createStatement();
			String query = "SELECT * FROM Notice where num="+num;
			ResultSet myResult = stmt.executeQuery(query);
			if (myResult.next()) {
				n.setNum(myResult.getInt("num"));
				n.setWriter(myResult.getString("writer"));
				n.setInDate(myResult.getString("inDate"));
				n.setTitle(myResult.getString("title"));
				n.setContent(myResult.getString("cont"));
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally { 
			try { 
				if(stmt != null)stmt.close();
				if(con != null)con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return n; 
	}

    /**
    * 매개변수로 전달된 번호의 공지사항 정보를 삭제합니다.
    * @param num 삭제하고자 하는 공지사항 번호
    * @return void : 
    */
	public void noticeDelete(int num) {
		Connection con = null;
		Statement stmt=null;

        try {
    		con = factory.getConnection();
			stmt = con.createStatement();
			String query = "delete from Notice where num="+num;
			stmt.executeUpdate(query);

        } catch (SQLException e) {
			System.out.println(e);
		} finally { 
			try { 
				if(stmt != null)stmt.close();
				if(con != null)con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}

//    /**
//    * NoticeDAO 클래스의 단독 테스트를 위한 메소드
//    */
//	public static void main(String[] a){
//		NoticeDao dao=new NoticeDao();
//		dao.noticeInsert("diana","test","test...");
//		dao.noticeView(1);
//		dao.noticeList();
//	}
}
