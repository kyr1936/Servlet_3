package com.yr.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.yr.page.SearchRow;
import com.yr.util.DBConnector;

public class NoticeDAO {
	public int getTotalCount(SearchRow searchRow)throws Exception{
		int result=0;
		Connection con = DBConnector.getConnect();
		String sql ="select count(num) from notice where "+searchRow.getSearch().getKind()+" like ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%"+searchRow.getSearch().getSearch()+"%");
		ResultSet rs = st.executeQuery();
		rs.next();
		result = rs.getInt(1);
		DBConnector.disConnect(con, st, rs);
		return result;
	}
	
	
	//delete
	public int delete(int num) throws Exception {
		int result=0;
		Connection con = DBConnector.getConnect();
		String sql ="delete notice where num=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, num);
		result = st.executeUpdate();
		DBConnector.disConnect(con, st);		
		return result;
	}
	
	//update
	public int update(NoticeDTO noticeDTO) throws Exception {
		int result=0;
		Connection con = DBConnector.getConnect();
		String sql ="update notice set title=?, contents=? where num=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, noticeDTO.getTitle());
		st.setString(2, noticeDTO.getContents());
		st.setInt(3, noticeDTO.getNum());
		result = st.executeUpdate();
		DBConnector.disConnect(con, st);
		
		return result;
	}
	//sequence
	public int getNum() throws Exception {
		int result=0;
		Connection con = DBConnector.getConnect();
		String sql = "select notice_seq.nextval from dual";		
		PreparedStatement st = con.prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		rs.next();
		result=rs.getInt(1);
		
		DBConnector.disConnect(con, st, rs);
		return result;
		
	}
	
	
	
	//insert
	public int insert(NoticeDTO noticeDTO)throws Exception{
		int result=0;
		Connection con = DBConnector.getConnect();
		String sql ="insert into notice values(?,?,?,?, sysdate,0)";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, noticeDTO.getNum());
		st.setString(2, noticeDTO.getTitle());
		st.setString(3, noticeDTO.getContents());
		st.setString(4, noticeDTO.getWriter());
		result = st.executeUpdate();
		DBConnector.disConnect(con, st);
		return result;
	}
	
	//selectOne
	public NoticeDTO selectOne(int num)throws Exception{
		NoticeDTO noticeDTO= null;
		Connection con = DBConnector.getConnect();
		String sql ="select * from notice where num=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, num);
		ResultSet rs = st.executeQuery();
		if(rs.next()) {
			noticeDTO = new NoticeDTO();
			noticeDTO.setNum(rs.getInt("num"));
			noticeDTO.setTitle(rs.getString("title"));
			noticeDTO.setContents(rs.getString("contents"));
			noticeDTO.setWriter(rs.getString("writer"));
			noticeDTO.setReg_date(rs.getDate("reg_date"));
			noticeDTO.setHit(rs.getInt("hit"));
		}
		
		DBConnector.disConnect(con, st, rs);
		
		return noticeDTO;
	}
	
	//selectList
	public ArrayList<NoticeDTO> selectList(SearchRow searchRow)throws Exception{
		ArrayList<NoticeDTO> ar = new ArrayList<NoticeDTO>();
		Connection con = DBConnector.getConnect();
		String sql="select * from "
				+ "(select rownum R, N.* from "
				+ "(select num, title, writer, reg_date, hit from notice where "+searchRow.getSearch().getKind()+ " like ? order by num desc) N) "
				+ "where R between ? and ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%"+searchRow.getSearch().getSearch()+"%");
		st.setInt(2, searchRow.getStartRow());
		st.setInt(3, searchRow.getLastRow());
		ResultSet rs = st.executeQuery();
		
		while(rs.next()) {
			NoticeDTO noticeDTO = new NoticeDTO();
			noticeDTO.setNum(rs.getInt("num"));
			noticeDTO.setTitle(rs.getString("title"));
			noticeDTO.setWriter(rs.getString("writer"));
			noticeDTO.setReg_date(rs.getDate("reg_date"));
			noticeDTO.setHit(rs.getInt("hit"));
			ar.add(noticeDTO);
			
		}
		DBConnector.disConnect(con, st, rs);
		
		return ar;
	}
}
