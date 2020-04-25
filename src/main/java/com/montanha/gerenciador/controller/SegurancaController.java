//package com.montanha.gerenciador.controller;
//
//import io.swagger.annotations.Api;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/login")
//@Api("SegurancaController")
//public class SegurancaController {
//
//	@GetMapping(value = "/{nome}")
//	@PreAuthorize("hasAnyRole('ADMIN')")
//	public String exemplo(@PathVariable("nome") String nome) {
//		return "Seja bem vindo " + nome;
//	}
//
//}
