package com.example.damafx.Model.Pedine;

import com.example.damafx.Model.Damiera.Damiera;
import com.example.damafx.Model.Database.DamaDB;
import com.example.damafx.Model.Partita.Partita;

public class PedinaClient {
    private final PedinaInterfaccia pedina = PedinaFactory.getPedina();
    private final DamaDB damaDB = DamaDB.getIstanza();

    private final Partita partita = Partita.getIstanza();
    private int xCorrente = 0;
    private int yCorrente = 0;
    private boolean rossa;
    private boolean nera;
    private boolean dama;


    Damiera damiera = Damiera.getInstanza();

    private Observer observer;

    public void registerObserver(Observer observer) {
        this.observer = observer;
    }

    public void setxCorrente(int xCorrente) {
        this.xCorrente = xCorrente;
    }

    public void setyCorrente(int yCorrente) {
        this.yCorrente = yCorrente;
    }

    public boolean isRossa() {
        return rossa;
    }

    public void setRossa(boolean rossa) {
        this.rossa = rossa;
    }

    public boolean isNera() {
        return nera;
    }

    public void setNera(boolean nera) {
        this.nera = nera;
    }

    public void setDama(boolean dama) {
        this.dama = dama;
    }

    public boolean isDama() {
        return dama;
    }

    public PedinaClient(){
    }

    /**
     * Metodo che verifica se il moviento della pedina è andato a buon fine allora modifica ciò che c'è da modificare sul DB
     * @param nuovaX parametro che indica la nuova ascissa su cui deve andare la pedina
     * @param nuovaY parametro che indica la nuova ordinata su cui deve andare la pedina
     * @return
     */
    public boolean muoviPedina(int nuovaX, int nuovaY){
        if(pedina.muoviPedina(this, xCorrente, yCorrente, nuovaX, nuovaY)) {
            damaDB.spostamentoPedinaDB(this, nuovaX, nuovaY);
            damaDB.spostaInContiene(this,nuovaX,nuovaY,partita.getGiocatoreGiocante());
            this.xCorrente = nuovaX;
            this.yCorrente = nuovaY;


            if (this.xCorrente == 0){
                damaDB.damaInContiene(this,partita.getGiocatoreGiocante());
                this.dama = true;
            }
            return true;
        }
        return false;
    }

    /**
     * Metodo che verifica se il moviento della pedina è andato a buon fine allora modifica ciò che c'è da modificare sul DB
     * @param nuovaX parametro che indica la nuova ascissa su cui deve andare la pedina
     * @param nuovaY parametro che indica la nuova ordinata su cui deve andare la pedina
     * @return
     */
    public void muoviPedinaCpu(int nuovaX, int nuovaY){
        if (pedina.muoviPedinaCpu(this, xCorrente, yCorrente, nuovaX, nuovaY)){
            damaDB.spostamentoPedinaDB(this,nuovaX,nuovaY);
            damaDB.spostaInContiene(this,nuovaX,nuovaY,partita.getGiocatoreGiocante());
            if(observer!=null) {
                observer.muoviPedinaObserver(xCorrente, yCorrente, nuovaX, nuovaY);
            }
            this.xCorrente = nuovaX;
            this.yCorrente = nuovaY;

            if (this.xCorrente == 7){
                damaDB.damaInContiene(this,partita.getGiocatoreGiocante());
                this.dama = true;
            }
        }
    }

    /**
     * Metodo che serve a verificare se la pedina può effettuare un qualche movimento
     * @return
     */
    public boolean siPuoMuovere(){

        if (this.isDama()) {
            if (xCorrente<7 && yCorrente<7 && damiera.tavolaDaGioco[xCorrente + 1][yCorrente + 1].getPedinaSopra() == null) {
                return true;
            }

            else if (xCorrente<7 && yCorrente>0 && damiera.tavolaDaGioco[xCorrente + 1][yCorrente - 1].getPedinaSopra() == null){
                return true;
            }

            else if (xCorrente>0 && yCorrente>0 && damiera.tavolaDaGioco[xCorrente - 1][yCorrente - 1].getPedinaSopra() == null) {
                return true;
            }

            else return xCorrente > 0 && yCorrente < 7 && damiera.tavolaDaGioco[xCorrente - 1][yCorrente + 1].getPedinaSopra() == null;
        }
        else {
            if (xCorrente<7 && yCorrente<7 && damiera.tavolaDaGioco[xCorrente + 1][yCorrente + 1].getPedinaSopra() == null) {
                return true;
            }
            else return xCorrente > 0 && yCorrente > 0 && damiera.tavolaDaGioco[xCorrente - 1][yCorrente - 1].getPedinaSopra() == null;
        }
    }

