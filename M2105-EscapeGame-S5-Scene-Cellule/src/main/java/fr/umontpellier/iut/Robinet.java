package fr.umontpellier.iut;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.stage.Screen;
import javafx.util.Duration;

import javax.sound.sampled.*;
import java.io.IOException;


public class Robinet extends Parent implements ElementInteractif{

    private boolean etat;
    private ImageView robinet;
    private ImageView animationEau;

    private RotateTransition rotation;
    private Clip audioEau;

    public Robinet(String cheminImage,String cheminAnimationEau, double x,double y,int taille) {
        this.etat = true;
        //Positionnement robinet
        this.robinet = new ImageView(new Image(SceneCellule.class.getResourceAsStream(cheminImage)));
        robinet.setPreserveRatio(true);
        robinet.setFitWidth(taille);
        robinet.setX(x);
        robinet.setY(y);
        this.getChildren().add(robinet);

        animationEau = new ImageView(new Image(SceneCellule.class.getResourceAsStream(cheminAnimationEau)));
        animationEau.setFitWidth(Screen.getPrimary().getBounds().getWidth());
        animationEau.setFitHeight(Screen.getPrimary().getBounds().getHeight());
        this.getChildren().add(animationEau);
        animationEau.setVisible(false);

        //initialisation de l'animation de rotation
        this.rotation = new RotateTransition(Duration.millis(1000), this.robinet);
        rotation.setDuration(Duration.millis(500));
        rotation.setInterpolator(Interpolator.LINEAR);

        try {
            audioEau = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/sons/eau.wav"));
            audioEau.open(inputStream);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }



        setOnMousePressed(mouseEvent -> actionner());

    }

    public boolean getEtat() {
        return etat;
    }

    //Permet de choisir entre ouvrir ou fermer en fonction de l'état actuel du robinet
    public void actionner(){
        if (etat) {
            audioEau.start();
            audioEau.loop(audioEau.LOOP_CONTINUOUSLY);
            ouvrir();
        } else {
            audioEau.stop();
            fermer();
        }
    }

    public void ouvrir (){
        Clip clip;
        try {
            clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/sons/ouvrirrobinet.wav"));
            clip.open(inputStream);
            clip.start();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }


        rotation.setByAngle(180);
        rotation.play();
        animationEau.setVisible(true);
        etat=!etat;
    }

    public void fermer(){
        Clip clip;
        try {
            clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/sons/fermerRobinet.wav"));
            clip.open(inputStream);
            clip.start();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        rotation.setByAngle(-180);
        rotation.play();
        animationEau.setVisible(false);
        etat=!etat;
    }




}
