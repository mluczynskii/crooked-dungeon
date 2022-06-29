package main;

import java.net.URL;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
    Clip clip; // Must be in .wav format

    // Most of sound effects come from freesound.org but some of them are recorded by us
    static String[] fileNames = {"stupid.wav", "slime-death.wav", "crooked-death.wav", "sword-sound.wav", "player-hit.wav",
                                 "slime-hit.wav", "coin-pickup.wav", "health-pickup.wav"};

    static String filePath = "/audio/";
    static HashMap<String, Clip> files;
    
    public Sound () {
        if (files == null) {
            files = new HashMap<>();
            for (int i = 0; i < fileNames.length; i++) {
                try {
                    URL url = getClass().getResource(filePath + fileNames[i]);
                    AudioInputStream a = AudioSystem.getAudioInputStream(url);
                    Clip c = AudioSystem.getClip();
                    c.open(a);

                    // Play muted once to fix lag
                    clip = c;
                    play(0f);
                    clip = null;

                    files.put(fileNames[i], c);
                } catch (Exception e) {
                    System.out.println("Something's wrong with: " + fileNames[i]);
                    e.printStackTrace();
                }
            }
        }
    }
    public void setFile (String name) {
        if (clip != null)
            stop();
        clip = files.get(name);
    }
    // https://stackoverflow.com/questions/40514910/set-volume-of-java-clip
    float getVolume() {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);        
        return (float) Math.pow(10f, gainControl.getValue() / 20f);
    }
    public void setVolume(float volume) {
        if (volume < 0f || volume > 1f)
            throw new IllegalArgumentException("Volume not valid: " + volume);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);        
        gainControl.setValue(20f * (float) Math.log10(volume));
    }
    public void play (float volume) { 
        clip.setFramePosition(0);
        setVolume(volume);
        clip.start();
    }
    public void resume () { clip.start(); }
    public void loop () { clip.loop(Clip.LOOP_CONTINUOUSLY); }
    public void stop () { clip.stop(); }
}
