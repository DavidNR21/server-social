package com.cloud.Enterprise.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cloud.Enterprise.model.Post;


@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

	@Query("SELECT p FROM Post p JOIN FETCH p.usuario u WHERE u.id = :usuarioId")
	List<Post> findPostsByUsuarioId(@Param("usuarioId") UUID usuarioId);
}
