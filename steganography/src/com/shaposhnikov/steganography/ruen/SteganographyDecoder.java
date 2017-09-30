package com.shaposhnikov.steganography.ruen;

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
                for (int i = 0; i < tmpStr.length(); i++) {
                    if (iter == 32) {
                        byteList.add((byte)Long.parseLong(binaryString.toString(), Character.MIN_RADIX));
                        binaryString = new StringBuilder();
                        iter = 0;
                    }

                    if (Dictionary.symbolWasReplaced(tmpStr.charAt(i))) {
                        binaryString.append('1');
                        iter++;
                    } else if (Dictionary.get(tmpStr.charAt(i)) != null) {
                        binaryString.append('0');
                        iter++;
                    }
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
