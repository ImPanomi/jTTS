package io.github.jtts;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Utility methods for text cleaning and minimization.
 */
public final class TextUtils {

    private static final Pattern ALL_PUNC_OR_SPACE = Pattern.compile("^[\\p{Punct}\\s]*$");

    private TextUtils() {}

    /**
     * Cleans a list of tokens by stripping whitespace and removing
     * tokens that consist only of punctuation or spaces.
     *
     * @param tokens raw tokens
     * @return cleaned token list
     */
    public static List<String> cleanTokens(List<String> tokens) {
        List<String> cleaned = new ArrayList<>();
        for (String t : tokens) {
            String s = t.trim();
            if (!s.isEmpty() && !ALL_PUNC_OR_SPACE.matcher(s).matches()) {
                cleaned.add(s);
            }
        }
        return cleaned;
    }

    /**
     * Minimizes a string into chunks no larger than maxSize,
     * splitting on the last occurrence of a delimiter within the limit.
     *
     * @param text    the string to split
     * @param delim   delimiter (e.g., space)
     * @param maxSize maximum chunk size
     * @return list of minimized chunks
     */
    public static List<String> minimize(String text, String delim, int maxSize) {
        List<String> result = new ArrayList<>();
        minimizeRecursive(text, delim, maxSize, result);
        return result;
    }

    private static void minimizeRecursive(String text, String delim, int maxSize, List<String> acc) {
        if (text.startsWith(delim)) {
            text = text.substring(delim.length());
        }
        if (text.length() <= maxSize) {
            if (!text.isEmpty()) {
                acc.add(text);
            }
            return;
        }
        int idx = text.lastIndexOf(delim, maxSize);
        if (idx < 0) {
            idx = maxSize;
        }
        String part = text.substring(0, idx);
        if (!part.isEmpty()) {
            acc.add(part);
        }
        String rest = text.substring(idx);
        minimizeRecursive(rest, delim, maxSize, acc);
    }
}