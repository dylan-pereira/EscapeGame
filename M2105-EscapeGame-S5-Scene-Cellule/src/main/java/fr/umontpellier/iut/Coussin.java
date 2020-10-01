package fr.umontpellier.iut;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.sound.sampled.*;
import java.io.IOException;


public class Coussin extends Parent implements ElementInteractif{
    private Inventaire inventaire;
    private boolean etat;
    private ImageView coussin;

    public Coussin(String chemin,double x,double y,double taille) {
        this.inventaire = new Inventaire(20);
        this.etat = true;
        this.coussin = new ImageView(new Image(SceneCellule.class.getResourceAsStream(chemin)));
        coussin.setPreserveRatio(true);
        coussin.setFitWidth(taille);
        coussin.setX(x);
        coussin.setY(y);
        this.getChildren().add(coussin);

        setOnMousePressed(mouseEvent -> actionner());

    }

    public boolean getEtat() {
        return etat;
    }


    public void actionner(){
        if(etat){
            ouvrir();
        }else{
            fermer();
        }
        etat=!etat;
    }

    public void ouvrir(){
        Clip clip;
        try {
            clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/sons/ouvrirCoussin.wav"));
            clip.open(inputStream);
            clip.start();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        coussin.setX(650);
        coussin.setY(550);
    }

    public void fermer(){
        Clip clip;
        try {
            clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/sons/fermerCoussin.wav"));
            clip.open(inputStream);
            clip.start();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        coussin.setX(780);
        coussin.setY(280);
    }

}
