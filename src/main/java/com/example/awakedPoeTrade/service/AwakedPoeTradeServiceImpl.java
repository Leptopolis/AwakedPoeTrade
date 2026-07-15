package com.example.awakedPoeTrade.service;

import com.example.awakedPoeTrade.entity.Item;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
@Service
@RequiredArgsConstructor
@Slf4j
public class AwakedPoeTradeServiceImpl implements AwakedPoeTradeService {
    private PoeItemReader reader;
    private Item item;

    private final ObjectMapper objectMapper = new ObjectMapper(); 
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String getText(){
        return reader.getClipboardText();
    }

    @Override
    public void parseItem(String itemText){
        System.out.println(itemText);
    }

    @Override
    public String findItem(String itemName){
            try {
        String url = "https://poe.ninja/poe1/economy/ancestors/unique-accessories/headhunter-leather-belt";

        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0")
                .timeout(10000)
                .get();

        // ✅ Ищем все span с числами
        Elements priceSpans = doc.select("span");
        String price = null;

        for (int i = 0; i < priceSpans.size() - 1; i++) {
            String current = priceSpans.get(i).text().trim();
            String next = priceSpans.get(i + 1).text().trim();

            // ✅ Если текущий span — число, а следующий — "Chaos Orb"
            if (current.matches("\\d+(\\.\\d+)?[k]?") && next.equals("Chaos Orb")) {
                price = current;
                break;
            }
        }

        if (price != null) {
            return "💰 " + itemName + ": " + price + " chaos";
        }

        return "❌ Цена не найдена";

    } catch (Exception e) {
        return "❌ Ошибка: " + e.getMessage();
    }
    }
}