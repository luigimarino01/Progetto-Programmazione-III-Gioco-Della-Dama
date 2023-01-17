package com.example.damafx.Model.Giocatore;

/**
 * Classe che contiene solo dati che dovranno essere inseriti nel DB
 */
public class GiocatoreBean implements java.io.Serializable{
    private  String nome;
    private  String cognome;
    private  String nickname;
    private  String email;
    private int numeroVittorie;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getNumeroVittorie() {
        return numeroVittorie;
    }

    public void setNumeroVittorie(int  numeroVittorie) {
        this.numeroVittorie = numeroVittorie;
    }
}
