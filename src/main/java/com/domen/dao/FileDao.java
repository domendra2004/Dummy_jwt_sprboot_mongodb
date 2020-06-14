package com.domen.dao;

import com.domen.entity.FileDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.util.Collection;
import java.util.Date;

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
    public FileDetail fileDetailsByDate( LocalDateTime date){

        Query query=new Query();
        date=date.with(ChronoField.NANO_OF_DAY,0);
        query.addCriteria(Criteria.where("uploadedTime").gte(Date.from(date.toInstant(ZoneOffset.UTC))).lte(Date.from(date.plusDays(1).toInstant(ZoneOffset.UTC))));
        return mongoOperations.findOne(query,FileDetail.class);

    }
    public long countDocUsingUsernameAndDate(String username, LocalDateTime date) {
        Query query = new Query();
        query.addCriteria(Criteria.where("uploadedTime").gte(Date.from(date.toInstant(ZoneOffset.UTC))).lte(Date.from(date.plusDays(1).toInstant(ZoneOffset.UTC))).and("username").is(username));
        return mongoOperations.count(query, FileDetail.class);
    }
    public long countDocByDate(LocalDateTime date) {

        Query query = new Query();
        query.addCriteria(Criteria.where("uploadedTime").gte(Date.from(date.toInstant(ZoneOffset.UTC))).lte(Date.from(date.plusDays(1).toInstant(ZoneOffset.UTC))));
        return mongoOperations.count(query, FileDetail.class);
    }


    public Collection<FileDetail> getAllUploadedFileDetails() {
        return fileRepository.findAll();
    }

    public Collection<FileDetail> getUploadedFileDetailsUsingUsernameAndDate(String username, LocalDateTime date) {
        Query query = new Query();
        query.addCriteria(Criteria.where("uploadedTime").gte(Date.from(date.toInstant(ZoneOffset.UTC))).lte(Date.from(date.plusDays(1).toInstant(ZoneOffset.UTC))).and("username").is(username));

        return mongoOperations.find(query,FileDetail.class);
    }

    public Collection<FileDetail> getUploadedFileDetailsDateWise() {
        TypedAggregation<FileDetail> studentAggregation =
                Aggregation.newAggregation(FileDetail.class,
                        Aggregation.group("uploadedTime"));
        AggregationResults<FileDetail> results = mongoOperations.
                aggregate(studentAggregation, FileDetail.class);
        return results.getMappedResults();

    }




}
