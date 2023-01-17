package com.example.damafx.Model.Cpu;

/**
 * classe che implementa l'interfaccia StatoCpu
 */
public class StatoOffensivo implements StatoCpu{
    private final IStrategia strategia = new StrategiaOffensiva();
    /**
     * Metodo riscritto dell'interfaccia implementata che serve ad impostare la strategia da attuare
     * @return
     */
    @Override
    public IStrategia impostaStrategia() {
        return strategia;
    }
}
