package com.montanha.gerenciador.dtos;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

public class ViagemDto implements Serializable {

	private static final long serialVersionUID = -8105241933692707649L;



	@ApiModelProperty(value = "Local de destino da viagem")
	private String localDeDestino;

	@ApiModelProperty(value = "Data de partida da viagem (yyyy-MM-dd)")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date dataPartida;

	@ApiModelProperty(value = "Data de retorno da viagem (yyyy-MM-dd)")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date dataRetorno;

	@ApiModelProperty(value = "Nome do acompanhante da viagem")
	private String acompanhante;
	
	@ApiModelProperty(value = "Região de destino da viagem [Norte, Sul, Leste, Oeste]")
	private String regiao;


	public ViagemDto() {

	}

	@NotNull(message = "Local de Destino é uma informação obrigatória")
	@Size(min = 3, max = 40, message = "Local de Destino deve estar entre 3 e 40 caracteres")
	public String getLocalDeDestino() {
		return localDeDestino;
	}

	public void setLocalDeDestino(String localDeDestino) {
		this.localDeDestino = localDeDestino;
	}

	@NotNull(message = "Data da Partida é uma informação obrigatória")
	public Date getDataPartida() {
		return dataPartida;
	}

	public void setDataPartida(Date dataPartida) {
		this.dataPartida = dataPartida;
	}

	public Date getDataRetorno() {
		return dataRetorno;
	}

	public void setDataRetorno(Date dataRetorno) {
		this.dataRetorno = dataRetorno;
	}

	public String getAcompanhante() {
		return acompanhante;
	}

	public void setAcompanhante(String acompanhante) {
		this.acompanhante = acompanhante;
	}
	
	public String getRegiao() {
		return regiao;
	}

	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}

	@Override
	public String toString() {
		return "ViagemDto [id=" + ", localDeDestino=" + localDeDestino + ", dataPartida=" + dataPartida
				+ ", dataRetorno=" + dataRetorno + ", acompanhante=" + acompanhante + "]";
	}
}
