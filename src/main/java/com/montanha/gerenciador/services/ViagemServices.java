package com.montanha.gerenciador.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.montanha.gerenciador.dtos.ViagemDto;
import com.montanha.gerenciador.entities.Viagem;
import com.montanha.gerenciador.repositories.ViagemRepository;
import com.montanha.gerenciador.services.exceptions.ViagemServiceException;

@Service
public class ViagemServices {

	@Autowired
	private ViagemRepository viagemRepository;

	public List<Viagem> listar() {
		return viagemRepository.findAll();
	}

	public Viagem salvar(ViagemDto viagemDto) {

		Viagem viagem = new Viagem();

		viagem.setLocalDeDestino(viagemDto.getLocalDeDestino());
		viagem.setDataPartida(viagemDto.getDataPartida());
		viagem.setDataRetorno(viagemDto.getDataRetorno());
		viagem.setAcompanhante(viagemDto.getAcompanhante());
		return viagemRepository.save(viagem);
	}

	public Viagem buscar(Long id) {
		Viagem viagem = viagemRepository.findOne(id);

		if (viagem == null) {
			throw new ViagemServiceException("Não existe esta viagem cadastrada");
		}
		return viagem;
	}
}
