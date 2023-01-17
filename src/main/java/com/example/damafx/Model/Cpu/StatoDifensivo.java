package com.example.damafx.Model.Cpu;

/**
 * classe che implementa l'interfaccia StatoCpu
 */
public class StatoDifensivo implements StatoCpu{
    private final IStrategia strategia = new StrategiaDifensiva();

    /**
     * Metodo riscritto dell'interfaccia implementata che serve ad impostare la strategia da attuare
     * @return
     */
    @Override
    public IStrategia impostaStrategia() {
        return strategia;

    }
}
