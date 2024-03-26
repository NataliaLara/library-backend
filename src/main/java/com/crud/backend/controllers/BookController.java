package com.crud.backend.controllers;

import com.crud.backend.dtos.BookDTO;
import com.crud.backend.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/book/{bookId}")
    public ResponseEntity<BookDTO> getBook(@PathVariable Long bookId){
        return ResponseEntity.ok(bookService.getBookById(bookId));
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PostMapping("/book/save")
    public ResponseEntity<BookDTO> saveBook(@RequestBody BookDTO bookDTO){
        return ResponseEntity.ok(bookService.createBook(bookDTO));
    }

    @PutMapping("/book/{bookId}/update")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long bookId, @RequestBody BookDTO bookDTO){
        return ResponseEntity.ok(bookService.updateBook(bookId, bookDTO));
    }

    @DeleteMapping("/book/{bookId}")
    public ResponseEntity<BookDTO> deleteBook(@PathVariable Long bookId){
        return ResponseEntity.ok(bookService.deleteGroup(bookId));
    }
}
