package utilities;

import java.io.File;

/**
 * Utility class providing helper methods for file resolution.
 */
public class FileHelper {

    /**
     * Resolves and returns a {@link File} object based on the given file name or
     * path.
     * 
     * @param fileName the file name or file path to resolve
     * @return a {@link File} object representing the resolved file
     * @throws IllegalArgumentException if {@code fileName} is null or empty
     * @throws RuntimeException         if a pure file name cannot be found in
     *                                  supported locations
     */
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