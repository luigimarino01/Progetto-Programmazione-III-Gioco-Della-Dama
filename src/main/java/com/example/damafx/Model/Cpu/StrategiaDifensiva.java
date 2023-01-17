package com.example.damafx.Model.Cpu;

import com.example.damafx.Model.Damiera.Damiera;
import com.example.damafx.Model.Database.DamaDB;
import com.example.damafx.Model.Partita.Partita;
import com.example.damafx.Model.Pedine.PedinaClient;
import java.util.ArrayList;
import java.util.Random;

public class StrategiaDifensiva implements IStrategia {
    Cpu cpu = Cpu.getIstanza();
    Partita partita = Partita.getIstanza();
    Damiera damiera = Damiera.getInstanza();
    Random randomNumber = new Random();
    int interoRandom;
    int movimento;
    ArrayList<PedinaClient> pedinaClients = new ArrayList<PedinaClient>();
    DamaDB damaDB = DamaDB.getIstanza();

    boolean haMangiato = false;

    /**
     * Metodo che quando viene chiamato verifica se la pedina passata come parametro può mangiare e se può farlo mangia e restituisce una
     * variabile booleana che fa capire se ha mangiato o meno
     * @param pedina pedina su cui si effettua il controllo se può mangiare
     * @return
     */
    private boolean mangiaSePuoi(PedinaClient pedina) {
        if (pedina.mangiaCpu()) {
            haMangiato = true;
            pedina.mangiaCpu();
            return true;
        }
        else {
            return false;
        }
    }
    /**
     * Metodo che ci permette di capire se la pedina che passiamo come parametro di input ha una mossa valida e sicura da fare.
     * per sicura si intende che la pedina con il movimento che fa è sicura al 100% che non può essere mangiata da alcuna pedina avversaria
     * Se la pedina è dama:
     * controlliamo se il movimento è possibile, quindi se la casella su cui dobbiamo spostarci è libera e se nei dintorni di dove ci spostiamo c'è o meno una DAMA
     * se c'è la dama nei dintorni il movimento non sarà sicuro ma se invece ci sarà una pedina qualunque o due pedine di qualsiasi tipo sulla stessa diagonale(in alto e in basso a dove la pedina deve spostarsi)
     * il movimento sarà possibile e sicuro
     * Se la pedina non è dama:
     * si fanno gli stessi controlli di quando si è dama ma solo in due direzione, ovvero quelle possibili verso il basso, e in più andranno omessi
     * controlli sulla tipologia delle pedine avversarie. Per tipologia si intende se è dama o meno
     * @param pedinaClient Pedina sulla quale viene effettuato il controllo se si può muovere in modo sicuro
     */
    protected int puoMuoversiSicuramente(PedinaClient pedinaClient){
        int xCorr = pedinaClient.getxCorrente();
        int yCorr = pedinaClient.getyCorrente();

        if (damiera.tavolaDaGioco[xCorr][yCorr] == null){
            return 0;
        }
        if(pedinaClient.isDama()){
            //BASSO A SINISTRA
            if(xCorr<6 && yCorr>1 && damiera.tavolaDaGioco[xCorr+1][yCorr-1].getPedinaSopra()==null && (damiera.tavolaDaGioco[xCorr+2][yCorr-2].getPedinaSopra()==null || (damiera.tavolaDaGioco[xCorr+2][yCorr-2].getPedinaSopra()!=null && (!damiera.tavolaDaGioco[xCorr+2][yCorr-2].getPedinaSopra().isDama() || pedinaClient.isNera()==damiera.tavolaDaGioco[xCorr+2][yCorr-2].getPedinaSopra().isNera())))){
                if((damiera.tavolaDaGioco[xCorr+2][yCorr].getPedinaSopra()==null && damiera.tavolaDaGioco[xCorr][yCorr-2].getPedinaSopra()==null) || (damiera.tavolaDaGioco[xCorr+2][yCorr].getPedinaSopra()!=null && damiera.tavolaDaGioco[xCorr][yCorr-2].getPedinaSopra()!=null) || (damiera.tavolaDaGioco[xCorr+2][yCorr].getPedinaSopra()!=null && (!damiera.tavolaDaGioco[xCorr+2][yCorr].getPedinaSopra().isDama()||pedinaClient.isNera()==damiera.tavolaDaGioco[xCorr+2][yCorr].getPedinaSopra().isNera()) && damiera.tavolaDaGioco[xCorr][yCorr-2].getPedinaSopra()==null) || (damiera.tavolaDaGioco[xCorr][yCorr-2].getPedinaSopra()!=null && (!damiera.tavolaDaGioco[xCorr][yCorr-2].getPedinaSopra().isDama() || pedinaClient.isNera()==damiera.tavolaDaGioco[xCorr][yCorr-2].getPedinaSopra().isNera()) && damiera.tavolaDaGioco[xCorr+2][yCorr].getPedinaSopra()==null)){
                    return 1;
                }
            }

            //BASSO A DESTRA
            if(xCorr<6 && yCorr<6 && damiera.tavolaDaGioco[xCorr+1][yCorr+1].getPedinaSopra()==null && (damiera.tavolaDaGioco[xCorr+2][yCorr+2].getPedinaSopra()==null || (damiera.tavolaDaGioco[xCorr+2][yCorr+2].getPedinaSopra()!=null && (!damiera.tavolaDaGioco[xCorr+2][yCorr+2].getPedinaSopra().isDama() || pedinaClient.isNera()==damiera.tavolaDaGioco[xCorr+2][yCorr+2].getPedinaSopra().isNera())))){
                if((damiera.tavolaDaGioco[xCorr+2][yCorr].getPedinaSopra()==null && damiera.tavolaDaGioco[xCorr][yCorr+2].getPedinaSopra()==null) || (damiera.tavolaDaGioco[xCorr+2][yCorr].getPedinaSopra()!=null && damiera.tavolaDaGioco[xCorr][yCorr+2].getPedinaSopra()!=null) || (damiera.tavolaDaGioco[xCorr+2][yCorr].getPedinaSopra()!=null && (!damiera.tavolaDaGioco[xCorr+2][yCorr].getPedinaSopra().isDama()||pedinaClient.isNera()==damiera.tavolaDaGioco[xCorr+2][yCorr].getPedinaSopra().isNera()) && damiera.tavolaDaGioco[xCorr][yCorr+2].getPedinaSopra()==null) || (damiera.tavolaDaGioco[xCorr][yCorr+2].getPedinaSopra()!=null && (!damiera.tavolaDaGioco[xCorr][yCorr+2].getPedinaSopra().isDama() || pedinaClient.isNera()==damiera.tavolaDaGioco[xCorr][yCorr+2].getPedinaSopra().isNera()) && damiera.tavolaDaGioco[xCorr+2][yCorr].getPedinaSopra()==null)){
                    return 2;
                }
            }

            //ALTO A SINISTRA
            if(xCorr>1 && yCorr>1 && damiera.tavolaDaGioco[xCorr-1][yCorr-1].getPedinaSopra()==null && (damiera.tavolaDaGioco[xCorr-2][yCorr-2].getPedinaSopra()==null || (damiera.tavolaDaGioco[xCorr-2][yCorr-2].getPedinaSopra()!=null && (!damiera.tavolaDaGioco[xCorr-2][yCorr-2].getPedinaSopra().isDama() || pedinaClient.isNera()==damiera.tavolaDaGioco[xCorr-2][yCorr-2].getPedinaSopra().isNera())))){
                if((damiera.tavolaDaGioco[xCorr-2][yCorr].getPedinaSopra()==null && damiera.tavolaDaGioco[xCorr][yCorr-2].getPedinaSopra()==null) || (damiera.tavolaDaGioco[xCorr-2][yCorr].getPedinaSopra()!=null && damiera.tavolaDaGioco[xCorr][yCorr-2].getPedinaSopra()!=null) || (damiera.tavolaDaGioco[xCorr-2][yCorr].getPedinaSopra()!=null && (!damiera.tavolaDaGioco[xCorr-2][yCorr].getPedinaSopra().isDama()||pedinaClient.isNera()==damiera.tavolaDaGioco[xCorr-2][yCorr].getPedinaSopra().isNera()) && damiera.tavolaDaGioco[xCorr][yCorr-2].getPedinaSopra()==null) || (damiera.tavolaDaGioco[xCorr][yCorr-2].getPedinaSopra()!=null && (!damiera.tavolaDaGioco[xCorr][yCorr-2].getPedinaSopra().isDama() || pedinaClient.isNera()==damiera.tavolaDaGioco[xCorr][yCorr-2].getPedinaSopra().isNera()) && damiera.tavolaDaGioco[xCorr-2][yCorr].getPedinaSopra()==null)){
                    return 3;
                }
            }

            //ALTO A DESTRA
            if(xCorr>1 && yCorr<6 && damiera.tavolaDaGioco[xCorr-1][yCorr+1].getPedinaSopra()==null && (damiera.tavolaDaGioco[xCorr-2][yCorr+2].getPedinaSopra()==null || (damiera.tavolaDaGioco[xCorr-2][yCorr+2].getPedinaSopra()!=null && (!damiera.tavolaDaGioco[xCorr-2][yCorr+2].getPedinaSopra().isDama() || pedinaClient.isNera()==damiera.tavolaDaGioco[xCorr-2][yCorr+2].getPedinaSopra().isNera())))){
                if((damiera.tavolaDaGioco[xCorr-2][yCorr].getPedinaSopra()==null && damiera.tavolaDaGioco[xCorr][yCorr+2].getPedinaSopra()==null) || (damiera.tavolaDaGioco[xCorr-2][yCorr].getPedinaSopra()!=null && damiera.tavolaDaGioco[xCorr][yCorr+2].getPedinaSopra()!=null) || (damiera.tavolaDaGioco[xCorr-2][yCorr].getPedinaSopra()!=null && (!damiera.tavolaDaGioco[xCorr-2][yCorr].getPedinaSopra().isDama()||pedinaClient.isNera()==damiera.tavolaDaGioco[xCorr-2][yCorr].getPedinaSopra().isNera()) && damiera.tavolaDaGioco[xCorr][yCorr+2].getPedinaSopra()==null) || (damiera.tavolaDaGioco[xCorr][yCorr+2].getPedinaSopra()!=null && (!damiera.tavolaDaGioco[xCorr][yCorr+2].getPedinaSopra().isDama() || pedinaClient.isNera()==damiera.tavolaDaGioco[xCorr][yCorr+2].getPedinaSopra().isNera()) && damiera.tavolaDaGioco[xCorr-2][yCorr].getPedinaSopra()==null)){
                    return 4;
                }
            }
        }
        else {

            //BASSO A SINISTRA
            if(xCorr<6 && yCorr>1 && damiera.tavolaDaGioco[xCorr+1][yCorr-1].getPedinaSopra()==null && (damiera.tavolaDaGioco[xCorr+2][yCorr-2].getPedinaSopra()==null || (damiera.tavolaDaGioco[xCorr+2][yCorr-2].getPedinaSopra()!=null && (!damiera.tavolaDaGioco[xCorr+2][yCorr-2].getPedinaSopra().isDama() || pedinaClient.isNera()==damiera.tavolaDaGioco[xCorr+2][yCorr-2].getPedinaSopra().isNera())))){
                if((damiera.tavolaDaGioco[xCorr+2][yCorr].getPedinaSopra()==null && damiera.tavolaDaGioco[xCorr][yCorr-2].getPedinaSopra()==null) || (damiera.tavolaDaGioco[xCorr+2][yCorr].getPedinaSopra()!=null && damiera.tavolaDaGioco[xCorr][yCorr-2].getPedinaSopra()!=null) || (damiera.tavolaDaGioco[xCorr+2][yCorr].getPedinaSopra()!=null && (!damiera.tavolaDaGioco[xCorr+2][yCorr].getPedinaSopra().isDama()||pedinaClient.isNera()==damiera.tavolaDaGioco[xCorr+2][yCorr].getPedinaSopra().isNera()) && damiera.tavolaDaGioco[xCorr][yCorr-2].getPedinaSopra()==null) || (damiera.tavolaDaGioco[xCorr][yCorr-2].getPedinaSopra()!=null && (!damiera.tavolaDaGioco[xCorr][yCorr-2].getPedinaSopra().isDama() || pedinaClient.isNera()==damiera.tavolaDaGioco[xCorr][yCorr-2].getPedinaSopra().isNera()) && damiera.tavolaDaGioco[xCorr+2][yCorr].getPedinaSopra()==null)){
                    return 1;
                }
            }

            //BASSO A DESTRA
            if(xCorr<6 && yCorr<6 && damiera.tavolaDaGioco[xCorr+1][yCorr+1].getPedinaSopra()==null && (damiera.tavolaDaGioco[xCorr+2][yCorr+2].getPedinaSopra()==null || (damiera.tavolaDaGioco[xCorr+2][yCorr+2].getPedinaSopra()!=null && (!damiera.tavolaDaGioco[xCorr+2][yCorr+2].getPedinaSopra().isDama() || pedinaClient.isNera()==damiera.tavolaDaGioco[xCorr+2][yCorr+2].getPedinaSopra().isNera())))){
                if((damiera.tavolaDaGioco[xCorr+2][yCorr].getPedinaSopra()==null && damiera.tavolaDaGioco[xCorr][yCorr+2].getPedinaSopra()==null) || (damiera.tavolaDaGioco[xCorr+2][yCorr].getPedinaSopra()!=null && damiera.tavolaDaGioco[xCorr][yCorr+2].getPedinaSopra()!=null) || (damiera.tavolaDaGioco[xCorr+2][yCorr].getPedinaSopra()!=null && (!damiera.tavolaDaGioco[xCorr+2][yCorr].getPedinaSopra().isDama()||pedinaClient.isNera()==damiera.tavolaDaGioco[xCorr+2][yCorr].getPedinaSopra().isNera()) && damiera.tavolaDaGioco[xCorr][yCorr+2].getPedinaSopra()==null) || (damiera.tavolaDaGioco[xCorr][yCorr+2].getPedinaSopra()!=null && (!damiera.tavolaDaGioco[xCorr][yCorr+2].getPedinaSopra().isDama() || pedinaClient.isNera()==damiera.tavolaDaGioco[xCorr][yCorr+2].getPedinaSopra().isNera()) && damiera.tavolaDaGioco[xCorr+2][yCorr].getPedinaSopra()==null)){
                    return 2;
                }
            }
        }
        return 0;
    }

