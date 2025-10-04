package com.hajji.springbootbasics.dto.standard;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CreateStandardRequestDTO {

    @NotBlank(message = "Name is required")
    @Size(max = 200)
    private String name;

    @Size(max = 50)
    private String version;

    private LocalDate publishedDate;

    // Getters & Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public LocalDate getPublishedDate() { return publishedDate; }
    public void setPublishedDate(LocalDate publishedDate) { this.publishedDate = publishedDate; }
}
