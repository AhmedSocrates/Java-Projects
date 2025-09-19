package Music;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sound {
    private static Clip currentClip;

    public static void play(String path, boolean loop) {
        new Thread(() -> {
            try {
                stop(); // Stop previous music
                URL url = Sound.class.getResource(path);
                if (url == null) {
                    System.err.println("Audio file not found: " + path);
                    return;
                }
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                currentClip = AudioSystem.getClip();
                currentClip.open(audioIn);
                if (loop) currentClip.loop(Clip.LOOP_CONTINUOUSLY);
                currentClip.start();
            } catch (Exception e) {
                System.err.println("Error playing sound: " + e.getMessage());
            }
        }).start();
    }

    public static void stop() {
        if (currentClip != null && currentClip.isRunning()) {
            currentClip.stop();
            currentClip.close();
        }
    }
}

//public class Sound {
//    private static Clip clip;
//
//    public static void play(String path, boolean loop) {
//        stop();// stop current clip if already playing
//
//        try {
//            URL soundURL = Sound.class.getResource(path);
//            if (soundURL == null) {
//                System.err.println("Could not find file: " + path);
//                return;
//            }
//
//            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundURL);
//            clip = AudioSystem.getClip();
//            clip.open(audioIn);
//            if (loop) {
//                clip.loop(Clip.LOOP_CONTINUOUSLY);
//            } else {
//                clip.start();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public static void stop() {
//        if (clip != null && clip.isRunning()) {
//            clip.stop();
//            clip.flush();
//            clip.close();
//        }
//    }
//}