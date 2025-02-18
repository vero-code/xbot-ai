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

    /**
     * Retrieves the entire portion of text following the specified keyword.
     * If the text after the keyword is empty, returns null.
     */
    public static String parseAllWordsAfter(String text, String keyword) {
        String lowerText = text.toLowerCase();
        String lowerKeyword = keyword.toLowerCase();

        int index = lowerText.indexOf(lowerKeyword);
        if (index < 0) {
            return null;
        }

        int start = index + lowerKeyword.length();
        String remainder = text.substring(start).trim();
        return remainder.isEmpty() ? null : remainder;
    }

    /**
     * Extracts the entire portion of text following a keyword and replaces spaces with underscores.
     * Returns null if there is no text after the keyword.
     */
    public static String parseAllWordsAfterAsOne(String text, String keyword) {
        String remainder = parseAllWordsAfter(text, keyword);
        return (remainder != null) ? remainder.replaceAll("\\s+", "_") : null;
    }

}
