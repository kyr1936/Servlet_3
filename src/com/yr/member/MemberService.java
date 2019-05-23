package com.yr.member;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.yr.action.Action;
import com.yr.action.ActionForward;
import com.yr.mupload.MemUploadDAO;
import com.yr.mupload.MemUploadDTO;
import com.yr.util.DBConnector;

public class MemberService implements Action{
	private MemberDAO memberDAO;
	private MemUploadDAO memUploadDAO;
	
	public MemberService() {
		memberDAO = new MemberDAO();
		memUploadDAO = new MemUploadDAO();
	
	}
	
	
	@Override
	public ActionForward check(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		String path="../WEB-INF/views/member/memberCheck.jsp";
		actionForward.setCheck(true);
		actionForward.setPath(path);
		
		return actionForward;
	}

	@Override
	public ActionForward list(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		
		return actionForward;
	}

	@Override
	public ActionForward select(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		MemberDTO memberDTO = null;
		boolean check = true;
		String path = "../WEB-INF/views/member/memberCheck.jsp";
		
		try {
			memberDTO = memberDAO.memberLogin(memberDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(memberDTO==null) {
			check=false;
			path="./memberLogin";
		} else {
			request.setAttribute("message", "Login Fail");
			request.setAttribute("path", "./memberLogin");
			check=false;
			path = "../WEB-INF/views/common/result.jsp";
		}
		
		
		
		actionForward.setCheck(check);
		actionForward.setPath(path);
		
		return actionForward;
	}

	@Override
	public ActionForward insert(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		
		String method = request.getMethod();
		boolean check = true;
		String path = "../WEB-INF/views/member/memberJoin.jsp";
		
		if(method.equals("POST")) {
			MemberDTO memberDTO = new MemberDTO();
			String saveDirectory = request.getServletContext().getRealPath("upload");
			int maxPostSize = 1024*1024*10;
			String encoding = "UTF-8";
			MultipartRequest multi = null;
			
			try {
				multi = new MultipartRequest(request, saveDirectory, maxPostSize, encoding, new DefaultFileRenamePolicy());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			String fileName = multi.getFilesystemName("f1");
			String oName = multi.getOriginalFileName("f1");
			
			MemUploadDTO memUploadDTO = new MemUploadDTO();
			
			memUploadDTO.setFname(fileName);
			memUploadDTO.setOname(oName);
			
			
			memberDTO.setId(multi.getParameter("id"));
			memberDTO.setPw(multi.getParameter("pw"));
			memberDTO.setName(multi.getParameter("name"));
			memberDTO.setEmail(multi.getParameter("email"));
			memberDTO.setPhone(multi.getParameter("phone"));
			memberDTO.setAge(Integer.parseInt(multi.getParameter("age")));
			
			
			int result = 0;
			
			Connection con =  null;
			try {
				con = DBConnector.getConnect();
				con.setAutoCommit(false);
				
				memUploadDTO.setId(memberDTO.getId());
				
				result = memberDAO.memberJoin(memberDTO, con);
				
				System.out.println("result1 : " + result);
				result= memUploadDAO.insert(memUploadDTO, con);
				
				if(result<1) {
					throw new Exception();
				}

				con.commit();
						
						
			} catch (Exception e) {
				result=0;
				try {
				
					con.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				e.printStackTrace();
			} finally {
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("result : " + result);
			
			if(result>0) {
				check=false;
				path= "../index.do";
			} else {
				request.setAttribute("message", "Join Fail");
				request.setAttribute("path", "./memberCheck");
				check=true;
				path = "../WEB-INF/views/common/result.jsp";
			}
		}
		
		actionForward.setCheck(check);
		actionForward.setPath(path);
		
		return actionForward;
	}

	@Override
	public ActionForward update(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		
		return actionForward;
	}

	@Override
	public ActionForward delete(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
