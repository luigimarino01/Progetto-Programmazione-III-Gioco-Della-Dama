package com.example.damafx.Controller;

import com.example.damafx.MainIniziale;
import com.example.damafx.Model.Database.DamaDB;
import com.example.damafx.Model.Giocatore.GiocatoreBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Creazione del controller che gestisce tutta la pagina della Classifica, fa uso di un'interfaccia initializable
 * che permette  di inizializzare variabili all'inizio del caricamento della pagina.
 */
public class SchermataClassificaController implements Initializable{
    DamaDB damaDB = DamaDB.getIstanza();
    @FXML
    private TableView<GiocatoreBean> classifica;

    @FXML private TableColumn<GiocatoreBean, String> colonnaNickname;
    @FXML private  TableColumn<GiocatoreBean, Integer> colonnaVittorie;
    @FXML
    ImageView bottoneEsciClassifica;

    /**
     * Metodo richiamato quando l'utente effettua il click sull'immagine Esci.
     * Questo metodo ti riporta alla paginaLoggato
     * @throws Exception
     */
    public void bottoneEsciClassificaClickHandler() throws Exception {
        classifica.getItems().clear();
        Parent root = FXMLLoader.load(MainIniziale.class.getResource("View/paginaLoggato.fxml"));
        Stage window = (Stage) bottoneEsciClassifica.getScene().getWindow();
        window.setScene(new Scene(root, 800, 800));
    }

    /**
     * Metodo che fa uso dell'interfaccia initialize e che inizializza le variabili all'interno della tabella cha fa visualizzare la classifica dei primi 10
     * utilizzando i dati presi dal db (nickname e numeroVittorie)
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colonnaNickname.setCellValueFactory(new PropertyValueFactory<GiocatoreBean,String>("nickname"));
        colonnaVittorie.setCellValueFactory(new PropertyValueFactory<GiocatoreBean,Integer>("numeroVittorie"));
        classifica.setItems(damaDB.ottieniClassifica());
    }
}
