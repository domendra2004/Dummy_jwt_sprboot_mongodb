package com.domen.dao;

import com.domen.entity.FileDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class FileDao {

    @Autowired
    FileRepository fileRepository;

    public FileDetail saveDocDetails(FileDetail fileDetail) {
        return fileRepository.insert(fileDetail);
    }
    public Collection<FileDetail>getUploadedDetailsbyUserName(String username){
        return fileRepository.findByUsername(username);
    }





}
