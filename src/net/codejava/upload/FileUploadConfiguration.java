package net.codejava.upload;

public class FileUploadConfiguration {

    // location to store file uploaded
    public static String UPLOAD_DIRECTORY = "/tmp/upload/";

    // upload settings
    public static int MEMORY_THRESHOLD = 1024 * 1024 * 3; // 3MB
    public static int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
    public static int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

}
