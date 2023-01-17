package com.example.damafx.Model.Damiera;

public class Damiera {
    private static Damiera istanza;
    public Casella[][] tavolaDaGioco;

    /**
     * Metodo che restituisce l'unica istanze della damiera(singleton)
     * @return
     */
    public static Damiera getInstanza(){
        if (istanza == null){
            istanza = new Damiera();
        }
        return istanza;
    }

    /**
     * Metodo che setta la tavola da gioco con tutte caselle al suo interno
     */
    public void setupDamiera(){
        for (int i = 0; i<8; i++){
            for (int j = 0; j<8; j++){
                tavolaDaGioco[i][j] = new Casella();
            }
        }
    }

    /**
     * Metodo che resetta la damiera
     * prende la tavola da gioco e setta a null il contenuto della casella contenuta se è possibile
     */
    public void resetDamiera(){
        for (int i = 0; i<8; i++){
            for (int j = 0; j<8; j++){
                if (tavolaDaGioco[i][j]!=null)
                tavolaDaGioco[i][j].setPedinaSopra(null);
            }
        }
    }

    private Damiera(){
        tavolaDaGioco = new Casella[8][8];
    }




}
