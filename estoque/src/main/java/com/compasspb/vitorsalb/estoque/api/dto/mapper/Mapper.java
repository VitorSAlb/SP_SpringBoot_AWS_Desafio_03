package com.compasspb.vitorsalb.estoque.api.dto.mapper;

import com.compasspb.vitorsalb.estoque.api.dto.PageableDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.stream.Collectors;

public class Mapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static <D, E> E toEntity(D dto, Class<E> entityClass) {
        return modelMapper.map(dto, entityClass);
    }

    public static <E, D> D toDto(E entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    public static <E, D> PageableDto pageableToDto(Page<E> page, Class<D> dtoClass) {
        PageableDto dto = new PageableDto();
        dto.setContent(page.getContent().stream()
                .map(entity -> modelMapper.map(entity, dtoClass))
                .collect(Collectors.toList()));
        dto.setNumber(page.getNumber());
        dto.setSize(page.getSize());
        dto.setNumberOfElements(page.getNumberOfElements());
        dto.setTotalPages(page.getTotalPages());
        dto.setTotalElements((int) page.getTotalElements());
        dto.setFirst(page.isFirst());
        dto.setLast(page.isLast());
        return dto;
    }

    public static <E, D> Page<D> mapEntityPageToDtoPage(Page<E> entities, Class<D> dtoClass) {
        return entities.map(entity -> modelMapper.map(entity, dtoClass));
    }

}
