package com.pandapip1.jchem.utils;

public class SubscriptConverter {
    // Convert a string with numbers and letters to a string with subscripts
    public static String convert(String s) {
        s = s.replace("0", "₀");
        s = s.replace("1", "₁");
        s = s.replace("2", "₂");
        s = s.replace("3", "₃");
        s = s.replace("4", "₄");
        s = s.replace("5", "₅");
        s = s.replace("6", "₆");
        s = s.replace("7", "₇");
        s = s.replace("8", "₈");
        s = s.replace("9", "₉");
        s = s.replace("a", "ₐ");
        s = s.replace("e", "ᵉ");
        s = s.replace("i", "ᵢ");
        s = s.replace("o", "ᵒ");
        s = s.replace("r", "ᵣ");
        s = s.replace("t", "ᵗ");
        s = s.replace("u", "ᵘ");
        s = s.replace("v", "ᵛ");
        s = s.replace("x", "ˣ");
        s = s.replace("y", "ʸ");
        s = s.replace("(", "₍");
        s = s.replace(")", "₎");
        return s;
    }

    // Convert a string's numbers to subscripts
    public static String convertNums(String s) {
        s = s.replace("0", "₀");
        s = s.replace("1", "₁");
        s = s.replace("2", "₂");
        s = s.replace("3", "₃");
        s = s.replace("4", "₄");
        s = s.replace("5", "₅");
        s = s.replace("6", "₆");
        s = s.replace("7", "₇");
        s = s.replace("8", "₈");
        s = s.replace("9", "₉");
        return s;
    }
}
