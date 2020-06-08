package com.montanha.gerenciador.controller;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import javax.validation.Valid;

import com.montanha.gerenciador.dtos.ViagemDtoResponse;
import io.swagger.annotations.Api;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import com.montanha.gerenciador.dtos.ViagemDto;
import com.montanha.gerenciador.entities.Viagem;
import com.montanha.gerenciador.responses.Response;
import com.montanha.gerenciador.services.ViagemServices;

import io.swagger.annotations.ApiOperation;

@RestController
@Api("GerenciadorViagensController")
public class GerenciadorViagemController {

	@Autowired
	private ViagemServices viagemService;

	@ApiOperation(value = "Cadastra uma viagem")
	@PreAuthorize("hasAnyRole('ADMIN')")	
	@RequestMapping(value = "/v1/viagens", method = RequestMethod.POST, produces = "application/json" )
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResponseEntity<Response<Viagem>> cadastrar(@Valid @RequestBody ViagemDto viagemDto, @RequestHeader String Authorization, BindingResult result) {

		// Não devemos expor entidades na resposta.
		Response<Viagem> response = new Response<Viagem>();

		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		Viagem viagemSalva = this.viagemService.salvar(viagemDto);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(viagemSalva.getId())
				.toUri();

		response.setData(viagemSalva);

		return ResponseEntity.created(location).body(response);
	}

	@ApiOperation(value = "Retorna todas as viagens")
	@RequestMapping(value = "/v1/viagens", method = RequestMethod.GET, produces = "application/json")
	@PreAuthorize("hasAnyRole('USUARIO')")
	public ResponseEntity<Response<List<Viagem>>> listar(@RequestParam(value = "regiao", required = false) String regiao, @RequestHeader String Authorization) {
		List<Viagem> viagens = null;

		if (regiao == null) {
			viagens = viagemService.listar();
		} else {
			viagens = viagemService.buscarViagensPorRegiao(regiao);
		}

		Response<List<Viagem>> viagensResponse = new Response<>();
		viagensResponse.setData(viagens);
		return ResponseEntity.status(HttpStatus.OK).body(viagensResponse);
	}

	@ApiOperation(value = "Retorna uma viagem específica")
	@RequestMapping(value = "/v1/viagens/{id}", method = RequestMethod.GET, produces = "application/json")
	@PreAuthorize("hasAnyRole('USUARIO')")
	public ResponseEntity<Response<ViagemDtoResponse>> buscar(@PathVariable("id") Long id, @RequestHeader String Authorization) throws  IOException {
		Response<ViagemDtoResponse> response = new Response<ViagemDtoResponse>();
		ViagemDtoResponse viagemDtoResponse;
		try {
			viagemDtoResponse = viagemService.buscar(id);

		}

		catch (NotFoundException e) {
			response.setErrors(Collections.singletonList(e.getMessage()));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}

        catch (HttpClientErrorException hce) {
			response.setErrors(Collections.singletonList(hce.getStatusText()));
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
		}

		response.setData(viagemDtoResponse);
		
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
