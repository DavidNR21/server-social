package com.cloud.Enterprise.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


@Entity
@Table(name = "posts")
public class Post implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	
	private String content;
	private Long likes = 0L;
	private Long views = 0L;
	
	@ElementCollection // Use para listas simples como `List<String>`
    @CollectionTable(name = "post_media", joinColumns = @JoinColumn(name = "post_id")) // Nome da tabela para armazenar a lista
	private List<String> media = new ArrayList<>();
	
	private String rule;
	private String type;
	private Boolean active = true;
	private Long share = 0L;
	private Long saves = 0L;
	private Long numberComentarios = 0L;
	private UUID comunidadeId;
	
	
	// ha quem pertence o post
	@ManyToOne()
	@JoinColumn(name = "user_id", nullable = false)
	private Usuario usuario;
	
	@JsonIgnore
    @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Salvos> salvos = new HashSet<>();
	
	@JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PostLike> postlikes = new HashSet<>();
	
	
	@JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comentarios> comnetario = new HashSet<>();
	
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	
	@PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

	
	public Post() {}



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


	public Boolean getActive() {
		return active;
	}


	public void setActive(Boolean active) {
		this.active = active;
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


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public Date getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}


	public Set<Salvos> getSalvos() {
		return salvos;
	}


	public void setSalvos(Set<Salvos> salvos) {
		this.salvos = salvos;
	}


	public Set<PostLike> getPostlikes() {
		return postlikes;
	}


	public void setPostlikes(Set<PostLike> postlikes) {
		this.postlikes = postlikes;
	}


	public Set<Comentarios> getComnetario() {
		return comnetario;
	}


	public void setComnetario(Set<Comentarios> comnetario) {
		this.comnetario = comnetario;
	}


	public UUID getComunidadeId() {
		return comunidadeId;
	}


	public void setComunidadeId(UUID comunidadeId) {
		this.comunidadeId = comunidadeId;
	}
	

}
