package com.example.damafx.Model.Giocatore;

/**
 * La classe che raccoglie i dati del giocatore che ha effettuato il login
 */
public class GiocatoreLoggato {

    public String nome;

    public String cognome;
    public String nicknameLoggato;
    public String emailLoggato;
    public int vittorieLoggato;
    private static GiocatoreLoggato istanza;

    /**
     * Metodo che serve a restituire l'unica istanza di giocatore loggato
     * @return
     */
    public static GiocatoreLoggato getIstanza(){
        if (istanza == null){
            istanza = new GiocatoreLoggato();
        }
        return istanza;
    }

    /**
     * costruttore privato per il singleton
     */
    private GiocatoreLoggato(){}
}
