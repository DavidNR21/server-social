package com.cloud.Enterprise.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cloud.Enterprise.model.Comunidades;

@Repository
public interface ComunidadeRepository extends JpaRepository<Comunidades, UUID> {

	// 🔹 Busca comunidades ativas
    List<Comunidades> findByActiveTrue();

    // 🔹 Busca comunidades públicas
    List<Comunidades> findByRule(String rule);

    // 🔹 Busca comunidades pelo nome (ignorando maiúsculas e minúsculas)
    //List<Comunidades> findByNomeContainingIgnoreCase(String nome);
	
    // Busca comunidades ativas pelo nome (case insensitive)
    List<Comunidades> findByNomeContainingIgnoreCaseAndActiveTrue(String nome);
}
