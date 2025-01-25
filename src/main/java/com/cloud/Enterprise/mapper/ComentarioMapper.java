package com.cloud.Enterprise.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.cloud.Enterprise.dto.ComentarioResponseDTO;
import com.cloud.Enterprise.dto.UserPostDTO;
import com.cloud.Enterprise.model.Comentarios;
import com.cloud.Enterprise.model.Usuario;

public class ComentarioMapper {

	
	public static ComentarioResponseDTO toResponseDTO(Comentarios comentario) {
        // Mapeia o usuário para um resumo
        Usuario usuario = comentario.getUsuario();
        UserPostDTO usuarioResumo = new UserPostDTO(usuario.getId(), usuario.getSign(), usuario.getFotoPerfil());

        // Mapeia as respostas recursivamente
        List<ComentarioResponseDTO> respostas = comentario.getRespostas().stream().map(ComentarioMapper::toResponseDTO).collect(Collectors.toList());

        // Retorna o DTO
        return new ComentarioResponseDTO(
            comentario.getId(),
            comentario.getConteudo(),
            comentario.getLikes(),
            comentario.getImg(),
            comentario.getCreatedAt(),
            usuarioResumo,
            respostas
        );
    }
	
	
}
