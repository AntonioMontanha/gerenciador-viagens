package com.montanha.gerenciador.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.montanha.gerenciador.dtos.ViagemDto;
import com.montanha.gerenciador.entities.Viagem;
import com.montanha.gerenciador.responses.Response;
import com.montanha.gerenciador.services.ViagemServices;

@RestController
@RequestMapping("/api/viagens")

public class GerenciadorViagemController {

	@Autowired
	private ViagemServices viagemService;

	@PostMapping(path = "/new")
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
	public ResponseEntity<List<Viagem>> listar() {
		List<Viagem> viagens = viagemService.listar();
		return ResponseEntity.status(HttpStatus.OK).body(viagens);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<Viagem> buscar(@PathVariable("id") Long id) {
		Viagem viagem = viagemService.buscar(id);
		return ResponseEntity.status(HttpStatus.OK).body(viagem);
	}
	/*
	 * @RequestMapping(method = RequestMethod.POST) public ResponseEntity<Void>
	 * salvar(@Valid @RequestBody Autor autor) { autor =
	 * autoresService.salvar(autor);
	 * 
	 * URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
	 * .path("/{id}").buildAndExpand(autor.getId()).toUri();
	 * 
	 * return ResponseEntity.created(uri).build(); }
	 * 
	 * @RequestMapping(method = RequestMethod.GET) public
	 * ResponseEntity<List<Autor>> listar() { List<Autor> autores =
	 * autoresService.listar(); return
	 * ResponseEntity.status(HttpStatus.OK).body(autores); }
	 * 
	 * @RequestMapping(value = "/{id}", method = RequestMethod.GET) public
	 * ResponseEntity<Autor> buscar(@PathVariable("id") Long id) { Autor autor =
	 * autoresService.buscar(id); return
	 * ResponseEntity.status(HttpStatus.OK).body(autor);
	 */
}
