package com.montanha.factory;

import com.montanha.model.Viagem;

public class ViagemDataFactory {
    public Viagem criaUmaViagemValida() {
        Viagem viagem = new Viagem();
        viagem.setLocalDeDestino("Osasco");
        viagem.setDataPartida("2020-07-27");
        viagem.setDataRetorno("2020-10-27");
        viagem.setAcompanhante("Julio");
        viagem.setRegiao("Sul");
        return viagem;
    }
}
