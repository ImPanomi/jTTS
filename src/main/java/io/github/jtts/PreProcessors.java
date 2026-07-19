package io.github.jtts;

/**
 * Collection of default text pre-processors used by gTTS.
 * Mimics the Python module gtts.tokenizer.pre_processors.
 */
public final class PreProcessors {

    private PreProcessors() {}

    /**
     * Removes tone marks (diacritics) from text.
     * Simplified version; full Unicode normalization can be added.
     */
    public static final TextPreProcessor TONE_MARKS = text ->
            // Basic: remove common combining diacritical marks
            text.replaceAll("\\p{M}", "");

    /**
     * Normalizes end-of-line characters to spaces.
     */
    public static final TextPreProcessor END_OF_LINE = text ->
            text.replaceAll("\\r?\\n", " ");

    /**
     * Expands common abbreviations (e.g., "Dr." -> "Doctor").
     * This is a stub; full implementation would require a dictionary.
     */
    public static final TextPreProcessor ABBREVIATIONS = text -> {
        // Placeholder: only a few examples
        String t = text.replaceAll("\\bDr\\.", "Doctor");
        t = t.replaceAll("\\bMr\\.", "Mister");
        t = t.replaceAll("\\bMrs\\.", "Misses");
        t = t.replaceAll("\\bMs\\.", "Miss");
        t = t.replaceAll("\\bSt\\.", "Saint");
        return t;
    };

    /**
     * Substitutes specific words or symbols (e.g., "&" -> "and").
     */
    public static final TextPreProcessor WORD_SUB = text ->
            text.replaceAll("&", " and ");

    /**
     * Chains multiple pre-processors in sequence.
     *
     * @param processors one or more pre-processors
     * @return a combined pre-processor
     */
    public static TextPreProcessor chain(TextPreProcessor... processors) {
        return text -> {
            String result = text;
            for (TextPreProcessor pp : processors) {
                result = pp.process(result);
            }
            return result;
        };
    }
}