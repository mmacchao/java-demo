package com.example.service;
public class ApiImplOne implements Api {
    @Override
    public String process(int num) {
        return num + " - processed by Implementation One";
    }
}