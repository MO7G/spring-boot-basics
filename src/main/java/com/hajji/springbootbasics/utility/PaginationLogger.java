package com.hajji.springbootbasics.utility;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

@Slf4j
public class PaginationLogger {

    // Reusable method
    public static void logPageFetch(String entityName, Pageable pageable) {
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        int start = page * size + 1;
        int end = start + size - 1;

        log.info("Fetching {} from {} to {}", entityName, start, end);
    }
}
