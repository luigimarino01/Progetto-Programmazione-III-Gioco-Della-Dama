package com.example.damafx.Controller;
import com.example.damafx.MainIniziale;
import com.example.damafx.Model.Cpu.Cpu;
import com.example.damafx.Model.Damiera.Damiera;
import com.example.damafx.Model.Database.DamaDB;
import com.example.damafx.Model.Giocatore.Giocatore;
import com.example.damafx.Model.Giocatore.GiocatoreLoggato;
import com.example.damafx.Model.Partita.Partita;
import com.example.damafx.Model.Pedine.Observer;
import com.example.damafx.Model.Pedine.PedinaClient;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Creazione del controller che gestisce tutta la pagina della partita, fa uso di due interfacce Observer spiegato in seguito,
 * ed initializable che permette  di inizializzare variabili all'inizio del caricamento della pagina.
 */
public class SchermataPartitaController implements Initializable, Observer {

    DamaDB damaDB = DamaDB.getIstanza();
    GiocatoreLoggato giocatoreLoggato = GiocatoreLoggato.getIstanza();

    Cpu cpu = Cpu.getIstanza();
    int x, y, xR, yR;
    Partita partita = Partita.getIstanza();
    Giocatore giocatore = damaDB.getGiocatore(giocatoreLoggato.nicknameLoggato);
    @FXML
    GridPane damieraGrafica;
    public Node[][] arrayDamiera = new Node[8][8];
    ObservableList<Node> observableList;
    private ArrayList<PedinaClient> pedineAssegnateGiocatore = new ArrayList<PedinaClient>();
 @FXML
    Button bottoneEsciHome;
    PedinaClient deveMangiare = null;
    @FXML
    ImageView vittoriaPng;
    @FXML
    ImageView sconfittaPng;
    @FXML
    ImageView bottoneEsci;
    Damiera damieraLogica = Damiera.getInstanza();
    Node casellaSelezionata = null;
    Node casellaSelezionataCpu = null;
    ImageView pedinaNera;
    ImageView pedinaRossa;
    ImageView damaNera ;
    ImageView damaRossa ;
    boolean haMangiato = false;
    boolean siPuoMuovere = true;


    /**
     * Questo è il metodo che uilizziamo per utilizzare l'implementazione dell'interfaccia Observer che ci permette di definire
     * una dipendenza uno a molti fra oggetti, in modo tale che se un oggetto cambia il suo stato interno, ciascuno degli oggetti
     * dipendenti da esso viene notificato e aggiornato automaticamente.
     * noi notifichiamo quando viene spostata una pedina della cpu per aggiornare il tutto graficamente e nel caso in cui la partita finisse
     * con la vittoria o la sconfitta del giocatore
     * @param oldX posizione ascissale della damiera in cui è situata la pedina prima del movimento
     * @param oldY posizione ordinale della damiera in cui è situata la pedina prima del movimento
     * @param newX posizione ascissale in cui la pedina dovrà effettuare il movimento
     * @param newY posizione ordinale in cui la pedina dovrà effettuare il movimento
     */
    public void muoviPedinaObserver(int oldX, int oldY, int newX, int newY) {
        pedineAssegnateGiocatore = giocatore.getPedineAssegnate();

        if (deveMangiare != null) {
            mangiaGraficamente(oldX, oldY, newX, newY);
            return;
        }


        deveMangiare = null;

        spostaPedinaGraficamenteCpu(oldX, oldY, newX, newY);

        if (partita.iseFinita()){
            if (pedineAssegnateGiocatore.size() > cpu.getPedineAssegnateCpu().size()){
                vittoriaPng.setOpacity(1);
                bottoneEsci.setOpacity(1);
                giocatore.aggiungiVittoria();
                damaDB.setEfinita(giocatore);
                damaDB.resetContiene(giocatore);
                partita.setInRipristino(false);
                return;
            }
            else if (pedineAssegnateGiocatore.size() < cpu.getPedineAssegnateCpu().size()){
                sconfittaPng.setOpacity(1);
                bottoneEsci.setOpacity(1);
                partita.setInRipristino(false);
                return;
            }

        }



        if (pedineAssegnateGiocatore.size() == 0){
            sconfittaPng.setOpacity(1);
            bottoneEsci.setOpacity(1);
            partita.seteFinita(true);
            partita.setInRipristino(false);
        }

    }

