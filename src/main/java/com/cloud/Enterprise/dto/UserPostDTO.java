package com.cloud.Enterprise.dto;

import java.util.UUID;

public class UserPostDTO {
	
	private UUID id;
    private String sign;
    private String perfil;

    // Construtor
    public UserPostDTO(UUID id, String sign, String perfil) {
        this.id = id;
        this.sign = sign;
        this.perfil = perfil;
    }

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

    
    
}
