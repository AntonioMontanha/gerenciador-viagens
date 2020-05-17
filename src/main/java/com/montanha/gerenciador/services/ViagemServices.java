package com.montanha.gerenciador.services;

import java.io.IOException;
import java.util.List;

import com.montanha.gerenciador.dtos.ViagemDtoResponse;
import com.montanha.gerenciador.utils.Conversor;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.montanha.gerenciador.dtos.ViagemDto;
import com.montanha.gerenciador.entities.Viagem;
import com.montanha.gerenciador.repositories.ViagemRepository;
import com.montanha.gerenciador.services.exceptions.ViagemServiceException;

import static java.lang.String.format;

@Service
public class ViagemServices {

	@Value("${previsaoDoTempoUri}")
	String previsaoDoTempoUri;

	@Autowired
	private ViagemRepository viagemRepository;

	@Autowired
	private ObjectMapper mapper;

	public List<Viagem> listar() {
		return viagemRepository.findAll();
	}

	public Viagem salvar(ViagemDto viagemDto) {

		Viagem viagem = new Viagem();

		viagem.setLocalDeDestino(viagemDto.getLocalDeDestino());
		viagem.setDataPartida(viagemDto.getDataPartida());
		viagem.setDataRetorno(viagemDto.getDataRetorno());
		viagem.setAcompanhante(viagemDto.getAcompanhante());
		viagem.setRegiao(viagemDto.getRegiao());
		return viagemRepository.save(viagem);
	}

	public ViagemDtoResponse buscar(Long id) throws IOException, NotFoundException {
		Viagem viagem = viagemRepository.findOne(id);

		if (viagem == null) {
			throw new NotFoundException(format("Viagem com id: [%s] não encontrada", id));

		}

		ViagemDtoResponse viagemDtoResponse = Conversor.converterViagemToViagemDtoResponse(viagem);
		String regiao = viagem.getRegiao();

		if (regiao != null) {
			final String uri = previsaoDoTempoUri + "tempo-api/temperatura?regiao=" + regiao;
			RestTemplate restTemplate = new RestTemplate();

			String previsaoJson = "";

			try {
				previsaoJson = restTemplate.getForObject(uri, String.class);
			} catch (HttpClientErrorException hcee) {
				throw new HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "A API do Tempo não está online");
			}

			ObjectNode node = mapper.readValue(previsaoJson, ObjectNode.class);
			viagemDtoResponse.setTemperatura((node.get("data").get("temperatura")).floatValue());

			System.out.println(previsaoDoTempoUri);

		}

		return viagemDtoResponse;
	}

	public Viagem buscarPorLocalDeDestino(String localDeDestino) {
		Viagem viagem = viagemRepository.findByLocalDeDestino(localDeDestino);

		if (viagem == null) {
			throw new ViagemServiceException("Não existe esta viagem cadastrada");
		}

		return viagem;
	}

	// Erro 500 porém com mensagem tratada.
	public List<Viagem> buscarViagensPorRegiao(String regiao) {
		List<Viagem> viagens = viagemRepository.findAllByRegiao(regiao);

		if (viagens.isEmpty()) {
			throw new ViagemServiceException("Não existem viagens cadastradas para esta Região");
		}
		return viagens;
	}

	public List<Viagem> deletar(Viagem viagem) {

		viagemRepository.delete(viagem);

		return viagemRepository.findAll();

	}

	public Viagem buscarSemTratativa(Long id) {
		Viagem viagem = viagemRepository.findOne(id);

		return viagem;
	}

	public Viagem alterar(ViagemDto viagemDto, Long id) {

		Viagem viagemExistente = viagemRepository.findOne(id);

		// iremos ter um nullPointerException aqui.
		viagemExistente.setLocalDeDestino(viagemDto.getLocalDeDestino());
		viagemExistente.setDataPartida(viagemDto.getDataPartida());
		viagemExistente.setDataRetorno(viagemDto.getDataRetorno());
		viagemExistente.setAcompanhante(viagemDto.getAcompanhante());
		viagemExistente.setRegiao(viagemDto.getRegiao());
		return viagemRepository.save(viagemExistente);
	}

}
