package com.shaposhnikov;

public class Main {

    private static final int SIZE = 33;

    public static final char[] ALPHABET = new char[]{
            'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З',
            'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П',
            'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч',
            'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я'
    };

    public static void main(String[] args) {
        String text = "СОВАНАПНЕ";
        String key = "ШЕРЛОК";
        System.out.println(decrypt(encrypt(text, key), key));
    }

    public static String encrypt(String text, String key) {
        char[] textChars = text.toCharArray();
        char[] keyChars = key.toCharArray();
        char[][] table = generateTable();
        char[] result = new char[text.length()];
        for (int i = 0; i < textChars.length; i++) {
            int column = 0;
            int row  = 0;
            for (int j = 0; j < SIZE; j++) {
                if (textChars[i] == table[0][j]) {
                    column = j;
                }
                if (keyChars[i % keyChars.length] == table[j][0]) {
                    row = j;
                }
            }
            result[i] = table[row][column];
        }

        return new String(result);
    }

    public static String decrypt(String text, String key) {
        char[] textChars = text.toCharArray();
        char[] keyChars = key.toCharArray();
        char[][] table = generateTable();
        char[] result = new char[text.length()];
        for (int i = 0; i < textChars.length; i++) {
            int column = 0;
            int row  = 0;
            for (int j = 0; j < SIZE; j++) {
                if (keyChars[i % keyChars.length] == table[j][0]) {
                    row = j;
                }
            }

            for (int j = 0; j < SIZE; j++) {
                if (textChars[i] == table[row][j]) {
                    column = j;
                }
            }

            result[i] = table[0][column];
        }

        return new String(result);
    }

    private static char[][] generateTable() {
        char[][] table = new char[SIZE][SIZE];
        int shift = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = shift; j < SIZE + shift; j++) {
                table[i][j - shift] = ALPHABET[j % SIZE];
            }
            shift++;
        }
        return table;
    }

    public static void outtputTable() {
        char[][] chars = generateTable();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(chars[i][j]);
            }
            System.out.println();
        }
    }
}
