package com.cloud.Enterprise.dto;


public class SeguidorResponseDTO {
	
	private UsuarioResponseDTO seguidor; // ID do seguidor
    private UsuarioResponseDTO seguido; // Dados do seguido

    

    public SeguidorResponseDTO(UsuarioResponseDTO seguidor, UsuarioResponseDTO seguido) {
        this.seguidor = seguidor;
        this.seguido = seguido;
    }


    
    public UsuarioResponseDTO getSeguidor() {
        return seguidor;
    }

    public void setSeguidor(UsuarioResponseDTO seguidorId) {
        this.seguidor = seguidorId;
    }

    public UsuarioResponseDTO getSeguido() {
        return seguido;
    }

    public void setSeguido(UsuarioResponseDTO seguido) {
        this.seguido = seguido;
    }

}
