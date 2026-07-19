package io.github.jtts;

/**
 * Represents the speech speed for Google TTS.
 * Maps to the boolean 'slow' parameter in the API payload.
 */
public enum Speed {
    /**
     * Normal reading speed.
     */
    NORMAL(false),

    /**
     * Slower reading speed.
     */
    SLOW(true);

    private final boolean slowFlag;

    Speed(boolean slowFlag) {
        this.slowFlag = slowFlag;
    }

    /**
     * Returns the boolean flag used in the Google TTS RPC payload.
     *
     * @return true for slow, false for normal
     */
    public boolean getSlowFlag() {
        return slowFlag;
    }
}