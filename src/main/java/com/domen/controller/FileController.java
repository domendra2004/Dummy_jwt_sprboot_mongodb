package com.domen.controller;

import com.domen.entity.CustomDatewiseModel;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@Api(tags = {"File Operation Related API"})
public class FileController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private FileService fileService;


    @GetMapping("/getUploadedFileDetailsByUsername")
    @ApiOperation(value = "All Uploaded file details of Particular Employee using username")
    public Collection<FileDetail> getUploadedDetailsbyUserName(@RequestParam("username") String username){
        return fileService.getUploadedDetailsbyUserName(username);
    }
    @GetMapping("/getAllUploadedFileDetails")
    @ApiOperation(value = "All Uploaded file details of Employee")
    public Collection<FileDetail> getAllUploadedDetails(){
        return fileService.getAllUploadedDetails();
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

    @GetMapping("/countUploadedDocByUsernameAndDate")
    @ApiOperation(value = "It will returns Number of uploaded document by user in any perticular date")
    public long countDocUsingUsernameAndDate(@RequestParam("username") String username,@RequestParam("date")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date){
        return fileService.countDocUsingUsernameAndDate(username,date.atStartOfDay());
    }
    @GetMapping("/countUploadedDocByDate")
    @ApiOperation(value = "It will returns number of uploaded files any particular date")
    public long countDocByDate(@RequestParam("uploadedTime")
                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                LocalDate date){
        return  fileService.countDocByDate(date.atStartOfDay());

    }



    @GetMapping("/getUploadedDocByUsernameAndDate")
    @ApiOperation(value = "It will returns List of uploaded document by user in any perticular date")
    public Collection<FileDetail> getUploadedFileDetailsUsingUsernameAndDate(@RequestParam("username") String username,@RequestParam("date")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date){
        return fileService.getUploadedFileDetailsUsingUsernameAndDate(username, date.atStartOfDay());
    }

    @GetMapping("/getUploadedFileDetailsByDate")
    @ApiOperation(value = "It will returns details of uploaded file of employee in any particular date")
    public FileDetail fileDetailsByDate(@RequestParam("uploadedTime")
                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                LocalDate date){
        return  fileService.fileDetailsByDate(date.atStartOfDay());

    }

    @GetMapping("/getUploadedFileDetailsDateWise")
    @ApiOperation(value = "{Not Completed }It will returns file  details date wise")
    public Collection<CustomDatewiseModel> getUploadedFileDetailsDateWise(){
        return fileService.getUploadedFileDetailsDateWise();
    }



}
