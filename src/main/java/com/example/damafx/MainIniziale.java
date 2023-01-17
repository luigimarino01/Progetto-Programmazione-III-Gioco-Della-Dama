package com.example.damafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainIniziale extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("View/paginaLogin.fxml"));
        primaryStage.setTitle("Dama");
        Image image = new javafx.scene.image.Image(getClass().getResource("Immagini/Icona.png").toExternalForm());
        primaryStage.getIcons().add(image);
        primaryStage.setScene(new Scene(root,800,800));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}