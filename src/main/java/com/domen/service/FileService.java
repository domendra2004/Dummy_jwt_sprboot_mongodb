package com.domen.service;

import com.domen.dao.FileDao;
import com.domen.entity.FileDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class FileService {

    @Autowired
    FileDao fileDao;

    public FileDetail saveDocDetails(FileDetail fileDetail) {
        return  fileDao.saveDocDetails(fileDetail);
    }
    public Collection<FileDetail>getUploadedDetailsbyUserName(String username){
        return fileDao.getUploadedDetailsbyUserName(username);
    }
    public long countDoc(String username) {
        return fileDao.countDoc(username);
    }
}
