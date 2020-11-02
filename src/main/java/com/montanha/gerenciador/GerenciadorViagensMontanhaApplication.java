package com.montanha.gerenciador;

import com.montanha.gerenciador.entities.Viagem;
import com.montanha.gerenciador.repositories.ViagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.montanha.gerenciador.entities.Usuario;
import com.montanha.gerenciador.enums.PerfilEnum;
import com.montanha.gerenciador.repositories.UsuarioRepository;
import com.montanha.utils.SenhaUtils;

import java.util.Date;

@SpringBootApplication
@Configuration
@ComponentScan("com.montanha")
public class GerenciadorViagensMontanhaApplication {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ViagemRepository viagemRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(GerenciadorViagensMontanhaApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			
			Usuario usuario = new Usuario();
			usuario.setEmail("usuario@email.com");
			usuario.setPerfil(PerfilEnum.ROLE_USUARIO);
			usuario.setSenha(SenhaUtils.gerarBCrypt("123456"));
			this.usuarioRepository.save(usuario);
			
			Usuario admin = new Usuario();
			admin.setEmail("admin@email.com");
			admin.setPerfil(PerfilEnum.ROLE_ADMIN);
			admin.setSenha(SenhaUtils.gerarBCrypt("654321"));
			this.usuarioRepository.save(admin);

			Viagem viagemNorteSucesso = new Viagem();
			viagemNorteSucesso.setAcompanhante("Isabelle");
			viagemNorteSucesso.setRegiao("Norte");
			viagemNorteSucesso.setLocalDeDestino("Manaus");
			viagemNorteSucesso.setDataPartida(new Date());
			this.viagemRepository.save(viagemNorteSucesso);

			Viagem viagemSudesteIndisponivel = new Viagem();
			viagemSudesteIndisponivel.setAcompanhante("Priscila");
			viagemSudesteIndisponivel.setRegiao("Sudeste");
			viagemSudesteIndisponivel.setLocalDeDestino("Rio de Janeiro");
			viagemSudesteIndisponivel.setDataPartida(new Date());
			this.viagemRepository.save(viagemSudesteIndisponivel);
		};
	}

}
