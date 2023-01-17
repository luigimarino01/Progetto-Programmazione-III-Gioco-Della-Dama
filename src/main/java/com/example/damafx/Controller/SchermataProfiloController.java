package com.example.damafx.Controller;

import com.example.damafx.MainIniziale;
import com.example.damafx.Model.Database.DamaDB;
import com.example.damafx.Model.Giocatore.GiocatoreLoggato;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * creazione del controller che gestisce la pagina che fa visualizzare il profilo fa utilizzo di un'interfaccia che ti permette di
 * inizializzare parametri al caricamento della pagina in cui ci è entrati
 */
public class SchermataProfiloController implements Initializable {

    GiocatoreLoggato giocatoreLoggato = GiocatoreLoggato.getIstanza();
    DamaDB damaDB = DamaDB.getIstanza();

    @FXML
    ImageView bottoneEsciProfilo;
    @FXML private Label labelNicknameProfilo;
    @FXML private Label labelEmailProfilo;
    @FXML private Label labelNumeroVittorieProfilo;


    /**
     * Metodo richiamato quando l'utente effettua il click sull'immagine Esci.
     * Questo metodo ti riporta alla paginaLoggato
     * @throws Exception
     */
    public void bottoneEsciProfiloClickHandler() throws Exception {
        Parent root = FXMLLoader.load(MainIniziale.class.getResource("View/paginaLoggato.fxml"));
        Stage window = (Stage)bottoneEsciProfilo.getScene().getWindow();
        window.setScene(new Scene(root, 800, 800));
    }

    /**
     * Metodo invocato quando si entra nella pagina dell'utente loggato e serve ad inizializzare i parametri del giocatore Loggato
     * I parametri inizializzati sono il nickname, l'emaill e il numero di vittorie che viene preso dal DB
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelNicknameProfilo.setText(giocatoreLoggato.nicknameLoggato);
        labelEmailProfilo.setText(giocatoreLoggato.emailLoggato);
        labelNumeroVittorieProfilo.setText(String.valueOf(damaDB.ottieniVittorie(giocatoreLoggato.nicknameLoggato)));
    }
}
