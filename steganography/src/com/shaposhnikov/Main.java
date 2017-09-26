package com.shaposhnikov;

import com.shaposhnikov.steganography.SteganographyCoder;
import com.shaposhnikov.steganography.SteganographyDecoder;

import java.io.File;
import java.io.UnsupportedEncodingException;

public class Main {

    public static void main(String[] args) throws UnsupportedEncodingException {
        new SteganographyCoder(new File("container.txt")).encrypt("- hello guys!\nHow are you?\nУ нас все хорошо ^_^");
        System.out.println(new SteganographyDecoder(new File("container.txt")).decrypt());
    }
}
