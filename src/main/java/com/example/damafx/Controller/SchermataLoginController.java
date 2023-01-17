package com.example.damafx.Controller;

import com.example.damafx.MainIniziale;
import com.example.damafx.Model.Database.DamaDB;
import com.example.damafx.Model.Giocatore.GiocatoreLoggato;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
/**
 * Creazione del controller che gestisce tutta la pagina di login
 */
public class SchermataLoginController {
    GiocatoreLoggato giocatoreLoggato = GiocatoreLoggato.getIstanza();
    @FXML
    Button bottoneLogin;
    @FXML
    private ImageView loginErrato;
    @FXML
    private TextField textFieldNickname;
    @FXML
    private TextField textFieldEmail;
    private final DamaDB damaDB = DamaDB.getIstanza();

    private String tempNickname;
    private String tempEmail;

    /**
     * metodo che viene richiamato quando si egffettua il click sul bottone del login
     * Si effettuano i controlli su i campi se sono stati riempiti o meno, o se è sbagliato qualche input verificando nel database se
     * i dati inseriti corrispondono a n qualche utente nel db
     * @throws Exception
     */
    public void bottoneLoginClickHandler() throws Exception{
       tempNickname = textFieldNickname.getText();
       tempEmail = textFieldEmail.getText();

       if(tempNickname.isEmpty() || tempEmail.isEmpty()){
           loginErrato.setOpacity(1.0);
       }
       else {
           if (!damaDB.controllaLogin(tempNickname, tempEmail)) {
               loginErrato.setOpacity(1.0);
           } else {
               giocatoreLoggato.nicknameLoggato = tempNickname;
               giocatoreLoggato.emailLoggato = tempEmail;
               giocatoreLoggato.vittorieLoggato = 5;
               Parent root = FXMLLoader.load(MainIniziale.class.getResource("View/paginaLoggato.fxml"));
               Stage window = (Stage) bottoneLogin.getScene().getWindow();
               window.setScene(new Scene(root, 800, 800));
           }
       }
    }

    /**
     * Metodo che ti riporta alla pagina di registrazione quando si clicca sull'immagine di nuovo utente
     * @throws Exception
     */
    public void nuovoUtenteClickHandler() throws Exception{
        Parent root = FXMLLoader.load(MainIniziale.class.getResource("View/paginaRegistrati.fxml"));
        Stage window = (Stage) bottoneLogin.getScene().getWindow();
        window.setScene(new Scene(root,800 ,800));
    }

}
