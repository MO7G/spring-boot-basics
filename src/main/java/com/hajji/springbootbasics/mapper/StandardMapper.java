package com.hajji.springbootbasics.mapper;

import com.hajji.springbootbasics.dto.standard.CreateStandardRequestDTO;
import com.hajji.springbootbasics.dto.standard.SectionNodeDTO;
import com.hajji.springbootbasics.dto.standard.StandardResponseDTO;
import com.hajji.springbootbasics.dto.standard.StandardSectionTreeDTO;
import com.hajji.springbootbasics.model.Standard;
import com.hajji.springbootbasics.model.StandardSection;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class StandardMapper {

    public static Standard toEntity(CreateStandardRequestDTO dto) {
        Standard standard = new Standard();
        standard.setName(dto.getName());
        standard.setVersion(dto.getVersion());
        standard.setPublishedDate(dto.getPublishedDate());
        standard.setCreatedAt(LocalDateTime.now());
        standard.setModifiedAt(LocalDateTime.now());
        return standard;
    }

    public static StandardResponseDTO toDTO(Standard standard) {
        StandardResponseDTO dto = new StandardResponseDTO();
        dto.setStandardId(standard.getStandardId());
        dto.setName(standard.getName());
        dto.setVersion(standard.getVersion());
        dto.setPublishedDate(standard.getPublishedDate());
        dto.setCreatedAt(standard.getCreatedAt());
        dto.setModifiedAt(standard.getModifiedAt());
        return dto;
    }



    // Map section -> SectionNodeDTO (recursively)
    public static SectionNodeDTO toSectionNode(StandardSection section) {
        if (section == null) {
            return null;
        }

        List<SectionNodeDTO> children = section.getChildSections() != null
                ? section.getChildSections()
                .stream()
                .map(StandardMapper::toSectionNode) // recursion
                .collect(Collectors.toList())
                : List.of();

        return new SectionNodeDTO(
                section.getSectionId(),
                section.getNumber(),
                section.getTitle(),
                section.getOrderIndex(),
                children
        );
    }

    // Map standard -> StandardSectionTreeDTO (with root sections)
    public static StandardSectionTreeDTO toTreeDTO(Standard standard) {
        if (standard == null) {
            return null;
        }

        List<SectionNodeDTO> rootSections = standard.getSections() != null
                ? standard.getSections().stream()
                .filter(sec -> sec.getParentSection() == null) // only roots
                .map(StandardMapper::toSectionNode)
                .collect(Collectors.toList())
                : List.of();

        StandardSectionTreeDTO dto = new StandardSectionTreeDTO();
        dto.setId(standard.getStandardId());
        dto.setTitle(standard.getName());    // standard name, not section title
        dto.setVersion(standard.getVersion());
        dto.setSections(rootSections);

        return dto;
    }


}
