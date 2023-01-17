package com.example.damafx.Model.Pedine;

/**
 * Classe da riferimento per pedinaclient (FlyWeight)
 */
public class PedinaFactory {
    private static PedinaInterfaccia pedina;

    /**
     * Metodo che restituisce una pedinaInterfaccia
     * @return
     */
    public static PedinaInterfaccia getPedina(){
        if (pedina==null){
            pedina = new PedinaImplementazione();
        }
        return pedina;
    }
}
