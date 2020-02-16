package com.montanha.gerenciador.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.montanha.gerenciador.dtos.ViagemDto;
import com.montanha.gerenciador.entities.Viagem;
import com.montanha.gerenciador.responses.Response;
import com.montanha.gerenciador.services.ViagemServices;

import io.swagger.annotations.ApiOperation;

@RestController
public class GerenciadorViagemController {

	@Autowired
	private ViagemServices viagemService;

	@ApiOperation(value = "Cadastra uma viagem")
	@PreAuthorize("hasAnyRole('ADMIN')")	
	@RequestMapping(value = "/api/viagem", method = RequestMethod.POST, produces = "application/json" )
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Response<Viagem>> cadastrar(@Valid @RequestBody ViagemDto viagemRequest, BindingResult result) {
		// Não devemos expor entidades na resposta.
		Response<Viagem> response = new Response<Viagem>();

		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		Viagem viagemSalva = this.viagemService.salvar(viagemRequest);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(viagemRequest.getId())
				.toUri();

		response.setData(viagemSalva);
		return ResponseEntity.created(location).body(response);
	}

	@ApiOperation(value = "Retorna todas as viagens")
	@RequestMapping(value = "/api/viagem", method = RequestMethod.GET, produces = "application/json")
	@PreAuthorize("hasAnyRole('USUARIO')")
	public ResponseEntity<List<Viagem>> listar(@RequestParam(value = "localDeDestino", required = false) String localDeDestino) {
		List<Viagem> viagens = null;

		if (localDeDestino == null) {
			viagens = viagemService.listar();
		} else {
			viagens = viagemService.buscarViagensPorLocalDeDestino(localDeDestino);
		}

		return ResponseEntity.status(HttpStatus.OK).body(viagens);
	}
	
	@ApiOperation(value = "Retorna uma viagem específica")
	@RequestMapping(value = "/api/viagem/{id}", method = RequestMethod.GET, produces = "application/json")
	@PreAuthorize("hasAnyRole('USUARIO')")
	public ResponseEntity<Response<Viagem>> buscar(@PathVariable("id") Long id) throws JsonParseException, JsonMappingException, IOException {
		
		Viagem viagem = viagemService.buscar(id);
		Response<Viagem> response = new Response<Viagem>();
		response.setData(viagem);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	
//	@GetMapping(path = "/{localDeDestino}")
//	@PreAuthorize("hasAnyRole('USUARIO')")
//	public ResponseEntity<Response<Viagem>> buscarPorLocalDestino(@PathVariable("localDeDestino") String localDeDestino) {
//		
//		Viagem viagem = viagemService.buscarPorLocalDeDestino(localDeDestino);
//		Response<Viagem> response = new Response<Viagem>();
//		response.setData(viagem);
//		return ResponseEntity.status(HttpStatus.OK).body(response);
//	}
	
	@ApiOperation(value = "Apaga uma viagem específica")
	@RequestMapping(value = "/api/viagem/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<List<Viagem>> delete(@PathVariable("id") Long id) {
		
		Viagem viagem = viagemService.buscarSemTratativa(id);
		List<Viagem> viagens = viagemService.deletar(viagem);

		return ResponseEntity.status(HttpStatus.OK).body(viagens);
	}
	
	@ApiOperation(value = "Atualiza uma viagem específica")
	@RequestMapping(value = "/api/viagem/{id}", method = RequestMethod.PUT, produces = "application/json")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<Viagem>> alterar(@PathVariable("id") Long id,@Valid @RequestBody ViagemDto viagemDto) throws JsonParseException, JsonMappingException, IOException {
		
		
		Viagem viagem = this.viagemService.alterar(viagemDto, id);
		
		Response<Viagem> response = new Response<Viagem>();
		response.setData(viagem);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	

}
