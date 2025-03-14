package com.example.service;
public class ApiImplTwo implements Api {
    @Override
    public String process(int num) {
        return num + " - processed by Implementation Two";
    }
}