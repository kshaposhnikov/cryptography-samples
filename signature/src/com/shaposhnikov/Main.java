package com.shaposhnikov;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println(">> Please provide source file");
        //String sourceFile = reader.readLine();
        //String sourceFile = "container.txt";
        String sourceFile = "signature.iml";

        System.out.println(">> Please provide directory to find");
        //String directoryPath = reader.readLine();
        String directoryPath = "C:\\Users\\Kirill\\tmp";
        findCopies(new File(directoryPath), getSignature(new File(sourceFile))).forEach(System.out::println);
    }

    private static List<String> findCopies(File directory, final byte[] signature) {
        if (directory.isFile()) {
            throw new RuntimeException("Was provided file instead of directory");
        }

        return Arrays.stream(directory.listFiles()).filter(file -> {
            try (RandomAccessFile accessFile = new RandomAccessFile(file, "r");
                 FileChannel channel = accessFile.getChannel()) {
                ByteBuffer part1 = ByteBuffer.allocate(16);
                part1.put(Arrays.copyOfRange(signature, 0, 16));
                part1.position(0);
                ByteBuffer part2 = ByteBuffer.allocate(16);
                part2.put(Arrays.copyOfRange(signature, 16, signature.length));
                part2.position(0);

                ByteBuffer buffer = ByteBuffer.allocate(16);
                int res = 0;
                while (channel.read(buffer) != -1) {
                    buffer.position(0);
                    if (part1.equals(buffer) || part2.equals(buffer)) {
                        res++;
                    }
                    buffer.clear();
                }

                return res == 2;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }).map(File::getAbsolutePath).collect(Collectors.toList());
    }

    private static byte[] getSignature(File file) throws IOException {
        try (RandomAccessFile accessFile = new RandomAccessFile(file, "r");
             FileChannel channel = accessFile.getChannel()) {
            byte[] signature = new byte[32];
            ByteBuffer buffer = ByteBuffer.allocate(16);
            long randomPosition = ThreadLocalRandom.current().nextLong(0, channel.size());
            long position = 0;
            int shift = 0;
            while (channel.read(buffer) != -1) {
                if (position == 0 || (randomPosition >= position && randomPosition <= position + 16)) {
                    for (int i = 0; i < buffer.array().length; i++) {
                        signature[i + shift] = buffer.array()[i];
                    }
                    shift = 16;
                }
                position += 16;
                buffer.clear();
            }

            return signature;
        }
    }
}
