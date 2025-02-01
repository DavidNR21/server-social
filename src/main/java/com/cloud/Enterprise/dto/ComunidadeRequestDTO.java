package com.cloud.Enterprise.dto;


import java.util.UUID;



public class ComunidadeRequestDTO {

	private String nome;
	private String banner;
	private String logo;
	private String descricao;
	private UUID criadoId; // so id mesmo
	private String rule; // privado ou publico
	private Integer codigoDeAcesso; // quando for privado
	
	
	public ComunidadeRequestDTO() {}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getBanner() {
		return banner;
	}


	public void setBanner(String banner) {
		this.banner = banner;
	}


	public String getLogo() {
		return logo;
	}


	public void setLogo(String logo) {
		this.logo = logo;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}



	public UUID getCriadoId() {
		return criadoId;
	}


	public void setCriadoId(UUID criadoId) {
		this.criadoId = criadoId;
	}


	public String getRule() {
		return rule;
	}


	public void setRule(String rule) {
		this.rule = rule;
	}


	public Integer getCodigoDeAcesso() {
		return codigoDeAcesso;
	}


	public void setCodigoDeAcesso(Integer codigoDeAcesso) {
		this.codigoDeAcesso = codigoDeAcesso;
	}

	
	
}
