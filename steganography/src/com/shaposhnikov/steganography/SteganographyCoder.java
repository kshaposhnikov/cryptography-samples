package com.shaposhnikov.steganography;

import com.shaposhnikov.api.Coder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class SteganographyCoder implements Coder {

    private final File container;

    public SteganographyCoder(File container) {
        this.container = container;
    }

    @Override
    public void encrypt(String inputText) {
        try (BufferedReader reader = new BufferedReader(new FileReader(container))) {
            StringBuilder binaryString = toBinaryString(inputText);

            String tmpStr;
            int iter = 0;
            StringBuilder result = new StringBuilder();
            while ((tmpStr = reader.readLine()) != null) {
                if (iter < binaryString.length()) {
                    if (binaryString.charAt(iter) == '1') {
                        tmpStr += " ";
                    }
                }
                result.append(tmpStr).append("\n");
                iter++;
            }
            reader.close();
            saveToFile(result.toString());
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Couldn't found provided file", e);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read file", e);
        }
    }

    private void saveToFile(String result) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(container))) {
            writer.write(result);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't write to file", e);
        }
    }

    private StringBuilder toBinaryString(String inputText) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        for (byte symbol : inputText.getBytes("UTF-8")) {
            result.append(fillHighBits(Integer.toBinaryString(symbol)));
        }
        return result;
    }

    private char[] fillHighBits(String binaryString) {
        if (binaryString.length() == 32) {
            return binaryString.toCharArray();
        } else {
            char[] result = new char[32];
            for (int i = 0; i < 32; i++) {
                result[i] = i < (32 - binaryString.length()) ? '0' : binaryString.charAt(binaryString.length() - (32 - i));
            }
            return result;
        }
    }
}
