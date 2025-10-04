package com.hajji.springbootbasics.mapper;

import com.hajji.springbootbasics.dto.standard.StandardSectionResponseDTO;
import com.hajji.springbootbasics.model.StandardSection;

public class StandardSectionMapper {

    public static StandardSectionResponseDTO toDTO(StandardSection section) {
        StandardSectionResponseDTO dto = new StandardSectionResponseDTO();
        dto.setSectionId(section.getSectionId());
        dto.setNumber(section.getNumber());
        dto.setTitle(section.getTitle());
        dto.setOrderIndex(section.getOrderIndex());
        dto.setCreatedAt(section.getCreatedAt());
        dto.setModifiedAt(section.getModifiedAt());
        dto.setStandardId(section.getStandard() != null ? section.getStandard().getStandardId() : null);
        dto.setParentSectionId(section.getParentSection() != null ? section.getParentSection().getSectionId() : null);
        return dto;
    }
}
