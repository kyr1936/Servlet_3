package com.yr.qna;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.yr.action.Action;
import com.yr.action.ActionForward;
import com.yr.page.SearchMakePage;
import com.yr.page.SearchPager;
import com.yr.page.SearchRow;
import com.yr.util.DBConnector;
import com.yr.upload.UploadDAO;
import com.yr.upload.UploadDTO;

public class QnAService implements Action{
	private QnaDTO qnaDTO;
	private QnaDAO qnaDAO;
	private UploadDTO uploadDTO;
	private UploadDAO uploadDAO;
	
	public QnAService() {
		qnaDAO = new QnaDAO();
		qnaDTO = new QnaDTO();
		uploadDAO = new UploadDAO();
		uploadDTO = new UploadDTO();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ActionForward list(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		int curPage =1;	
		
		try {
		curPage = Integer.parseInt(request.getParameter("curPage"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		String kind = request.getParameter("kind");
		String search = request.getParameter("search");
		
		SearchMakePage s = new SearchMakePage(curPage, kind, search);
		
		SearchRow searchRow = s.makeRow();
		ArrayList<QnaDTO> ar = null;
		
		try {
			ar = qnaDAO.list(searchRow);

			int totalCount = qnaDAO.getTotalCount(searchRow);
			
			SearchPager searchPager = s.makePage(totalCount);
			
			request.setAttribute("pager", searchPager);
			request.setAttribute("list", ar);
			actionForward.setCheck(true);
			actionForward.setPath("../WEB-INF/views/qna/qnaList.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "Server Error");
			request.setAttribute("path", "");
			actionForward.setCheck(true);
			actionForward.setPath("../WEB-INF/views/common/result.jsp");
			
		}
		return actionForward;
	}

	@Override
	public ActionForward select(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionForward insert(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		boolean check = true;
		String path = "../WEB-INF/views/qna/qnaWrite.jsp";
		String method=request.getMethod();
		
		if(method.equals("POST")) {
			String saveDirectory = request.getServletContext().getRealPath("upload");
			int maxPostSize = 1024*1024*100;
			Connection con=null;

			try {
				MultipartRequest multipartRequest = new MultipartRequest(request, saveDirectory, maxPostSize,"UTF-8", new DefaultFileRenamePolicy());
				Enumeration<String> e = multipartRequest.getFileNames();
				ArrayList<UploadDTO> ar = new ArrayList<UploadDTO>();
				while(e.hasMoreElements()) {
					String s = e.nextElement();
					String fname=multipartRequest.getFilesystemName(s);
					String oname = multipartRequest.getOriginalFileName(s);
					UploadDTO uploadDTO = new UploadDTO();
					uploadDTO.setFname(fname);
					uploadDTO.setOname(oname);
					ar.add(uploadDTO);
				}
				
				QnaDTO qnaDTO = new QnaDTO();
				qnaDTO.setTitle(multipartRequest.getParameter("title"));
				qnaDTO.setWriter(multipartRequest.getParameter("writer"));
				qnaDTO.setContents(multipartRequest.getParameter("contents"));
				con = DBConnector.getConnect();
				
				//1. 시퀀스번호 가져오기
				int num = qnaDAO.getNum();
				qnaDTO.setNum(num);
				
				con.setAutoCommit(false);
				//2. qna insert
				num = qnaDAO.insert(qnaDTO, con);
				
				//3. upload insert
				for(UploadDTO uploadDTO : ar) {
					uploadDTO.setNum(qnaDTO.getNum());
					num = uploadDAO.insert(uploadDTO, con);
					if(num<1) {
						throw new Exception();
					}
				}
				con.commit();
			} catch (Exception e) {
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
			actionForward.setCheck(false);
			actionForward.setPath("./qnaList");
			
		}
		
		actionForward.setCheck(check);
		actionForward.setPath(path);
				
		return actionForward;
	}

	@Override
	public ActionForward update(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionForward delete(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
