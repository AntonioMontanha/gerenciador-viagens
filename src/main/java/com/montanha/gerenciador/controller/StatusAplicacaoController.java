package com.montanha.gerenciador.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/status")
public class StatusAplicacaoController {

	@GetMapping()
	public String verificaStatusAplicacao() {
		
		return "Aplicação está funcionando corretamente";
	}
	
	
}
