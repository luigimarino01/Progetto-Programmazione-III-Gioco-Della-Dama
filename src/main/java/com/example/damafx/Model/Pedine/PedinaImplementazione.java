package com.example.damafx.Model.Pedine;

import com.example.damafx.Model.Cpu.Cpu;
import com.example.damafx.Model.Damiera.Damiera;
import com.example.damafx.Model.Database.DamaDB;
import com.example.damafx.Model.Partita.Partita;

public class PedinaImplementazione implements PedinaInterfaccia {
    Damiera damiera = Damiera.getInstanza();
    Cpu cpu = Cpu.getIstanza();
    DamaDB damaDB = DamaDB.getIstanza();
    Partita partita = Partita.getIstanza();


    /**
     * Metodo che effettua il movimento della pedian in modo logico delle pedine del giocatore
     * @param pedina Pedina di riferimento che deve effettuare lo spostamento
     * @param xPrecedente X in cui si trovava la pedina
     * @param yPrecedente Y in cui si trovava la pedina
     * @param nuovaX X in cui si troverà la pedina
     * @param nuovaY Y in cui si troverà la pedina
     * @return
     */
    @Override
    public boolean muoviPedina(PedinaClient pedina, int xPrecedente, int yPrecedente, int nuovaX, int nuovaY)  {
        if (nuovaX > 7 || nuovaX < 0 || nuovaY > 7 || nuovaY < 0) {
            return false;
        }
        if (pedina.isDama()) {
            if (damiera.tavolaDaGioco[nuovaX][nuovaY].getPedinaSopra() == null && ((nuovaX == xPrecedente + 1 || nuovaX == xPrecedente - 1) && (nuovaY == yPrecedente + 1 || nuovaY == yPrecedente - 1))) {
                damiera.tavolaDaGioco[nuovaX][nuovaY].setPedinaSopra(pedina);
                damiera.tavolaDaGioco[xPrecedente][yPrecedente].setPedinaSopra(null);
                return true;
            }
        }
        else{
            if (damiera.tavolaDaGioco[nuovaX][nuovaY].getPedinaSopra() == null && (nuovaY == yPrecedente + 1 || nuovaY == yPrecedente - 1) && nuovaX == xPrecedente - 1) {
                damiera.tavolaDaGioco[nuovaX][nuovaY].setPedinaSopra(pedina);
                damiera.tavolaDaGioco[xPrecedente][yPrecedente].setPedinaSopra(null);
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo che effettua il movimento della pedian in modo logico delle pedine della cpu
     * @param pedina Pedina di riferimento che deve effettuare lo spostamento
     * @param xPrecedente X in cui si trovava la pedina
     * @param yPrecedente Y in cui si trovava la pedina
     * @param nuovaX X in cui si troverà la pedina
     * @param nuovaY Y in cui si troverà la pedina
     * @return
     */
    public boolean muoviPedinaCpu(PedinaClient pedina, int xPrecedente, int yPrecedente, int nuovaX, int nuovaY){
        if (nuovaX > 7 || nuovaX < 0 || nuovaY > 7 || nuovaY < 0) {
            return false;
        }
        if (pedina.isDama()) {
            if (damiera.tavolaDaGioco[nuovaX][nuovaY].getPedinaSopra() == null && ((nuovaX == xPrecedente + 1 || nuovaX == xPrecedente - 1) && (nuovaY == yPrecedente + 1 || nuovaY == yPrecedente - 1))) {
                damiera.tavolaDaGioco[nuovaX][nuovaY].setPedinaSopra(pedina);
                damiera.tavolaDaGioco[xPrecedente][yPrecedente].setPedinaSopra(null);
                return true;
            }
        }
        else{
            if (damiera.tavolaDaGioco[nuovaX][nuovaY].getPedinaSopra() == null && (nuovaY == yPrecedente + 1 || nuovaY == yPrecedente - 1) && nuovaX == xPrecedente + 1 ) {
                damiera.tavolaDaGioco[nuovaX][nuovaY].setPedinaSopra(pedina);
                damiera.tavolaDaGioco[xPrecedente][yPrecedente].setPedinaSopra(null);
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo che verifica se la pedina del giocatore è in grado di mangiare qualcuno
     * @param pedina
     * @return
     */
    public int puoMangiare(PedinaClient pedina) {
        int x = pedina.getxCorrente();
        int y = pedina.getyCorrente();
        if (pedina.isDama()) {
            if ((x <6 && y<6) && (damiera.tavolaDaGioco[x+1][y+1].getPedinaSopra()!=null && pedina.isNera() != damiera.tavolaDaGioco[x+1][y+1].getPedinaSopra().isNera()) && damiera.tavolaDaGioco[x+2][y+2].getPedinaSopra()==null) {
                return 1;//Basso a destra
            } else if ((x<6 && y>1) &&  (damiera.tavolaDaGioco[x+1][y-1].getPedinaSopra()!=null && pedina.isNera() != damiera.tavolaDaGioco[x+1][y-1].getPedinaSopra().isNera())  && damiera.tavolaDaGioco[x+2][y-2].getPedinaSopra()==null) {
                return 2;//Basso a sinistra
            } else if ((x>1 && y>1) &&  (damiera.tavolaDaGioco[x-1][y-1].getPedinaSopra()!=null && pedina.isNera() != damiera.tavolaDaGioco[x-1][y-1].getPedinaSopra().isNera()) && damiera.tavolaDaGioco[x-2][y-2].getPedinaSopra()==null) {
                return 3;//Alto a sinistra
            } else if ((x>1 && y<6) &&  (damiera.tavolaDaGioco[x-1][y+1].getPedinaSopra()!=null && pedina.isNera() != damiera.tavolaDaGioco[x-1][y+1].getPedinaSopra().isNera()) && damiera.tavolaDaGioco[x-2][y+2].getPedinaSopra()==null) {
                return 4;//Alto a destra
            }
        } else if (!pedina.isDama()) {
            if ((x>1 && y>1) && (damiera.tavolaDaGioco[x-1][y-1].getPedinaSopra()!=null && !damiera.tavolaDaGioco[x-1][y-1].getPedinaSopra().isDama() &&  (pedina.isNera() != damiera.tavolaDaGioco[x-1][y-1].getPedinaSopra().isNera())) && damiera.tavolaDaGioco[x-2][y-2].getPedinaSopra()==null) {

                return 3;//Alto a sinistra
            } else if ((x>1 && y<6) && (damiera.tavolaDaGioco[x-1][y+1].getPedinaSopra()!=null && !damiera.tavolaDaGioco[x-1][y+1].getPedinaSopra().isDama()) && pedina.isNera() != damiera.tavolaDaGioco[x-1][y+1].getPedinaSopra().isNera() && damiera.tavolaDaGioco[x-2][y+2].getPedinaSopra()==null) {

                return 4;//Alto a destra
            }
        }
        return 0;//NON PUO MANGIARE//
    }
    /**
     * Metodo che verifica se la pedina della CPU è in grado di mangiare qualcuno
     * @param pedina
     * @return
     */
    public int puoMangiareCpu(PedinaClient pedina) {
        int x = pedina.getxCorrente();
        int y = pedina.getyCorrente();
        if (pedina.isDama()) {
            if ((x <6 && y<6) && (damiera.tavolaDaGioco[x+1][y+1].getPedinaSopra()!=null && pedina.isNera() != damiera.tavolaDaGioco[x+1][y+1].getPedinaSopra().isNera()) && damiera.tavolaDaGioco[x+2][y+2].getPedinaSopra()==null) {
                return 1;//Basso a destra
            } else if ((x<6 && y>1) &&  (damiera.tavolaDaGioco[x+1][y-1].getPedinaSopra()!=null && pedina.isNera() != damiera.tavolaDaGioco[x+1][y-1].getPedinaSopra().isNera())  && damiera.tavolaDaGioco[x+2][y-2].getPedinaSopra()==null) {
                return 2;//Basso a sinistra
            } else if ((x>1 && y>1) &&  (damiera.tavolaDaGioco[x-1][y-1].getPedinaSopra()!=null && pedina.isNera() != damiera.tavolaDaGioco[x-1][y-1].getPedinaSopra().isNera()) && damiera.tavolaDaGioco[x-2][y-2].getPedinaSopra()==null) {

                return 3;//Alto a sinistra
            } else if ((x>1 && y<6) &&  (damiera.tavolaDaGioco[x-1][y+1].getPedinaSopra()!=null && pedina.isNera() != damiera.tavolaDaGioco[x-1][y+1].getPedinaSopra().isNera()) && damiera.tavolaDaGioco[x-2][y+2].getPedinaSopra()==null) {
                return 4;//Alto a destra
            }
        } else if (!pedina.isDama()) {
            if ((x <6 && y<6) && (damiera.tavolaDaGioco[x+1][y+1].getPedinaSopra()!=null &&  !damiera.tavolaDaGioco[x+1][y+1].getPedinaSopra().isDama()) && pedina.isNera()  != damiera.tavolaDaGioco[x+1][y+1].getPedinaSopra().isNera() && damiera.tavolaDaGioco[x+2][y+2].getPedinaSopra()==null) {
                return 1;//Basso a destra
            } else if ((x<6 && y>1) &&  (damiera.tavolaDaGioco[x+1][y-1].getPedinaSopra()!=null && !damiera.tavolaDaGioco[x+1][y-1].getPedinaSopra().isDama()) && pedina.isNera() != damiera.tavolaDaGioco[x+1][y-1].getPedinaSopra().isNera()  && damiera.tavolaDaGioco[x+2][y-2].getPedinaSopra()==null) {
                return 2;//Basso a sinistra
            }
        }
        return 0;//NON PUO MANGIARE//
    }

    /**
     * Metodo che effettua l'operazione del mangia cancellando la pedina che deve essere mangiata dalla damiera e spostando la pedina "assassina"
     * nella posizione corretta
     * alla fine del mangia si restituisce il valore che corrisponde al movimento fatto per mangiare
     * @param pedina Pedina di riferimento che effettua il movimento
     * @return
     */
    public int mangiaCpu (PedinaClient pedina){
        int movimento;
        movimento = puoMangiareCpu(pedina);

        if (movimento == 0) return 0;

        else if (movimento == 1) {
            damiera.tavolaDaGioco[pedina.getxCorrente()][pedina.getyCorrente()].setPedinaSopra(null);
            damaDB.mortaInContiene(damiera.tavolaDaGioco[pedina.getxCorrente()+1][pedina.getyCorrente()+1].getPedinaSopra(), partita.getGiocatoreGiocante());
            damiera.tavolaDaGioco[pedina.getxCorrente()+1][pedina.getyCorrente()+1].setPedinaSopra(null);
            partita.getGiocatoreGiocante().rimuoviPedina(pedina.getxCorrente()+1,pedina.getyCorrente()+1);
            damiera.tavolaDaGioco[pedina.getxCorrente()+2][pedina.getyCorrente()+2].setPedinaSopra(pedina);
            damaDB.spostaInContiene(pedina, pedina.getxCorrente()+2, pedina.getyCorrente()+2, partita.getGiocatoreGiocante());

            //BASSO DX
            return 1;
        } else if (movimento == 2) {
            damiera.tavolaDaGioco[pedina.getxCorrente()][pedina.getyCorrente()].setPedinaSopra(null);
            damaDB.mortaInContiene(damiera.tavolaDaGioco[pedina.getxCorrente()+1][pedina.getyCorrente()-1].getPedinaSopra(), partita.getGiocatoreGiocante());
            damiera.tavolaDaGioco[pedina.getxCorrente()+1][pedina.getyCorrente()-1].setPedinaSopra(null);
            partita.getGiocatoreGiocante().rimuoviPedina(pedina.getxCorrente()+1,pedina.getyCorrente()-1);
            damiera.tavolaDaGioco[pedina.getxCorrente()+2][pedina.getyCorrente()-2].setPedinaSopra(pedina);
            damaDB.spostaInContiene(pedina, pedina.getxCorrente()+2, pedina.getyCorrente()-2, partita.getGiocatoreGiocante());

            //BASSO SX
            return 2;
        } else if (movimento == 3) {
            damiera.tavolaDaGioco[pedina.getxCorrente()][pedina.getyCorrente()].setPedinaSopra(null);
            damaDB.mortaInContiene(damiera.tavolaDaGioco[pedina.getxCorrente()-1][pedina.getyCorrente()-1].getPedinaSopra(), partita.getGiocatoreGiocante());
            damiera.tavolaDaGioco[pedina.getxCorrente()-1][pedina.getyCorrente()-1].setPedinaSopra(null);
            partita.getGiocatoreGiocante().rimuoviPedina(pedina.getxCorrente()-1,pedina.getyCorrente()-1);
            damiera.tavolaDaGioco[pedina.getxCorrente()-2][pedina.getyCorrente()-2].setPedinaSopra(pedina);
            damaDB.spostaInContiene(pedina, pedina.getxCorrente()-2, pedina.getyCorrente()-2, partita.getGiocatoreGiocante());

            //ALTO SX
            return 3;
        } else if (movimento == 4) {
            damiera.tavolaDaGioco[pedina.getxCorrente()][pedina.getyCorrente()].setPedinaSopra(null);
            damaDB.mortaInContiene(damiera.tavolaDaGioco[pedina.getxCorrente()-1][pedina.getyCorrente()+1].getPedinaSopra(), partita.getGiocatoreGiocante());
            damiera.tavolaDaGioco[pedina.getxCorrente()-1][pedina.getyCorrente()+1].setPedinaSopra(null);
            partita.getGiocatoreGiocante().rimuoviPedina(pedina.getxCorrente()-1,pedina.getyCorrente()+1);
            damiera.tavolaDaGioco[pedina.getxCorrente()-2][pedina.getyCorrente()+2].setPedinaSopra(pedina);
            damaDB.spostaInContiene(pedina, pedina.getxCorrente()-2, pedina.getyCorrente()+2, partita.getGiocatoreGiocante());

            //ALTO DX
            return 4;
        }
        return 0;
    }
    /**
     * Metodo che effettua l'operazione del mangia cancellando la pedina che deve essere mangiata dalla damiera e spostando la pedina "assassina"
     * nella posizione corretta
     * alla fine del mangia si restituisce il valore che corrisponde al movimento fatto per mangiare
     * @param pedina Pedina di riferimento che effettua il movimento
     * @return
     */
    public int mangia (PedinaClient pedina, int xR, int yR){
        int movimento;
        movimento = puoMangiare(pedina);

        if (movimento == 0) return 0;

        else if (movimento == 1 && xR == pedina.getxCorrente()+2 && yR == pedina.getyCorrente()+2 ) {
            damiera.tavolaDaGioco[pedina.getxCorrente()][pedina.getyCorrente()].setPedinaSopra(null);
            damaDB.mortaInContiene(damiera.tavolaDaGioco[pedina.getxCorrente()+1][pedina.getyCorrente()+1].getPedinaSopra(), partita.getGiocatoreGiocante());
            damiera.tavolaDaGioco[pedina.getxCorrente()+1][pedina.getyCorrente()+1].setPedinaSopra(null);
            cpu.rimuoviPedina(pedina.getxCorrente()+1,pedina.getyCorrente()+1);
            damiera.tavolaDaGioco[pedina.getxCorrente()+2][pedina.getyCorrente()+2].setPedinaSopra(pedina);
            damaDB.spostaInContiene(pedina, pedina.getxCorrente()+2, pedina.getyCorrente()+2, partita.getGiocatoreGiocante());

            return 1;
        } else if (movimento == 2 && xR == pedina.getxCorrente()+2 && yR == pedina.getyCorrente()-2) {
            damiera.tavolaDaGioco[pedina.getxCorrente()][pedina.getyCorrente()].setPedinaSopra(null);
            damaDB.mortaInContiene(damiera.tavolaDaGioco[pedina.getxCorrente()+1][pedina.getyCorrente()-1].getPedinaSopra(), partita.getGiocatoreGiocante());
            damiera.tavolaDaGioco[pedina.getxCorrente()+1][pedina.getyCorrente()-1].setPedinaSopra(null);
            cpu.rimuoviPedina(pedina.getxCorrente()+1,pedina.getyCorrente()-1);
            damiera.tavolaDaGioco[pedina.getxCorrente()+2][pedina.getyCorrente()-2].setPedinaSopra(pedina);
            damaDB.spostaInContiene(pedina, pedina.getxCorrente()+2, pedina.getyCorrente()-2, partita.getGiocatoreGiocante());

            return 2;
        } else if (movimento == 3 && xR == pedina.getxCorrente()-2 && yR == pedina.getyCorrente()-2) {
            damiera.tavolaDaGioco[pedina.getxCorrente()][pedina.getyCorrente()].setPedinaSopra(null);
            damaDB.mortaInContiene(damiera.tavolaDaGioco[pedina.getxCorrente()-1][pedina.getyCorrente()-1].getPedinaSopra(), partita.getGiocatoreGiocante());
            damiera.tavolaDaGioco[pedina.getxCorrente()-1][pedina.getyCorrente()-1].setPedinaSopra(null);
            cpu.rimuoviPedina(pedina.getxCorrente()-1,pedina.getyCorrente()-1);
            damiera.tavolaDaGioco[pedina.getxCorrente()-2][pedina.getyCorrente()-2].setPedinaSopra(pedina);
            damaDB.spostaInContiene(pedina, pedina.getxCorrente()-2, pedina.getyCorrente()-2, partita.getGiocatoreGiocante());

            return 3;
        } else if (movimento == 4 && xR == pedina.getxCorrente()-2 && yR == pedina.getyCorrente()+2) {
            damiera.tavolaDaGioco[pedina.getxCorrente()][pedina.getyCorrente()].setPedinaSopra(null);
            damaDB.mortaInContiene(damiera.tavolaDaGioco[pedina.getxCorrente()-1][pedina.getyCorrente()+1].getPedinaSopra(), partita.getGiocatoreGiocante());
            damiera.tavolaDaGioco[pedina.getxCorrente()-1][pedina.getyCorrente()+1].setPedinaSopra(null);
            cpu.rimuoviPedina(pedina.getxCorrente()-1,pedina.getyCorrente()+1);
            damiera.tavolaDaGioco[pedina.getxCorrente()-2][pedina.getyCorrente()+2].setPedinaSopra(pedina);
            damaDB.spostaInContiene(pedina, pedina.getxCorrente()-2, pedina.getyCorrente()+2, partita.getGiocatoreGiocante());

            return 4;
        }
        return 0;
    }
}



