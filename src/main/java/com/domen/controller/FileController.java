package com.domen.controller;

import com.domen.entity.FileDetail;
import com.domen.service.FileService;
import com.domen.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;

@RestController
@Api(tags = {"File Operation Related API"})
public class FileController {

    public static String fileDirectory = System.getProperty("user.dir") + "/uploaded";

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private FileService fileService;


    @GetMapping("/getUploadedFileDetailsByUsername")
    @ApiOperation(value = "Uploaded file details of Employee")
    public Collection<FileDetail> getUploadedDetailsbyUserName(@RequestParam("username") String username){
        return fileService.getUploadedDetailsbyUserName(username);
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ApiOperation(value = "Download file using path and filename")
    public ResponseEntity<Object> downloadFile(@RequestParam("filename") String filename) throws IOException
    {
        return fileService.downlaodFile(filename);
    }


    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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
    public Collection<FileDetail> fileDetailsByDate(@RequestParam("date") String date){
        return fileService.fileDetailsByDate(date);
    }

    @GetMapping("/getUploadedFileDetailsDateWise")
    @ApiOperation(value = "It will returns file  details date wise")
    public  Collection<FileDetail> getUploadedFileDetailsDateWise(){
        return null;
    }



}
