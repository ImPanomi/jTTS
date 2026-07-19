package io.github.jtts;

/**
 * Functional interface for text pre-processing.
 * Corresponds to the pre-processor functions in Python gTTS.
 */
@FunctionalInterface
public interface TextPreProcessor {
    /**
     * Transforms the input text.
     *
     * @param text the original text
     * @return the transformed text
     */
    String process(String text);
}