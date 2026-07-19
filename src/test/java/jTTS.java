import io.github.jtts.GTTs;
import io.github.jtts.Speed;

public class jTTS {
    public static void main(String[] args) {
        try {
            System.out.println("Starting TTS test...");

            GTTs tts = GTTs.builder("Hello World.")
                    .lang("en")
                    .speed(Speed.NORMAL)
                    .build();

            System.out.println("Generating audio...");
            tts.save("output.mp3");
            System.out.println("Audio generated successfully!");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}