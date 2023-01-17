package com.example.damafx.Model.Partita;

import com.example.damafx.Model.Cpu.Cpu;
import com.example.damafx.Model.Cpu.StatoCpu;
import com.example.damafx.Model.Cpu.StatoDifensivo;
import com.example.damafx.Model.Cpu.StatoOffensivo;
import com.example.damafx.Model.Database.DamaDB;
import com.example.damafx.Model.Giocatore.Giocatore;
import com.example.damafx.Model.Pedine.PedinaClient;
import com.example.damafx.Model.Damiera.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.ArrayList;
import java.util.Random;

/**
 * classe cha fa funzionare tutta logica del gioco
 */
public class Partita {

    boolean isAttiva = false;

    public boolean isAttiva() {
        return isAttiva;
    }

    public void setAttiva(boolean attiva) {
        isAttiva = attiva;
    }

    private static Partita istanza;
    Damiera damiera = Damiera.getInstanza();
    DamaDB damaDB = DamaDB.getIstanza();

    boolean eFinita = false;
    Giocatore player;
    Cpu cpu = Cpu.getIstanza();
    StatoCpu statoCpu;
    Random randomNumber = new Random();
    ArrayList<PedinaClient> pedineNere = new ArrayList<>();
    ArrayList<PedinaClient> pedineRosse = new ArrayList<>();
    boolean inRipristino = false;
    public final BooleanProperty playerTurn = new SimpleBooleanProperty(true);

    public boolean isInRipristino() {
        return inRipristino;
    }

    public void setInRipristino(boolean inRipristino) {
        this.inRipristino = inRipristino;
    }

    /**
     * Costruttore privato per il singleton
     */
    private Partita(){}

    /**
     * Metodo che restituisce l'unica istanza della partita
     * @return
     */
    public static Partita getIstanza(){
        if (istanza == null){
            istanza = new Partita();
        }
        return istanza;
    }
    public boolean iseFinita() {
        return eFinita;
    }
    public void seteFinita(Boolean eFinita) {
        this.eFinita = eFinita;
    }

    /**
     * Metodo che inizializza la partita logicamente creando una damiera e pulendo e settando gli ArrayList delle pedine rosse e delle pedine nere
     */
    private void inizializzaPartita(){
        damiera.setupDamiera();
        pedineRosse.clear();
        pedineNere.clear();
        for (int i = 0; i<12; i++){
            pedineRosse.add(new PedinaClient());
            pedineRosse.get(i).setRossa(true);
            pedineRosse.get(i).setNera(false);
            pedineRosse.get(i).setDama(false);
        }

        for (int j = 0; j<12; j++){
            pedineNere.add(new PedinaClient());
            pedineNere.get(j).setNera(true);
            pedineNere.get(j).setRossa(false);
            pedineNere.get(j).setDama(false);
        }
    }

    /**
     * Metodo che tramite un numero geerato casualmente setta le pedine di colore rosso o nero al giocatore e setta quindi l'appartenenza
     * del giocatore ad una delle due squadre
     * @param giocatore
     */
    private void assegnaPedine(Giocatore giocatore) {
        int interoRandom = randomNumber.nextInt(11);
        if (interoRandom < 5) {
            giocatore.setPedineAssegnate(pedineNere);
            cpu.setPedineAssegnateCpu(pedineRosse);
            giocatore.setSquadraNera(true);
            giocatore.setSquadraRossa(false);
            cpu.setSquadraRossa(true);
            cpu.setSquadraNera(false);

        } else {
            giocatore.setPedineAssegnate(pedineRosse);
            cpu.setPedineAssegnateCpu(pedineNere);
            giocatore.setSquadraNera(false);
            giocatore.setSquadraRossa(true);
            cpu.setSquadraRossa(false);
            cpu.setSquadraNera(true);
        }
    }
    public Giocatore getGiocatoreGiocante(){
        return player;
    }

    /**
     * Metodo che restituisce se è il turno del giocatore
     * @return
     */
    public boolean isPlayerTurn() { return playerTurn.get(); }

    /**
     * Metodo che setta il turno del giocatore a false
     */
    public void setPlayerTurnFalse() { playerTurn.set(false); }
    /**
     * Metodo che setta il turno del giocatore a true
     */
    public void setPlayerTurnTrue() { playerTurn.set(true); }

