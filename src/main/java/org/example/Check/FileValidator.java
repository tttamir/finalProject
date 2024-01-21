package org.example.Check;
import java.io.File;

    public class FileValidator {

        public static boolean isValidFile(File file) {

            return file.exists() && file.isFile() && file.canRead();
        }

        public static boolean isValidFileExtension(File file, String extension) {
            return file.getName().endsWith(extension);
        }
    }
