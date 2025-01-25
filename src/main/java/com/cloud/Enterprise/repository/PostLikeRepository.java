package com.cloud.Enterprise.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cloud.Enterprise.model.Post;
import com.cloud.Enterprise.model.PostLike;
import com.cloud.Enterprise.model.Usuario;


@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, UUID> {
	// Verifica se o usuário já deu like em um post
    Optional<PostLike> findByPostAndUsuario(Post post, Usuario usuario);
    
    // Conta o número de likes de um post
    @Query("SELECT COUNT(pl) FROM PostLike pl WHERE pl.post.id = :postId")
    long countLikesByPostId(@Param("postId") UUID postId);
}
