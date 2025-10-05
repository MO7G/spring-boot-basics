package com.hajji.springbootbasics.dto.response;

import com.hajji.springbootbasics.dto.standard.StandardResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "API response wrapper containing a list of StandardResponseDTO")
public class StandardListResponseWrapper {

    @Schema(description = "HTTP status code", example = "200")
    private int status;

    @Schema(description = "Response message", example = "Standards fetched successfully")
    private String message;

    @Schema(description = "List of standards")
    private List<StandardResponseDTO> data;

    @Schema(description = "Timestamp of the response")
    private LocalDateTime timestamp;

    public StandardListResponseWrapper(int status, String message, List<StandardResponseDTO> data, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }

    // Getters
    public int getStatus() { return status; }
    public String getMessage() { return message; }
    public List<StandardResponseDTO> getData() { return data; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
