package com.example.damafx.Model.Cpu;

/**
 * Implementazione di un interfaccia che viene utilizzata per implementare il movimento della pedina
 */
public interface IStrategia {
    /**
     * Metodo all'interno dell'interfaccia che va implementato in altre classi
     * @throws InterruptedException
     */
    void muoviPedina() throws InterruptedException;
}
