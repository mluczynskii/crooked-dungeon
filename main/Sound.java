package main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
    Clip clip; // Must be in .wav format
    static String[] fileNames = {"stupid.wav"};
    static String filePath = "/audio/";
    URL[] files = new URL[fileNames.length];
    
    public Sound () {
        for (int i = 0; i < fileNames.length; i++) {
            files[i] = getClass().getResource(filePath + fileNames[i]);
        }
    }
    public void setFile (int i) {
        try {
            AudioInputStream a = AudioSystem.getAudioInputStream(files[i]);
            clip = AudioSystem.getClip();
            clip.open(a);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        setVolume(volume);
        clip.start();
    }
    public void loop () { clip.loop(Clip.LOOP_CONTINUOUSLY); }
    public void stop () { clip.stop(); }
}
