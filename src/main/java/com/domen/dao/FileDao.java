package com.domen.dao;

import com.domen.entity.Employee;
import com.domen.entity.FileDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class FileDao {

    @Autowired
    FileRepository fileRepository;
    @Autowired
    MongoOperations mongoOperations;

    public FileDetail saveDocDetails(FileDetail fileDetail) {
        return fileRepository.insert(fileDetail);
    }
    public Collection<FileDetail>getUploadedDetailsbyUserName(String username){
        return fileRepository.findByUsername(username);
    }
    public long countDoc(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));

        return mongoOperations.count(query, FileDetail.class);
    }





}
