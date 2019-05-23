package com.yr.mupload;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.yr.util.DBConnector;

public class MemUploadDAO {
	public int insert(MemUploadDTO memUploadDTO, Connection con) throws Exception {
		int result=0;
		
		String sql = "insert into memupload values(point_seq.nextval,?,?,?)";
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setString(1, memUploadDTO.getId());
		st.setString(2, memUploadDTO.getOname());
		st.setString(3, memUploadDTO.getFname());
		
		result = st.executeUpdate();
		
		st.close();
		return result;
	}
	public MemUploadDTO select(String id) throws Exception {
		MemUploadDTO memUploadDTO = null;
		Connection con = DBConnector.getConnect();
		String sql = "select * from memUpload where id=?";
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setString(1, id);
		ResultSet rs = st.executeQuery();
		
		if(rs.next()) {
			memUploadDTO = new MemUploadDTO();
			
			memUploadDTO.setPnum(rs.getInt("pnum"));
			memUploadDTO.setId(rs.getString("id"));
			memUploadDTO.setOname(rs.getString("oname"));
			memUploadDTO.setFname(rs.getString("fname"));
		}
		DBConnector.disConnect(con, st, rs);
		return memUploadDTO;
		
	}
	
}
