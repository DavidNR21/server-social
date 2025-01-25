package com.cloud.Enterprise.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cloud.Enterprise.model.Comentarios;

@Repository
public interface ComentariosRepository extends JpaRepository<Comentarios, UUID> {

	@Query("SELECT c FROM Comentarios c WHERE c.post.id = :postId AND c.comentarioPai IS NULL")
	List<Comentarios> findComentariosPrincipaisByPostId(@Param("postId") UUID postId);

    // Buscar respostas de um comentário pai
    @Query("SELECT c FROM Comentarios c WHERE c.comentarioPai.id = :comentarioPaiId ORDER BY c.createdAt ASC")
    List<Comentarios> findRespostasByComentarioPaiId(@Param("comentarioPaiId") UUID comentarioPaiId);

	
}