    /**
     * Metodo che viene invocato quando l'utente effettua in click sullo schermo
     * il parametro che viene passasto in input è l'evento spiegato pocanzi.
     * se la partita non è terminata sul click dell'utente bisogna verificare se qualche pedina può mangiare siccome il regolamento della dama impone che se una pedina può mangiare
     * deve farlo per forza, quindi verificato se l'utente può mangiare o meno non sono concessi altri movimenti al di fuori del mangiare
     * il primo ciclo for ci permette di capire se la partita deve terminare o meno, verifichiamo se l'utente può ancora muovere una qualsiasi pedina, se quest'ultima affermazione
     * è vera allora la partita deve continuare altrimenti bisogna far visualizzare all'utente la scritta "SCONFITTA"
     * infine dopo aver effettuato tutti questi controlli si verifica se la casella su cui l'utente ha cliccato è idonea o meno, quindi se ha selezionato o meno una pedina che è della sua squadra
     * @param e
     * @throws Exception
     */
    @FXML
    protected void onMousePressed(MouseEvent e) throws Exception {
        pedineAssegnateGiocatore = giocatore.getPedineAssegnate();

        if (!partita.iseFinita()) {

            for (int i = 0; i < pedineAssegnateGiocatore.size(); i++) {
                deveMangiare = pedineAssegnateGiocatore.get(i).deveMangiare();
                if (deveMangiare != null) {
                    break;
                }
            }

            for (int i = 0; i < giocatore.getPedineAssegnate().size(); i++) {
                siPuoMuovere = giocatore.getPedineAssegnate().get(i).siPuoMuovere();
                if (siPuoMuovere) break;

            }

            if (!siPuoMuovere) {
                sconfittaPng.setOpacity(1);
                bottoneEsci.setOpacity(1);
                partita.seteFinita(true);
                partita.setAttiva(false);
                damaDB.setEfinita(giocatore);
                damaDB.resetContiene(giocatore);
                partita.setInRipristino(false);
            }


            x = (int) (e.getY() / 500 * 8);
            y = (int) (e.getX() / 500 * 8);

            if (!((Pane) arrayDamiera[x][y]).getChildren().isEmpty()) {
                if ((damieraLogica.tavolaDaGioco[x][y].getPedinaSopra().isRossa() && giocatore.isSquadraRossa()) || damieraLogica.tavolaDaGioco[x][y].getPedinaSopra().isNera() && giocatore.isSquadraNera()) {
                    casellaSelezionata = arrayDamiera[x][y];
                }
            }

        }

    }

    /**
     * Metodo utilizzato per capire se la pedina che deve mangiare ha mangiato
     * gli passiamo la x e la y in cui l'utente ha effettuato il rilascio del muose e quindi se la x e la y rispettano il movimento che la pedina dovrebbe fare
     * facciamo un return della variabile booleana "hamangiato" uguale a true altrimenti facciamo un return di false
     * @param xR posizione ascissale di dove l'utente rilascia il muose
     * @param yR posizione ordinale di dove l'utente rilascia il muose
     * @return
     */
    private int haMangiato(int xR, int yR) {

        if (deveMangiare != null) {
            if (xR > x && yR > y) {
                if (xR == deveMangiare.getxCorrente() + 2 && yR == deveMangiare.getyCorrente() + 2 && damieraLogica.tavolaDaGioco[x][y].getPedinaSopra().mangia(xR, yR) == 1) {
                    haMangiato = true;
                    return 1;


                }
            } else if (xR > x && yR < y) {
                if (xR == deveMangiare.getxCorrente() + 2 && yR == deveMangiare.getyCorrente() - 2 && damieraLogica.tavolaDaGioco[x][y].getPedinaSopra().mangia(xR, yR) == 2) {
                    haMangiato = true;
                    return 2;


                }
            } else if (xR < x && yR > y) {
                if (xR == deveMangiare.getxCorrente() - 2 && yR == deveMangiare.getyCorrente() + 2 && damieraLogica.tavolaDaGioco[x][y].getPedinaSopra().mangia( xR, yR) == 4) {
                    haMangiato = true;
                    return 3;

                }
            } else if (xR < x && yR < y) {
                if (xR == deveMangiare.getxCorrente() - 2 && yR == deveMangiare.getyCorrente() - 2 && damieraLogica.tavolaDaGioco[x][y].getPedinaSopra().mangia( xR, yR) == 3) {
                    haMangiato = true;
                    return 4;

                }
            }
        }


        return 0;
    }

