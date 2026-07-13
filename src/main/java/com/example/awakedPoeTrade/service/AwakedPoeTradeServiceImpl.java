package com.example.awakedPoeTrade.service;

import com.example.awakedPoeTrade.entity.Item;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class AwakedPoeTradeServiceImpl implements AwakedPoeTradeService {
    private PoeItemReader reader;
    private Item item;

    @Override
    public String getText(){
        return reader.getClipboardText();
    }

    @Override
    public void parseItem(String itemText){
        System.out.println(itemText);
    }

    @Override
    public String findItem(String itemText){
        System.out.println(itemText);
        return "5 chaos";
    }
}