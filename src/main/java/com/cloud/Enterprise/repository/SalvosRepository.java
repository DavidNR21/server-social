package com.cloud.Enterprise.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cloud.Enterprise.model.Salvos;


@Repository
public interface SalvosRepository extends JpaRepository<Salvos, UUID> {
	
	// Verifica se um usuário já salvou o post
    @Query("SELECT s FROM Salvos s WHERE s.posts.id = :postId AND s.usuario.id = :userId")
    Optional<Salvos> findByPostAndUsuario(@Param("postId") UUID postId, @Param("userId") UUID userId);

    // Conta o número de vezes que um post foi salvo
    @Query("SELECT COUNT(s) FROM Salvos s WHERE s.posts.id = :postId")
    long countSalvosByPostId(@Param("postId") UUID postId);
}
