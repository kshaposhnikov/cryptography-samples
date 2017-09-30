package com.shaposhnikov.steganography.ruen;

import java.util.HashMap;
import java.util.Map;

public final class Dictionary {

    private static final Map<Character, Character> DICTIONARY = new HashMap<>();

    static {
        DICTIONARY.put('А', 'A');
        DICTIONARY.put('В', 'B');
        DICTIONARY.put('С', 'C');
        DICTIONARY.put('Н', 'H');
        DICTIONARY.put('К', 'K');
        DICTIONARY.put('Т', 'T');
        DICTIONARY.put('Е', 'E');
        DICTIONARY.put('Р', 'P');
        DICTIONARY.put('а', 'a');
        DICTIONARY.put('с', 'c');
        DICTIONARY.put('е', 'e');
        DICTIONARY.put('х', 'x');
        DICTIONARY.put('р', 'p');
        DICTIONARY.put('о', 'o');
        DICTIONARY.put('у', 'y');
        DICTIONARY.put('к', 'k');
    }

    public static Character get(Character key) {
        return DICTIONARY.get(key);
    }

    public static boolean symbolWasReplaced(Character value) {
        return DICTIONARY.containsValue(value);
    }
}
