package com.montanha.security.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.montanha.gerenciador.responses.Response;
import com.montanha.gerenciador.dtos.JwtAuthenticationDto;
import com.montanha.gerenciador.dtos.TokenDto;
import com.montanha.security.utils.JwtTokenUtil;

@RestController
@CrossOrigin(origins = "*")
public class AuthenticationController {

	private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
	private static final String TOKEN_HEADER = "Authorization";
	private static final String BEARER_PREFIX = "Bearer ";

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * Gera e retorna um novo token JWT.
	 * 
	 * @param authenticationDto
	 * @param result
	 * @return ResponseEntity<Response<TokenDto>>
	 * @throws AuthenticationException
	 */
	@PostMapping("/v1/auth")
	@ApiOperation(value = "Gera um Token de acesso")
	public ResponseEntity<Response<TokenDto>> gerarTokenJwt(@Valid @RequestBody JwtAuthenticationDto authenticationDto,
			BindingResult result) throws AuthenticationException {
		Response<TokenDto> response = new Response<TokenDto>();

		if (result.hasErrors()) {
			log.error("Erro: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		log.info("Gerando token para o email {}.", authenticationDto.getEmail());
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationDto.getEmail(), authenticationDto.getSenha()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationDto.getEmail());
		String token = jwtTokenUtil.obterToken(userDetails);
		response.setData(new TokenDto(token));

		return ResponseEntity.ok(response);
	}

	/**
	 * Gera um novo token com uma nova data de expiração.
	 * 
	 * @param request
	 * @return ResponseEntity<Response<TokenDto>>
	 */
//	@PostMapping(value = "/refresh")
//	public ResponseEntity<Response<TokenDto>> gerarRefreshTokenJwt(HttpServletRequest request) {
//		log.info("Gerando refresh token JWT.");
//		Response<TokenDto> response = new Response<TokenDto>();
//		Optional<String> token = Optional.ofNullable(request.getHeader(TOKEN_HEADER));
//
//		if (token.isPresent() && token.get().startsWith(BEARER_PREFIX)) {
//			token = Optional.of(token.get().substring(7));
//		}
//
//		if (!token.isPresent()) {
//			response.getErrors().add("Token não informado.");
//		} else if (!jwtTokenUtil.tokenValido(token.get())) {
//			response.getErrors().add("Token inválido ou expirado.");
//		}
//
//		if (!response.getErrors().isEmpty()) {
//			return ResponseEntity.badRequest().body(response);
//		}
//
//		String refreshedToken = jwtTokenUtil.refreshToken(token.get());
//		response.setData(new TokenDto(refreshedToken));
//
//		return ResponseEntity.ok(response);
//	}

}
