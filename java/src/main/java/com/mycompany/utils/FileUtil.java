package org.mdback.util;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
    public static String getFileExtension(MultipartFile file){
        String fileNm = file.getOriginalFilename();
        int dotIndex = fileNm.lastIndexOf(".");
        return (dotIndex != -1) ? fileNm.substring(dotIndex + 1) : "";
    }
}
