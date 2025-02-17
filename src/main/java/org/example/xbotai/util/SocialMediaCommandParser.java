package org.example.xbotai.util;

public class SocialMediaCommandParser {

    /**
     * Extracts the word immediately following the specified keyword in the text.
     * Returns null if the word is not found.
     */
    public static String parseNextWordAfter(String text, String keyword) {
        String lowerText = text.toLowerCase();
        String lowerKeyword = keyword.toLowerCase();

        int index = lowerText.indexOf(lowerKeyword);
        if (index < 0) {
            return null;
        }

        int start = index + lowerKeyword.length();
        String remainder = text.substring(start).trim();
        String[] tokens = remainder.split("\\s+");
        if (tokens.length > 0) {
            return tokens[0];
        }
        return null;
    }
}
