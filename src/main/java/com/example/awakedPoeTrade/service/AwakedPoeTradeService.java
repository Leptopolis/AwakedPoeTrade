package com.example.awakedPoeTrade.service;

import com.example.awakedPoeTrade.entity.Item;

public interface AwakedPoeTradeService{

    public String getText();
    public void parseItem(String itemText);

    public String findItem(String itemText);
}  