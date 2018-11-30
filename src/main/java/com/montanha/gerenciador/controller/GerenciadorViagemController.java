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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.montanha.gerenciador.dtos.ViagemDto;
import com.montanha.gerenciador.entities.Viagem;
import com.montanha.gerenciador.responses.Response;
import com.montanha.gerenciador.services.ViagemServices;

@RestController
@RequestMapping("/api/viagem")

public class GerenciadorViagemController {

	@Autowired
	private ViagemServices viagemService;

	
	@PostMapping()
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<Viagem>> cadastrar(@Valid @RequestBody ViagemDto viagemDto, BindingResult result) {
		Response<Viagem> response = new Response<Viagem>();

		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		Viagem viagemSalva = this.viagemService.salvar(viagemDto);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(viagemDto.getId())
				.toUri();

		response.setData(viagemSalva);
		return ResponseEntity.created(location).body(response);
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('USUARIO')")
	public ResponseEntity<List<Viagem>> listar() {
		List<Viagem> viagens = viagemService.listar();
		return ResponseEntity.status(HttpStatus.OK).body(viagens);
	}
	
	

	@GetMapping(path = "/{id}")
	@PreAuthorize("hasAnyRole('USUARIO')")
	public ResponseEntity<Response<Viagem>> buscar(@PathVariable("id") Long id) throws JsonParseException, JsonMappingException, IOException {
		
		Viagem viagem = viagemService.buscar(id);
		Response<Viagem> response = new Response<Viagem>();
		response.setData(viagem);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	
	@GetMapping(path = "buscarPorLocal/{localDeDestino}")
	@PreAuthorize("hasAnyRole('USUARIO')")
	public ResponseEntity<Response<Viagem>> buscarPorLocalDestino(@PathVariable("localDeDestino") String localDeDestino) {
		
		Viagem viagem = viagemService.buscarPorLocalDeDestino(localDeDestino);
		Response<Viagem> response = new Response<Viagem>();
		response.setData(viagem);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@DeleteMapping(path = "/delete/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<List<Viagem>> delete(@PathVariable("id") Long id) {
		
		Viagem viagem = viagemService.buscarSemTratativa(id);
		List<Viagem> viagens = viagemService.deletar(viagem);

		return ResponseEntity.status(HttpStatus.OK).body(viagens);
	}
	
	@PutMapping(path = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<Viagem>> alterar(@PathVariable("id") Long id,@Valid @RequestBody ViagemDto viagemDto) throws JsonParseException, JsonMappingException, IOException {
		
		
		Viagem viagem = this.viagemService.alterar(viagemDto, id);
		
		Response<Viagem> response = new Response<Viagem>();
		response.setData(viagem);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	

}
