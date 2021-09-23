package ui;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.io.File;

//A splash screen for our logic calculator Gui
public class SplashScreen extends JFrame {
    private JLabel loadingAnimation;
    private Icon icon;
    private final String sound1 = "data/buttonSound1.wav";

    // runs the splash screen
    public SplashScreen() {
        icon = new ImageIcon("data/calculatorSplashScreen1.png");
        loadingAnimation = new JLabel(icon);
        getContentPane().add(loadingAnimation);
        setBounds(0,0, icon.getIconWidth(), icon.getIconHeight());
        setUndecorated(true);
        setLocationRelativeTo(null);
        setVisible(true);
        playSound(sound1);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(null,
                    "Unable to run loading screen",
                    "Error Loading", JOptionPane.ERROR_MESSAGE);
        }
        dispose();
    }

    //REQUIRES: soundLocation must be a valid location of a sound wav file
    //EFFECTS: plays a sound file at the soundLocation, if unable to then display an "Unable to play audio" error
    public void playSound(String soundLocation) {
        //code modelled from: https://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java
        try {
            File soundFile = new File(soundLocation);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile.getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Unable to play audio",
                    "Error Loading", JOptionPane.ERROR_MESSAGE);
        }
    }
}
