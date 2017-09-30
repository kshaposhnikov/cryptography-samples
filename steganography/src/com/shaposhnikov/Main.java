package com.shaposhnikov;

import com.shaposhnikov.steganography.ruen.SteganographyCoder;
import com.shaposhnikov.steganography.ruen.SteganographyDecoder;

import java.io.File;
import java.io.UnsupportedEncodingException;

public class Main {

    public static void main(String[] args) throws UnsupportedEncodingException {
        new SteganographyCoder(new File("container.txt")).encrypt("A я!");
        System.out.println(new SteganographyDecoder(new File("container.txt")).decrypt());
    }
}
