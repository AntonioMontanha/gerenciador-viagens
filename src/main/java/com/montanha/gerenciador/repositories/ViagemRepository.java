package com.montanha.gerenciador.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.montanha.gerenciador.entities.Viagem;

@Repository
public interface ViagemRepository extends JpaRepository<Viagem, Long> {

	Viagem findByLocalDeDestino(String localDeDestino);
	

}
