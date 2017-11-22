package com.example.market.utils;

/**
 * Author:ZhuQing
 * Date:2017/11/22 21:00
 */
public class MarketException extends Exception {

    public MarketException(){

    }

    public MarketException(String s){
        super(s);
    }

    public MarketException(String s, Throwable throwable){
        super(s,throwable);
    }

    public MarketException(Throwable throwable){
        super(throwable);
    }
}
