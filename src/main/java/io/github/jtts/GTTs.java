package io.github.jtts;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Main gTTS4j class. Provides Text-to-Speech synthesis using Google Translate.
 */
public class GTTs {

    public static final int MAX_CHARS = 100;

    private final String text;
    private final String tld;
    private final String lang;
    private final Speed speed;
    private final boolean langCheck;
    private final TextPreProcessor preProcessor;
    private final OkHttpClient httpClient;
    private final String userAgent;

    /**
     * Builder-style constructor.
     */
    private GTTs(Builder builder) {
        this.text = builder.text;
        this.tld = builder.tld;
        this.speed = builder.speed;
        this.langCheck = builder.langCheck;
        this.preProcessor = builder.preProcessor;
        this.httpClient = builder.httpClient;
        this.userAgent = builder.userAgent;

        // Validate text
        if (this.text == null || this.text.trim().isEmpty()) {
            throw new IllegalArgumentException("No text to speak");
        }

        // Language validation with fallback
        String effectiveLang = builder.lang;  // temporary variable
        if (this.langCheck) {
            String fallbackLang = LanguageManager.fallbackDeprecated(builder.lang);
            if (!LanguageManager.isSupported(fallbackLang)) {
                throw new IllegalArgumentException("Language not supported: " + builder.lang);
            }
            effectiveLang = fallbackLang;
        }
        this.lang = effectiveLang;  // assign only once
    }

    /**
     * Returns a new Builder instance.
     *
     * @param text the text to speak
     * @return Builder
     */
    public static Builder builder(String text) {
        return new Builder(text);
    }

    /**
     * Generates the TTS audio and writes it to the given OutputStream.
     *
     * @param out destination OutputStream (must support bytes)
     * @throws GTTsException if any TTS error occurs
     */
    public void writeToOutputStream(OutputStream out) throws GTTsException {
        try {
            for (byte[] chunk : stream()) {
                out.write(chunk);
            }
            out.flush();
        } catch (IOException e) {
            throw new GTTsException("I/O error while writing to stream: " + e.getMessage());
        }
    }

    /**
     * Saves the TTS audio to a file.
     *
     * @param filePath path to the output MP3 file
     * @throws GTTsException if any TTS error occurs
     */
    public void save(String filePath) throws GTTsException {
        try (java.io.FileOutputStream fos = new java.io.FileOutputStream(filePath)) {
            writeToOutputStream(fos);
        } catch (IOException e) {
            throw new GTTsException("Could not write to file: " + filePath);
        }
    }

    /**
     * Streams audio chunks as byte arrays.
     *
     * @return Iterable of byte arrays (each is a MP3 chunk)
     * @throws GTTsException if request fails
     */
    public Iterable<byte[]> stream() throws GTTsException {
        List<byte[]> audioChunks = new ArrayList<>();

        List<String> tokens = tokenizeText();
        if (tokens.isEmpty()) {
            throw new GTTsException("No text to send to TTS API");
        }

        for (int idx = 0; idx < tokens.size(); idx++) {
            String part = tokens.get(idx);
            try {
                Request request = RequestBuilder.build(tld, part, lang, speed, userAgent);
                Response response = httpClient.newCall(request).execute();

                if (!response.isSuccessful()) {
                    throw new GTTsException(tld, response);
                }

                byte[] audio = ResponseParser.extractAudio(response);
                audioChunks.add(audio);

                response.close();

            } catch (IOException e) {
                throw new GTTsException("Network error: " + e.getMessage());
            }
        }

        return audioChunks;
    }

    private List<String> tokenizeText() {
        // Pre-process text
        String processed = preProcessor.process(text);

        // Tokenize
        List<String> tokens = TextTokenizer.tokenize(processed);

        // Clean tokens
        tokens = TextUtils.cleanTokens(tokens);

        // Minimize each token to max 100 chars
        List<String> finalTokens = new ArrayList<>();
        for (String t : tokens) {
            finalTokens.addAll(TextUtils.minimize(t, " ", MAX_CHARS));
        }

        // Filter empty
        finalTokens.removeIf(String::isEmpty);
        return finalTokens;
    }

    // ==================== Builder ====================

    public static class Builder {
        private final String text;
        private String tld = "com";
        private String lang = "en";
        private Speed speed = Speed.NORMAL;
        private boolean langCheck = true;
        private TextPreProcessor preProcessor = PreProcessors.chain(
                PreProcessors.TONE_MARKS,
                PreProcessors.END_OF_LINE,
                PreProcessors.ABBREVIATIONS,
                PreProcessors.WORD_SUB
        );
        private OkHttpClient httpClient = new OkHttpClient();
        private String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) " +
                "Chrome/47.0.2526.106 Safari/537.36";

        public Builder(String text) {
            this.text = text;
        }

        public Builder tld(String tld) {
            this.tld = tld;
            return this;
        }

        public Builder lang(String lang) {
            this.lang = lang;
            return this;
        }

        public Builder speed(Speed speed) {
            this.speed = speed;
            return this;
        }

        public Builder langCheck(boolean langCheck) {
            this.langCheck = langCheck;
            return this;
        }

        public Builder preProcessor(TextPreProcessor preProcessor) {
            this.preProcessor = preProcessor;
            return this;
        }

        public Builder httpClient(OkHttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        public Builder userAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public GTTs build() {
            return new GTTs(this);
        }
    }
}