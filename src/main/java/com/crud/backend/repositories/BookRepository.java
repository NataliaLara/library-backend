package com.crud.backend.repositories;

import com.crud.backend.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    Optional<BookEntity> findFirstByTitleAndFormatAndAuthor(String title, String format, String author);

}
