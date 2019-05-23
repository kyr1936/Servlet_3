package com.yr.notice;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.yr.action.Action;
import com.yr.action.ActionForward;
import com.yr.page.SearchMakePage;
import com.yr.page.SearchPager;
import com.yr.page.SearchRow;
import com.yr.upload.UploadDAO;
import com.yr.upload.UploadDTO;
import com.yr.util.DBConnector;

public class NoticeService implements Action {
	private NoticeDAO noticeDAO;
	private UploadDAO uploadDAO;
	
	public NoticeService() {
		noticeDAO = new NoticeDAO();
		uploadDAO = new UploadDAO();
	}
	
	@Override
	public ActionForward check(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionForward list(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		System.out.println("service");
		int curPage=1;
		try {
		curPage=Integer.parseInt(request.getParameter("curPage"));
		} catch (Exception e) {
		
		}
		String kind = request.getParameter("kind");
		String search = request.getParameter("search");
		
		SearchMakePage s = new SearchMakePage(curPage, kind, search);
		
		//1. row
		SearchRow searchRow = s.makeRow();
		ArrayList<NoticeDTO> ar = null;
		try {
			ar = noticeDAO.selectList(searchRow);
			
		//2. page
			int totalCount = noticeDAO.getTotalCount(searchRow);
			SearchPager searchPager = s.makePage(totalCount);
			
			request.setAttribute("pager", searchPager);
			request.setAttribute("list", ar);
			actionForward.setCheck(true);
			actionForward.setPath("../WEB-INF/views/notice/noticeList.jsp");
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "Server Error");
			request.setAttribute("path", "../index.do");
			actionForward.setCheck(true);
			actionForward.setPath("../WEB-INF/views/common/result.jsp");
			
		}
		
		
		return actionForward;
	}

	@Override
	public ActionForward select(HttpServletRequest request, HttpServletResponse response) {
		NoticeDTO noticeDTO = null;
		UploadDTO uploadDTO = null;
		try {
			int num = Integer.parseInt(request.getParameter("num"));
			noticeDTO = noticeDAO.selectOne(num);
			uploadDTO = uploadDAO.select(num);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		String path ="";
		if(noticeDTO!=null) { 
			request.setAttribute("dto", noticeDTO);
			request.setAttribute("upload", uploadDTO);
			
			path ="../WEB-INF/views/notice/noticeSelect.jsp";
			
		} else {
			request.setAttribute("message", "No Data");
			request.setAttribute("path", "./noticeList");
			path="../WEB-INF/views/common/result.jsp";
		}
		//글이 있으면 출력
		//글이 없으면 삭제되었거나 없는 글입니다 출력
		
		
		ActionForward actionForward = new ActionForward();
		actionForward.setCheck(true);
		actionForward.setPath(path);
		return actionForward;
	}

	@Override
	public ActionForward insert(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		
		String method = request.getMethod(); //get, post
		boolean check = true;
		String path = "../WEB-INF/views/notice/noticeWrite.jsp";
		
		
		if(method.equals("POST")) {
			NoticeDTO noticeDTO = new NoticeDTO();	//
			//1. request를 하나로 합치기
			//파일을 저장할 디스크 경로(C: D:)
			String saveDirectory =request.getServletContext().getRealPath("upload");
			System.out.println(saveDirectory);
			int maxPostSize=1024*1024*10;	// byte단위
			String encoding = "UTF-8";
			MultipartRequest multi = null;
			try {
				multi = new MultipartRequest(request, saveDirectory, maxPostSize, encoding, new DefaultFileRenamePolicy());
			
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
				
			// saveDirectory에 파일이 저장됨
			
			//HDD에 저장된 이름
			String fileName = multi.getFilesystemName("f1");  // 파일의 파라미터 이름	
			//클라이언트가 업로드할 때 파일 이름
			String oName = multi.getOriginalFileName("f1"); 			
			
			UploadDTO uploadDTO = new UploadDTO();
			uploadDTO.setFname(fileName);
			uploadDTO.setOname(oName);
			uploadDTO.setNum(noticeDTO.getNum());
					
			noticeDTO.setTitle(multi.getParameter("title"));
			noticeDTO.setWriter(multi.getParameter("writer"));
			noticeDTO.setContents(multi.getParameter("contents"));

			int result =0; 
			
			Connection con = null;
			try {
				con = DBConnector.getConnect();
				// auto commit 해제
				con.setAutoCommit(false);
				
				int num = noticeDAO.getNum();
				noticeDTO.setNum(num);
				result = noticeDAO.insert(noticeDTO, con);

				uploadDTO.setNum(num);
				result = uploadDAO.insert(uploadDTO, con);
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
			
			if(result>0) {
				check=false;
				path = "./noticeList";
			} else {
				request.setAttribute("message", "Write Fail");
				request.setAttribute("path", "./noticeList");
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
		boolean check = true;
		int result =0;
		
		NoticeDTO noticeDTO = new NoticeDTO();
		
		try {
			result=noticeDAO.update(noticeDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String path="";
		if(result>0) {
			check=false;
			path="./noticeSelect";
		} else {
			request.setAttribute("message", "Update Fail");
			request.setAttribute("path", "./noticeSelect");
			check=true;
			path="../WEB-INF/views/common/result.jsp";
		}
	
		
		actionForward.setCheck(check);
		actionForward.setPath(path);
		return actionForward;
	}

	@Override
	public ActionForward delete(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		int result=0;
		boolean check=true;
		int num=0;
		try {
			num = Integer.parseInt(request.getParameter("num"));
			System.out.println("num: " + num);
			result = noticeDAO.delete(num);
		} catch (Exception e) {
	
			e.printStackTrace();
		}
		System.out.println("result : " + result);
		
		String path="";
		if(result>0) {
			check = false;
			path = "./noticeList";
		} else {
			request.setAttribute("message", "Delete Fail");
			request.setAttribute("path", "./noticeList");
			check = true;
			path="../WEB-INF/views/common/result.jsp";
		}
		
	
		
		
		actionForward.setCheck(check);
		actionForward.setPath(path);
		
		return actionForward;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
}