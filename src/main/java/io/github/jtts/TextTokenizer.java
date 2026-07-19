package io.github.jtts;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Tokenizes text based on punctuation and whitespace.
 * Inspired by gtts.tokenizer.Tokenizer and tokenizer_cases.
 */
public class TextTokenizer {

    private static final Pattern TONE_MARKS_PATTERN = Pattern.compile("[。？！、，；：¡!()\\[\\]¿?.,،;:—…\\n]");
    private static final Pattern PERIOD_COMMA_PATTERN = Pattern.compile("[.,]");
    private static final Pattern COLON_PATTERN = Pattern.compile(":");
    private static final Pattern OTHER_PUNCT_PATTERN = Pattern.compile("[;—…]");

    /**
     * Splits text into sentences/tokens based on punctuation.
     *
     * @param text the input text
     * @return list of token strings
     */
    public static List<String> tokenize(String text) {
        // Use a combined pattern or sequential splits.
        // Simpler: split on punctuation that likely ends a phrase.
        String[] rawTokens = text.split("(?<=[。？！，、；：¡!()\\[\\]¿?.,،;:—…\\n])");
        List<String> tokens = new ArrayList<>();
        for (String t : rawTokens) {
            String trimmed = t.trim();
            if (!trimmed.isEmpty()) {
                tokens.add(trimmed);
            }
        }
        return tokens;
    }

    /**
     * A more refined tokenizer that applies specific cases (similar to Python).
     * For now, this is a simplified version.
     */
    public static List<String> tokenizeWithCases(String text) {
        // This can be expanded to match the Python tokenizer_cases logic.
        return tokenize(text);
    }
}