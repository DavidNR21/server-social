package com.cloud.Enterprise.dto;

import java.util.UUID;



public class CardSeguidoresDTO {
	
	private String perfil;
	private String sign;
	private UUID id;
	private Long seguidores;
	private UUID seguidoId;
	
	
	
	public CardSeguidoresDTO(String perfil, String sign, UUID id, Long seguidores, UUID seguidoId) {
		this.perfil = perfil;
		this.sign = sign;
		this.id = id;
		this.seguidores = seguidores;
		this.seguidoId = seguidoId;
	}



	public String getPerfil() {
		return perfil;
	}


	public UUID getSeguidoId() {
		return seguidoId;
	}



	public void setSeguidoId(UUID seguidoId) {
		this.seguidoId = seguidoId;
	}



	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}



	public String getSign() {
		return sign;
	}



	public void setSign(String sign) {
		this.sign = sign;
	}



	public UUID getId() {
		return id;
	}



	public void setId(UUID id) {
		this.id = id;
	}



	public Long getSeguidores() {
		return seguidores;
	}



	public void setSeguidores(Long seguidores) {
		this.seguidores = seguidores;
	}
	
	
	

}
