package net.codejava.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static net.codejava.upload.FileUploadConfiguration.*;

public class FileDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String filenameParam = "";
		try {
			filenameParam = request.getParameter("filename");
			String fileType = "application/octet-stream";
			response.setContentType(fileType);

			// Make sure to show the download dialog
			response.setHeader("Content-disposition", "attachment; filename=\"" + filenameParam + "\"");

			// Assume file name is retrieved from database
			// For example D:\\file\\test.pdf

			File my_file = new File(UPLOAD_DIRECTORY + filenameParam);

			// This should send the file to browser
			OutputStream out = response.getOutputStream();
			FileInputStream in = new FileInputStream(my_file);
			byte[] buffer = new byte[4096];
			int length;
			while ((length = in.read(buffer)) > -1) {
				out.write(buffer, 0, length);
			}
			in.close();
			out.flush();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			request.setAttribute("message", "There was an error while downloading file  " + filenameParam + " " + ex.getMessage());
			getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);

		}
	}
}
