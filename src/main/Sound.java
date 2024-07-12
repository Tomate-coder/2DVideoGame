package main;

import java.net.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.io.File;

public class Sound {

    Clip clip;
    File soundURL[] = new File[30];

    public Sound() {

        File music1 = new File("./src/sound/theme.wav");
        File sound1 = new File("./src/sound/click.wav");

        // soundURL[0] = getClass().getResource("./src/sound/theme.wav");

        // URL url = music1.toURI();

        // URL url1 = music1.toURI().toURL();
        // soundURL[0] = url1;

        // File music1 = new File("./src/sound/theme.wav");
        soundURL[0] = music1;
        soundURL[1] = sound1;

        // soundURL[0] = getClass().getResource("./src/sound/yoshi.wav");

    }

    public void setFile(int i) {
        try {

            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            // AudioInputStream ais = AudioSystem.getAudioInputStream(new
            // File("./src/sound/yoshi.wav").getAbsoluteFile());

            clip = AudioSystem.getClip();
            clip.open(ais);

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);

    }

    public void stop() {
        clip.stop();
    }

}
