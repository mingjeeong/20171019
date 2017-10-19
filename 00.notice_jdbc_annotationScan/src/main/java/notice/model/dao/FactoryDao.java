package notice.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component("factoryDao")
public class FactoryDao {

	private Set dbserver;
	
	private static String driver;
	private static String url;
	private static String username;
	private static String password;
	
	private FactoryDao() {}
	
	//싱글톤 패턴
	private static FactoryDao factory ;
		
	public static FactoryDao getInstance() {
		if(factory == null) {
			factory = new FactoryDao();
		}
		return factory;
	}
	
	private FactoryDao(Set dbserver) {
		System.out.println(dbserver);
		Iterator<String> iter =dbserver.iterator();
		driver =(String)iter.next();
		url=(String)iter.next();
		username=(String)iter.next();
		password=(String)iter.next();
		System.out.println(driver+"|"+url+"|"+username+"|"+password);
//		driver = dbserver.get("driver").toString();
//		url = dbserver.get("url").toString();
//		username = dbserver.get("username").toString();
//		password = dbserver.get("password").toString();
		this.dbserver = dbserver;
		try {
			System.out.println(driver+"<<<");
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 오류");
		}

		
	}
	public static FactoryDao getInstance(Set dbserver) {
		if(factory == null){
			factory = new FactoryDao(dbserver);
		}
		return factory;
	}
	
	
	public Connection getConnection() {
		try {
			
			return DriverManager.getConnection(url, username, password);
			
		} catch (SQLException e) {

			e.printStackTrace();
			System.out.println("DB서버 연결오류");
		}
		return null;
	}
	
	//cud
	/**
	 * cud 자원해제 메서드
	 * @param conn
	 * @param stmt
	 */
	public void close(Connection conn , Statement stmt) {

		
		close(conn,stmt,null);
	}
	
	
	/**
	 * select 자원해제
	 * @param conn
	 * @param stmt
	 * @param rs
	 */
	public void close(Connection conn , Statement stmt, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {	
				conn.close();
			}
		} catch (SQLException e) {

			e.printStackTrace();
			System.out.println("error : 자원해제 오류");
		}
	}
	
	
}
