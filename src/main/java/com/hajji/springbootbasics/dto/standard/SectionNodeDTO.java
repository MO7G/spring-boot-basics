package com.hajji.springbootbasics.dto.standard;

import java.util.List;

public class SectionNodeDTO {
    private Integer id;
    private String number;
    private String title;
    private Integer orderIndex;
    private List<SectionNodeDTO> children;

    public SectionNodeDTO(Integer id, String number, String title, Integer orderIndex, List<SectionNodeDTO> children) {
        this.id = id;
        this.number = number;
        this.title = title;
        this.orderIndex = orderIndex;
        this.children = children;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public List<SectionNodeDTO> getChildren() {
        return children;
    }

    public void setChildren(List<SectionNodeDTO> children) {
        this.children = children;
    }
}
