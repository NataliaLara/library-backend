package com.crud.backend.services;

import com.crud.backend.entities.BookEntity;
import com.crud.backend.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;


@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class DetectChange {

    private final BookRepository bookRepository;
    private final FileService fileService;
    private static final String TIME_ZONE = "America/Sao_Paulo";

    @Scheduled(cron = "0/30 * * ? * *", zone = TIME_ZONE)
    public void checkDatabaseUpdate(){
        var bookList = bookRepository.findAll();
        var booksChanged = new ArrayList<BookEntity>();
        bookList.forEach(bookEntity -> {
            if (!bookEntity.getUpdatedAt().equals(bookEntity.getCreatedAt()) && !bookEntity.isHasChecked()){
                booksChanged.add(bookEntity);
                bookEntity.setHasChecked(true);
                bookRepository.save(bookEntity);
                log.info("{}-{} has changed at {}", bookEntity.getId(), bookEntity.getTitle(), bookEntity.getUpdatedAt());
            }
        });
        if (!CollectionUtils.isEmpty(booksChanged)){
            fileService.createAndSendFile(booksChanged);
        }
    }
}
