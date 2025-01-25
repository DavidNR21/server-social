package com.cloud.Enterprise.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PostResponseDTO {
	
	private UUID id;
    private String content;
    private Long likes;
    private Long views;
    private List<String> media;
    private String rule;
    private String type;
    private Long share;
    private Long saves;
    private Long numberComentarios;
    private Date createdAt;
    private UserPostDTO usuario; // Dados resumidos do usuário
    private Boolean active;
    
    
	public PostResponseDTO(UUID id, String content, Long likes, Long views, List<String> media, String rule, String type, Long share, Long saves, Long numberComentarios, Date createdAt, Boolean active, UserPostDTO usuario) {
		this.id = id;
		this.content = content;
		this.likes = likes;
		this.views = views;
		this.media = media;
		this.rule = rule;
		this.type = type;
		this.share = share;
		this.saves = saves;
		this.numberComentarios = numberComentarios;
		this.createdAt = createdAt;
		this.active = active;
		this.usuario = usuario;
	}


	public UUID getId() {
		return id;
	}


	public void setId(UUID id) {
		this.id = id;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Long getLikes() {
		return likes;
	}


	public void setLikes(Long likes) {
		this.likes = likes;
	}


	public Long getViews() {
		return views;
	}


	public void setViews(Long views) {
		this.views = views;
	}


	public List<String> getMedia() {
		return media;
	}


	public void setMedia(List<String> media) {
		this.media = media;
	}


	public String getRule() {
		return rule;
	}


	public void setRule(String rule) {
		this.rule = rule;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public Long getShare() {
		return share;
	}


	public void setShare(Long share) {
		this.share = share;
	}


	public Long getSaves() {
		return saves;
	}


	public void setSaves(Long saves) {
		this.saves = saves;
	}


	public Long getNumberComentarios() {
		return numberComentarios;
	}


	public void setNumberComentarios(Long numberComentarios) {
		this.numberComentarios = numberComentarios;
	}


	public Date getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}


	public UserPostDTO getUsuario() {
		return usuario;
	}


	public void setUsuario(UserPostDTO usuario) {
		this.usuario = usuario;
	}


	public Boolean getActive() {
		return active;
	}


	public void setActive(Boolean active) {
		this.active = active;
	}
    
    
	
    

}
