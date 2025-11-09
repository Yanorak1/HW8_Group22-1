package com.example.hw8_group22.controller;

import com.example.hw8_group22.model.SearchItem;
import com.example.hw8_group22.service.GoogleCseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SearchController {

    private final GoogleCseService cseService;

    public SearchController(GoogleCseService cseService) {
        this.cseService = cseService;
    }

    @GetMapping("/")
    public String home(@RequestParam(value="q", required = false) String q, Model model) {
        model.addAttribute("q", q == null ? "" : q);
        List<SearchItem> items = (q == null || q.isBlank())
                ? List.of() : cseService.search(q);
        model.addAttribute("items", items);
        return "index";
    }

    @PostMapping("/search")
    public String search(@RequestParam("q") String q) {
        // 用 PRG 模式避免重新整理重送表單
        return "redirect:/?q=" + q.trim();
    }
}