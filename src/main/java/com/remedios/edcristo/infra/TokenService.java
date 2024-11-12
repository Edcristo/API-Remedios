package com.remedios.edcristo.infra;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.remedios.edcristo.usuarios.Usuario;

@Service
public class TokenService {

	@Value("${api.seurity.token.secret}")
	private String secret;

	public String gerarToken(Usuario usuario) {
		try {
			var algorithm = Algorithm.HMAC256(secret);
			return JWT.create().withIssuer("Remedios_api").withSubject(usuario.getLogin())
					// .withClaim("id",usuario.getId())
					.withExpiresAt(dataExpiracao()).sign(algorithm);
		} catch (JWTCreationException exception) {
			throw new RuntimeException("Erro ao gerar token", exception);
		}
	}

	public String getSubject(String TokenJWT) {

		try {
			var algorithm = Algorithm.HMAC256(secret);

			return JWT.require(algorithm).withIssuer("Remedios_api").build().verify(TokenJWT).getSubject();

		} catch (JWTVerificationException exception) {
			throw new RuntimeException("Token inválido ou ixpirado");
		}
	}

	private Instant dataExpiracao() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}
