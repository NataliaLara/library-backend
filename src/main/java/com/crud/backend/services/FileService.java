package com.crud.backend.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.crud.backend.entities.BookEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${aws.bucket.file}")
    private String path;
    @Value("${aws.bucket.name}")
    private String bucketName;
    private final AmazonS3 amazonS3;

    public void createAndSendFile(List<BookEntity> bookEntityList){
        FileWriter file = null;
        try {
            file = new FileWriter(path);
            PrintWriter printFile = new PrintWriter(file);
            printFile.println("Title\tupdated_at");
            bookEntityList.forEach(bookEntity ->
                    printFile.println(bookEntity.getTitle()+"\t"+bookEntity.getUpdatedAt()));
            file.close();
            log.info("File created successfully");
//            sendFile();
        } catch (IOException e) {
            log.error("File not saved");
        }

    }
    public void sendFile() {
        var file = new File(path);
        amazonS3.putObject(new PutObjectRequest(bucketName, file.getName(), file));
        log.info("File sent to AWS successfully");
    }
}
