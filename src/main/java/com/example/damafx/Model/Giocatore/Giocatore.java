package com.example.damafx.Model.Giocatore;

import com.example.damafx.Model.Database.DamaDB;
import com.example.damafx.Model.Pedine.PedinaClient;

import java.util.ArrayList;

/**
 * Classe che serve a far giocare l'utente
 */
public class Giocatore {
    DamaDB damaDB = DamaDB.getIstanza();
    private final GiocatoreBean giocatore = new GiocatoreBean();
    private ArrayList<PedinaClient> pedineAssegnate = new ArrayList<PedinaClient>();

    private boolean squadraRossa;
    private boolean squadraNera;

    public boolean isSquadraRossa() {
        return squadraRossa;
    }

    public void setSquadraRossa(boolean squadraRossa) {
        this.squadraRossa = squadraRossa;
    }

    public boolean isSquadraNera() {
        return squadraNera;
    }

    public void setSquadraNera(boolean squadraNera) {
        this.squadraNera = squadraNera;
    }

    /**
     * Costruttore della classe in cui si inizializzano tutte le variabili del Giocatore
     * @param nome stringa che indica il nome
     * @param cognome stringa che indica il cognome
     * @param nickname stringa che indica il nickname
     * @param email stringa che indica l'email
     * @param vittorie stringa che indica le vittorie
     */
    public Giocatore(String nome, String cognome, String nickname, String email, int vittorie){
        giocatore.setNome(nome);
        giocatore.setCognome(cognome);
        giocatore.setNickname(nickname);
        giocatore.setEmail(email);
        giocatore.setNumeroVittorie(vittorie);
    }

    public ArrayList<PedinaClient> getPedineAssegnate() {
        return pedineAssegnate;
    }

    public void setPedineAssegnate(ArrayList<PedinaClient> pedineAssegnate) {
        this.pedineAssegnate = pedineAssegnate;
    }

    public GiocatoreBean getGiocatore() {
        return giocatore;
    }

    public void rimuoviPedina(int x, int y){
        for (int i = 0; i<pedineAssegnate.size();i++){
            if (pedineAssegnate.get(i).getxCorrente() == x && pedineAssegnate.get(i).getyCorrente() == y){
                pedineAssegnate.remove(i);
            }
        }
    }

    /**
     * Metodo che aggiunge una vittoria al campo vittoria del giocatore e richiama il metodo contenuto in damaDB per aggiornare
     * anche nel DB la vittoria
     */
    public void aggiungiVittoria(){
        giocatore.setNumeroVittorie(giocatore.getNumeroVittorie()+1);
        damaDB.aggiungiVittoriaDB(this);
    }
}
