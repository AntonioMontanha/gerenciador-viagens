package com.montanha.gerenciador.controller;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import javax.validation.Valid;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.montanha.gerenciador.dtos.ViagemDtoResponse;
import com.montanha.gerenciador.repositories.ViagemRepository;
import com.montanha.gerenciador.utils.Conversor;
import io.swagger.annotations.Api;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import com.montanha.gerenciador.dtos.ViagemDto;
import com.montanha.gerenciador.entities.Viagem;
import com.montanha.gerenciador.responses.Response;
import com.montanha.gerenciador.services.ViagemServices;

import io.swagger.annotations.ApiOperation;

@RestController
@Api("GerenciadorViagensController")
public class GerenciadorViagemController {

	@Value("${previsaoDoTempoUri}")
	String previsaoDoTempoUri;
	@Autowired
	private ViagemServices viagemService;

	@Autowired
	private ViagemRepository viagemRepository;

	@Autowired
	private ObjectMapper mapper;

	@ApiOperation(value = "Cadastra uma viagem")
	@PreAuthorize("hasAnyRole('ADMIN')")	
	@RequestMapping(value = "/v1/viagens", method = RequestMethod.POST, produces = "application/json" )
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResponseEntity cadastrar(@Valid @RequestBody ViagemDto viagemDto, @RequestHeader String Authorization, BindingResult result) {

		this.viagemService.salvar(viagemDto);


		return new ResponseEntity(HttpStatus.OK);
	}

	@ApiOperation(value = "Retorna todas as viagens")
	@RequestMapping(value = "/v1/viagens", method = RequestMethod.GET, produces = "application/json")
	@PreAuthorize("hasAnyRole('USUARIO')")
	public ResponseEntity<Response<List<Viagem>>> listar( @RequestHeader String Authorization) {
		List<Viagem> viagens = null;

		viagens = viagemRepository.findAll();

		Response<List<Viagem>> viagensResponse = new Response<>();
		viagensResponse.setData(viagens);
		return ResponseEntity.status(HttpStatus.OK).body(viagensResponse);
	}

	@ApiOperation(value = "Retorna uma viagem específica")
	@RequestMapping(value = "/v1/viagens/{id}", method = RequestMethod.GET, produces = "application/json")
	@PreAuthorize("hasAnyRole('USUARIO')")
	public ResponseEntity<Response<ViagemDtoResponse>> buscar(@PathVariable("id") Long id, @RequestHeader String Authorization) throws  IOException {
		Response<ViagemDtoResponse> response = new Response<ViagemDtoResponse>();

		Viagem viagem = viagemRepository.findOne(id);

		ViagemDtoResponse viagemDtoResponse = Conversor.converterViagemToViagemDtoResponse(viagem);
		String regiao = viagem.getRegiao();

		if (regiao != null) {
			final String uri = previsaoDoTempoUri + "tempo-api/temperatura?regiao=" + regiao;
			RestTemplate restTemplate = new RestTemplate();

			String previsaoJson = "";
			previsaoJson = restTemplate.getForObject(uri, String.class);
			ObjectNode node = mapper.readValue(previsaoJson, ObjectNode.class);
			viagemDtoResponse.setTemperatura((node.get("data").get("temperatura")).floatValue());

			response.setData(viagemDtoResponse);
			}

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}



	@ApiOperation(value = "Apaga uma viagem específica")
	@RequestMapping(value = "/v1/viagens/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id, @RequestHeader String Authorization) {
		
		Viagem viagem = viagemService.buscarSemTratativa(id);
		viagemService.deletar(viagem);

	}
	
	@ApiOperation(value = "Atualiza uma viagem específica")
	@RequestMapping(value = "/v1/viagens/{id}", method = RequestMethod.PUT, produces = "application/json")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<Viagem>> alterar(@PathVariable("id") Long id,@Valid @RequestBody ViagemDto viagemDto, @RequestHeader String Authorization) {
		
		
		viagemService.alterar(viagemDto, id);
		
		Response<Viagem> response = new Response<>();
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
	}
	

}
