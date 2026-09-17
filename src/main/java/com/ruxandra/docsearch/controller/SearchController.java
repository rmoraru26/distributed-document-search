package com.ruxandra.docsearch.controller;

import com.ruxandra.docsearch.dto.SearchResult;
import com.ruxandra.docsearch.service.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public List<SearchResult> search(@RequestParam String query) {
        return searchService.search(query);
    }
}
