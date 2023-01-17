package com.example.damafx.Model.Pedine;

public interface PedinaInterfaccia {
     boolean muoviPedina(PedinaClient pedina, int xPrecedente, int yPrecedente, int nuovaX, int nuovaY);
     boolean muoviPedinaCpu(PedinaClient pedina, int xPrecedente, int yPrecedente, int nuovaX, int nuovaY);

     int puoMangiare(PedinaClient pedina);

     int puoMangiareCpu(PedinaClient pedina);
     int mangia(PedinaClient pedina, int xR, int yR);

     int mangiaCpu(PedinaClient pedina);
}
