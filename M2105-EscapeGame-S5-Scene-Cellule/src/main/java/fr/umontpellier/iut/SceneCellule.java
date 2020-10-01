package fr.umontpellier.iut;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;

import javax.sound.sampled.*;
import java.io.IOException;

public class SceneCellule extends Parent {

    private final double screenheight = Screen.getPrimary().getBounds().getHeight();
    private final double screenwidth  = Screen.getPrimary().getBounds().getWidth();

    public SceneCellule() {

        //Création Fond Cellule
        ImageView fondCellule = new ImageView(new Image(SceneCellule.class.getResourceAsStream("/images/fondCellule.jpg")));
        fondCellule.setFitWidth(screenwidth);fondCellule.setFitHeight(screenheight);

        //Création du coussin
        Coussin coussin = new Coussin("/images/coussin.png",780,280,screenwidth/11);

        //Création de l'évier
        Evier evier = new Evier();
        this.getChildren().addAll(fondCellule,coussin,evier);



        //Importation du fond de l'inventaire
        ImageView fondinventaire = new ImageView(new Image(SceneCellule.class.getResourceAsStream("/images/fondinventaire.png")));
        fondinventaire.setPreserveRatio(true);fondinventaire.setFitHeight(350);
        fondinventaire.setX(800);fondinventaire.setY(200);



        //Importation de la touche du Digicode
        ImageView toucheDigicode6 = new ImageView(new Image(SceneCellule.class.getResourceAsStream("/images/bouton6Digicode.png")));
        toucheDigicode6.setPreserveRatio(true);toucheDigicode6.setFitHeight(15);
        toucheDigicode6.setX(550);toucheDigicode6.setY(270);toucheDigicode6.setVisible(false);
        this.getChildren().addAll(fondinventaire,toucheDigicode6);




        //Création d'un bouton dans les toilettes qui permet de recuperer la touche du digicode
        Button boutonToilette = new Button();
        boutonToilette.setTranslateX(560);
        boutonToilette.setTranslateY(365);
        boutonToilette.setMinSize(30,30);
        boutonToilette.setOpacity(0);
        this.getChildren().add(boutonToilette);

        //Création d'un bouton temporaire pour le bouchon de l'évier
        Button boutonEvier = new Button();boutonEvier.setMinSize(20,10);boutonEvier.setTranslateX(405);boutonEvier.setTranslateY(610);boutonEvier.setOpacity(0);
        this.getChildren().add(boutonEvier);
        Button boutontest = new Button();boutontest.setTranslateX(screenwidth-100);boutontest.setText("Suivant");boutontest.setTranslateY(screenheight/2);
        this.getChildren().add(boutontest);

        //Création d'un bouton d'aide
        ImageView boutonAide = new ImageView(new Image(SceneCellule.class.getResourceAsStream("/images/boutonAide.png")));
        boutonAide.setPreserveRatio(true);
        boutonAide.setFitHeight(50);
        this.getChildren().add(boutonAide);
        boutonAide.setX(960);
        boutonAide.setY(980);

        //Création Label explicatif
        Label labelAide = new Label();
        labelAide.setText("Vous vous trouvez actuellement dans votre cellule.\n" +
                "Explorez la afin de comprendre l'ensemble des possibilités qu'elle vous offre.\n\n" +
                "Utilisez l'évier pour y confectionner des recettes!\n" +
                "Vous pouvez cacher des objets sous votre coussin. Ainsi, les gardes ne pourront pas vous voir avec.\n\n" +
                "Attention, vous ne pouvez porter qu'un seul objet à la fois ! Montrez-vous astucieux pour sortir de cette prison rapidement !");
        labelAide.setStyle("-fx-background-color:rgba(31,31,31,0.95);-fx-font-size:30px;-fx-padding: 20;-fx-text-fill: white;-fx-border-width: 3px;-fx-border-color: white");
        labelAide.setTranslateX(100);labelAide.setTranslateY(screenheight/4);
        labelAide.setVisible(false);
        this.getChildren().add(labelAide);

        //  Apparition / Disparition du Label explicatif
        boutonAide.setOnMouseEntered(mouseEvent -> labelAide.setVisible(true));
        boutonAide.setOnMouseExited(mouseEvent -> labelAide.setVisible(false));

        //  Apparition de la touche du digicode + son + désactivation du bouton apres 1 utilisation
        boutonToilette.setOnMousePressed(mouseEvent -> {
            Clip clip;
            try {
                clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/sons/goutteEau.wav"));
                clip.open(inputStream);
                clip.start();
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                e.printStackTrace();
            }
            toucheDigicode6.setVisible(true);
            boutonToilette.setDisable(true);
        });

        //Fonction qui va analyser en permanence l'état du robinet et de l'inventaire
        AnimationTimer timer = new AnimationTimer() {
            public void handle(long now) {
                fondinventaire.setVisible(!coussin.getEtat());
            }
        };
        timer.start();

        toucheDigicode6.setOnMousePressed(mouseEvent -> {
            Clip clip;
            try {
                clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/sons/recupererTouche.wav"));
                clip.open(inputStream);
                clip.start();
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                e.printStackTrace();
            }
            toucheDigicode6.setVisible(false);
            System.out.println("A implémenter");
        });


        boutonEvier.setOnMousePressed(mouseEvent -> evier.setBouchon());
        boutontest.setOnMousePressed(mouseEvent -> {
            this.getChildren().removeAll(this.getChildren());
            Clip clip;
            try {
                clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/sons/sortieCellule.wav"));
                clip.open(inputStream);
                clip.start();
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                e.printStackTrace();
            }
            Platform.exit();
        });



    }




}