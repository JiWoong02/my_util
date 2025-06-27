package bimconstruct.service.file.impl;

import bimconstruct.model.bimManage.BimFileDTO;
import bimconstruct.service.bimManage.impl.BimManageMapper;
import bimconstruct.service.file.FileService;
import org.apache.commons.io.FileUtils;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    private final String rootPath;
    private static final String SEPARATOR = File.separator;
    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    @Resource
    private BimManageMapper bimManageMapper;
    public FileServiceImpl(EgovPropertyService propertiesService, ServletContext servletContext) {
        //톰캣의 bin폴더 경로 찾기
        String tomcatBinPath = Paths.get(servletContext.getRealPath("/")).getParent().getParent().toString() +SEPARATOR+ "bin" + SEPARATOR;
        this.rootPath = tomcatBinPath + propertiesService.getString("fileUploadPath") + SEPARATOR;
    }

    @Override
    public String saveFile(String basicFolderPath, MultipartFile file) throws IOException {
        //rootPath를 제외한 그 이후 경로를 파라미터로 받아서 파일을 저장하고 저장한 경로를 리턴
        //ex ) bimmanage\20\d002
        if(file.isEmpty()){
            return null;
        }
        String extension = getFileExtension(file);
        String folderPath = rootPath + basicFolderPath;

        File saveFolder = new File(folderPath);
        // 디렉토리 생성
        if (!saveFolder.exists() || saveFolder.isFile()) {
            saveFolder.mkdirs();
        }
        UUID randomUUID = UUID.randomUUID();

        String savePath = folderPath + SEPARATOR + randomUUID + '.' + extension;
        file.transferTo(new File(savePath));
        //rootPath를 제외한 저장한 경로를 리턴
        /*ex )) 저장경로가 D:\\apache-tomcat-9.0.95\\bin\\uploads\\bimmanage\\20\\메타데이터 일때*/
        //bimmanage\20\메타데이터\UUID파일명.확장자    를 리턴
        return basicFolderPath + SEPARATOR + randomUUID + '.' + extension;
    }

    @Override
    //파일을 저장하고 DTO객체에 정보를 담아 반환
    public BimFileDTO saveBimFile(String basicFolderPath, MultipartFile file) throws IOException {
        if(file.isEmpty()){
            return null;
        }

        String extension = getFileExtension(file);
        String folderPath = rootPath+SEPARATOR+"bimmanage"+SEPARATOR+basicFolderPath;
        File saveFolder = new File(folderPath);

        // 디렉토리 생성
        if (!saveFolder.exists() || saveFolder.isFile()) {
            saveFolder.mkdirs();
        }

        BimFileDTO bimFileDTO = new BimFileDTO();
        bimFileDTO.setFileExtn(extension);
        String idFileName = getFileNameWithoutExtension(file)+ "_" +getNowTime() + "." + extension;
        String savePath = folderPath + SEPARATOR + idFileName;
        bimFileDTO.setFileNm(idFileName);
        bimFileDTO.setFilePath(savePath);
        bimFileDTO.setOrgnFileNm(file.getOriginalFilename());
        file.transferTo(new File(savePath));
        return bimFileDTO;
    }

    @Override
    public File getFile(String filePath) {
    	String savePath = rootPath + filePath;

        logger.debug("SavePath : {}", savePath);
        File file = new File(savePath);
        return file;
    }

    @Override
    public File get3DFile(int fileNo) {

    	BimFileDTO bimFileDTO = bimManageMapper.getFilePath(fileNo);
        String savePath = bimFileDTO.getFilePath();

        logger.debug("SavePath : {}", savePath);
        File file = new File(savePath);
        return file;
    }

    @Override
    public void deleteFile(String filePath) {
            if (filePath == null) return;
            File file = new File(rootPath + filePath);
            file.delete();
    }

    @Override
    public void deleteFolderBybizNo(String bizNo){
        String folderPath = rootPath + bizNo;

        File folder = new File(folderPath);

        try {
            if (folder.exists()) {
                FileUtils.cleanDirectory(folder);//하위 폴더와 파일 모두 삭제
                if (folder.isDirectory()) {
                    folder.delete(); // 대상폴더 삭제
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("폴더 삭제 실패 error:"+e);
        }
    }

    @Override
    public String getFileExtension(MultipartFile file){
        String fileNm = file.getOriginalFilename();
        int dotIndex = fileNm.lastIndexOf(".");
        return (dotIndex != -1) ? fileNm.substring(dotIndex + 1) : "";
    }

    @Override
    //성과품 전체 복사, 실패시 트랜잭션을 위해 runtimeException
    public void copyAllDirectory(String sourcePath, String destPath) {
        File source = new File(rootPath + "bimmanage" + SEPARATOR+ sourcePath); // 복사할 폴더 경로
        File target = new File(rootPath + "bimmanage" + SEPARATOR+destPath); // 복사 대상 경로
        if (!target.exists()) {
            target.mkdirs();
        }
        try {
//            FileFilter fileFilter = file -> {
//                String name = file.getName();
//                // 예외 조건: 제외할 파일이나 폴더 이름
//                return !name.equals("excludeFile.txt") && !name.equals("excludeFolder");
//            };
            FileUtils.copyDirectory(source, target);
        } catch (IOException e) {
            logger.debug(e.toString());
            logger.debug(e.getMessage());
            throw new RuntimeException("성과품 복사 실패");
        }
    }

    @Override
    //성과품 부분 복사, 파일 경로와 목표 프로젝트 번호를 받아 복사
    public void copyPartFile(String filePath, String destPath){
        File source = new File(rootPath + filePath);
        File target = new File(rootPath + destPath); // 복사 대상 경로
        try {
            FileUtils.copyDirectory(source, target);
        } catch (IOException e) {
            throw new RuntimeException("성과품 복사 실패");
        }
    }

    private String getNowTime(){
        LocalDateTime now = LocalDateTime.now();
        // 포맷 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        // 포맷된 문자열 반환
        return now.format(formatter);
    }

    private String getFileNameWithoutExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            return "";
        }
        // 마지막 점(.) 위치를 찾고 파일명 추출
        int lastDotIndex = originalFilename.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return originalFilename;
        }
        return originalFilename.substring(0, lastDotIndex);
    }


}
