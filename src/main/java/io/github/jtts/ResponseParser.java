package io.github.jtts;

import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses the response from the Google TTS RPC endpoint.
 * Extracts the base64-encoded audio data.
 */
public final class ResponseParser {

    private static final Pattern AUDIO_PATTERN = Pattern.compile("jQ1olc\",\"\\[\\\\\"(.*?)\\\\\"]");

    private ResponseParser() {}

    /**
     * Extracts the audio bytes from a successful HTTP response.
     *
     * @param response the OkHttp response
     * @return byte array of MP3 audio
     * @throws IOException if response body is null or parsing fails
     */
    public static byte[] extractAudio(Response response) throws IOException {
        ResponseBody body = response.body();
        if (body == null) {
            throw new IOException("Response body is null");
        }

        String responseBody = body.string();
        Matcher matcher = AUDIO_PATTERN.matcher(responseBody);
        if (matcher.find()) {
            String base64Audio = matcher.group(1);
            return Base64.getDecoder().decode(base64Audio);
        } else {
            throw new IOException("No audio data found in response: " + responseBody);
        }
    }
}