package com.example.damafx.Model.Database;

import com.example.damafx.Model.Giocatore.GiocatoreBean;
import com.example.damafx.Model.Giocatore.GiocatoreLoggato;
import com.example.damafx.Model.Partita.Partita;
import com.example.damafx.Model.Giocatore.Giocatore;
import com.example.damafx.Model.Pedine.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
/**Classe che utilizziamo per tutte le operazioni da effettuare sul database*/
public class DamaDB {
    ObservableList<GiocatoreBean> observableList = FXCollections.observableArrayList();
    private static DamaDB istanza;
    private final String url = "jdbc:mysql://localhost:3306/damadatabase";
    Connection con;
    private DamaDB(){}
    public static DamaDB getIstanza(){
        if (istanza == null){
            istanza = new DamaDB();
        }
        return istanza;
    }

    /**
     * Proviamo ad effettuare una connessione verso il database, una volta connessi facciamo eseguire la query per
     * creare gli oggetti GiocatoreBean da inserire nella observableList
     * ed infine facciamo il return di quest'ultima
     * @return
     */
    public ObservableList<GiocatoreBean> ottieniClassifica(){
    int contatore = 0;
    String nickname;
    String nome;
    String cognome;
    String email;
    Integer numeroVittorie;
        try {
            con = DriverManager.getConnection(url,"ROOT","ROOT");
            PreparedStatement stmt = con.prepareStatement("SELECT nome,cognome,nickname,email,numeroVittorie FROM Giocatore ORDER BY numeroVittorie DESC LIMIT 10");
           ResultSet resultSet = stmt.executeQuery();
           while (resultSet.next()){
               nome = resultSet.getString(1);
               cognome = resultSet.getString(2);
               nickname = resultSet.getString(3);
               email= resultSet.getString(4);
               numeroVittorie = resultSet.getInt(5);
               observableList.add(new Giocatore(nome,cognome,nickname,email,numeroVittorie).getGiocatore());
           }
           return observableList;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try{
                if (con!=null)
                    con.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
        return observableList;
    }

    /**
     *Metodo che dopo aver provato ad effettuare la connesione al database ed esserci riuscito, tramite query ti restituisce il numero
     * di vittorie dell'utente loggato
     * @param nickname un parametro di tipo stringa che serve a estrapolare i dati dalla query
     * @return
     */
    public int ottieniVittorie(String nickname){
        int n = 0;
        try {
            con = DriverManager.getConnection(url,"ROOT","ROOT");
            PreparedStatement stmt = con.prepareStatement("SELECT numeroVittorie FROM Giocatore WHERE nickname = (?)");
            stmt.setString(1,nickname);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                n = resultSet.getInt(1);
            }
            return n;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try{
                if (con!=null)
                    con.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
        return n;
    }

    /**
     * Metodo che dopo aver provato ad effettuare la connesione al database ed esserci riuscito, tramite query ti aggiorna il numero
     * delle vittorie presenti per l'utente loggato in quel momento
     * @param giocatore
     */
    public void aggiungiVittoriaDB(Giocatore giocatore){

        try {
            con = DriverManager.getConnection(url,"ROOT","ROOT");
            PreparedStatement stmt = con.prepareStatement("UPDATE Giocatore SET NumeroVittorie=(?) WHERE nickname = (?)");
            stmt.setInt(1,giocatore.getGiocatore().getNumeroVittorie());
            stmt.setString(2,giocatore.getGiocatore().getNickname());
            stmt.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try{
                if (con!=null)
                    con.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Metodo che si assicura che i dati che sono stati inseriti nella pagine del login corrispondano ad un utente presente nel DB
     * @param nickname stringa che viene estrapolata dalla pagina del login nel campo nickname
     * @param email stringa che viene estrapolata dalla pagina del login nel campo email
     * @return
     */
    public boolean controllaLogin(String nickname, String email){
        try {
            con = DriverManager.getConnection(url, "ROOT", "ROOT");
            PreparedStatement stmt = con.prepareStatement("SELECT nickname, email FROM Giocatore WHERE nickname = (?) AND email = (?) ");
            stmt.setString(1, nickname);
            stmt.setString(2, email);
            ResultSet result = stmt.executeQuery();
            return result.next();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try{
                if (con!=null)
                    con.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Metodo che dopo aver provato ad effettuare la connesione al database ed esserci riuscito,
     * tramite query aggiunge un utente all'interno del database
     * @param nome stringa che viene estrapolata dalla pagina della registrazione nel campo nome
     * @param cognome stringa che viene estrapolata dalla pagina della registrazione nel campo cognome
     * @param nickname stringa che viene estrapolata dalla pagina della registrazione nel campo nickname
     * @param email stringa che viene estrapolata dalla pagina della registrazione nel campo email
     * @return
     */
    public boolean aggiungiUtente(String nome, String cognome, String nickname, String email){

        try {
            con = DriverManager.getConnection(url, "ROOT", "ROOT");
            PreparedStatement stmt = con.prepareStatement("SELECT nickname FROM Giocatore WHERE nickname = (?) ");
            stmt.setString(1, nickname);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                return false;
            } else {
                PreparedStatement query = con.prepareStatement("INSERT INTO GIOCATORE (nome, cognome, nickname, email, numeroVittorie) VALUES(?, ?, ?, ?, ?)");
                query.setString(1,nome);
                query.setString(2,cognome);
                query.setString(3,nickname);
                query.setString(4,email);
                query.setInt(5,0);
                query.execute();
                return true;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try{
                if (con!=null)
                    con.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Metodo usato per settare le pedine nel DB
     * Tramite query estrapoliamo le posizioni di tutte le prime 12 pedine tramite id, estrapoliamo la partita in cui sono situate
     * e le aggiungiamo all'interno della tabella contiene con associate alla partita tutte le pedine con tutte le proprie caratteristiche
     * @param pedine ArrayList di pedine da ispezionare
     */
    public void inizializzaPrimePedineDB(ArrayList<PedinaClient> pedine){
        int idPartita = -1;
    try {
        con = DriverManager.getConnection(url,"ROOT","ROOT");
        for (int i = 1; i<13; i++){
            PreparedStatement stmt = con.prepareStatement("UPDATE Pedina SET x = (?), y = (?) WHERE idPedina = (?)");
            stmt.setInt(1,pedine.get(i-1).getxCorrente());
            stmt.setInt(2,pedine.get(i-1).getyCorrente());
            stmt.setInt(3,i);
            stmt.execute();

            PreparedStatement query = con.prepareStatement("SELECT max(idPartita) FROM Partita");
            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()){
                idPartita=resultSet.getInt(1);
            }


            PreparedStatement stmtContiene = con.prepareStatement("INSERT INTO CONTIENE (idPartita, idPedina, x,y, viva, dama, colore) VALUES (?, ?, ?, ?,?,?, ?)");
            stmtContiene.setInt(1,idPartita);
            stmtContiene.setInt(2,i);
            stmtContiene.setInt(3,pedine.get(i-1).getxCorrente());
            stmtContiene.setInt(4,pedine.get(i-1).getyCorrente());
            stmtContiene.setInt(5,1);
            stmtContiene.setInt(6,0);
            if (pedine.get(i-1).isNera()){
                stmtContiene.setInt(7,0);
            }
            else{
                stmtContiene.setInt(7,1);
            }
            stmtContiene.execute();
        }

    }
    catch (SQLException e){
        e.printStackTrace();
    }
    finally {
        try{
            if (con!=null)
                con.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}

    /**
     * Metodo usato per settare le pedine nel DB
     * Tramite query estrapoliamo le posizioni di tutte le ultime 12 pedine tramite id, estrapoliamo la partita in cui sono situate
     * e le aggiungiamo all'interno della tabella contiene con associate alla partita tutte le pedine con tutte le proprie caratteristiche
     * @param pedine
     */
    public void inizializzaSecondePedineDB(ArrayList<PedinaClient> pedine){
        int idPartita = -1;
        try {
            con = DriverManager.getConnection(url,"ROOT","ROOT");
            for (int i = 13; i<25; i++){
                PreparedStatement stmt = con.prepareStatement("UPDATE Pedina SET x = (?), y = (?) WHERE idPedina = (?)");
                stmt.setInt(1,pedine.get(i-13).getxCorrente());
                stmt.setInt(2,pedine.get(i-13).getyCorrente());
                stmt.setInt(3,i);
                stmt.execute();


                PreparedStatement query = con.prepareStatement("SELECT max(idPartita) FROM Partita");
                ResultSet resultSet = query.executeQuery();
                while (resultSet.next()){
                    idPartita=resultSet.getInt(1);

                }


                PreparedStatement stmtContiene = con.prepareStatement("INSERT INTO CONTIENE (idPartita, idPedina, x,y, viva,dama, colore) VALUES (?, ?, ?, ?,?,?,?)");
                stmtContiene.setInt(1,idPartita);
                stmtContiene.setInt(2,i);
                stmtContiene.setInt(3,pedine.get(i-13).getxCorrente());
                stmtContiene.setInt(4,pedine.get(i-13).getyCorrente());
                stmtContiene.setInt(5,1);
                stmtContiene.setInt(6,0);
                if (pedine.get(i-13).isNera()){
                    stmtContiene.setInt(7,0);
                }
                else{
                    stmtContiene.setInt(7,1);
                }

                stmtContiene.execute();
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try{
                if (con!=null)
                    con.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Metodo che ci permette di poter spostare le posizioni x,y della pedina all'interno del DB
     * @param pedina la pedina di riferimento che deve essere aggiornata
     * @param x la posizione ascissale della pedina di riferimento che deve essere aggiornata
     * @param y la posizione ordinale della pedina di riferimento che deve essere aggiornata
     */
    public void spostamentoPedinaDB(PedinaClient pedina, int x, int y){
        try {
            con = DriverManager.getConnection(url,"ROOT","ROOT");
            PreparedStatement stmt = con.prepareStatement("UPDATE Pedina SET x = (?), y = (?) WHERE x = (?) AND  y = (?)");
            stmt.setInt(1,x);
            stmt.setInt(2,y);

            stmt.setInt(3,pedina.getxCorrente());
            stmt.setInt(4,pedina.getyCorrente());

            stmt.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try{
                if (con!=null)
                    con.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Metodo che serve a prendere il Giocatore che ha effettuato il login
     * una volta estrapolati i dati tramite query, si insericono all'interno di una variabile di tipo "Giocatore" e si restituisce quest'ultima
     * @param nicknameLoggato stringa che viene utilizzata dalla query per estrapolare i dati del giocatore
     * @return
     */
    public Giocatore getGiocatore(String nicknameLoggato) {
        try {
            con = DriverManager.getConnection(url, "ROOT", "ROOT");
            PreparedStatement stmt = con.prepareStatement("SELECT nome, cognome, email, nickname, numeroVittorie FROM Giocatore WHERE nickname = (?)");
            stmt.setString(1, nicknameLoggato);
            ResultSet resultSet = stmt.executeQuery();
            Giocatore giocatore = null;
            while (resultSet.next()) {

                giocatore = new Giocatore(resultSet.getString(1), resultSet.getString(2), resultSet.getString(4), resultSet.getString(3), resultSet.getInt(5));
            }
            return giocatore;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try{
                if (con!=null)
                    con.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Metodo che permette di aggiungere la partita nel DB
     * serve per tenere traccia delle partite che sono ancora in sospeso o che sono finite
     * @param partita parametro che ci serve per estrapolare il nickname del giocatore associato alla patrtita
     */
    public void aggiungiPartita(Partita partita){
        try {
            con = DriverManager.getConnection(url,"ROOT","ROOT");
            PreparedStatement stmt = con.prepareStatement("INSERT INTO PARTITA (nickname, finita, turnoGiocatore, squadraGiocatore) VALUES(?, ?, ?, ?)");
            stmt.setString(1,partita.getGiocatoreGiocante().getGiocatore().getNickname());
            stmt.setInt(2,1);
            stmt.setInt(3,0);
            stmt.setInt(4,0);
            stmt.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try{
                if (con!=null)
                    con.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Metodo che setta il turno del giocatore all'interno del DB, 1 se è il turno del giocatore e 0 se è il turno della CPU
     * @param partita parametro che ci serve per estrapolare il nickname del giocatore associato alla patrtita
     */
    public void setTurnoGiocatore(Partita partita){
        try {
            con = DriverManager.getConnection(url,"ROOT","ROOT");
            PreparedStatement stmt = con.prepareStatement("UPDATE PARTITA set turnoGiocatore = (?) WHERE nickname = (?)");
            if (partita.isPlayerTurn()){
                stmt.setInt(1,1);
            }else{
                stmt.setInt(1,0);
            }
            stmt.setString(2,partita.getGiocatoreGiocante().getGiocatore().getNickname());
            stmt.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try{
                if (con!=null)
                    con.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Metodo che serve per settare la partita a finita nel DB
     * @param giocatore parametro che ci serve per estrapolare il nickname del giocatore associato alla patrtita
     */
    public void setEfinita(Giocatore giocatore){
        try {
            con = DriverManager.getConnection(url,"ROOT","ROOT");
            PreparedStatement stmt = con.prepareStatement("UPDATE Partita SET finita = -1 WHERE nickname = (?) AND  finita = 1");
            stmt.setString(1, giocatore.getGiocatore().getNickname());
            stmt.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try{
                if (con!=null)
                    con.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Metodo che pulisce la tabella contiene dalla partita finita per evitare accumulo di dati superflui
     * @param giocatore parametro che ci serve per estrapolare il nickname del giocatore associato alla patrtita
     */
    public void resetContiene(Giocatore giocatore){
        int idPartita = -1;
        try {

            con = DriverManager.getConnection(url, "ROOT", "ROOT");
            PreparedStatement stmtQ = con.prepareStatement("SELECT max(idPartita) FROM Partita WHERE nickname = (?)");
            stmtQ.setString(1, giocatore.getGiocatore().getNickname());
            ResultSet resultSet = stmtQ.executeQuery();
            while (resultSet.next()) {
                idPartita = resultSet.getInt(1);
            }

            con = DriverManager.getConnection(url,"ROOT","ROOT");
            PreparedStatement stmt = con.prepareStatement("DELETE FROM CONTIENE WHERE idPartita = (?)");
            stmt.setInt(1,idPartita);
            stmt.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try{
                if (con!=null)
                    con.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Metodo che aggiorna le posiuzioni delle pedine all'interno della tabella contiene del DB
     * @param pedinaClient Pedina di riferimento che deve essere aggiornata
     * @param x parametro di aggiornamento della posizione ascissale della pedina
     * @param y parametro di aggiornamento della posizione ordinale della pedina
     * @param giocatore parametro che ci serve per estrapolare il nickname del giocatore associato alla patrtita
     */
    public void spostaInContiene(PedinaClient pedinaClient, int x, int y, Giocatore giocatore){
        try {
            int res = 0;
            con = DriverManager.getConnection(url, "ROOT", "ROOT");
            PreparedStatement stmtQ = con.prepareStatement("SELECT max(idPartita) FROM Partita WHERE nickname = (?)");
            stmtQ.setString(1, giocatore.getGiocatore().getNickname());
            ResultSet result = stmtQ.executeQuery();
            while (result.next()) {
                res = result.getInt(1);
            }

            con = DriverManager.getConnection(url,"ROOT","ROOT");
            PreparedStatement stmt = con.prepareStatement("UPDATE CONTIENE SET x = (?), y = (?) WHERE x = (?) AND  y = (?) AND idPartita = (?)");
            stmt.setInt(1,x);
            stmt.setInt(2,y);

            stmt.setInt(3,pedinaClient.getxCorrente());
            stmt.setInt(4,pedinaClient.getyCorrente());
            stmt.setInt(5,res);
            stmt.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try{
                if (con!=null)
                    con.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Metodo che setta la pedina a dama sul DB
     * @param pedinaClient Pedina di riferimento che deve aggiornare la sua condizione (essere dama o meno)
     * @param giocatore parametro che ci serve per estrapolare il nickname del giocatore associato alla patrtita
     */
    public void damaInContiene(PedinaClient pedinaClient, Giocatore giocatore){
        try {
            int res = 0;
            con = DriverManager.getConnection(url, "ROOT", "ROOT");
            PreparedStatement stmtQ = con.prepareStatement("SELECT max(idPartita) FROM Partita WHERE nickname = (?)");
            stmtQ.setString(1, giocatore.getGiocatore().getNickname());
            ResultSet result = stmtQ.executeQuery();
            while (result.next()) {
                res = result.getInt(1);
            }

            con = DriverManager.getConnection(url,"ROOT","ROOT");
            PreparedStatement stmt = con.prepareStatement("UPDATE CONTIENE SET dama =(?) WHERE x = (?) AND  y = (?) AND idPartita = (?)");
            stmt.setInt(1,1);
            stmt.setInt(2,pedinaClient.getxCorrente());
            stmt.setInt(3,pedinaClient.getyCorrente());
            stmt.setInt(4,res);



            stmt.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try{
                if (con!=null)
                    con.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Metodo che assegna la squadra al giocatore nel DB
     * @param giocatore parametro che ci serve per estrapolare il nickname del giocatore associato alla patrtita
     */
    public void cambiaSquadraInPartita(Giocatore giocatore){

        try {
            int res = 0;
            con = DriverManager.getConnection(url, "ROOT", "ROOT");
            PreparedStatement stmtQ = con.prepareStatement("SELECT max(idPartita) FROM Partita WHERE nickname = (?)");
            stmtQ.setString(1, giocatore.getGiocatore().getNickname());
            ResultSet result = stmtQ.executeQuery();
            while (result.next()) {
                res = result.getInt(1);
            }



            con = DriverManager.getConnection(url,"ROOT","ROOT");
            PreparedStatement stmt = con.prepareStatement("UPDATE PARTITA SET squadraGiocatore = (?) WHERE nickname = (?) AND idPartita = (?)");
            if (giocatore.isSquadraNera()){
                stmt.setInt(1,0);
            }else{
                stmt.setInt(1,1);
            }

            stmt.setString(2,giocatore.getGiocatore().getNickname());
            stmt.setInt(3,res);




            stmt.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try{
                if (con!=null)
                    con.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Metodo che setta la pedina a morta, aggiornando il valore contenuto nel campo viva a 0
     * @param pedinaClient Pedina di riferimento che deve essere aggiornata
     * @param giocatore parametro che ci serve per estrapolare il nickname del giocatore associato alla patrtita
     */
    public void mortaInContiene(PedinaClient pedinaClient, Giocatore giocatore){
        try {

            int res = 0;
            con = DriverManager.getConnection(url, "ROOT", "ROOT");
            PreparedStatement stmtQ = con.prepareStatement("SELECT max(idPartita) FROM Partita WHERE nickname = (?)");
            stmtQ.setString(1, giocatore.getGiocatore().getNickname());
            ResultSet result = stmtQ.executeQuery();
            while (result.next()) {
                res = result.getInt(1);
            }

            con = DriverManager.getConnection(url,"ROOT","ROOT");
            PreparedStatement stmt = con.prepareStatement("DELETE FROM  CONTIENE WHERE x = (?) AND y = (?) AND idPartita = (?)");
            stmt.setInt(1,pedinaClient.getxCorrente());
            stmt.setInt(2,pedinaClient.getyCorrente());
            stmt.setInt(3,res);
            stmt.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try{
                if (con!=null)
                    con.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Metodo che serve a capire se la partita a cui si fa riferimento è finita, tramite query andiamo a controllare il campo eFinita
     * della partita a cui si fa riferimento e se è -1 o 0 torniamo un valore che ci indica che è finita altrimenti se torna 1 torniamo un valore che ci indica che non è finita
     * @param giocatore parametro che ci serve per estrapolare il nickname del giocatore associato alla patrtita
     * @return
     */
    public boolean checkEFinita(Giocatore giocatore) {
        try {
            int res = -1000;
            con = DriverManager.getConnection(url, "ROOT", "ROOT");
            PreparedStatement stmt = con.prepareStatement("SELECT idPartita,finita FROM partita WHERE nickname = (?) ORDER BY idPartita DESC LIMIT 1");
            stmt.setString(1, giocatore.getGiocatore().getNickname());
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                res = result.getInt(2);
                if (res == 1)
                    return false;

                else if (res == -1)
                    return true;

                if(res == 0)
                {
                    return true;
                }
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try{
                if (con!=null)
                    con.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * Metodo che restituisce un ArrayList di pedine che fanno riferimento al giocatore loggato e che fanno riferimento all'ultima partita
     * del giocatore loggato
     * @param giocatore parametro che ci serve per estrapolare il nickname del giocatore associato alla patrtita
     * @return
     */
    public ArrayList<PedinaClient> getPedineGiocatoreContiene(Giocatore giocatore){
        ArrayList<PedinaClient> arrayList = new ArrayList<PedinaClient>();

        try {
            int res = 0;
            int squad = 0;
            con = DriverManager.getConnection(url, "ROOT", "ROOT");
            PreparedStatement stmtQ = con.prepareStatement("SELECT idPartita, squadraGiocatore FROM Partita WHERE nickname = (?) ORDER BY idPartita DESC LIMIT 1");
            stmtQ.setString(1, giocatore.getGiocatore().getNickname());
            ResultSet result = stmtQ.executeQuery();
            if (result.next()) {
                res = result.getInt(1);
                squad = result.getInt(2);

            }



            con = DriverManager.getConnection(url, "ROOT", "ROOT");
            PreparedStatement stmt = con.prepareStatement("SELECT x,y,dama,colore FROM CONTIENE WHERE idPartita = (?) AND colore = (?)");
            stmt.setInt(1,res);
            if (squad == 1){
                stmt.setInt(2,1);
            }
            else stmt.setInt(2,0);


            ResultSet resultQ = stmt.executeQuery();
            while (resultQ.next()){
                int x = resultQ.getInt(1);
                int y = resultQ.getInt(2);
                int dama = resultQ.getInt(3);
                int colore = resultQ.getInt(4);

                PedinaClient pedina = new PedinaClient();
                pedina.setxCorrente(x);
                pedina.setyCorrente(y);
                pedina.setDama(dama == 1);
                if (colore == 1){
                    pedina.setRossa(true);
                    pedina.setNera(false);
                }
                else {
                    pedina.setNera(true);
                    pedina.setRossa(false);
                }
                arrayList.add(pedina);
            }
            return arrayList;





        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try{
                if (con!=null)
                    con.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
        return arrayList;
    }

    /**
     * Metodo che restituisce un ArrayList di pedine che fanno riferimento alla CPU e che fanno riferimento all'ultima partita
     * del giocatore loggato
     * @param giocatore parametro che ci serve per estrapolare il nickname del giocatore associato alla patrtita
     * @return
     */
    public ArrayList<PedinaClient> getPedineCpuContiene(Giocatore giocatore){
        ArrayList<PedinaClient> arrayList = new ArrayList<PedinaClient>();

        try {
            int res = 0;
            int squad = 0;
            con = DriverManager.getConnection(url, "ROOT", "ROOT");
            PreparedStatement stmtQ = con.prepareStatement("SELECT idPartita, squadraGiocatore FROM Partita WHERE nickname = (?) ORDER BY idPartita DESC LIMIT 1 ");
            stmtQ.setString(1, giocatore.getGiocatore().getNickname());

            ResultSet result = stmtQ.executeQuery();
            if (result.next()) {
                res = result.getInt(1);
                squad = result.getInt(2);

            }



            con = DriverManager.getConnection(url, "ROOT", "ROOT");
            PreparedStatement stmt = con.prepareStatement("SELECT x,y,dama,colore FROM CONTIENE WHERE idPartita = (?) AND colore = (?)");
            stmt.setInt(1,res);

            if (squad == 1){
                stmt.setInt(2,0);
            }
            else stmt.setInt(2,1);


            ResultSet resultQ = stmt.executeQuery();
            while (resultQ.next()){
                int x = resultQ.getInt(1);
                int y = resultQ.getInt(2);
                int dama = resultQ.getInt(3);
                int colore = resultQ.getInt(4);

                PedinaClient pedina = new PedinaClient();
                pedina.setxCorrente(x);
                pedina.setyCorrente(y);
                pedina.setDama(dama == 1);
                if (colore == 1){
                    pedina.setRossa(true);
                    pedina.setNera(false);
                }
                else {
                    pedina.setNera(true);
                    pedina.setRossa(false);
                }
                arrayList.add(pedina);
            }
            return arrayList;





        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try{
                if (con!=null)
                    con.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
        return arrayList;
    }
}