    /**
     * Metodo che viene chimato ogni qualvolta l'utente effetta un rilascio dal click del mouse
     * il parametro e è proprio l'evento spiegato pocanzi e quando viene chiamato si memorizza la posizione in cui avviene il rilascio in due variabili,
     * poi successivamente si verifica il turno in partita a quale giocatore è assegnatoi in quel momento e si verifica se qualcuno deve mangiare ed ha mangiato, infatti
     * se si effettua un rilascio diverso dall'operazione che la pedina che deve mangiare deve effettuare il programma attende un nuovo rilascio fino
     * a quando la pedina che deve mangiare non ha mangiato,invece se nessuno deve mangiare sposta la pedina selezionata(dal click dell'utente) e
     * se idonea avviene lo spostamento con gli opportuni controlli sulla posizione e se deve divengtare dama.
     * @param e
     * @throws Exception
     */
    @FXML
    protected void onMouseReleased(MouseEvent e) throws Exception {
        xR = (int) (e.getY() / 500 * 8);
        yR = (int) (e.getX() / 500 * 8);

        if (!partita.isPlayerTurn()) {
            return;
        }
        if (deveMangiare != null) {
            int doveLascia = haMangiato(xR, yR);
            if (doveLascia == 0) {
                return;
            }
            else {
                deveMangiare = deveMangiare.deveMangiare();//richiama la funzione una volta mangiato per capire se deve mangiare altro
            }

            if (deveMangiare == null && haMangiato){
                partita.setPlayerTurnFalse();
                damaDB.setTurnoGiocatore(partita);
            }

        }


        if (casellaSelezionata == null) {
            return;
        }


        xR = (int) (e.getY() / 500 * 8);
        yR = (int) (e.getX() / 500 * 8);

        if (!((Pane) casellaSelezionata).getChildren().isEmpty() && damieraLogica.tavolaDaGioco[x][y].getPedinaSopra().muoviPedina(xR, yR)) {
            spostaPedinaGraficamente(xR, yR);
            partita.setPlayerTurnFalse();
            damaDB.setTurnoGiocatore(partita);

            if (xR == 0) {
                diventaDamaGraficamente(xR, yR);
            }
        }

        casellaSelezionata = null;

        if (partita.iseFinita()){
            if (pedineAssegnateGiocatore.size() > cpu.getPedineAssegnateCpu().size()){
                vittoriaPng.setOpacity(1);
                giocatore.aggiungiVittoria();
                bottoneEsci.setOpacity(1);
                bottoneEsciHome.setOpacity(0);
                damaDB.setEfinita(giocatore);
                partita.setAttiva(false);
                damaDB.resetContiene(giocatore);
                partita.setInRipristino(false);
            }
            else if (pedineAssegnateGiocatore.size() > cpu.getPedineAssegnateCpu().size())
                sconfittaPng.setOpacity(1);
                bottoneEsci.setOpacity(1);
                bottoneEsciHome.setOpacity(0);
                damaDB.setEfinita(giocatore);
                partita.setInRipristino(false);
                partita.setAttiva(false);
                damaDB.resetContiene(giocatore);
        }

    }

