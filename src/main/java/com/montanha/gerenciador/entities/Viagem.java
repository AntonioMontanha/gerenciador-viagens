package com.montanha.gerenciador.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Component
@Table(name = "viagem")
public class Viagem implements Serializable {

	private static final long serialVersionUID = -6888542263201514002L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(value = "Id da viagem")
	private Long id;

	@Column(name = "local_destino", nullable = false)
	@ApiModelProperty(value = "Local de destino da viagem")
	private String localDeDestino;

	@JsonSerialize(using = DateSerializer.class)
	@Column(name = "data_partida", nullable = false)
	@ApiModelProperty(value = "Data de partida da viagem (yyyy-MM-dd)")
	private Date dataPartida;

	@JsonSerialize(using = DateSerializer.class)
	@ApiModelProperty(value = "Data de retorno da viagem (yyyy-MM-dd)")
	@Column(name = "data_retorno", nullable = true)
	private Date dataRetorno;

	@Column(name = "acompanhante", nullable = true)
	@ApiModelProperty(value = "Nome do acompanhante da viagem")
	private String acompanhante;
	
	@Column(name = "regiao", nullable = true)
	@ApiModelProperty(value = "Região de destino da viagem [Norte, Sul, Leste, Oeste]")
	private String regiao;

	public Viagem() {

	}

	public Viagem(Long id, String localDeDestino, Date dataPartida, Date dataRetorno, String acompanhante) {
		this.id = id;
		this.localDeDestino = localDeDestino;
		this.dataPartida = dataPartida;
		this.dataRetorno = dataRetorno;
		this.acompanhante = acompanhante;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	public Date getDataPartida() {
		return dataPartida;
	}

	public void setDataPartida(Date dataPartida) {
		this.dataPartida = dataPartida;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLocalDeDestino() {
		return localDeDestino;
	}

	public void setLocalDeDestino(String localDeDestino) {
		this.localDeDestino = localDeDestino;
	}
	
	public String getRegiao() {
		return regiao;
	}

	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}

	@Override
	public String toString() {
		return "Viagem [id=" + id + ", localDeDestino=" + localDeDestino + ", dataPartida=" + dataPartida
				+ ", dataRetorno=" + dataRetorno + ", acompanhante=" + acompanhante + "]";
	}

}