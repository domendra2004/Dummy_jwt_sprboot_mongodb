package com.domen.controller;

import com.domen.entity.FileDetail;
import com.domen.service.FileService;
import com.domen.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Collection;

@RestController
@Api(tags = {"File Operation Related API"})
public class FileController {
    public static String fileDirectory = System.getProperty("user.dir") + "/uploaded";

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private FileService fileService;


    @GetMapping("getUploadedFileDetailsByUsername")
    @ApiOperation(value = "Uploaded file details of Employee")
    public Collection<FileDetail> getUploadedDetailsbyUserName(@RequestParam("username") String username){
        return fileService.getUploadedDetailsbyUserName(username);
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ApiOperation(value = "Download file using path and filename")
    public ResponseEntity<Object> downloadFile(@RequestParam("filename") String filename) throws IOException
    {
        String path="/home/xyz/Springboot_jwt_mongodb/uploaded";

        String downloadableFile =path+"/"+filename ;
        File file = new File(downloadableFile);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition",
                String.format("attachment; filename=\"%s\"", file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/txt")).body(resource);

        return responseEntity;
    }


    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "For Upload Document")
    public ResponseEntity<Object> uploadfile(@RequestParam("file") MultipartFile file, HttpServletRequest req) throws IOException {
        String currentToken=req.getHeader("Authorization");
        LocalTime time = LocalTime.now();
        String message="Not Available";
        String username=jwtUtil.extractUsername(currentToken);
        String uploadedTime=time+"";
        String fileName=username+uploadedTime+file.getOriginalFilename();
        if(fileService.saveDocDetails(
                new FileDetail(username,fileDirectory,fileName,uploadedTime))!=null){

            File converFile = new File(fileDirectory ,fileName);
            converFile.createNewFile();
            FileOutputStream fout = new FileOutputStream(converFile);
            fout.write(file.getBytes());
            fout.close();
            message="File is Uploaded successfully";
        }else{
            message="File Not Uploaded";
        }

        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @GetMapping("countUploadedDoc")
    @ApiOperation(value = "It will returns number of uploaded document of employee")
    public long countDoc(@RequestParam("username") String username){
        return fileService.countDoc(username);
    }






}
