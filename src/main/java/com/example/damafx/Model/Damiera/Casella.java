package com.example.damafx.Model.Damiera;
import com.example.damafx.Model.Pedine.*;

/**
 * Classe che serve a costruire il campo di gioco
 */
public class Casella{
    private PedinaClient pedinaSopra = null;

    /**
     * Metodo che serve a settare la pedina sulla casella
     * @param pedina parametro che viene copiato all'interno della classe e modifica la variabile della casella
     */
    public void setPedinaSopra(PedinaClient pedina){
        this.pedinaSopra = pedina;
    }

    /**
     * Metodo che restituisce la pedina presente sulla casella
     * @return
     */
    public PedinaClient getPedinaSopra(){
        return pedinaSopra;
    }
}
