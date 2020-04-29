package com.domen.service;

import com.domen.dao.FileDao;
import com.domen.entity.FileDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@Service
public class FileService {

    @Autowired
    FileDao fileDao;
    DateTimeFormatter dtf;
    DateTimeFormatter dtf2;
    private static String fileDirectory = System.getProperty("user.dir") + "/uploaded";

    public FileDetail saveDocDetails(FileDetail fileDetail) {
        return  fileDao.saveDocDetails(fileDetail);
    }
    public Collection<FileDetail>getUploadedDetailsbyUserName(String username){
        return fileDao.getUploadedDetailsbyUserName(username);
    }
    public long countDoc(String username) {
        return fileDao.countDoc(username);
    }

    public Collection<FileDetail> fileDetailsByDate(String date) {
        return fileDao.fileDetailsByDate(date);
    }


    public String uploadDocument(String username, MultipartFile file)throws IOException {
        dtf= DateTimeFormatter.ofPattern("yyMMddHHmm");
        dtf2= DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String message="Not Available";
        String uploadedTime=dtf2.format(now);
        String fileName=username+dtf.format(now)+file.getOriginalFilename();
        if(saveDocDetails(new FileDetail(username,fileDirectory,fileName,uploadedTime))!=null){

            File converFile = new File(fileDirectory ,fileName);
            converFile.createNewFile();
            FileOutputStream fout = new FileOutputStream(converFile);
            fout.write(file.getBytes());
            fout.close();
            message="File is Uploaded successfully";
        }else{
            message="File Not Uploaded";
        }
return message;
    }


    public ResponseEntity<Object> downlaodFile(String filename)throws IOException{
        String downloadableFile =fileDirectory+"/"+filename ;
        File file = new File(downloadableFile);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition",
                String.format("attachment; filename=\"%s\"", file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok().headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/txt")).body(resource);
    }



}
