package com.example.awakedPoeTrade.controller;

import com.example.awakedPoeTrade.service.AwakedPoeTradeService;
import lombok.*;
import org.springframework.stereotype.Component;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import jakarta.annotation.PostConstruct;


import javax.swing.*;
import java.awt.*;

@Component
public class MainController{
    private final AwakedPoeTradeService awakedPoeTradeService;
    private JFrame overlayFrame;
    private JLabel priceLabel;


    public MainController(AwakedPoeTradeService awakedPoeTrade){
        this.awakedPoeTradeService = awakedPoeTrade;
    }

    @PostConstruct
    public void init(){
        try {
            GlobalScreen.registerNativeHook();
            System.out.println("✅ GlobalScreen зарегистрирован!");
            registerHotKey();
        } catch (Exception e) {
            System.err.println("❌ Ошибка регистрации: " + e.getMessage());
            e.printStackTrace();
        }
        initOverlay();
    }

    private void initOverlay(){
        //JFrame mainFrame = new JFrame();
        //mainFrame.setUndecorated(true);
        //mainFrame.setAlwaysOnTop(true);
        //mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        overlayFrame = new JFrame("Awaked POE Trade");
        overlayFrame.setUndecorated(false);
        overlayFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        overlayFrame.setAlwaysOnTop(true);
        //overlayFrame.setBackground(new Color(0,0,0,0));
        overlayFrame.setSize(300, 100);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(30,30,30,220));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        priceLabel = new JLabel("Press Ctrl + D on Item");
        priceLabel.setForeground(Color.WHITE);
        priceLabel.setFont(new Font("Arial", Font.BOLD, 16));

        panel.add(priceLabel);
        overlayFrame.add(panel);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle screen = ge.getMaximumWindowBounds();
        overlayFrame.setLocation(screen.width - 320, 50);

        overlayFrame.setVisible(true);
    }

    private void showPrice(String price){
        SwingUtilities.invokeLater(()->{
            priceLabel.setText(price);
            overlayFrame.setVisible(true);
        });
    }

    private void hideOverlay(){
        SwingUtilities.invokeLater(()->overlayFrame.setVisible(false));
    }

    private void registerHotKey(){
        try{
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(new NativeKeyListener(){
                @Override
                public void nativeKeyPressed(NativeKeyEvent e){
                    if(e.getKeyCode() == NativeKeyEvent.VC_D && (e.getModifiers() & NativeKeyEvent.CTRL_MASK) != 0){
                        handlePriceCheck();
                    }
                }

                @Override
                public void nativeKeyReleased(NativeKeyEvent e) {};

                @Override 
                public void nativeKeyTyped(NativeKeyEvent e) {};
            });
        } catch (Exception ex){
            System.err.println("Error in register hotkeys: " + ex.getMessage());
        }
    }

    private void handlePriceCheck(){
        System.out.println("Ctrl + D is pressed");
        try{
            String itemText = awakedPoeTradeService.getText();
            awakedPoeTradeService.parseItem(itemText);
            String price = awakedPoeTradeService.findItem(itemText);
            priceLabel.setText("");
            System.out.println(price);
            showPrice(price);
        }catch(Exception e){
            System.err.println("error");
        }


    }

}