package fr.umontpellier.iut;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
 
    public static void main(String[] args) {
	launch(args);
    }

	@Override
	public void start(Stage primaryStage){

		primaryStage.setTitle("EscapeGame");
		Group root = new Group();
		Scene scene = new Scene(root, 800, 800);



		SceneCellule scenecellule = new SceneCellule();
		root.getChildren().add(scenecellule);

		primaryStage.setScene(scene);
		primaryStage.setFullScreen(true);
		primaryStage.show();
    }
 
}