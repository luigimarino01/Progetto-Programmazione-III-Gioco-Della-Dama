package com.example.damafx.Controller;

import com.example.damafx.MainIniziale;
import com.example.damafx.Model.Database.DamaDB;
import com.example.damafx.Model.Giocatore.Giocatore;
import com.example.damafx.Model.Partita.Partita;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Creazione del controller che gestisce tutta la pagina del Ripristino della partita
 */
public class SchermataRipristinaController {
    Partita partita = Partita.getIstanza();
    DamaDB damaDB = DamaDB.getIstanza();
    Giocatore giocatore = partita.getGiocatoreGiocante();

    @FXML
    Button bottoneSiRipristina;

    @FXML Button bottoneNoRipristina;

    @FXML Button bottoneEsciRipristina;

    /**
     * Metodo invocato quando si effettua il click sul bottone di uscita della pagina.
     * Euando viene effettuato il click il metodo ti riinvia alla pagina dell'utente loggato(Home)
     * @param actionEvent
     * @throws Exception
     */
    public void bottoneEsciRipristinaClickHandler(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(MainIniziale.class.getResource("View/paginaLoggato.fxml"));
        Stage window = (Stage) bottoneEsciRipristina.getScene().getWindow();
        window.setScene(new Scene(root, 800, 800));

        partita.getGiocatoreGiocante().setPedineAssegnate(null);
    }

    /**
     * Metodo invocato quando si effettua il click sul bottone con scritto "SI".
     * Quando si effettua il click si setta il ripristino della partita a true e si setta il turno del giocatore
     * ed infine riinvia l'utente alla pagina della partita in sospeso che sarà ricaricata
     * @param actionEvent
     * @throws Exception
     */
    public void bottoneSiRipristinaClickHandler(ActionEvent actionEvent) throws Exception {
        partita.setInRipristino(true);
        partita.setPlayerTurnTrue();
        Parent root = FXMLLoader.load(MainIniziale.class.getResource("View/paginaPartita.fxml"));
        Stage window = (Stage) bottoneNoRipristina.getScene().getWindow();
        window.setScene(new Scene(root, 800, 800));
    }

    /**
     * Metodo invocato quando si effettua il click sul bottone con scritto "NO".
     * Quando si effettua il click si setta La fine della partita in sospeso a true e si setta la modalità ripristino a false
     * ed infine riinvia l'utente alla pagina della partita in cui potrà giocare una nuova partita
     * @param actionEvent
     * @throws Exception
     */
    public void bottoneNoRipristinaClickHandler(ActionEvent actionEvent) throws Exception {
        damaDB.setEfinita(giocatore);
        partita.setInRipristino(false);

        Parent root = FXMLLoader.load(MainIniziale.class.getResource("View/paginaPartita.fxml"));
        Stage window = (Stage) bottoneNoRipristina.getScene().getWindow();
        window.setScene(new Scene(root, 800, 800));
    }
}
