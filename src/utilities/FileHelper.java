package utilities;

import java.io.File;

public class FileHelper {

    public static File getFile(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty");
        }

        fileName = fileName.trim().replace("\"", "");

        // check if it's a pure file name (no path separators)
        boolean isPureFileName = !fileName.contains(File.separator)
                && !fileName.contains("/")
                && !fileName.contains("\\");

        if (isPureFileName) {
            // Current directory
            File file = new File(fileName);
            if (file.exists() && file.isFile()) {
                return file;
            }

            // res directory
            File resFile = new File("res", fileName);
            if (resFile.exists() && resFile.isFile()) {
                return resFile;
            }

            // All attempts failed
            throw new RuntimeException("File not found: " + fileName);

        } else {
            // Relative paths are returned as File objects (regardless of existence)
            return new File(fileName);
        }
    }
}