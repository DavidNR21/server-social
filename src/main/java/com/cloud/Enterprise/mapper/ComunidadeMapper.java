package com.cloud.Enterprise.mapper;

import com.cloud.Enterprise.dto.ComunidadeRequestDTO;
import com.cloud.Enterprise.model.Comunidades;



public class ComunidadeMapper {

	
	// Converte de UsuarioRequestDTO para Usuario (entidade)
    public static Comunidades toEntity(ComunidadeRequestDTO dto) {
        Comunidades c = new Comunidades();
        
        c.setBanner(dto.getBanner());
        c.setCodigoDeAcesso(dto.getCodigoDeAcesso());
        c.setCriadoId(dto.getCriadoId());
        c.setDescricao(dto.getDescricao());
        c.setLogo(dto.getLogo());
        c.setNome(dto.getNome());
        c.setRule(dto.getRule());
        
        return c;
    }
	
	
}
