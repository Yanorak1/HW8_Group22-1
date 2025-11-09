package com.example.hw8_group22.service;

import com.example.hw8_group22.model.SearchItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class GoogleCseService {

    private final WebClient webClient;

    @Value("${google.cse.enabled:true}")
    private boolean enabled;

    @Value("${google.cse.apiKey}")
    private String apiKey;

    @Value("${google.cse.cx}")
    private String cx;

    @Value("${google.cse.num:10}")
    private int num;

    public GoogleCseService(WebClient webClient) {
        this.webClient = webClient;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public List<SearchItem> search(String query) {
        if (!enabled || query == null || query.isBlank()) {
            return Collections.emptyList();
        }
        try {
            String q = URLEncoder.encode(query, StandardCharsets.UTF_8);
            String url = "https://www.googleapis.com/customsearch/v1"
                       + "?key=" + apiKey
                       + "&cx=" + cx
                       + "&num=" + num
                       + "&q=" + q;

            Map body = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            List<SearchItem> results = new ArrayList<>();
            if (body == null) return results;

            Object itemsObj = body.get("items");
            if (itemsObj instanceof List) {
                for (Object obj : (List) itemsObj) {
                    if (obj instanceof Map) {
                        Map item = (Map) obj;
                        String title = Objects.toString(item.get("title"), "");
                        String link = Objects.toString(item.get("link"), "");
                        String snippet = Objects.toString(item.get("snippet"), "");
                        if (!title.isBlank() && !link.isBlank()) {
                            results.add(new SearchItem(title, link, snippet));
                        }
                    }
                }
            }
            return results;
        } catch (Exception e) {
            // 失敗就回傳空清單，頁面會顯示「沒有找到結果」
            return Collections.emptyList();
        }
    }
}