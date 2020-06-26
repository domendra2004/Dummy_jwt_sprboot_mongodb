package com.domen.entity;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class CustomDatewiseModel {
    private String year;
    private String month;
    private String day;
    private List<FileDetail> uploaders;

    public CustomDatewiseModel() {
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<FileDetail> getUploaders() {
        return uploaders;
    }

    public void setUploaders(List<FileDetail> uploaders) {
        this.uploaders = uploaders;
    }

    public CustomDatewiseModel(String year, String month, String day, List<FileDetail> uploaders) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.uploaders = uploaders;
    }

    @Override
    public String toString() {
        return "CustomDatewiseModel{" +
                "year='" + year + '\'' +
                ", month='" + month + '\'' +
                ", day='" + day + '\'' +
                ", uploaders=" + uploaders +
                '}';
    }
}
