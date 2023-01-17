package com.example.damafx.Controller;

import com.example.damafx.MainIniziale;
import com.example.damafx.Model.Database.DamaDB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
/**
 * Creazione del controller che gestisce tutta la pagina della registrazione dell'utente
 */
public class SchermataRegistratiController {

    private final DamaDB damaDB = DamaDB.getIstanza();

    @FXML
    private TextField textFieldNome;
    @FXML
    private TextField textFieldCognome;
    @FXML
    private TextField textFieldNickname;
    @FXML
    private TextField textFieldEmail;
    @FXML
    Button bottoneRegistrati;

    private String tempNickname;
    private String tempEmail;
    private String tempNome;
    private String tempCognome;
    @FXML
    private ImageView compilazioneErrata;
    @FXML
    private ImageView nicknameInUso;

    /**
     * Metodo che viene richiamato qunado l'utente effettua il click sul bottone della registrazione
     * si fa visualizzare un messaggio "COMPILAZIONE ERRATA" se l'utente dimentica di riempire qualche campo, mentre si visualizza
     * "NICKNAME IN USO" se quel nickname è già presente nel DB
     * Se invece tutta la compilazione avviene con successo l'utente viene rinviato alla pagina di login per poi poter effettuare il
     * login vero e proprio nel gioco
     * @throws Exception
     */
    public void bottoneRegistratiClickHandler() throws Exception{
        tempNome = textFieldNome.getText();
        tempCognome = textFieldCognome.getText();
        tempNickname = textFieldNickname.getText();
        tempEmail = textFieldEmail.getText();

        if (tempNome.isEmpty() || tempCognome.isEmpty() || tempNickname.isEmpty() || tempEmail.isEmpty()){
            nicknameInUso.setOpacity(0.0);
            compilazioneErrata.setOpacity(1.0);
        }
        else {

            if (!damaDB.aggiungiUtente(tempNome, tempCognome, tempNickname, tempEmail)) {
                compilazioneErrata.setOpacity(0.0);
                nicknameInUso.setOpacity(1.0);
            } else {
                Parent root = FXMLLoader.load(MainIniziale.class.getResource("View/paginaLogin.fxml"));
                Stage window = (Stage) bottoneRegistrati.getScene().getWindow();
                window.setScene(new Scene(root, 800, 800));
            }
        }
    }

    /**
     * Metodo che viene chiamato quando l'utente effettua il click sul bottone di uscita.
     * Una volta effettuato il click il metodo ti rimanda alla pagina di login
     * @throws Exception
     */
    public void bottoneEsciClickHandler() throws Exception {
        Parent root = FXMLLoader.load(MainIniziale.class.getResource("View/paginaLogin.fxml"));
        Stage window = (Stage) bottoneRegistrati.getScene().getWindow();
        window.setScene(new Scene(root,800 ,800));
    }
}
