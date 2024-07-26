package com.example.lab6.select;

import com.example.lab6.SanPham;

public class SvrResponseSelect {
    private SanPham[] products;
    private String message;

    public SanPham[] getProducts() {
        return products;
    }

    public String getMessage() {
        return message;
    }
}