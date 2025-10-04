package com.hajji.springbootbasics.dto.standard;

import java.util.List;

public class StandardSectionTreeDTO {
    private Integer id;
    private String title;
    private String Number;
    private String version;
    private Integer orderIndex;
    private List<SectionNodeDTO> sections;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public List<SectionNodeDTO> getSections() {
        return sections;
    }

    public void setSections(List<SectionNodeDTO> sections) {
        this.sections = sections;
    }
}


