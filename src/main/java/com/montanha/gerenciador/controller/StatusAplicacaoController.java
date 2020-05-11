package com.montanha.gerenciador.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/status")
@ApiOperation(value = "Verifica se a Aplicação está no ar")
@Api("StatusAplicacaoController")
public class StatusAplicacaoController {

	@GetMapping()
	public String verificaStatusAplicacao() {
		
		return "Aplicação está funcionando corretamente";
	}
	
	
}
