package com.cloud.Enterprise.repository;


import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cloud.Enterprise.model.Usuario;

@Repository
public interface UserRepository extends JpaRepository<Usuario, UUID> {
	// Busca um usuário por e-mail e status ativo
    Optional<Usuario> findByEmailAndActiveTrue(String email);
    Optional<Usuario> findByIdAndActiveTrue(UUID id);
}
