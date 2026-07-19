package io.github.jtts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.util.List;

/**
 * Builds the HTTP POST request for the Google Translate TTS RPC endpoint.
 */
public class RequestBuilder {

    private static final String RPC_METHOD = "jQ1olc";
    private static final String BASE_URL = "https://translate.google.%s/_/TranslateWebserverUi/data/batchexecute";
    private static final MediaType FORM_URLENCODED = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Constructs a request for a single text part.
     *
     * @param tld        top-level domain (e.g., "com", "co.uk")
     * @param textPart   the text chunk (max 100 chars)
     * @param lang       IETF language tag
     * @param speed      speed setting (NORMAL or SLOW)
     * @param userAgent  user agent string
     * @return an OkHttp Request
     * @throws JsonProcessingException if JSON serialization fails
     */
    public static Request build(String tld, String textPart, String lang, Speed speed, String userAgent)
            throws JsonProcessingException {

        // Validation to avoid nulls
        if (tld == null) tld = "com";
        if (textPart == null) textPart = "";
        if (lang == null) lang = "en";
        if (speed == null) speed = Speed.NORMAL;
        if (userAgent == null) userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) " +
                "Chrome/47.0.2526.106 Safari/537.36";

        String url = String.format(BASE_URL, tld);
        Boolean slowFlag = speed.getSlowFlag();

        // parameter = [textPart, lang, slowFlag, null]
        List<Object> parameter = new java.util.ArrayList<>();
        parameter.add(textPart);
        parameter.add(lang);
        parameter.add(slowFlag);
        parameter.add(null);

        String escapedParameter = OBJECT_MAPPER.writeValueAsString(parameter);

        // rpc = [[[RPC_METHOD, escapedParameter, null, "generic"]]]
        List<Object> inner = new java.util.ArrayList<>();
        inner.add(RPC_METHOD);
        inner.add(escapedParameter);
        inner.add(null);
        inner.add("generic");

        List<Object> middle = new java.util.ArrayList<>();
        middle.add(inner);

        List<Object> rpc = new java.util.ArrayList<>();
        rpc.add(middle);

        String rpcJson = OBJECT_MAPPER.writeValueAsString(rpc);
        String formData = "f.req=" + urlEncode(rpcJson);

        RequestBody body = RequestBody.create(formData, FORM_URLENCODED);

        return new Request.Builder()
                .url(url)
                .post(body)
                .header("Referer", "http://translate.google.com/")
                .header("User-Agent", userAgent)
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .build();
    }

    private static String urlEncode(String value) {
        try {
            return java.net.URLEncoder.encode(value, "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 not supported", e);
        }
    }
}