    /**
     * Metodo che serve a capire se la pedina in questione deve mangiare o meno, se deve mangiare viene restituita la pedina che deve mangiare
     * @return
     */
    public PedinaClient deveMangiare(){
        if (pedina.puoMangiare(this) != 0){
            return this;
        }
        return null;
    }

    /**
     * Metodo che effettua il movimento della pedina che deve mangiare
     * @param xR Posizione ascissale in cui si troverà la pedina di riferimento
     * @param yR Posizione ordinale in cui si troverà la pedina di riferimento
     * @return
     */
    public int mangia( int xR, int yR) {
        int res = pedina.mangia(this, xR, yR);

        if (res == 1) {
            if (observer != null) {
                observer.muoviPedinaObserver(xCorrente, yCorrente, xCorrente + 2, yCorrente + 2);
            }
            this.xCorrente = xCorrente + 2;
            this.yCorrente = yCorrente + 2;

            return 1;
        }
        else if (res == 2) {
            if (observer != null) {
                observer.muoviPedinaObserver(xCorrente, yCorrente, xCorrente + 2, yCorrente - 2);
                this.xCorrente = xCorrente + 2;
                this.yCorrente = yCorrente - 2;
                return 2;
            }
        }
        else if (res == 3) {
                if (observer != null) {
                    observer.muoviPedinaObserver(xCorrente, yCorrente, xCorrente - 2, yCorrente - 2);
                }
            this.xCorrente = xCorrente - 2;
            this.yCorrente = yCorrente - 2;
            if (this.xCorrente == 0) {
                this.setDama(true);
                damaDB.damaInContiene(this,partita.getGiocatoreGiocante());
            }
                return 3;
            }
        else if (res == 4) {
                if (observer != null) {
                    observer.muoviPedinaObserver(xCorrente, yCorrente, xCorrente - 2, yCorrente + 2);
                }
            this.xCorrente = xCorrente - 2;
            this.yCorrente = yCorrente + 2;
            if (this.xCorrente == 0) {
                this.setDama(true);
                damaDB.damaInContiene(this,partita.getGiocatoreGiocante());
            }
                return 4;
            }
            return 0;
        }
    /**
     * Metodo che effettua il movimento della pedina che deve mangiare della CPU
     * @return
     */
    public boolean mangiaCpu() {
        int res = pedina.mangiaCpu(this);
        if (res == 1) {
            if (observer != null) {
                observer.muoviPedinaObserver(xCorrente, yCorrente, xCorrente + 2, yCorrente + 2);
            }
            this.xCorrente = xCorrente + 2;
            this.yCorrente = yCorrente + 2;
            if (this.xCorrente == 7) {
                this.setDama(true);
                damaDB.damaInContiene(this,partita.getGiocatoreGiocante());
            }
            return true;
        }
        else if (res == 2) {
            if (observer != null) {
                observer.muoviPedinaObserver(xCorrente, yCorrente, xCorrente + 2, yCorrente - 2);
                this.xCorrente = xCorrente + 2;
                this.yCorrente = yCorrente - 2;
                if (this.xCorrente == 7) {
                    this.setDama(true);
                    damaDB.damaInContiene(this,partita.getGiocatoreGiocante());
                }
                return true;
            }
        }
        else if (res == 3) {
            if (observer != null) {
                observer.muoviPedinaObserver(xCorrente, yCorrente, xCorrente - 2, yCorrente - 2);
            }
            this.xCorrente = xCorrente - 2;
            this.yCorrente = yCorrente - 2;

            return true;
        }
        else if (res == 4) {
            if (observer != null) {
                observer.muoviPedinaObserver(xCorrente, yCorrente, xCorrente - 2, yCorrente + 2);
            }
            this.xCorrente = xCorrente - 2;
            this.yCorrente = yCorrente + 2;
            return true;
        }
        return false;
    }
    public int getxCorrente() {
        return xCorrente;
    }

    public int getyCorrente() {
        return yCorrente;
    }
}
