package me.invis.holo.util;

public class ArrayUtil {

    public static String fromArray(String... array) {
        StringBuilder output = new StringBuilder();
        for (String s : array) output.append(s).append(" ");
        output.deleteCharAt(output.length()-1);

        return output.toString();
    }

}
