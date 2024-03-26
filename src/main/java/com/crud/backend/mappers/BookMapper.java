package com.crud.backend.mappers;

import com.crud.backend.dtos.BookDTO;
import com.crud.backend.entities.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface BookMapper {

    @Mapping(target = "id", source = "id")
    BookDTO mapToDto(BookEntity source);

    @Mapping(target = "author", defaultValue = "N/A")
    @Mapping(target = "hasChecked", constant = "true")
    BookEntity mapToEntity(BookDTO source);

    @Mapping(target = "hasChecked", constant = "false")
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "amount", source = "dto.amount")
    @Mapping(target = "author", source = "dto.author", defaultValue = "N/A")
    @Mapping(target = "format", source = "dto.format")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    BookEntity mapToEntityUpdate(BookEntity entity, BookDTO dto);

    List<BookDTO> mapToDtoList(List<BookEntity> source);


}