    /**
     * Metodo che assegna il giocatore alla partita
     * @param giocatore parametro che serve per settare il giocatore della partita
     */
    public void assegnaGiocatore(Giocatore giocatore){
        this.player = giocatore;
    }

    /**
     * Metodo che inizializza sia lo stato che la strategia che la cpu deve adottare nella partita
     * @param cpu parametro che serve per settare il giocatore automatico della partita
     */
    public void inizializzaCpu(Cpu cpu){
        int i = randomNumber.nextInt(11);
        if(i<6){
            statoCpu = new StatoDifensivo();
        }
        else {
            statoCpu = new StatoOffensivo();
        }
        cpu.setStato(statoCpu);
        cpu.setStrategia();
    }

    /**
     * Metodo che setta le posizioni delle pedina logicamente sulla damiera
     */
    private void posizionaPedine(){

        if (this.player.isSquadraRossa()){
            for (int i = 0; i<4; i++){
                damiera.tavolaDaGioco[0][i*2+1].setPedinaSopra(pedineNere.get(i));
                pedineNere.get(i).setxCorrente(0);
                pedineNere.get(i).setyCorrente(i*2+1);

                damiera.tavolaDaGioco[1][i*2].setPedinaSopra(pedineNere.get(4+i));
                pedineNere.get(4+i).setxCorrente(1);
                pedineNere.get(4+i).setyCorrente(i*2);

                damiera.tavolaDaGioco[2][i*2+1].setPedinaSopra(pedineNere.get(8+i));
                pedineNere.get(8+i).setxCorrente(2);
                pedineNere.get(8+i).setyCorrente(i*2+1);
            }

            for (int i = 0; i<4; i++){
                damiera.tavolaDaGioco[7][i*2].setPedinaSopra(pedineRosse.get(i));
                pedineRosse.get(i).setxCorrente(7);
                pedineRosse.get(i).setyCorrente(i*2);

                damiera.tavolaDaGioco[6][i*2+1].setPedinaSopra(pedineRosse.get(4+i));
                pedineRosse.get(4+i).setxCorrente(6);
                pedineRosse.get(4+i).setyCorrente(i*2+1);

                damiera.tavolaDaGioco[5][i*2].setPedinaSopra(pedineRosse.get(8+i));
                pedineRosse.get(8+i).setxCorrente(5);
                pedineRosse.get(8+i).setyCorrente(i*2);
            }
        }

        else if (this.player.isSquadraNera())

        {
            for (int i = 0; i<4; i++){
                damiera.tavolaDaGioco[0][i*2+1].setPedinaSopra(pedineRosse.get(i));
                pedineRosse.get(i).setxCorrente(0);
                pedineRosse.get(i).setyCorrente(i*2+1);

                damiera.tavolaDaGioco[1][i*2].setPedinaSopra(pedineRosse.get(4+i));
                pedineRosse.get(4+i).setxCorrente(1);
                pedineRosse.get(4+i).setyCorrente(i*2);

                damiera.tavolaDaGioco[2][i*2+1].setPedinaSopra(pedineRosse.get(8+i));
                pedineRosse.get(8+i).setxCorrente(2);
                pedineRosse.get(8+i).setyCorrente(i*2+1);
            }

            for (int i = 0; i<4; i++){
                damiera.tavolaDaGioco[7][i*2].setPedinaSopra(pedineNere.get(i));
                pedineNere.get(i).setxCorrente(7);
                pedineNere.get(i).setyCorrente(i*2);

                damiera.tavolaDaGioco[6][i*2+1].setPedinaSopra(pedineNere.get(4+i));
                pedineNere.get(4+i).setxCorrente(6);
                pedineNere.get(4+i).setyCorrente(i*2+1);


                damiera.tavolaDaGioco[5][i*2].setPedinaSopra(pedineNere.get(8+i));
                pedineNere.get(8+i).setxCorrente(5);
                pedineNere.get(8+i).setyCorrente(i*2);
            }
        }
        damaDB.inizializzaPrimePedineDB(pedineRosse);
        damaDB.inizializzaSecondePedineDB(pedineNere);
    }

