package com.montanha.gerenciador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class GerenciadorViagensMontanhaApplication {

	public static void main(String[] args) {
		SpringApplication.run(GerenciadorViagensMontanhaApplication.class, args);
	}

	/*
	 * @Bean public CommandLineRunner commandLineRunner() { return args -> {
	 * 
	 * List<String> acompanhantes1 = new ArrayList<String>();
	 * acompanhantes1.add("Sérgio"); acompanhantes1.add("Camila");
	 * acompanhantes1.add("Laura");
	 * 
	 * Viagem viagem1 = new Viagem(); viagem1.setDataPartida(LocalDate.of(2018, 12,
	 * 15)); viagem1.setDataPartida(LocalDate.now());
	 * viagem1.setLocalDeDestino("Itapema"); viagem1.setAcompanhante("Mauro");
	 * 
	 * this.viagemRepository.save(viagem1);
	 * 
	 * List<Viagem> viagens = viagemRepository.findAll();
	 * viagens.forEach(System.out::println);
	 * 
	 * Viagem viagemRetorno = viagemRepository.findByLocalDeDestino("Itapema");
	 * System.out.println("A Viagem é: " + viagemRetorno); }; }
	 */
}

/*
 * private Map<Integer, Viagem> viagens; viagens = new
 * HashMap<Integer,Viagem>();
 * 
 * List<String> acompanhantes1 = new ArrayList<String>();
 * acompanhantes1.add("Sérgio"); acompanhantes1.add("Camila");
 * acompanhantes1.add("Laura");
 * 
 * List<String> acompanhantes2 = new ArrayList<String>();
 * acompanhantes2.add("Roseli"); acompanhantes2.add("Henrique");
 * 
 * Viagem viagem1 = new Viagem(1,"Itapema",
 * LocalDate.now(),LocalDate.of(2018,12,15),acompanhantes1); Viagem viagem2 =
 * new
 * Viagem(2,"Caiobá",LocalDate.now(),LocalDate.of(2018,12,15),acompanhantes2);
 * 
 * viagens.put(1, viagem1); viagens.put(2, viagem2);
 */