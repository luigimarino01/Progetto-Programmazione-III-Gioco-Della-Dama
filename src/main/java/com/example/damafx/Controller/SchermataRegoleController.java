package com.example.damafx.Controller;

import com.example.damafx.MainIniziale;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Creazione del controller che gestisce tutta la pagine del regolamento
 */
public class SchermataRegoleController {
    @FXML
    ImageView bottoneEsciRegole;

    /**
     * Metodo invocato quando si effettua il click sul bottone di uscita.
     * Una volta effettuato il click si rimanda l'utente alla pagina di utente loggato(Home)
     * @throws Exception
     */
    public void bottoneEsciRegoleClickHandler() throws Exception {
        Parent root = FXMLLoader.load(MainIniziale.class.getResource("View/paginaLoggato.fxml"));
        Stage window = (Stage)bottoneEsciRegole.getScene().getWindow();
        window.setScene(new Scene(root, 800, 800));
    }
}
