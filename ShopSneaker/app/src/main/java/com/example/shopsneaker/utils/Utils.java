package com.example.shopsneaker.utils;

import com.example.shopsneaker.model.GioHang;
import com.example.shopsneaker.model.SaleDetails;
import com.example.shopsneaker.model.User;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    //public static final String BASE_URL = "http://vlthsneakers.000webhostapp.com/server/";
    public static final String BASE_URL = "http://192.168.0.20:8080/server/";
    public static List<GioHang> manggiohang;
    public static User user_current = new User();
    public static ArrayList<SaleDetails> ListSaleDetails = new ArrayList<>();
    public static ArrayList<SaleDetails> ListSaleDetailsDelete = new ArrayList<>();
    public static String getMD5(String input) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            return convertByteToHex1(messageDigest);
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public static String convertByteToHex1(byte[] data) {
        BigInteger number = new BigInteger(1, data);
        String hashtext = number.toString(16);
        // Now we need to zero pad it if you actually want the full 32 chars.
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext;
    }

}