    /**
     * Metodo che fa visualizzare in modo grafico lo spostamento delle pedine dell'utente.
     * I parametri nuovaX e nuovaY insieme determinano la posizione finale in cui si dovrà trovare la pedina del giocatore.
     * Le posizioni iniziali sono salvate all'interno di una variabile chiamata "casellaSelezionata", che contiene le coordinate in base alla damiera,
     * cioè la x e la y di dove l'utente ha effettuato il click
     * @param nuovaX posizione ascissale della damiera la pedina deve effettuare il movimento
     * @param nuovaY posizione ordinale della damiera la pedina deve effettuare il movimento
     */
    public void spostaPedinaGraficamente(int nuovaX, int nuovaY) {
        ((Pane) casellaSelezionata).getChildren().clear();//puliamo la casella su cui è presenta la pedina che deve effettuare il movimento
        if (damieraLogica.tavolaDaGioco[nuovaX][nuovaY].getPedinaSopra().isDama()){//verifichiamo che la pedina sia dama o meno
            if (giocatore.isSquadraNera()) {//se è dama ed è della squadra nera facciamo lo spostamento facendo visualizzare sulla casella su cui l'utente ha rilasciato l'immagine della dama nera
                ((Pane) arrayDamiera[nuovaX][nuovaY]).getChildren().add(damaNera = new ImageView(new Image(MainIniziale.class.getResourceAsStream("Immagini/damaNera.png"))));
                damaNera.setFitHeight(62.0);
                damaNera.setFitWidth(62.0);
            } else {//se è dama ed è della squadra rossa facciamo lo spostamento facendo visualizzare sulla casella su cui l'utente ha rilasciato l'immagine della dama rossa
                ((Pane) arrayDamiera[nuovaX][nuovaY]).getChildren().add(damaRossa = new ImageView(new Image(MainIniziale.class.getResourceAsStream("Immagini/damaRossa.png"))));
                damaRossa.setFitHeight(62.0);
                damaRossa.setFitWidth(62.0);
           }
            return;
        }
        if (giocatore.isSquadraNera()) {//se non è dama ed è della squadra nera facciamo lo spostamento facendo visualizzare sulla casella su cui l'utente ha rilasciato l'immagine della pedina nera
            ((Pane) arrayDamiera[nuovaX][nuovaY]).getChildren().add(pedinaNera = new ImageView(new Image(MainIniziale.class.getResourceAsStream("Immagini/pezzoNero.png"))));
            pedinaNera.setFitHeight(62.0);
            pedinaNera.setFitWidth(62.0);
        } else {//se non è dama ed è della squadra rossa facciamo lo spostamento facendo visualizzare sulla casella su cui l'utente ha rilasciato l'immagine della pedina rossa
            ((Pane) arrayDamiera[nuovaX][nuovaY]).getChildren().add(pedinaRossa = new ImageView(new Image(MainIniziale.class.getResourceAsStream("Immagini/pezzoRosso.png"))));
            pedinaRossa.setFitHeight(62.0);
            pedinaRossa.setFitWidth(62.0);
        }

    }

    /**
     * Con questo metodo facciamo visualizzare in modo grafico lo spostamento delle pedine della cpu
     * i parametri vecchiaX e vecchiaY insieme determinano la posizione iniziale in cui si trova la pedina della cpu
     * mentre gli altri due parametri nuovaX e nuovaY insieme determinano la posizione finale in cui si dovrà trovare la pedina della cpu
     * @param vecchiaX posizione ascissale della damiera in cui era situata la pedina della cpu prima del movimento
     * @param vecchiaY posizione ordinale della damiera in cui era situata la pedina della cpu prima del movimento
     * @param nuovaX posizione ascissale della damiera la pedina deve effettuare il movimento
     * @param nuovaY posizione ordinale della damiera la pedina deve effettuare il movimento
     */
    public void spostaPedinaGraficamenteCpu(int vecchiaX, int vecchiaY, int nuovaX, int nuovaY) {
        casellaSelezionataCpu = arrayDamiera[vecchiaX][vecchiaY];
        ((Pane) casellaSelezionataCpu).getChildren().clear();
        if(nuovaX==vecchiaX+2 && nuovaY==vecchiaY-2){
            ((Pane) arrayDamiera[vecchiaX+1][vecchiaY-1]).getChildren().clear();
        }
        else if(nuovaX == vecchiaX+2 && nuovaY == vecchiaY+2){
            ((Pane) arrayDamiera[vecchiaX+1][vecchiaY+1]).getChildren().clear();
        }
        else if(nuovaX == vecchiaX-2 && nuovaY == vecchiaY-2){
            ((Pane) arrayDamiera[vecchiaX-1][vecchiaY-1]).getChildren().clear();
        }
        else if(nuovaX == vecchiaX-2 && nuovaY == vecchiaY+2){
            ((Pane) arrayDamiera[vecchiaX-1][vecchiaY+1]).getChildren().clear();
        }

        if (nuovaX == 7){
            if (!giocatore.isSquadraNera()) {
                ((Pane) arrayDamiera[nuovaX][nuovaY]).getChildren().add(damaNera = new ImageView(new Image(MainIniziale.class.getResourceAsStream("Immagini/damaNera.png"))));
                damaNera.setFitHeight(62.0);
                damaNera.setFitWidth(62.0);
            } else {
                ((Pane) arrayDamiera[nuovaX][nuovaY]).getChildren().add(damaRossa = new ImageView(new Image(MainIniziale.class.getResourceAsStream("Immagini/damaRossa.png"))));
                damaRossa.setFitHeight(62.0);
                damaRossa.setFitWidth(62.0);
            }
            return;
        }

        if (damieraLogica.tavolaDaGioco[nuovaX][nuovaY].getPedinaSopra().isDama()){
            if (!giocatore.isSquadraNera()) {
                ((Pane) arrayDamiera[nuovaX][nuovaY]).getChildren().add(damaNera = new ImageView(new Image(MainIniziale.class.getResourceAsStream("Immagini/damaNera.png"))));
                damaNera.setFitHeight(62.0);
                damaNera.setFitWidth(62.0);
            } else {
                ((Pane) arrayDamiera[nuovaX][nuovaY]).getChildren().add(damaRossa = new ImageView(new Image(MainIniziale.class.getResourceAsStream("Immagini/damaRossa.png"))));
                damaRossa.setFitHeight(62.0);
                damaRossa.setFitWidth(62.0);
            }
            return;
        }

        if (giocatore.isSquadraNera()) {
            ((Pane) arrayDamiera[nuovaX][nuovaY]).getChildren().add(pedinaRossa = new ImageView(new Image(MainIniziale.class.getResourceAsStream("Immagini/pezzoRosso.png"))));
            pedinaRossa.setFitHeight(62.0);
            pedinaRossa.setFitWidth(62.0);
        } else {
            ((Pane) arrayDamiera[nuovaX][nuovaY]).getChildren().add(pedinaNera = new ImageView(new Image(MainIniziale.class.getResourceAsStream("Immagini/pezzoNero.png"))));
            pedinaNera.setFitHeight(62.0);
            pedinaNera.setFitWidth(62.0);
        }
    }

