package com.domen.dao;

import com.domen.entity.CustomDatewiseModel;
import com.domen.entity.FileDetail;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Collection;

public interface FileRepository extends MongoRepository<FileDetail,String> {
    public Collection<FileDetail>findByUsername(String username);
}
