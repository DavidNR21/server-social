package com.cloud.Enterprise.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cloud.Enterprise.model.LikesComentarios;

@Repository
public interface LikesComentariosRepository extends JpaRepository<LikesComentarios, UUID> {

	@Query("SELECT lc FROM LikesComentarios lc WHERE lc.comentario.id = :comentarioId AND lc.usuario.id = :userId")
    Optional<LikesComentarios> findByComentarioIdAndUserId(@Param("comentarioId") UUID comentarioId, @Param("userId") UUID userId);
	
}