    /**
     * Metodo utilizzato quando si vuole ripristinare la partita e posiziona le pedine nella posizione giusta in cui erano antecedentemente
     * @param pedinaClients ArrayList delle pedine che devono essere riposizionate
     */
    private void ripristinaPosizioni(ArrayList<PedinaClient> pedinaClients){

            for (int i = 0; i<pedinaClients.size(); i++){
                damiera.tavolaDaGioco[pedinaClients.get(i).getxCorrente()][pedinaClients.get(i).getyCorrente()].setPedinaSopra(pedinaClients.get(i));
            }
    }

    /**
     * Metodo invocato per verificare se una partita è terminata
     * @return
     */
    public boolean controlla(){
        return !damaDB.checkEFinita(player);
    }

    /**
     * Metodo utilizzato per riassegnare le pedine giuste al Giocatore giusto quando si vuole ripristinare una partita
     */
    public void controllaERipristina(){
                if (damaDB.getPedineGiocatoreContiene(player).get(0).isRossa()){
                    pedineRosse = damaDB.getPedineGiocatoreContiene(player);
                    pedineNere = damaDB.getPedineCpuContiene(player);
                    player.setPedineAssegnate(pedineRosse);
                    player.setSquadraNera(false);
                    player.setSquadraRossa(true);
                    cpu.setSquadraNera(true);
                    cpu.setSquadraRossa(false);
                    cpu.setPedineAssegnateCpu(pedineNere);
                } else {
                    pedineNere = damaDB.getPedineGiocatoreContiene(player);
                    pedineRosse = damaDB.getPedineCpuContiene(player);
                    player.setPedineAssegnate(pedineNere);
                    cpu.setPedineAssegnateCpu(pedineRosse);
                    cpu.setSquadraNera(false);
                    cpu.setSquadraRossa(true);
                    player.setSquadraNera(true);
                    player.setSquadraRossa(false);
                }

                ripristinaPosizioni(pedineNere);
                ripristinaPosizioni(pedineRosse);

        }

    /**
     * Metodo utilizzato per far cominciare una partita, inizialmente facciamo il set di alcune variabili come se attiva e se la partita è finita
     * poi aggiungiamo la partita al DB
     * ed infine tramite chiamate a metodi di questa classe e non solo settiamo tutto ciò che ci serve per iniziare a giocare e per sggiunger
     * tutto al DB
     * in più facciamo uso di un listener che sta in ascolto fino a quando non capta un cambiamento della variabile player turn
     * e viene richiamato tutto ciò che si trova all'interno del listener ogni volta che il turno del giocatore cambia
     */
    public void iniziaPartita() {

            setAttiva(true);
            seteFinita(false);
            damaDB.aggiungiPartita(this);


            inizializzaPartita();
            damiera.resetDamiera();
            inizializzaCpu(cpu);
            assegnaPedine(player);
            damaDB.cambiaSquadraInPartita(player);
            posizionaPedine();


            playerTurn.addListener((obs, oldValue, newValue) -> {
                if (!newValue) {
                    try {
                        if (cpu.getPedineAssegnateCpu().size() == 0) {
                            seteFinita(true);
                            damaDB.setEfinita(player);
                            setAttiva(false);
                            return;
                        }
                        if (player.getPedineAssegnate().size() == 0) {
                            seteFinita(true);
                            damaDB.setEfinita(player);
                            setAttiva(false);
                            return;
                        }
                        cpu.muoviPedina();
                        cpu.setTurnoCpu(false);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

        }

    /**
     * Metodo utilizzato per fatr ricominciare una partita già iniziata in precedenza, fuzniona com il metodo "iniziaPartita()"
     * con la differenza che vengono omessi alcuni controlli, come quelli di aggiungi part sul DB ecc...
     */
    public void iniziaPartitaRipristinata(){
        setAttiva(true);
        seteFinita(false);
        inizializzaCpu(cpu);
        setPlayerTurnTrue();
            playerTurn.addListener((obs, oldValue, newValue) -> {
                if (!newValue) {
                    try {
                        if (cpu.getPedineAssegnateCpu().size() == 0) {
                            seteFinita(true);
                            damaDB.setEfinita(player);
                            return;
                        }
                        if (player.getPedineAssegnate().size() == 0) {
                            seteFinita(true);
                            damaDB.setEfinita(player);
                            return;
                        }
                        cpu.muoviPedina();
                        cpu.setTurnoCpu(false);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

        }
}






