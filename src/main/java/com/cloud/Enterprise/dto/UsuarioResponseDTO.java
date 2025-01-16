package com.cloud.Enterprise.dto;

import java.util.Date;
import java.util.UUID;



public class UsuarioResponseDTO {
	
	
	private UUID id;
    private String nomeCompleto;
    private String sign;
    private String email;
    private Date dataNascimento;
    private Boolean active;
    private String bio;
    private String fotoPerfil;
    private String banner;
    private String local;
    private String links;
    private Boolean verifed;
    private String rule;
    
    
    
	public UsuarioResponseDTO(UUID id, String nomeCompleto, String sign, String email, Date dataNascimento,Boolean active, String bio, String fotoPerfil, String banner, String local, String links, Boolean verifed, String rule) {
		this.id = id;
		this.nomeCompleto = nomeCompleto;
		this.sign = sign;
		this.email = email;
		this.dataNascimento = dataNascimento;
		this.active = active;
		this.bio = bio;
		this.fotoPerfil = fotoPerfil;
		this.banner = banner;
		this.local = local;
		this.links = links;
		this.verifed = verifed;
		this.rule = rule;
	}


	public UUID getId() {
		return id;
	}
	
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	
	public String getNomeCompleto() {
		return nomeCompleto;
	}
	
	
	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
	
	
	public String getSign() {
		return sign;
	}
	
	
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	
	public String getEmail() {
		return email;
	}
	
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public Date getDataNascimento() {
		return dataNascimento;
	}
	
	
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	
	public Boolean getActive() {
		return active;
	}
	
	
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	
	public String getBio() {
		return bio;
	}
	
	
	public void setBio(String bio) {
		this.bio = bio;
	}
	
	
	public String getFotoPerfil() {
		return fotoPerfil;
	}
	
	
	public void setFotoPerfil(String fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}
	
	
	public String getBanner() {
		return banner;
	}
	
	
	public void setBanner(String banner) {
		this.banner = banner;
	}
	
	
	public String getLocal() {
		return local;
	}
	
	
	public void setLocal(String local) {
		this.local = local;
	}
	
	
	public String getLinks() {
		return links;
	}
	
	
	public void setLinks(String links) {
		this.links = links;
	}
	
	
	public Boolean getVerifed() {
		return verifed;
	}
	
	
	public void setVerifed(Boolean verifed) {
		this.verifed = verifed;
	}
	
	
	public String getRule() {
		return rule;
	}
	
	
	public void setRule(String rule) {
		this.rule = rule;
	}
    
    
    
    

}
