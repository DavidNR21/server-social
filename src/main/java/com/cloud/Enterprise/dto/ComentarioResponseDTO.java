package com.cloud.Enterprise.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ComentarioResponseDTO {

	private UUID id;
    private String conteudo;
    private Long likes;
    private String img;
    private Date createdAt;

    private UserPostDTO usuario; // DTO contendo as informações do usuário

    private List<ComentarioResponseDTO> respostas; // Lista de respostas

    public ComentarioResponseDTO(UUID id, String conteudo, Long likes, String img, Date createdAt, UserPostDTO usuario, List<ComentarioResponseDTO> respostas) {
        this.id = id;
        this.conteudo = conteudo;
        this.likes = likes;
        this.img = img;
        this.createdAt = createdAt;
        this.usuario = usuario;
        this.respostas = respostas;
    }

    
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public Long getLikes() {
		return likes;
	}

	public void setLikes(Long likes) {
		this.likes = likes;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public List<ComentarioResponseDTO> getRespostas() {
		return respostas;
	}

	public void setRespostas(List<ComentarioResponseDTO> respostas) {
		this.respostas = respostas;
	}


	public UserPostDTO getUsuario() {
		return usuario;
	}


	public void setUsuario(UserPostDTO usuario) {
		this.usuario = usuario;
	}
    
    
    
}
