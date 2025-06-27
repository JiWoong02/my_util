package bimconstruct.service.file;

import bimconstruct.model.bimManage.BimFileDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface FileService {

    String saveFile(String filePath, MultipartFile file) throws IOException;

    BimFileDTO saveBimFile(String basicFolderPath, MultipartFile file) throws IOException;

    File getFile(String filePath);

    File get3DFile(int fileNo);

    void deleteFile(String filePath);

    void deleteFolderBybizNo(String bizNo);

    String getFileExtension(MultipartFile file);

    void copyAllDirectory(String sourcePath, String destPath);

    void copyPartFile(String filePath, String destPath);
}
