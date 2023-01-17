package com.example.damafx.Model.Cpu;

import com.example.damafx.Model.Damiera.Damiera;
import com.example.damafx.Model.Database.DamaDB;
import com.example.damafx.Model.Partita.Partita;
import com.example.damafx.Model.Pedine.PedinaClient;

import java.util.ArrayList;
import java.util.Random;

public class StrategiaOffensiva implements IStrategia {
    DamaDB damaDB = DamaDB.getIstanza();
    Cpu cpu = Cpu.getIstanza();
    Partita partita = Partita.getIstanza();
    Damiera damiera = Damiera.getInstanza();
    Random randomNumber = new Random();
    int interoRandom;
    int movimento;
    ArrayList<PedinaClient> pedinaClients = new ArrayList<PedinaClient>();

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
        } else {
            return false;
        }
    }

    /**
     * Metodo che ci permette di capire se la pedina che passiamo come parametro di input ha una mossa valida e aggressiva da fare.
     * Per aggressiva intendiamo quando la pedina della cpu fa la mossa se ha buona probabilità di mangiare alla prossima mossa dell'utente
     * Controlliamo se il movimento è possibile e se le caselle successive a dove ci muoviamo sono vuote e se nelle successive ancora c'è una pedina avversaria
     * se tutto ciò è verificato allora la pedina effettua il movimento
     * @param pedinaClient Pedina sulla quale viene effettuato il controllo se si può muovere in modo aggressivo
     * @return
     */
    protected int puoMuoversiAggressivo(PedinaClient pedinaClient) {
        int xCorr = pedinaClient.getxCorrente();
        int yCorr = pedinaClient.getyCorrente();
        if (damiera.tavolaDaGioco[xCorr][yCorr] == null) {
            return 0;
        }

        if (pedinaClient.isDama()) {
            //BASSO A SINISTRA
            if (xCorr <= 6 && yCorr > 0) {
                if (damiera.tavolaDaGioco[xCorr + 1][yCorr - 1].getPedinaSopra() == null) {

                    if (yCorr > 2 && xCorr < 5 && yCorr <= 7) {
                        if (damiera.tavolaDaGioco[xCorr + 2][yCorr].getPedinaSopra() == null && damiera.tavolaDaGioco[xCorr + 2][yCorr - 2].getPedinaSopra() == null) {
                            if (damiera.tavolaDaGioco[xCorr + 3][yCorr - 3].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 3][yCorr - 3].getPedinaSopra().isNera() != pedinaClient.isNera() || damiera.tavolaDaGioco[xCorr + 3][yCorr - 1].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 3][yCorr - 1].getPedinaSopra().isNera() != pedinaClient.isNera()) {
                                return 1;
                            }
                        }
                    } else if (yCorr > 1 && yCorr < 7 && xCorr < 5) {
                        if (damiera.tavolaDaGioco[xCorr + 2][yCorr].getPedinaSopra() == null && damiera.tavolaDaGioco[xCorr + 2][yCorr - 2].getPedinaSopra() == null) {
                            if (damiera.tavolaDaGioco[xCorr + 3][yCorr + 1].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 3][yCorr + 1].getPedinaSopra().isNera() != pedinaClient.isNera() || damiera.tavolaDaGioco[xCorr + 3][yCorr - 1].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 3][yCorr - 1].getPedinaSopra().isNera() != pedinaClient.isNera()) {
                                return 1;
                            }
                        }
                    } else if (xCorr < 5 && yCorr == 1) {
                        if (damiera.tavolaDaGioco[xCorr + 2][yCorr].getPedinaSopra() == null) {
                            if (damiera.tavolaDaGioco[xCorr + 3][yCorr - 1].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 3][yCorr - 1].getPedinaSopra().isNera() != pedinaClient.isNera()) {
                                return 1;
                            } else if (damiera.tavolaDaGioco[xCorr + 3][yCorr + 1].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 3][yCorr + 1].getPedinaSopra().isNera() != pedinaClient.isNera()) {
                                return 1;
                            }
                        }
                    } else if (xCorr == 6 && yCorr == 7) {
                        if (damiera.tavolaDaGioco[xCorr + 1][yCorr - 2].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 1][yCorr - 2].getPedinaSopra().isNera() != pedinaClient.isNera())
                            return 1;
                    } else if (xCorr == 6 && yCorr > 2) {
                        if (damiera.tavolaDaGioco[xCorr + 1][yCorr - 2].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 1][yCorr - 2].getPedinaSopra().isNera() != pedinaClient.isNera())
                            return 1;
                        if (damiera.tavolaDaGioco[xCorr + 1][yCorr + 2].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 1][yCorr + 2].getPedinaSopra().isNera() != pedinaClient.isNera())
                            return 1;
                    } else if (xCorr == 6 && yCorr == 1) {
                        if (damiera.tavolaDaGioco[xCorr + 1][yCorr + 2].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 1][yCorr + 2].getPedinaSopra().isNera() != pedinaClient.isNera())
                            return 1;
                    }


                }
            }


            //BASSO A DESTRA
            else if (xCorr <= 6 && yCorr < 7) {
                if (damiera.tavolaDaGioco[xCorr + 1][yCorr + 1].getPedinaSopra() == null) {

                    if (yCorr >= 0 && xCorr < 5 && yCorr < 5) {
                        if (damiera.tavolaDaGioco[xCorr + 2][yCorr].getPedinaSopra() == null && damiera.tavolaDaGioco[xCorr + 2][yCorr + 2].getPedinaSopra() == null) {
                            if (damiera.tavolaDaGioco[xCorr + 3][yCorr + 3].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 3][yCorr + 3].getPedinaSopra().isNera() != pedinaClient.isNera() || damiera.tavolaDaGioco[xCorr + 3][yCorr + 1].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 3][yCorr + 1].getPedinaSopra().isNera() != pedinaClient.isNera()) {
                                return 2;
                            }
                        }
                    } else if (yCorr >= 1 && yCorr <= 5 && xCorr < 5) {
                        if (damiera.tavolaDaGioco[xCorr + 2][yCorr].getPedinaSopra() == null && damiera.tavolaDaGioco[xCorr + 2][yCorr + 2].getPedinaSopra() == null) {
                            if (damiera.tavolaDaGioco[xCorr + 3][yCorr - 1].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 3][yCorr - 1].getPedinaSopra().isNera() != pedinaClient.isNera() || damiera.tavolaDaGioco[xCorr + 3][yCorr + 1].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 3][yCorr + 1].getPedinaSopra().isNera() != pedinaClient.isNera()) {
                                return 2;
                            }
                        }
                    } else if (xCorr < 5 && yCorr == 6) {
                        if (damiera.tavolaDaGioco[xCorr + 2][yCorr].getPedinaSopra() == null) {
                            if (damiera.tavolaDaGioco[xCorr + 3][yCorr - 1].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 3][yCorr - 1].getPedinaSopra().isNera() != pedinaClient.isNera() || damiera.tavolaDaGioco[xCorr + 3][yCorr + 1].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 3][yCorr + 1].getPedinaSopra().isNera() != pedinaClient.isNera()) {
                                return 2;
                            }
                        }
                    } else if (xCorr == 6 && yCorr == 0) {
                        if (damiera.tavolaDaGioco[xCorr + 1][yCorr + 2].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 1][yCorr + 2].getPedinaSopra().isNera() != pedinaClient.isNera())
                            return 2;
                    } else if (xCorr == 6 && yCorr < 6) {
                        if (damiera.tavolaDaGioco[xCorr + 1][yCorr - 2].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 1][yCorr - 2].getPedinaSopra().isNera() != pedinaClient.isNera())
                            return 2;
                        if (damiera.tavolaDaGioco[xCorr + 1][yCorr + 2].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 1][yCorr + 2].getPedinaSopra().isNera() != pedinaClient.isNera())
                            return 2;
                    }

                    if (xCorr == 6 && yCorr == 6) {
                        if (damiera.tavolaDaGioco[xCorr + 1][yCorr - 2].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 1][yCorr + 2].getPedinaSopra().isNera() != pedinaClient.isNera())
                            return 2;
                    }


                }
            }

            //ALTO A SINISTRA
            else if (xCorr >= 1 && yCorr > 0) {
                if (damiera.tavolaDaGioco[xCorr - 1][yCorr - 1].getPedinaSopra() == null) {
                    if (yCorr > 2 && xCorr > 2 && yCorr <= 7) {
                        if (damiera.tavolaDaGioco[xCorr - 2][yCorr].getPedinaSopra() == null && damiera.tavolaDaGioco[xCorr - 2][yCorr - 2].getPedinaSopra() == null) {
                            if (damiera.tavolaDaGioco[xCorr - 3][yCorr - 3].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr - 3][yCorr - 3].getPedinaSopra().isNera() != pedinaClient.isNera() || damiera.tavolaDaGioco[xCorr - 3][yCorr - 1].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr - 3][yCorr - 1].getPedinaSopra().isNera() != pedinaClient.isNera()) {
                                return 3;
                            }
                        }
                    } else if (yCorr > 1 && yCorr < 7 && xCorr > 2) {
                        if (damiera.tavolaDaGioco[xCorr - 2][yCorr].getPedinaSopra() == null && damiera.tavolaDaGioco[xCorr - 2][yCorr - 2].getPedinaSopra() == null) {
                            if (damiera.tavolaDaGioco[xCorr - 3][yCorr + 1].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr - 3][yCorr + 1].getPedinaSopra().isNera() != pedinaClient.isNera() || damiera.tavolaDaGioco[xCorr - 3][yCorr - 1].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr - 3][yCorr - 1].getPedinaSopra().isNera() != pedinaClient.isNera()) {
                                return 3;
                            }
                        }
                    } else if (xCorr > 2 && yCorr == 1) {
                        if (damiera.tavolaDaGioco[xCorr - 2][yCorr].getPedinaSopra() == null) {
                            if (damiera.tavolaDaGioco[xCorr - 3][yCorr - 1].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr - 3][yCorr - 1].getPedinaSopra().isNera() != pedinaClient.isNera()) {
                                return 3;
                            } else if (damiera.tavolaDaGioco[xCorr - 3][yCorr + 1].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr - 3][yCorr + 1].getPedinaSopra().isNera() != pedinaClient.isNera()) {
                                return 3;
                            }
                        }
                    } else if (xCorr == 1 && yCorr == 7) {
                        if (damiera.tavolaDaGioco[xCorr - 1][yCorr - 2].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr - 1][yCorr - 2].getPedinaSopra().isNera() != pedinaClient.isNera())
                            return 3;
                    } else if (xCorr == 1 && yCorr > 2) {
                        if (damiera.tavolaDaGioco[xCorr - 1][yCorr - 2].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr - 1][yCorr - 2].getPedinaSopra().isNera() != pedinaClient.isNera())
                            return 3;
                        if (damiera.tavolaDaGioco[xCorr - 1][yCorr + 2].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr - 1][yCorr + 2].getPedinaSopra().isNera() != pedinaClient.isNera())
                            return 3;
                    } else if (xCorr == 1 && yCorr == 1) {
                        if (damiera.tavolaDaGioco[xCorr - 1][yCorr + 2].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr - 1][yCorr + 2].getPedinaSopra().isNera() != pedinaClient.isNera())
                            return 3;
                    }
                }
            }

            //ALTO A DESTRA
            else if ((xCorr >= 1 && yCorr < 7)) {
                if (damiera.tavolaDaGioco[xCorr - 1][yCorr + 1].getPedinaSopra() == null) {

                    if (yCorr >= 0 && xCorr > 2 && yCorr < 5) {
                        if (damiera.tavolaDaGioco[xCorr - 2][yCorr].getPedinaSopra() == null && damiera.tavolaDaGioco[xCorr - 2][yCorr + 2].getPedinaSopra() == null) {
                            if (damiera.tavolaDaGioco[xCorr - 3][yCorr + 3].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr - 3][yCorr + 3].getPedinaSopra().isNera() != pedinaClient.isNera() || damiera.tavolaDaGioco[xCorr - 3][yCorr + 1].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr - 3][yCorr + 1].getPedinaSopra().isNera() != pedinaClient.isNera()) {
                                return 4;
                            }
                        }
                    } else if (yCorr >= 1 && yCorr <= 5 && xCorr > 2) {
                        if (damiera.tavolaDaGioco[xCorr - 2][yCorr].getPedinaSopra() == null && damiera.tavolaDaGioco[xCorr - 2][yCorr + 2].getPedinaSopra() == null) {
                            if (damiera.tavolaDaGioco[xCorr - 3][yCorr - 1].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr - 3][yCorr - 1].getPedinaSopra().isNera() != pedinaClient.isNera() || damiera.tavolaDaGioco[xCorr - 3][yCorr + 1].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr - 3][yCorr + 1].getPedinaSopra().isNera() != pedinaClient.isNera()) {
                                return 4;
                            }
                        }
                    } else if (xCorr > 2 && yCorr == 6) {
                        if (damiera.tavolaDaGioco[xCorr - 2][yCorr].getPedinaSopra() == null) {
                            if (damiera.tavolaDaGioco[xCorr - 3][yCorr - 1].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr - 3][yCorr - 1].getPedinaSopra().isNera() != pedinaClient.isNera() || damiera.tavolaDaGioco[xCorr - 3][yCorr + 1].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr - 3][yCorr + 1].getPedinaSopra().isNera() != pedinaClient.isNera()) {
                                return 4;
                            }
                        }
                    } else if (xCorr == 2 && yCorr == 0) {
                        if (damiera.tavolaDaGioco[xCorr - 1][yCorr + 2].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr - 1][yCorr + 2].getPedinaSopra().isNera() != pedinaClient.isNera())
                            return 4;
                    } else if (xCorr == 2 && yCorr < 6) {
                        if (damiera.tavolaDaGioco[xCorr - 1][yCorr - 2].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr - 1][yCorr - 2].getPedinaSopra().isNera() != pedinaClient.isNera())
                            return 4;
                        if (damiera.tavolaDaGioco[xCorr - 1][yCorr + 2].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr - 1][yCorr + 2].getPedinaSopra().isNera() != pedinaClient.isNera())
                            return 4;
                    }

                    if (xCorr == 2 && yCorr == 6) {
                        if (damiera.tavolaDaGioco[xCorr - 1][yCorr - 2].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr - 1][yCorr + 2].getPedinaSopra().isNera() != pedinaClient.isNera())
                            return 4;
                    }
                }

            }
            } else {
                //BASSO A SINISTRA
                if (xCorr <= 6 && yCorr > 0) {
                    if (damiera.tavolaDaGioco[xCorr + 1][yCorr - 1].getPedinaSopra() == null) {

                        if (yCorr > 2 && xCorr < 5 && yCorr <= 7) {
                            if (damiera.tavolaDaGioco[xCorr + 2][yCorr].getPedinaSopra() == null && damiera.tavolaDaGioco[xCorr + 2][yCorr - 2].getPedinaSopra() == null) {
                                if (damiera.tavolaDaGioco[xCorr + 3][yCorr - 3].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 3][yCorr - 3].getPedinaSopra().isNera() != pedinaClient.isNera() && !damiera.tavolaDaGioco[xCorr + 3][yCorr - 3].getPedinaSopra().isDama() || damiera.tavolaDaGioco[xCorr + 3][yCorr - 1].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 3][yCorr - 1].getPedinaSopra().isNera() != pedinaClient.isNera() && !damiera.tavolaDaGioco[xCorr + 3][yCorr - 1].getPedinaSopra().isDama()) {
                                    return 1;
                                }
                            }
                        } else if (yCorr > 1 && yCorr < 7 && xCorr < 5) {
                            if (damiera.tavolaDaGioco[xCorr + 2][yCorr].getPedinaSopra() == null && damiera.tavolaDaGioco[xCorr + 2][yCorr - 2].getPedinaSopra() == null) {
                                if (damiera.tavolaDaGioco[xCorr + 3][yCorr + 1].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 3][yCorr + 1].getPedinaSopra().isNera() != pedinaClient.isNera() && !damiera.tavolaDaGioco[xCorr + 3][yCorr + 1].getPedinaSopra().isDama() || damiera.tavolaDaGioco[xCorr + 3][yCorr - 1].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 3][yCorr - 1].getPedinaSopra().isNera() != pedinaClient.isNera() && !damiera.tavolaDaGioco[xCorr + 3][yCorr - 1].getPedinaSopra().isDama()) {
                                    return 1;
                                }
                            }
                        } else if (xCorr < 5 && yCorr == 1) {
                            if (damiera.tavolaDaGioco[xCorr + 2][yCorr].getPedinaSopra() == null) {
                                if (damiera.tavolaDaGioco[xCorr + 3][yCorr - 1].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 3][yCorr - 1].getPedinaSopra().isNera() != pedinaClient.isNera() && !damiera.tavolaDaGioco[xCorr + 3][yCorr - 1].getPedinaSopra().isDama()) {
                                    return 1;
                                } else if (damiera.tavolaDaGioco[xCorr + 3][yCorr + 1].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 3][yCorr + 1].getPedinaSopra().isNera() != pedinaClient.isNera() && !damiera.tavolaDaGioco[xCorr + 3][yCorr + 1].getPedinaSopra().isDama()) {
                                    return 1;
                                }
                            }
                        } else if (xCorr == 6 && yCorr == 7) {
                            if (damiera.tavolaDaGioco[xCorr + 1][yCorr - 2].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 1][yCorr - 2].getPedinaSopra().isNera() != pedinaClient.isNera())
                                return 1;
                        } else if (xCorr == 6 && yCorr > 2) {
                            if (damiera.tavolaDaGioco[xCorr + 1][yCorr - 2].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 1][yCorr - 2].getPedinaSopra().isNera() != pedinaClient.isNera())
                                return 1;
                            if (damiera.tavolaDaGioco[xCorr + 1][yCorr + 2].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 1][yCorr + 2].getPedinaSopra().isNera() != pedinaClient.isNera())
                                return 1;
                        } else if (xCorr == 6 && yCorr == 1) {
                            if (damiera.tavolaDaGioco[xCorr + 1][yCorr + 2].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 1][yCorr + 2].getPedinaSopra().isNera() != pedinaClient.isNera())
                                return 1;
                        }
                    }
                }
                //BASSO A DESTRA
                else if (xCorr <= 6 && yCorr < 7) {
                    if (damiera.tavolaDaGioco[xCorr + 1][yCorr + 1].getPedinaSopra() == null) {

                        if (yCorr >= 0 && xCorr < 5 && yCorr < 5) {
                            if (damiera.tavolaDaGioco[xCorr + 2][yCorr].getPedinaSopra() == null && damiera.tavolaDaGioco[xCorr + 2][yCorr + 2].getPedinaSopra() == null) {
                                if (damiera.tavolaDaGioco[xCorr + 3][yCorr + 3].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 3][yCorr + 3].getPedinaSopra().isNera() != pedinaClient.isNera() && !damiera.tavolaDaGioco[xCorr + 3][yCorr + 3].getPedinaSopra().isDama() || damiera.tavolaDaGioco[xCorr + 3][yCorr + 1].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 3][yCorr + 1].getPedinaSopra().isNera() != pedinaClient.isNera() && !damiera.tavolaDaGioco[xCorr + 3][yCorr + 1].getPedinaSopra().isDama()) {
                                    return 2;
                                }
                            }
                        } else if (yCorr >= 1 && yCorr <= 5 && xCorr < 5) {
                            if (damiera.tavolaDaGioco[xCorr + 2][yCorr].getPedinaSopra() == null && damiera.tavolaDaGioco[xCorr + 2][yCorr + 2].getPedinaSopra() == null) {
                                if (damiera.tavolaDaGioco[xCorr + 3][yCorr - 1].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 3][yCorr - 1].getPedinaSopra().isNera() != pedinaClient.isNera() && !damiera.tavolaDaGioco[xCorr + 3][yCorr - 1].getPedinaSopra().isDama() || damiera.tavolaDaGioco[xCorr + 3][yCorr + 1].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 3][yCorr + 1].getPedinaSopra().isNera() != pedinaClient.isNera() && !damiera.tavolaDaGioco[xCorr + 3][yCorr + 1].getPedinaSopra().isDama()) {
                                    return 2;
                                }
                            }
                        } else if (xCorr < 5 && yCorr == 6) {
                            if (damiera.tavolaDaGioco[xCorr + 2][yCorr].getPedinaSopra() == null) {
                                if (damiera.tavolaDaGioco[xCorr + 3][yCorr - 1].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 3][yCorr - 1].getPedinaSopra().isNera() != pedinaClient.isNera() && !damiera.tavolaDaGioco[xCorr + 3][yCorr - 1].getPedinaSopra().isDama() || damiera.tavolaDaGioco[xCorr + 3][yCorr + 1].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 3][yCorr + 1].getPedinaSopra().isNera() != pedinaClient.isNera() && !damiera.tavolaDaGioco[xCorr + 3][yCorr + 1].getPedinaSopra().isDama()) {
                                    return 2;
                                }
                            }
                        } else if (xCorr == 6 && yCorr == 0) {
                            if (damiera.tavolaDaGioco[xCorr + 1][yCorr + 2].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 1][yCorr + 2].getPedinaSopra().isNera() != pedinaClient.isNera())
                                return 2;
                        } else if (xCorr == 6 && yCorr < 6) {
                            if (damiera.tavolaDaGioco[xCorr + 1][yCorr - 2].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 1][yCorr - 2].getPedinaSopra().isNera() != pedinaClient.isNera())
                                return 2;
                            if (damiera.tavolaDaGioco[xCorr + 1][yCorr + 2].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 1][yCorr + 2].getPedinaSopra().isNera() != pedinaClient.isNera())
                                return 2;
                        }

                        if (xCorr == 6 && yCorr == 6) {
                            if (damiera.tavolaDaGioco[xCorr + 1][yCorr - 2].getPedinaSopra() != null && damiera.tavolaDaGioco[xCorr + 1][yCorr + 2].getPedinaSopra().isNera() != pedinaClient.isNera())
                                return 2;
                        }
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
            if (xCorr<7 && yCorr<7  && damiera.tavolaDaGioco[xCorr + 1][yCorr + 1].getPedinaSopra() == null) {
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

    /**Metodo che fa muovere, se è possibile, la pedina passata come parametro di input verso il centro
     * @param pedinaClient pedina su cui si effettua il controllo se può fare il movimento verso il centro
     * @return
     */
    protected boolean muovitiVersoIlCentro(PedinaClient pedinaClient){
        if (pedinaClient.getyCorrente() < 4 && pedinaClient.getyCorrente() >0 && pedinaClient.getxCorrente() > 0 && pedinaClient.getxCorrente() < 7){
            if (pedinaClient.isDama()){
                if (damiera.tavolaDaGioco[pedinaClient.getxCorrente()+1][pedinaClient.getyCorrente()+1].getPedinaSopra() == null) {
                    pedinaClient.muoviPedinaCpu(pedinaClient.getxCorrente() + 1, pedinaClient.getyCorrente() + 1);
                    return true;
                }
                else if(damiera.tavolaDaGioco[pedinaClient.getxCorrente()-1][pedinaClient.getyCorrente()+1].getPedinaSopra() == null) {
                    pedinaClient.muoviPedinaCpu(pedinaClient.getxCorrente() - 1, pedinaClient.getyCorrente() + 1);
                    return true;
                }

            }
            else{
                if(damiera.tavolaDaGioco[pedinaClient.getxCorrente()+1][pedinaClient.getyCorrente()+1].getPedinaSopra() == null) {
                    pedinaClient.muoviPedinaCpu(pedinaClient.getxCorrente() + 1, pedinaClient.getyCorrente() + 1);
                    return true;
                }
            }
        } else  if (pedinaClient.getyCorrente() > 3 && pedinaClient.getyCorrente() < 7 && pedinaClient.getxCorrente() > 0 && pedinaClient.getxCorrente() < 7){
            if (pedinaClient.isDama()){
                if (damiera.tavolaDaGioco[pedinaClient.getxCorrente()+1][pedinaClient.getyCorrente()-1].getPedinaSopra() == null) {
                    pedinaClient.muoviPedinaCpu(pedinaClient.getxCorrente() + 1, pedinaClient.getyCorrente() - 1);
                    return true;
                }
                else if(damiera.tavolaDaGioco[pedinaClient.getxCorrente()-1][pedinaClient.getyCorrente()+1].getPedinaSopra() == null) {
                    pedinaClient.muoviPedinaCpu(pedinaClient.getxCorrente() - 1, pedinaClient.getyCorrente() + 1);
                    return true;
                }

            }
            else{
                if(damiera.tavolaDaGioco[pedinaClient.getxCorrente()+1][pedinaClient.getyCorrente()-1].getPedinaSopra() == null) {
                    pedinaClient.muoviPedinaCpu(pedinaClient.getxCorrente() + 1, pedinaClient.getyCorrente() - 1);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Metodo che gestisce tutto il movimento della pedina della cpu
     * 1. cattura una pedina se ha la possibilità di catturare
     * 2a. nel 70% dei casi muove la pedina con più probabilità di catturare (la pedina che garantisce di mangiare alla prossima mossa dell’avversario)
     * 2b. nel 30% dei casi muove la pedina casualmente
     * 3. muove la pedina mantenendosi al centro della scacchiera
     * Questo metodo chiama i metodi descritti prima in un determinato modo:
     * Inizialmente si verifica se si può mangiare
     * se non si può mangiare si verifica se si può fare un movimento in maniera aggressiva tramite la generazione di un numero random, facendo in modo che ci sia il 70% di probavilità che si faccia la mossa aggressiva
     * mentre nel 30% dei casi il movimento da effettuare è quello in modo casuale
     * se però il movimento in modo sicuro non può essere effettuato allora viene richiamata la funzione che permette di muovere la pedina verso il centro
     * per motivi di sicurezza abbiamo dovuto inserire il movimento casuale dopo aver provato ad effettuare un movimento sicuro e un movimento verso il centro e non esserci riusciti
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
                movimento = puoMuoversiAggressivo(pedinaClient);
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
            }
            for (PedinaClient pedinaClient : pedinaClients) {
                if (muovitiVersoIlCentro(pedinaClient)){
                    partita.setPlayerTurnTrue();
                    damaDB.setTurnoGiocatore(partita);
                    return;
                }
            }

        }
        else if (interoRandom>=7){
            for (PedinaClient pedinaClient : pedinaClients) {
                movimento = muoviCasualmente(pedinaClient);
                if (movimento!=0){


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
