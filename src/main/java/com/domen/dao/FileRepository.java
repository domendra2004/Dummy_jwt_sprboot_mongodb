package com.domen.dao;

import com.domen.entity.FileDetail;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileRepository extends MongoRepository<FileDetail,String> {
}
