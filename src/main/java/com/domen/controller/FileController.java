package com.domen.controller;

import com.domen.entity.FileDetail;
import com.domen.service.FileService;
import com.domen.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

@RestController
@Api(tags = {"File Operation Related API"})
public class FileController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private FileService fileService;


    @GetMapping("/getUploadedFileDetailsByUsername")
    @ApiOperation(value = "Uploaded file details of Employee")
    public Collection<FileDetail> getUploadedDetailsbyUserName(@RequestParam("username") String username){
        return fileService.getUploadedDetailsbyUserName(username);
    }

    @GetMapping("/download")
    @ApiOperation(value = "Download file using path and filename")
    public ResponseEntity<Object> downloadFile(@RequestParam("filename") String filename) throws IOException
    {
        return fileService.downlaodFile(filename);
    }
    @PostMapping("/upload")
    @ApiOperation(value = "For Upload Document")
    public ResponseEntity<Object> uploadfile(@RequestParam("file") MultipartFile file, HttpServletRequest req) throws IOException {
        String currentToken=req.getHeader("Authorization");
        return new ResponseEntity<>(fileService.uploadDocument(jwtUtil.extractUsername(currentToken),file), HttpStatus.OK);
    }
    @GetMapping("/countUploadedDoc")
    @ApiOperation(value = "It will returns number of uploaded document of employee")
    public long countDoc(@RequestParam("username") String username){
        return fileService.countDoc(username);
    }

    @GetMapping("/getUploadedFileDetailsByDate")
    @ApiOperation(value = "It will returns details of uploaded file of employee in any particular date")
    public FileDetail fileDetailsByDate(@RequestParam("uploadedTime")
                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                LocalDate date){
    //public FileDetail fileDetailsByDate(@RequestParam("uploadedTime") String date){
      // Instant instant = Instant.parse(date);
       // Date date1 = Date.from(instant);
        //LocalDateTime ldt=date;
        FileDetail fl=fileService.fileDetailsByDate(date.atStartOfDay());
        return fl;
    }


    @GetMapping("/getUploadedFileDetailsDateWise")
    @ApiOperation(value = "It will returns file  details date wise")
    public Collection<FileDetail> getUploadedFileDetailsDateWise(){
        return fileService.getUploadedFileDetailsDateWise();
    }



}
