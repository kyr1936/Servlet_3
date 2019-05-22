package com.yr.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnector {
	//Connection
	//메서드명 getConnect()
	//DB 연결 객체를 리턴(Connection)
	//클래스메서드로 생성
	
	public static Connection getConnect() throws Exception {
		String user = "user03";
		String password = "user03";
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		String driver = "oracle.jdbc.driver.OracleDriver";

		Class.forName(driver);
		Connection con = DriverManager.getConnection(url, user, password);
		
		return con;
	
	}
	
	// 연결 끊는 메서드
	
	public static void disConnect(Connection con) throws SQLException {
		con.close();
		
	}
	public static void disConnect(Connection con, PreparedStatement st) throws Exception{
		con.close();
		st.close();
	}
	public static void disConnect(Connection con, PreparedStatement st, ResultSet rs) throws Exception{
		con.close();
		st.close();
		rs.close();
	}
	
	
	
}
