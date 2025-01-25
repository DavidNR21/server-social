package com.cloud.Enterprise.mapper;

import java.util.List;
import java.util.stream.Collectors;


import com.cloud.Enterprise.dto.PostResponseDTO;
import com.cloud.Enterprise.dto.UserPostDTO;
import com.cloud.Enterprise.model.Post;

public class PostMapper {

	
	// Método para converter um único Post em PostResponseDTO
    public static PostResponseDTO toResponseDTO(Post post) {
        UserPostDTO usuarioResumo = new UserPostDTO(
                post.getUsuario().getId(),
                post.getUsuario().getSign(),
                post.getUsuario().getFotoPerfil()
        );

        return new PostResponseDTO(
                post.getId(),
                post.getContent(),
                post.getLikes(),
                post.getViews(),
                post.getMedia(),
                post.getRule(),
                post.getType(),
                post.getShare(),
                post.getSaves(),
                post.getNumberComentarios(),
                post.getCreatedAt(),
                post.getActive(),
                usuarioResumo
        );
    }

    // Método para converter uma lista de Post em uma lista de PostResponseDTO
    public static List<PostResponseDTO> toResponseDTOList(List<Post> posts) {
        return posts.stream()
                .map(PostMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
	
}
