package com.example.awakedPoeTrade.service;

import java.awt.Toolkit;
import java.awt.datatransfer.*;


public class PoeItemReader{
    public static String getClipboardText(){
        try{
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            Transferable contents = clipboard.getContents(null);

            if(contents == null || ! contents.isDataFlavorSupported(DataFlavor.stringFlavor));

            return  (String) contents.getTransferData(DataFlavor.stringFlavor);
        } catch(Exception e){
            return null;
        }
    }
}