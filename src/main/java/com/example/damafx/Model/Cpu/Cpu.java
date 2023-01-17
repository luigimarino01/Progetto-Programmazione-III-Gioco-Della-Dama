package com.example.damafx.Model.Cpu;

import com.example.damafx.Model.Pedine.PedinaClient;

import java.util.ArrayList;

/**
 * Classe utilizzata per la creazione del giocatore automatico
 */
public class Cpu {
    private IStrategia strategia;
    private StatoCpu stato;
    private static Cpu istanza;
    private boolean squadraRossa;
    private boolean squadraNera;
    public boolean turnoCpu = false;
    private ArrayList<PedinaClient> pedineAssegnateCpu = new ArrayList<PedinaClient>();

    /**
     * Metodo che setta il turno della cpu
     * @param turnoCpu parametro booleano che serve a modificare il turno
     */
    public void setTurnoCpu(boolean turnoCpu) {
        this.turnoCpu = turnoCpu;
    }

    /**
     * Metodo che serve a settare se la squadra della cpu a quella rossa
     * @param squadraRossa
     */
    public void setSquadraRossa(boolean squadraRossa) {
        this.squadraRossa = squadraRossa;
    }
    /**
     * Metodo che serve a settare se la squadra della cpu a quella nera
     * @param squadraNera
     */
    public void setSquadraNera(boolean squadraNera) {
        this.squadraNera = squadraNera;
    }

    /**
     * Costruttore privato per rendere la classe singleton
     */
    private Cpu(){

    }

    /**
     * Metodo che serve a restituire l'unica istanza della cpu e se non esiste la crea
     * @return
     */
    public static Cpu getIstanza(){
        if (istanza==null){
            istanza = new Cpu();
        }
            return istanza;
    }

    /**
     * Metodo che viene invocato per settare la strategia da applicare
     * @param stato
     */
    public void setStato(StatoCpu stato){
        this.stato = stato;
    }

    /**
     * Metodo che viene invocato per settare il tipo di strategia
     */
    public void setStrategia(){
        strategia = stato.impostaStrategia();
    }

    /**
     * Metodo che viene invocato per far muovere la pedina con un tipo di strategia
     * @throws InterruptedException
     */
    public void muoviPedina() throws InterruptedException {
        strategia.muoviPedina();
    }

    /**
     * Metodo che restituisce l'ArrayList delle pedine che sono state assegnate alla cpu
     * @return
     */
    public ArrayList<PedinaClient> getPedineAssegnateCpu() {
        return pedineAssegnateCpu;
    }

    /**
     * Metodo che setta le pedine che devono essere assegnate alla cpu
     * @param pedineAssegnate è un ArrayList di tipo PedinaClient che contiene tutte le pedine che appartengono alla cpu
     */
    public void setPedineAssegnateCpu(ArrayList<PedinaClient> pedineAssegnate) {
        this.pedineAssegnateCpu = pedineAssegnate;
    }

    /**
     * Metodo che rimuove la pedina dall'ArrayList della cpu
     * i due parametri insieme servono a vedere la pedina da rimuovere in che posizione è,
     * ed in base alla posizione risaliamo a qual è la pedina da rimuovere nell'ArrayList
     * @param x posizione ascissale della damiera
     * @param y posizione ordinale della damiera
     */
    public void rimuoviPedina(int x, int y){
        for (int i = 0; i < pedineAssegnateCpu.size(); i++){
            if(pedineAssegnateCpu.get(i).getxCorrente() == x && pedineAssegnateCpu.get(i).getyCorrente() == y){
                pedineAssegnateCpu.remove(i);
            }
        }
    }
}
