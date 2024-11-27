package com.compasspb.vitorsalb.pedido.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class PageableDto {

    private List<Object> content = new ArrayList<>();
    private boolean first;
    private boolean last;
    @JsonProperty("page")
    private int number;
    private int size;
    @JsonProperty("pageElements")
    private int numberOfElements;
    private int totalPages;
    private int totalElements;

    public List<Object> getContent() {
        return content;
    }

    public void setContent(List<Object> content) {
        this.content = content;
    }

    public PageableDto() {
    }

    public PageableDto(List<Object> content, boolean first, boolean last, int number, int size, int numberOfElements, int totalPages, int totalElements) {
        this.content = content;
        this.first = first;
        this.last = last;
        this.number = number;
        this.size = size;
        this.numberOfElements = numberOfElements;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }
}
