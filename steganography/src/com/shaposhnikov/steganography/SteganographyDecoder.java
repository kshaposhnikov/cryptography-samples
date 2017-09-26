package com.shaposhnikov.steganography;

import com.shaposhnikov.api.Decoder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SteganographyDecoder implements Decoder {

    private final File container;

    public SteganographyDecoder(File container) {
        this.container = container;
    }

    @Override
    public String decrypt() {
        try (BufferedReader reader = new BufferedReader(new FileReader(container))) {
            String tmpStr;
            int iter = 0;
            StringBuilder binaryString = new StringBuilder();
            List<Byte> byteList = new ArrayList<>();
            while ((tmpStr  = reader.readLine()) != null) {
                if (tmpStr.charAt(tmpStr.length() - 1) == ' ') {
                    binaryString.append('1');
                } else {
                    binaryString.append('0');
                }

                if (iter == 31) {
                    byteList.add((byte)Long.parseLong(binaryString.toString(), Character.MIN_RADIX));
                    binaryString = new StringBuilder();
                    iter = 0;
                } else {
                    iter++;
                }
            }
            byte[] result = new byte[byteList.size()];
            for (int i = 0; i < result.length; i++) {
                result[i] = byteList.get(i);
            }
            return new String(result);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Couldn't found provided file", e);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read file", e);
        }
    }
}
