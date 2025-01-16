package com.cloud.Enterprise.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.cloud.Enterprise.dto.UsuarioRequestDTO;
import com.cloud.Enterprise.dto.UsuarioResponseDTO;
import com.cloud.Enterprise.model.Usuario;


public class UsuarioMapper {

    // Converte de UsuarioRequestDTO para Usuario (entidade)
    public static Usuario toEntity(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(dto.getNomeCompleto());
        usuario.setSign(dto.getSign());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setDataNascimento(dto.getDataNascimento());
        usuario.setBio(dto.getBio());
        usuario.setFotoPerfil(dto.getFotoPerfil());
        usuario.setBanner(dto.getBanner());
        usuario.setLocal(dto.getLocal());
        usuario.setLinks(dto.getLinks());
        usuario.setRule(dto.getRule());
        usuario.setVerifed(dto.getVerifed());
        usuario.setActive(dto.getActive());
        return usuario;
    }

    // Converte de Usuario (entidade) para UsuarioResponseDTO
    public static UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
            usuario.getId(),
            usuario.getNomeCompleto(),
            usuario.getSign(),
            usuario.getEmail(),
            usuario.getDataNascimento(),
            usuario.getActive(),
            usuario.getBio(),
            usuario.getFotoPerfil(),
            usuario.getBanner(),
            usuario.getLocal(),
            usuario.getLinks(),
            usuario.getVerifed(),
            usuario.getRule()
        );
    }

    // Converte uma lista de Usuario (entidade) para uma lista de UsuarioResponseDTO
    public static List<UsuarioResponseDTO> toResponseDTOList(List<Usuario> usuarios) {
        return usuarios.stream().map(UsuarioMapper::toResponseDTO).collect(Collectors.toList());
    }
}

