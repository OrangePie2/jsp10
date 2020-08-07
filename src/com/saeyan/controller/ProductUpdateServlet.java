package com.saeyan.controller;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.*;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.saeyan.dao.ProductDAO;
import com.saeyan.dto.ProductVO;

/**
 * Servlet implementation class ProductUpdateServlet
 */
@WebServlet("/productUpdate.do")
public class ProductUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String code= request.getParameter("code");
		
		ProductDAO pDao=ProductDAO.getInstance();
		ProductVO pVo=pDao.selectProductByCode(code);
		
		request.setAttribute("product", pVo);
		RequestDispatcher dispatcher= request
				.getRequestDispatcher("productUpdate.jsp");
		dispatcher.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		
		ServletContext context = getServletContext();
		String path = context.getRealPath("upload");
		String encType = "UTF-8";
		int sizeLimit = 20 * 1024 * 1024;
		
		MultipartRequest multi= new MultipartRequest(
				request,//request객체
				path,//서버상의 실제 디렉토리
				sizeLimit,//최대 업로드 파일 크기
				encType,//인코딩 방법
				//동일한 이름이 존재하면 새로운 이름이 부여됨
				new DefaultFileRenamePolicy());
		
		String code = multi.getParameter("code");
		String name = multi.getParameter("name");
		int price =Integer.parseInt(multi.getParameter("proce"));
		String description = multi.getParameter("description");
		String pictureUrl = multi.getParameter("pictureUrl");
		if(pictureUrl==null) {
			pictureUrl=multi.getParameter("nonmakeImg");
		}
		
		ProductVO pVo= new ProductVO();
		pVo.setCode(Integer.parseInt(code));
		pVo.setName(name);
		pVo.setPrice(price);
		pVo.setDescription(description);
		pVo.setPictureUrl(pictureUrl);
		
		ProductDAO pDao= ProductDAO.getInstance();
		pDao.insertProduct(pVo);
		
		response.sendRedirect("productList.do");
	}

}
