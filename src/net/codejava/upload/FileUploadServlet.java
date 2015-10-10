package net.codejava.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

/**
 * A Java servlet that handles file upload from client.
 *
 * @author www.codejava.net
 */
public class FileUploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // location to store file uploaded
    private static final String UPLOAD_DIRECTORY = "/tmp/upload/";

    // upload settings
    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3; // 3MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

    /**
     * Upon receiving file upload submission, parses the request to read upload
     * data and saves the file on disk.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// checks if the request actually contains upload file
	if (!ServletFileUpload.isMultipartContent(request)) {
	    // if not, we stop here
	    PrintWriter writer = response.getWriter();
	    writer.println("Error: Form must have enctype=multipart/form-data.");
	    writer.flush();
	    return;
	}

	// Create a new file upload handler
	ServletFileUpload upload = new ServletFileUpload();

	// Parse the request
	try {
	    FileItemIterator iter = upload.getItemIterator(request);
	    while (iter.hasNext()) {
		FileItemStream item = iter.next();
		String name = item.getFieldName();
		InputStream inputStream = item.openStream();
		if (item.isFormField()) {
		    System.out.println("Form field " + name + " with value " + name.toString() + " detected.");
		} else {
		    File uploadDir = new File(UPLOAD_DIRECTORY);
		    if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		    }

		    String fileName = item.getName();
		    System.out.println("File field " + name + " with file name " + fileName + " detected.");
		    File inputFile = new File(fileName);
		    File outputFile = new File(uploadDir, inputFile.getName());
		    OutputStream outputStream = new FileOutputStream(outputFile);
		    boolean closeOutputStream = true;
		    // Process the input stream
		    Streams.copy(inputStream, outputStream, closeOutputStream);
		    String message = "file successfully created " + outputFile.getAbsolutePath();
		    System.out.println(message);
		    request.setAttribute("message", "Upload has been done successfully!");

		}
	    } // redirects client to message page
	} catch (Exception ex) {
	    ex.printStackTrace();
	    request.setAttribute("message", "There was an error: " + ex.getMessage());
	}
	getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
    }
}
