package com.domen.entity;

import io.swagger.annotations.ApiModel;
import org.springframework.data.mongodb.core.mapping.Document;

@Document()
@ApiModel(description = "File Details Object")
public class FileDetail {
    private String username;
    private String path;
    private String fileName;
    private String uploadedTime;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUploadedTime() {
        return uploadedTime;
    }

    public void setUploadedTime(String uploadedTime) {
        this.uploadedTime = uploadedTime;
    }

    public FileDetail(String username, String path, String fileName, String uploadedTime) {
        this.username = username;
        this.path = path;
        this.fileName = fileName;
        this.uploadedTime = uploadedTime;
    }

    public FileDetail() {
    }

    @Override
    public String toString() {
        return "FIleDetail{" +
                "username='" + username + '\'' +
                ", path='" + path + '\'' +
                ", fileName='" + fileName + '\'' +
                ", uploadedTime='" + uploadedTime + '\'' +
                '}';
    }
}
