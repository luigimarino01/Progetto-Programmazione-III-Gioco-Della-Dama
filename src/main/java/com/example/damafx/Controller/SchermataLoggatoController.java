package com.example.damafx.Controller;

import com.example.damafx.MainIniziale;
import com.example.damafx.Model.Giocatore.GiocatoreLoggato;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
/**
 * Creazione del controller che gestisce tutta la pagina dell'utente loggato
 */
public class SchermataLoggatoController {
    GiocatoreLoggato giocatoreLoggato = GiocatoreLoggato.getIstanza();


    @FXML
    Button bottoneIniziaPartita;
    @FXML
    Button bottoneClassifica;
    @FXML
    Button bottoneProfilo;
    @FXML
    Button bottoneRegole;

    /**
     * Metodo richiamato quando si effettua il click sul bottone che fa iniziare la partita.
     * Il metodo ti rimanda alla pagina della partita
     * @throws Exception
     */
    public void bottoneIniziaPartitaClickHandler() throws Exception{
        Parent root = FXMLLoader.load(MainIniziale.class.getResource("View/paginaPartita.fxml"));
        Stage window = (Stage) bottoneIniziaPartita.getScene().getWindow();
        window.setScene(new Scene(root, 800, 800));
    }

    /**
     * Metodo richiamato quando si effettua il click sul bottone che fa visualizzare la classifica.
     * Il metodo ti rimanda alla pagina della classifica
     * @throws Exception
     */
    public void bottoneClassificaClickHandler() throws Exception{
        Parent root = FXMLLoader.load(MainIniziale.class.getResource("View/paginaClassifica.fxml"));
        Stage window = (Stage) bottoneIniziaPartita.getScene().getWindow();
        window.setScene(new Scene(root, 800, 800));
    }

    /**
     * Metodo richiamato quando si effettua il click sul bottone che fa visualizzare il profilo dell'utente.
     * Il metodo ti rimanda alla pagina del profilo dell'utente
     * @throws Exception
     */
    public void bottoneProfiloClickHandler() throws Exception{
        Parent root = FXMLLoader.load(MainIniziale.class.getResource("View/paginaProfilo.fxml"));
        Stage window = (Stage) bottoneIniziaPartita.getScene().getWindow();
        window.setScene(new Scene(root, 800, 800));
    }

    /**
     * Metodo richiamato quando si effettua il click sul bottone che fa visualizzare il regolamento del gioco.
     * Il metodo ti rimanda alla pagina del regolamento
     * @throws Exception
     */
    public void bottoneRegoleClickHandler() throws Exception{
        Parent root = FXMLLoader.load(MainIniziale.class.getResource("View/paginaRegole.fxml"));
        Stage window = (Stage) bottoneIniziaPartita.getScene().getWindow();
        window.setScene(new Scene(root, 800, 800));

    }
    /**
     * Metodo richiamato quando si effettua il click sul bottone che fa visualizzare il logout.
     * Il metodo ti rimanda alla pagina del login
     * @throws Exception
     */
    public void bottoneLogoutClickHandler() throws Exception {
        giocatoreLoggato.nicknameLoggato = null;
        giocatoreLoggato.emailLoggato = null;
        giocatoreLoggato.vittorieLoggato = 0;
        Parent root = FXMLLoader.load(MainIniziale.class.getResource("View/paginaLogin.fxml"));
        Stage window = (Stage) bottoneIniziaPartita.getScene().getWindow();
        window.setScene(new Scene(root, 800, 800));
    }
}
