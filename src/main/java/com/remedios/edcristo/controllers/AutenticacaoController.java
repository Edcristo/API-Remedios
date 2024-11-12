package com.remedios.edcristo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.remedios.edcristo.infra.DadosTokenJWT;
import com.remedios.edcristo.infra.TokenService;
import com.remedios.edcristo.usuarios.DadosAutenticacao;
import com.remedios.edcristo.usuarios.Usuario;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private TokenService tokenService;

	@PostMapping
	public ResponseEntity<?> efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
		var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
		var autenticacao = manager.authenticate(token);
		//return ResponseEntity.ok().build();
		
		var tokenJWT = tokenService.gerarToken((Usuario) autenticacao.getPrincipal());
		return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
	
	
	}
}