    /**
     * Metodo che prende una pedina come input e la fa muovere in modo totalmente casuale se è possibile
     * @param pedinaClient pedina su cui si effettua il controllo se può fare il movimento casuale
     * @return
     */
    protected int muoviCasualmente(PedinaClient pedinaClient){
        int xCorr = pedinaClient.getxCorrente();
        int yCorr = pedinaClient.getyCorrente();

        if (pedinaClient.isDama()) {
            if (xCorr<7 && yCorr<7 && damiera.tavolaDaGioco[xCorr + 1][yCorr + 1].getPedinaSopra() == null) {
                pedinaClient.muoviPedinaCpu(xCorr + 1, yCorr + 1);
                return 1;
            }
            else if (xCorr<7 && yCorr>0 && damiera.tavolaDaGioco[xCorr + 1][yCorr - 1].getPedinaSopra() == null){
                pedinaClient.muoviPedinaCpu(xCorr + 1, yCorr - 1);
                return 1;
            }
            else if (xCorr>0 && yCorr>0 && damiera.tavolaDaGioco[xCorr - 1][yCorr - 1].getPedinaSopra() == null) {
                pedinaClient.muoviPedinaCpu(xCorr - 1, yCorr - 1);
                return 1;
            }
            else if (xCorr>0 && yCorr<7 && damiera.tavolaDaGioco[xCorr - 1][yCorr + 1].getPedinaSopra() == null) {
                pedinaClient.muoviPedinaCpu(xCorr - 1, yCorr + 1);
                return 1;
            }
        }
        else {
            if (xCorr<7 && yCorr<7 && damiera.tavolaDaGioco[xCorr + 1][yCorr + 1].getPedinaSopra() == null) {
                pedinaClient.muoviPedinaCpu(xCorr + 1, yCorr + 1);
                return 1;
            }
            else if (xCorr<7 && yCorr>0 && damiera.tavolaDaGioco[xCorr + 1][yCorr - 1].getPedinaSopra() == null) {
                pedinaClient.muoviPedinaCpu(xCorr + 1, yCorr - 1);
                return 1;
            }
        }
        return 0;
    }
    /**Metodo che fa muovere, se è possibile, la pedina passata come parametro di input verso il bordo più vicino
     * @param pedinaClient pedina su cui si effettua il controllo se può fare il movimento verso i brodi
     * @return
     */
    protected boolean muovitiVersoIBordi(PedinaClient pedinaClient){
        if (pedinaClient.getyCorrente() < 4 && pedinaClient.getyCorrente() >0 && pedinaClient.getxCorrente() > 0 && pedinaClient.getxCorrente() < 7){
            if (pedinaClient.isDama()){
                if (damiera.tavolaDaGioco[pedinaClient.getxCorrente()-1][pedinaClient.getyCorrente()-1].getPedinaSopra() == null) {
                    pedinaClient.muoviPedinaCpu(pedinaClient.getxCorrente() - 1, pedinaClient.getyCorrente() - 1);
                    return true;
                }
                else if(damiera.tavolaDaGioco[pedinaClient.getxCorrente()+1][pedinaClient.getyCorrente()-1].getPedinaSopra() == null) {
                    pedinaClient.muoviPedinaCpu(pedinaClient.getxCorrente() + 1, pedinaClient.getyCorrente() - 1);
                    return true;
                }

            }
            else{
                if(damiera.tavolaDaGioco[pedinaClient.getxCorrente()+1][pedinaClient.getyCorrente()-1].getPedinaSopra() == null) {
                    pedinaClient.muoviPedinaCpu(pedinaClient.getxCorrente() + 1, pedinaClient.getyCorrente() - 1);
                    return true;
                }
            }
        } else  if (pedinaClient.getyCorrente() > 3 && pedinaClient.getyCorrente() < 7 && pedinaClient.getxCorrente() > 0 && pedinaClient.getxCorrente() < 7){
            if (pedinaClient.isDama()){
                if (damiera.tavolaDaGioco[pedinaClient.getxCorrente()-1][pedinaClient.getyCorrente()+1].getPedinaSopra() == null) {
                    pedinaClient.muoviPedinaCpu(pedinaClient.getxCorrente() - 1, pedinaClient.getyCorrente() + 1);
                    return true;
                }
                else if(damiera.tavolaDaGioco[pedinaClient.getxCorrente()+1][pedinaClient.getyCorrente()+1].getPedinaSopra() == null) {
                    pedinaClient.muoviPedinaCpu(pedinaClient.getxCorrente() + 1, pedinaClient.getyCorrente() + 1);
                    return true;
                }

            }
            else{
                if(damiera.tavolaDaGioco[pedinaClient.getxCorrente()+1][pedinaClient.getyCorrente()+1].getPedinaSopra() == null) {
                    pedinaClient.muoviPedinaCpu(pedinaClient.getxCorrente() + 1, pedinaClient.getyCorrente() + 1);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Metodo che gestisce tutto il movimento delle pedine
     * 1. cattura una pedina se ha la possibilità di catturare.
     * 2.a nel 70% dei casi muove la pedina con meno probabilità di essere catturata (la pedina che garantisce di non essere mangiata alla prossima mossa dell’avversario).
     * 2.b nel 30% dei casi muove la pedina casualmente.
     * 3. muove la pedina mantenendosi sui bordi della scacchiera.
     * Si effettuano le operazioni nella seguente modalità
     * inizialmente si verifica se si può mangiare
     * se non si può mangiare si verifica se si può fare un movimento in maniera sicura tramite la generazione di un numero random, facendo in modo che ci sia il 70% di probavilità che si faccia la mossa sicura
     * mentre nel 30% dei casi il movimento da effettuare è quello in modo casuale
     * se però il movimento in modo sicuro non può essere effettuato allora viene richiamata la funzione che permette di muovere la pedina verso i bordi
     * per motivi di sicurezza abbiamo dovuto inserire il movimento casuale dopo aver provato ad effettuare un movimento sicuro e un movimento verso i bordi e non esserci riusciti
     */
    @Override
    public void muoviPedina(){
        cpu.setTurnoCpu(true);
        interoRandom = randomNumber.nextInt(11);
        pedinaClients = cpu.getPedineAssegnateCpu();
        haMangiato = false;
        int i = 0;

        while (!mangiaSePuoi(cpu.getPedineAssegnateCpu().get(i)) && i<cpu.getPedineAssegnateCpu().size()-1){
            i++;
        }
        if (haMangiato){
            partita.setPlayerTurnTrue();
            damaDB.setTurnoGiocatore(partita);
            return;
        }
        if (interoRandom<7) {
            for (PedinaClient pedinaClient : pedinaClients) {
                movimento = puoMuoversiSicuramente(pedinaClient);
                if (movimento == 1) {
                    pedinaClient.muoviPedinaCpu(pedinaClient.getxCorrente() + 1, pedinaClient.getyCorrente() - 1);
                    partita.setPlayerTurnTrue();
                    damaDB.setTurnoGiocatore(partita);
                    return;
                } else if (movimento == 2) {
                    pedinaClient.muoviPedinaCpu(pedinaClient.getxCorrente() + 1, pedinaClient.getyCorrente() + 1);

                    partita.setPlayerTurnTrue();
                    damaDB.setTurnoGiocatore(partita);
                    return;
                } else if (movimento == 3) {
                    pedinaClient.muoviPedinaCpu(pedinaClient.getxCorrente() - 1, pedinaClient.getyCorrente() - 1);

                    partita.setPlayerTurnTrue();
                    damaDB.setTurnoGiocatore(partita);
                    return;
                }
                else if (movimento == 4){

                    pedinaClient.muoviPedinaCpu(pedinaClient.getxCorrente() - 1, pedinaClient.getyCorrente() + 1);

                    partita.setPlayerTurnTrue();
                    damaDB.setTurnoGiocatore(partita);
                    return;
                }
                else if (movimento == 0) {

                }
            }
            for (PedinaClient pedinaClient : pedinaClients) {
                if (muovitiVersoIBordi(pedinaClient)){
                    partita.setPlayerTurnTrue();
                    damaDB.setTurnoGiocatore(partita);
                    return;
                }
            }
        }
        else if (interoRandom>=7) {
            for (PedinaClient pedinaClient : pedinaClients) {
                movimento = muoviCasualmente(pedinaClient);
                if (movimento != 0) {


                    partita.setPlayerTurnTrue();
                    damaDB.setTurnoGiocatore(partita);
                    return;
                }
            }
        }
        for (PedinaClient pedinaClient : pedinaClients) {
            movimento = muoviCasualmente(pedinaClient);
            if (movimento != 0) {


                partita.setPlayerTurnTrue();
                damaDB.setTurnoGiocatore(partita);
                return;
            }

        }
        partita.setPlayerTurnTrue();
        damaDB.setTurnoGiocatore(partita);
        partita.seteFinita(true);
    }

}




