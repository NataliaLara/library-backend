package com.crud.backend.services;

import com.crud.backend.dtos.BookDTO;
import com.crud.backend.exceptions.BadRequestException;
import com.crud.backend.exceptions.EntityNotFoundException;
import com.crud.backend.mappers.BookMapper;
import com.crud.backend.repositories.BookRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Validated
public class BookService {

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;

    public BookDTO getBookById(Long bookId) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));;
        return this.bookMapper.mapToDto(book);
    }

    public List<BookDTO> getAllBooks() {
        var book = bookRepository.findAll();
        return this.bookMapper.mapToDtoList(book);
    }

    public BookDTO createBook(@Valid BookDTO bookDTO) {
        var entity = bookMapper.mapToEntity(bookDTO);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(entity.getCreatedAt());
        var savedEntity = bookRepository
                .findFirstByTitleAndFormatAndAuthor(bookDTO.getTitle(), bookDTO.getFormat(), bookDTO.getAuthor());
        if (savedEntity.isPresent()){
            throw new BadRequestException("Livro já existe no acervo");
        }
        return bookMapper.mapToDto(bookRepository.save(entity));

    }

    public BookDTO updateBook(Long bookId, @Valid BookDTO bookDTO) {
        var savedEntity = bookRepository.findById(bookId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));
        var updatedBook = bookMapper.mapToEntityUpdate(savedEntity, bookDTO);
        return bookMapper.mapToDto(bookRepository.save(updatedBook));
    }

    public BookDTO deleteGroup(Long bookId){
        var entityToDelete = bookRepository.findById(bookId)
                        .orElseThrow(()-> new EntityNotFoundException("Livro não encontrado"));
        bookRepository.deleteById(bookId);
        return bookMapper.mapToDto(entityToDelete);
    }
}
