package com.montanha.model;

import lombok.Data;

@Data
public class Viagem {
    private String localDeDestino;
    private String dataPartida;
    private String dataRetorno;
    private String acompanhante;
    private String regiao;
}
