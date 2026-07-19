package io.github.jtts;

import okhttp3.Response;

/**
 * Custom exception for gTTS4j that provides context-rich error messages
 * based on HTTP response status, TLD, and known failure patterns.
 */
public class GTTsException extends Exception {

    private final String tld;
    private final transient Response response;

    /**
     * Constructs an exception with a custom message.
     *
     * @param message error detail
     */
    public GTTsException(String message) {
        super(message);
        this.tld = null;
        this.response = null;
    }

    /**
     * Constructs an exception with inferred message from context.
     *
     * @param tld      the TLD used in the request
     * @param response the HTTP response, may be null if connection failed
     */
    public GTTsException(String tld, Response response) {
        super(inferMessage(tld, response));
        this.tld = tld;
        this.response = response;
    }

    /**
     * Attempts to guess the probable cause of failure.
     *
     * @param tld      the TLD used
     * @param response the HTTP response, may be null
     * @return a human-readable error message
     */
    private static String inferMessage(String tld, Response response) {
        if (response == null) {
            String host = "https://translate.google." + (tld != null ? tld : "com");
            return "Failed to connect. Probable cause: Host '" + host + "' is not reachable.";
        }

        int status = response.code();
        String reason = response.message();
        String premise = status + " (" + reason + ") from TTS API";

        String cause;
        if (status == 403) {
            cause = "Bad RPC method or upstream API changes";
        } else if (status == 404 && !"com".equals(tld)) {
            cause = "Unsupported TLD '" + tld + "'";
        } else if (status >= 500) {
            cause = "Upstream API error. Try again later.";
        } else if (status == 200) {
            cause = "No audio stream in response. Possibly unsupported language or malformed payload.";
        } else {
            cause = "Unknown cause. Check HTTP response for details.";
        }

        return premise + ". Probable cause: " + cause;
    }

    public String getTld() {
        return tld;
    }

    public Response getResponse() {
        return response;
    }
}