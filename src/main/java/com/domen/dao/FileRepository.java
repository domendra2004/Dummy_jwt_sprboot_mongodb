package com.domen.dao;

import com.domen.entity.FileDetail;
import io.jsonwebtoken.lang.Collections;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;

public interface FileRepository extends MongoRepository<FileDetail,String> {
    public Collection<FileDetail>findByUsername(String username);
}
