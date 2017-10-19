package notice.model.dao;

import java.sql.*;
import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.naming.InitialContext;

/**
 *<pre>
 * Database의 Users 정보를 접근하기위한 CRUD처리 기능을 담고있는 클래스
 *</pre>
 *
 */
@Component("loginDao")
public class LoginDao{
	
	@Resource(name="factoryDao")
	private FactoryDao factory;
	
    // Singleton Pattern 적용 클래스
    private static LoginDao dao;
    public static LoginDao getInstance() {
        if (dao == null) {
            dao = new LoginDao();
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
	public LoginDao() {}
	/**
	* loginCheck() method는 전달된 아이디와 패스워드를 DB와 비교하여 확인후 그 결과를 boolean 타입으로 리턴합니다.
	* @param id  로그인한 아이디
	* @param pw  로그인한 패스워드
	* @return boolean : 아이디와 패스워드가 DB에 존재하면 true, 존재하지 않으면 false를 리턴합니다.
	*/	
	public boolean loginCheck(String id,String pw){
		Connection con = null;
		Statement stmt=null;
		boolean check=false;
		try {
			con = factory.getConnection();
			stmt = con.createStatement();
			String query = "Select * from users where id='"+id+"'";
			ResultSet myResult = stmt.executeQuery(query);

			if (myResult.next()) {
			    if(pw.equals(myResult.getString(2)))
						check=true;
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
		return check; 
	}
}