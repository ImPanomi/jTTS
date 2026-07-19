package io.github.jtts;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages supported languages and provides fallback for deprecated tags.
 * Based on the Python gTTS language list (gtts.lang).
 */
public final class LanguageManager {

    private static final Map<String, String> LANGUAGES = new HashMap<>();

    static {
        // Populate with the latest from gTTS 2.5.4 (langs.py)
        LANGUAGES.put("af", "Afrikaans");
        LANGUAGES.put("am", "Amharic");
        LANGUAGES.put("ar", "Arabic");
        LANGUAGES.put("bg", "Bulgarian");
        LANGUAGES.put("bn", "Bengali");
        LANGUAGES.put("bs", "Bosnian");
        LANGUAGES.put("ca", "Catalan");
        LANGUAGES.put("cs", "Czech");
        LANGUAGES.put("cy", "Welsh");
        LANGUAGES.put("da", "Danish");
        LANGUAGES.put("de", "German");
        LANGUAGES.put("el", "Greek");
        LANGUAGES.put("en", "English");
        LANGUAGES.put("es", "Spanish");
        LANGUAGES.put("et", "Estonian");
        LANGUAGES.put("eu", "Basque");
        LANGUAGES.put("fi", "Finnish");
        LANGUAGES.put("fr", "French");
        LANGUAGES.put("fr-CA", "French (Canada)");
        LANGUAGES.put("gl", "Galician");
        LANGUAGES.put("gu", "Gujarati");
        LANGUAGES.put("ha", "Hausa");
        LANGUAGES.put("hi", "Hindi");
        LANGUAGES.put("hr", "Croatian");
        LANGUAGES.put("hu", "Hungarian");
        LANGUAGES.put("id", "Indonesian");
        LANGUAGES.put("is", "Icelandic");
        LANGUAGES.put("it", "Italian");
        LANGUAGES.put("iw", "Hebrew");
        LANGUAGES.put("ja", "Japanese");
        LANGUAGES.put("jw", "Javanese");
        LANGUAGES.put("km", "Khmer");
        LANGUAGES.put("kn", "Kannada");
        LANGUAGES.put("ko", "Korean");
        LANGUAGES.put("la", "Latin");
        LANGUAGES.put("lt", "Lithuanian");
        LANGUAGES.put("lv", "Latvian");
        LANGUAGES.put("ml", "Malayalam");
        LANGUAGES.put("mr", "Marathi");
        LANGUAGES.put("ms", "Malay");
        LANGUAGES.put("my", "Myanmar (Burmese)");
        LANGUAGES.put("ne", "Nepali");
        LANGUAGES.put("nl", "Dutch");
        LANGUAGES.put("no", "Norwegian");
        LANGUAGES.put("pa", "Punjabi (Gurmukhi)");
        LANGUAGES.put("pl", "Polish");
        LANGUAGES.put("pt", "Portuguese (Brazil)");
        LANGUAGES.put("pt-PT", "Portuguese (Portugal)");
        LANGUAGES.put("ro", "Romanian");
        LANGUAGES.put("ru", "Russian");
        LANGUAGES.put("si", "Sinhala");
        LANGUAGES.put("sk", "Slovak");
        LANGUAGES.put("sq", "Albanian");
        LANGUAGES.put("sr", "Serbian");
        LANGUAGES.put("su", "Sundanese");
        LANGUAGES.put("sv", "Swedish");
        LANGUAGES.put("sw", "Swahili");
        LANGUAGES.put("ta", "Tamil");
        LANGUAGES.put("te", "Telugu");
        LANGUAGES.put("th", "Thai");
        LANGUAGES.put("tl", "Filipino");
        LANGUAGES.put("tr", "Turkish");
        LANGUAGES.put("uk", "Ukrainian");
        LANGUAGES.put("ur", "Urdu");
        LANGUAGES.put("vi", "Vietnamese");
        LANGUAGES.put("yue", "Cantonese");
        LANGUAGES.put("zh-CN", "Chinese (Simplified)");
        LANGUAGES.put("zh-TW", "Chinese (Traditional)");
        // Extra from gtts.lang._extra_langs
        LANGUAGES.put("zh", "Chinese (Mandarin)");
    }

    private static final Map<String, String> DEPRECATED_FALLBACK = new HashMap<>();
    static {
        DEPRECATED_FALLBACK.put("en-us", "en");
        DEPRECATED_FALLBACK.put("en-ca", "en");
        DEPRECATED_FALLBACK.put("en-uk", "en");
        DEPRECATED_FALLBACK.put("en-gb", "en");
        DEPRECATED_FALLBACK.put("en-au", "en");
        DEPRECATED_FALLBACK.put("en-gh", "en");
        DEPRECATED_FALLBACK.put("en-in", "en");
        DEPRECATED_FALLBACK.put("en-ie", "en");
        DEPRECATED_FALLBACK.put("en-nz", "en");
        DEPRECATED_FALLBACK.put("en-ng", "en");
        DEPRECATED_FALLBACK.put("en-ph", "en");
        DEPRECATED_FALLBACK.put("en-za", "en");
        DEPRECATED_FALLBACK.put("en-tz", "en");
        DEPRECATED_FALLBACK.put("fr-fr", "fr");
        DEPRECATED_FALLBACK.put("pt-br", "pt");
        DEPRECATED_FALLBACK.put("pt-pt", "pt");
        DEPRECATED_FALLBACK.put("es-es", "es");
        DEPRECATED_FALLBACK.put("es-us", "es");
        DEPRECATED_FALLBACK.put("zh-cn", "zh-CN");
        DEPRECATED_FALLBACK.put("zh-tw", "zh-TW");
    }

    private LanguageManager() {}

    /**
     * Checks if a given language tag is supported.
     *
     * @param lang IETF language tag (e.g., "en", "fr-CA")
     * @return true if supported, false otherwise
     */
    public static boolean isSupported(String lang) {
        return LANGUAGES.containsKey(lang);
    }

    /**
     * Returns the language name for a given tag.
     *
     * @param lang IETF language tag
     * @return the full English name, or null if not found
     */
    public static String getLanguageName(String lang) {
        return LANGUAGES.get(lang);
    }

    /**
     * Applies fallback for deprecated language tags.
     * For example, "en-US" becomes "en".
     *
     * @param lang the original language tag
     * @return the fallback tag if deprecated, otherwise the original
     */
    public static String fallbackDeprecated(String lang) {
        String lower = lang.toLowerCase();
        return DEPRECATED_FALLBACK.getOrDefault(lower, lang);
    }

    /**
     * Provides an unmodifiable view of the supported languages map.
     *
     * @return map of language tags to names
     */
    public static Map<String, String> getSupportedLanguages() {
        return new HashMap<>(LANGUAGES);
    }
}