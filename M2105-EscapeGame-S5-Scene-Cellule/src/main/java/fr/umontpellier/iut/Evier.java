package fr.umontpellier.iut;

import javafx.animation.AnimationTimer;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;

import javax.sound.sampled.*;
import java.io.IOException;

public class Evier extends Parent {

    private boolean rempli;
    private boolean bouchon;//fermer ou non

    private Robinet robinet1;
    private Robinet robinet2;

    private ImageView eauCentrale;
    private ImageView bouchonEvier;
    private ImageView eauRempli;
    private ImageView eauRempliNoire;

    public Evier(){
        rempli=false;
        bouchon=false;
        robinet1 = new Robinet("/images/robinetCellule1.png","/images/eaurobinet1.png",270,595,30);
        robinet2 = new Robinet("/images/robinetCellule2.png","/images/eaurobinet2.png",270,645,30);

        //Importation du bouchon d'évier
        bouchonEvier = new ImageView(new Image(SceneCellule.class.getResourceAsStream("/images/bouchon évier.png")));
        bouchonEvier.setFitWidth(Screen.getPrimary().getBounds().getWidth());bouchonEvier.setFitHeight(Screen.getPrimary().getBounds().getHeight());bouchonEvier.setVisible(false);
        //Importation de l'eau centrale
        eauCentrale = new ImageView(new Image(SceneCellule.class.getResourceAsStream("/images/eaucentrale.png")));
        eauCentrale.setFitWidth(Screen.getPrimary().getBounds().getWidth());eauCentrale.setFitHeight(Screen.getPrimary().getBounds().getHeight());eauCentrale.setVisible(false);
        this.getChildren().addAll(robinet1,robinet2,bouchonEvier,eauCentrale);
        //Importation de l'eau rempli
        eauRempli = new ImageView(new Image(SceneCellule.class.getResourceAsStream("/images/eaubleue.png")));
        eauRempli.setFitWidth(Screen.getPrimary().getBounds().getWidth());eauRempli.setFitHeight(Screen.getPrimary().getBounds().getHeight());eauRempli.setVisible(false);
        this.getChildren().add(eauRempli);
        //Importation de l'eau rempli noire
        eauRempliNoire = new ImageView(new Image(SceneCellule.class.getResourceAsStream("/images/eaunoire.png")));
        eauRempliNoire.setFitWidth(Screen.getPrimary().getBounds().getWidth());eauRempliNoire.setFitHeight(Screen.getPrimary().getBounds().getHeight());eauRempliNoire.setVisible(false);
        this.getChildren().add(eauRempliNoire);


        eauRempli.setOnMousePressed(mouseEvent -> {
            Clip clip;
            try {
                clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/sons/bubbling.wav"));
                clip.open(inputStream);
                clip.start();
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                e.printStackTrace();
            }

            eauRempli.setVisible(false);
            eauRempliNoire.setVisible(true);
        });


        AnimationTimer timer = new AnimationTimer() {
            public void handle(long now) {
                eauCentrale.setVisible(!robinet2.getEtat() || !robinet1.getEtat());

                if(!robinet1.getEtat() && !robinet2.getEtat() && bouchonEvier.isVisible()){
                    remplir();
                }
            }
        };
        timer.start();

    }

    public void setBouchon(){
        bouchonEvier.setVisible(!bouchon);
        if(!bouchon){
            Clip clip;
            try {
                clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/sons/avecbouchon.wav"));
                clip.open(inputStream);
                clip.start();
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                e.printStackTrace();
            }
        }else{
            Clip clip;
            try {
                clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/sons/sansbouchon.wav"));
                clip.open(inputStream);
                clip.start();
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                e.printStackTrace();
            }
            if(rempli){
                rempli=!rempli;
                eauRempli.setVisible(false);
                eauRempliNoire.setVisible(false);
                robinet1.setDisable(false);
                robinet2.setDisable(false);
            }
        }
        bouchon=!bouchon;
    }

    public void remplir(){
        rempli =!rempli;
        eauRempli.setVisible(true);
        robinet1.actionner();robinet2.actionner();
        robinet1.setDisable(true);robinet2.setDisable(true);
    }



}