    /**
     * Metodo che permette di visualizzare in modo grafico l'operazione del mangia
     * i parametri xCorr,yCorr,nuovaX,nuovaY servono per tenere traccia di chi deve essere cancellato dalla damiera e di chi deve effettuare
     * lo spostamento
     * xCorr e yCorr insieme determinano la posizione iniziale nella quale stava la pedina che deve mangiare
     * nuovaX e nuovaY insieme determinano la posizione finale nella quale la pedina deve effettuare lo spostamento
     * la pedina che deve essere mangiate viene ricavata in base alla posizione in cui si sposta la pedina "assassina"
     * @param xCorr Posizione ascissale della damiera dove è contenuta la pedina che deve mangiare prima del movimento
     * @param yCorr Posizione ordinale della damiera dove è contenuta la pedina che deve mangiare prima del movimento
     * @param nuovaX Posizione ascissale della damiera dove la pedina che deve mangiare si deve spostare
     * @param nuovaY Posizione ordinale della damiera dove la pedina che deve mangiare si deve spostare
     */
    public void mangiaGraficamente(int xCorr, int yCorr, int nuovaX, int nuovaY) {
        ((Pane) arrayDamiera[xCorr][yCorr]).getChildren().clear();//pulizia di dove era la pedina prima di mangiare

        if (xR<x && yR<y){
            ((Pane) arrayDamiera[xCorr-1][yCorr-1]).getChildren().clear();
        }
        else if (xR<x && yR>y){
            ((Pane) arrayDamiera[xCorr-1][yCorr+1]).getChildren().clear();
        }
        else if (xR>x && yR<y){
            ((Pane) arrayDamiera[xCorr+1][yCorr-1]).getChildren().clear();
        }
        else if (xR>x && yR>y){
            ((Pane) arrayDamiera[xCorr+1][yCorr+1]).getChildren().clear();

        }

        if (nuovaX == 0) {
            diventaDamaGraficamente(nuovaX,nuovaY);
            return;
        }

        if (damieraLogica.tavolaDaGioco[nuovaX][nuovaY].getPedinaSopra().isDama()){
            if (giocatore.isSquadraNera()) {
                ((Pane) arrayDamiera[nuovaX][nuovaY]).getChildren().add(damaNera = new ImageView(new Image(MainIniziale.class.getResourceAsStream("Immagini/damaNera.png"))));
                damaNera.setFitHeight(62.0);
                damaNera.setFitWidth(62.0);
            } else {
                ((Pane) arrayDamiera[nuovaX][nuovaY]).getChildren().add(damaRossa = new ImageView(new Image(MainIniziale.class.getResourceAsStream("Immagini/damaRossa.png"))));
                damaRossa.setFitHeight(62.0);
                damaRossa.setFitWidth(62.0);
            }
            return;
        }
        if (giocatore.isSquadraNera()) {
            ((Pane) arrayDamiera[nuovaX][nuovaY]).getChildren().add(pedinaNera = new ImageView(new Image(MainIniziale.class.getResourceAsStream("Immagini/pezzoNero.png"))));
            pedinaNera.setFitHeight(62.0);
            pedinaNera.setFitWidth(62.0);
        } else {
            ((Pane) arrayDamiera[nuovaX][nuovaY]).getChildren().add(pedinaRossa = new ImageView(new Image(MainIniziale.class.getResourceAsStream("Immagini/pezzoRosso.png"))));
            ((Pane) arrayDamiera[xCorr][yCorr]).getChildren().clear();
            pedinaRossa.setFitHeight(62.0);
            pedinaRossa.setFitWidth(62.0);
        }
    }

