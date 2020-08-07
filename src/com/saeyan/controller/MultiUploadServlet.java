package com.saeyan.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/upload2.do")
public class MultiUploadServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String savePath = "upload";
		int uploadFileSizeLimit = 50 * 1024 * 1024;
		String encType = "UTF-8";
		ServletContext context = getServletContext();
		String uploadFilePath = context.getRealPath(savePath);
		System.out.println(uploadFilePath);
		try {
			MultipartRequest multi= new MultipartRequest(
					request, 
					uploadFilePath, 
					uploadFileSizeLimit,
					encType,
					new DefaultFileRenamePolicy());
			Enumeration files= multi.getFileNames();
			
			while(files.hasMoreElements()){//파일이 업로드되지 않을때
				String file=(String) files.nextElement();
				String file_name = multi.getFilesystemName(file);
				//중복된 파일을 업로드할때 파일명이 바뀐다.
				String ori_file_name = multi.getOriginalFileName(file);
				out.println("<br> 업로드된 파일명 : "+file_name);
				out.println("<br> 원본 파일명 : "+ori_file_name);
				out.println("<hr>");
			}
		}catch(Exception e) {
			System.out.print("예외발생 : "+e);
			
		}//catch
	}

}