    /**
     * Metodo che quando viene invocato setta tutte le pedine in modo grafico. Effettuiamo la pulizia della observableList
     * per non riscontrare conflitti quando si effettua il ripristino della partita, riinizializziamo tutta la matrice arrayDamiera
     * ed infine settiamo in ogni casella della matrice le pedine e i valori null al posto giusto facendo visualizzare in modo grafico
     * le pedine con il proprio colore
     */
    public void inizializzaPedineGraficamente() {


        int contatore = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ((Pane)observableList.get(contatore)).getChildren().clear();
                arrayDamiera[i][j] = observableList.get(contatore);
                contatore++;
            }
        }

        if (giocatore.isSquadraNera()) {

            for (int i = 0; i < 4; i++) {


                ((Pane) arrayDamiera[7][i * 2]).getChildren().add(pedinaNera = new ImageView(new Image(MainIniziale.class.getResourceAsStream("Immagini/pezzoNero.png"))));
                pedinaNera.setFitHeight(62.0);
                pedinaNera.setFitWidth(62.0);
                ((Pane) arrayDamiera[6][i * 2 + 1]).getChildren().add(pedinaNera = new ImageView(new Image(MainIniziale.class.getResourceAsStream("Immagini/pezzoNero.png"))));
                pedinaNera.setFitHeight(62.0);
                pedinaNera.setFitWidth(62.0);
                ((Pane) arrayDamiera[5][i * 2]).getChildren().add(pedinaNera = new ImageView(new Image(MainIniziale.class.getResourceAsStream("Immagini/pezzoNero.png"))));
                pedinaNera.setFitHeight(62.0);
                pedinaNera.setFitWidth(62.0);


                ((Pane) arrayDamiera[0][i * 2 + 1]).getChildren().add(pedinaRossa = new ImageView(new Image(MainIniziale.class.getResourceAsStream("Immagini/pezzoRosso.png"))));
                pedinaRossa.setFitHeight(62.0);
                pedinaRossa.setFitWidth(62.0);
                ((Pane) arrayDamiera[1][i * 2]).getChildren().add(pedinaRossa = new ImageView(new Image(MainIniziale.class.getResourceAsStream("Immagini/pezzoRosso.png"))));
                pedinaRossa.setFitHeight(62.0);
                pedinaRossa.setFitWidth(62.0);
                ((Pane) arrayDamiera[2][i * 2 + 1]).getChildren().add(pedinaRossa = new ImageView(new Image(MainIniziale.class.getResourceAsStream("Immagini/pezzoRosso.png"))));
                pedinaRossa.setFitHeight(62.0);
                pedinaRossa.setFitWidth(62.0);

            }
        } else {
            for (int i = 0; i < 4; i++) {


                ((Pane) arrayDamiera[7][i * 2]).getChildren().add(pedinaRossa = new ImageView(new Image(MainIniziale.class.getResourceAsStream("Immagini/pezzoRosso.png"))));
                pedinaRossa.setFitHeight(62.0);
                pedinaRossa.setFitWidth(62.0);
                ((Pane) arrayDamiera[6][i * 2 + 1]).getChildren().add(pedinaRossa = new ImageView(new Image(MainIniziale.class.getResourceAsStream("Immagini/pezzoRosso.png"))));
                pedinaRossa.setFitHeight(62.0);
                pedinaRossa.setFitWidth(62.0);
                ((Pane) arrayDamiera[5][i * 2]).getChildren().add(pedinaRossa = new ImageView(new Image(MainIniziale.class.getResourceAsStream("Immagini/pezzoRosso.png"))));
                pedinaRossa.setFitHeight(62.0);
                pedinaRossa.setFitWidth(62.0);

                ((Pane) arrayDamiera[0][i * 2 + 1]).getChildren().add(pedinaNera = new ImageView(new Image(MainIniziale.class.getResourceAsStream("Immagini/pezzoNero.png"))));
                pedinaNera.setFitHeight(62.0);
                pedinaNera.setFitWidth(62.0);
                ((Pane) arrayDamiera[1][i * 2]).getChildren().add(pedinaNera = new ImageView(new Image(MainIniziale.class.getResourceAsStream("Immagini/pezzoNero.png"))));
                pedinaNera.setFitHeight(62.0);
                pedinaNera.setFitWidth(62.0);
                ((Pane) arrayDamiera[2][i * 2 + 1]).getChildren().add(pedinaNera = new ImageView(new Image(MainIniziale.class.getResourceAsStream("Immagini/pezzoNero.png"))));
                pedinaNera.setFitHeight(62.0);
                pedinaNera.setFitWidth(62.0);

            }
        }

    }

    /**
     * Metodo che viene invocato se la pedina che effettuato lo spostamento si trova nella posizione in cui  si diventa dama
     * infine modifica l'immagine alla pedina per far capire all'utente che è una dama
     * @param x posizione ascissale della damiera
     * @param y posizione ordinale della damiera
     */
    public void diventaDamaGraficamente(int x, int y) {

        if (damieraLogica.tavolaDaGioco[x][y].getPedinaSopra().isNera()) {
            ((Pane) arrayDamiera[x][y]).getChildren().add(damaNera = new ImageView(new Image(MainIniziale.class.getResourceAsStream("Immagini/damaNera.png"))));
            damaNera.setFitHeight(62.0);
            damaNera.setFitWidth(62.0);
        } else {
            ((Pane) arrayDamiera[x][y]).getChildren().add(damaRossa = new ImageView(new Image(MainIniziale.class.getResourceAsStream("Immagini/damaRossa.png"))));
            damaRossa.setFitHeight(62.0);
            damaRossa.setFitWidth(62.0);
        }
    }

    /**
     * Metodo richiamato per ripristinare la partita in sospeso graficamente.
     * Come cosa primaria puliamo la observableList e la matrice arrayDamiera e poi riaggiungiamo tutte le pedine all'inteno dell'arrayDamiera
     * verificando se logicamente c'è qualcosa nelle posizioni controllate
     */
    public void ripristinaAVideo(){



        int contatore = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ((Pane)observableList.get(contatore)).getChildren().clear();
                arrayDamiera[i][j] = observableList.get(contatore);
                contatore++;
            }
        }


        for (int i = 0; i<8;i++){
            for (int j = 0; j<8; j++){
                if (damieraLogica.tavolaDaGioco[i][j].getPedinaSopra() == null){

                }
                else if (damieraLogica.tavolaDaGioco[i][j].getPedinaSopra().isNera() && damieraLogica.tavolaDaGioco[i][j].getPedinaSopra().isDama()){
                    ((Pane) arrayDamiera[i][j]).getChildren().add(damaNera = new ImageView(new Image(MainIniziale.class.getResourceAsStream("Immagini/damaNera.png"))));
                    damaNera.setFitHeight(62.0);
                    damaNera.setFitWidth(62.0);
                }
                else if (damieraLogica.tavolaDaGioco[i][j].getPedinaSopra().isNera() && !damieraLogica.tavolaDaGioco[i][j].getPedinaSopra().isDama()){
                    ((Pane) arrayDamiera[i][j]).getChildren().add(pedinaNera = new ImageView(new Image(MainIniziale.class.getResourceAsStream("Immagini/pezzoNero.png"))));
                    pedinaNera.setFitHeight(62.0);
                    pedinaNera.setFitWidth(62.0);
                }
                else if (damieraLogica.tavolaDaGioco[i][j].getPedinaSopra().isRossa() && damieraLogica.tavolaDaGioco[i][j].getPedinaSopra().isDama()){
                    ((Pane) arrayDamiera[i][j]).getChildren().add(damaRossa = new ImageView(new Image(MainIniziale.class.getResourceAsStream("Immagini/damaRossa.png"))));
                    damaRossa.setFitHeight(62.0);
                    damaRossa.setFitWidth(62.0);
                }
                else if (damieraLogica.tavolaDaGioco[i][j].getPedinaSopra().isRossa() && !damieraLogica.tavolaDaGioco[i][j].getPedinaSopra().isDama()){
                    ((Pane) arrayDamiera[i][j]).getChildren().add(pedinaRossa = new ImageView(new Image(MainIniziale.class.getResourceAsStream("Immagini/pezzoRosso.png"))));
                    pedinaRossa.setFitHeight(62.0);
                    pedinaRossa.setFitWidth(62.0);
                }
            }
        }
    }

    /**
     * metodo chiamato per inizializzare all'inizio di ogni pagina dei parametri che devono essere settati
     * questo metodo fa parte dell'interfaccia initialize.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pedineAssegnateGiocatore = null;
        partita.seteFinita(false);
        bottoneEsci.setOpacity(0);
        vittoriaPng.setOpacity(0);
        sconfittaPng.setOpacity(0);
        bottoneEsciHome.setOpacity(1);
        damieraLogica.setupDamiera();

        observableList = damieraGrafica.getChildren();
        partita.assegnaGiocatore(giocatore);
        for (int i = 0; i<64; i++){
            ((Pane)observableList.get(i)).getChildren().clear();
        }
    }

    /**
     * Metodo che viene richiamato quando si clicca sull'immagine della scritta "ESCI",
     * altro non fa che rimandarti alla pgina della classifica di tutti gli utenti
     * @param mouseEvent
     * @throws Exception
     */
    public void bottoneEsciClickHandler(MouseEvent mouseEvent) throws Exception {
        Parent root = FXMLLoader.load(MainIniziale.class.getResource("View/paginaClassifica.fxml"));
        Stage window = (Stage)bottoneEsci.getScene().getWindow();
        window.setScene(new Scene(root, 800, 800));
        partita.setPlayerTurnTrue();
        damaDB.setTurnoGiocatore(partita);
        partita.setAttiva(false);
    }

    /**
     * Metodo che viene richiamato quando si clicca sull'immagine con id bottoneEsciHome che ti permette a partita in corso di tornare alla home
     * @throws Exception
     */
    public void bottoneEsciHomeClickHandler() throws Exception {
        Parent root = FXMLLoader.load(MainIniziale.class.getResource("View/paginaLoggato.fxml"));
        Stage window = (Stage)bottoneEsciHome.getScene().getWindow();
        window.setScene(new Scene(root, 800, 800));
        if (partita.isInRipristino() && !partita.iseFinita()){
            partita.setInRipristino(false);
        }

        partita.setAttiva(false);
    }

    /**
     * Metodo che fa entrare il programma nella pagina in cui il programma chiede se si vuole ripristinare la partita
     * @throws Exception
     */
    public void entraInRipristina() throws  Exception{
        Parent root = FXMLLoader.load(MainIniziale.class.getResource("View/paginaRipristina.fxml"));
        Stage window = (Stage)bottoneEsciHome.getScene().getWindow();
        window.setScene(new Scene(root, 800, 800));
    }

    /**
     * Metodo che controlla se c'è una partita da ripristinare, se c'è allora richiama la funzione che fa entra il programma in modalità di ripristino
     * successivamente controlla se non sia nella modalità ripristino e che la prtita in corso sia non attiva, se si verifica ciò allora fa cominciare
     * una nuova partita, se invece la partita si trova già in modalità ripristino, si ripristina tutta la partita che era in sospeso
     * @param mouseEvent evento del muose captato quando il curosore entra nella finestra della pagina
     * @throws Exception
     */
    public void mouseEnteredFinestra(MouseEvent mouseEvent) throws  Exception{

        if (partita.isAttiva()) return;

        if (!partita.isInRipristino()) {
            try {
                if (partita.controlla() && !partita.isAttiva()) {
                    partita.setPlayerTurnTrue();
                    damaDB.setTurnoGiocatore(partita);
                    entraInRipristina();
                    return;

                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        if (!partita.isInRipristino() && !partita.isAttiva()) {
            partita.iniziaPartita();
            inizializzaPedineGraficamente();
            siPuoMuovere = true;


            for (int i = 0; i < cpu.getPedineAssegnateCpu().size(); i++) {
                cpu.getPedineAssegnateCpu().get(i).registerObserver(this);
            }

            for (int i = 0; i < giocatore.getPedineAssegnate().size(); i++) {
                giocatore.getPedineAssegnate().get(i).registerObserver(this);
            }

            if (partita.getGiocatoreGiocante().isSquadraNera()) {
                partita.setPlayerTurnFalse();
                damaDB.setTurnoGiocatore(partita);
            }
        }

        if (partita.isInRipristino()){
            damieraLogica.resetDamiera();
            partita.controllaERipristina();
            partita.iniziaPartitaRipristinata();
            pedineAssegnateGiocatore = giocatore.getPedineAssegnate();
            for (int i = 0; i < cpu.getPedineAssegnateCpu().size(); i++) {
                cpu.getPedineAssegnateCpu().get(i).registerObserver(this);
            }

            for (int i = 0; i < giocatore.getPedineAssegnate().size(); i++) {
                giocatore.getPedineAssegnate().get(i).registerObserver(this);
            }

            ripristinaAVideo();
        }
    }
}
