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
@Table(name = "comentarios")
public class Comentarios implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	
	private String conteudo;
	private Long likes = 0L;
	private String img;
	
	
	@ManyToOne()
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;
	
	@ManyToOne()
	@JoinColumn(name = "user_id", nullable = false)
	private Usuario usuario;
	
	
	// Relacionamento com o comentário pai (nulo para comentários principais)
    @ManyToOne
    @JoinColumn(name = "comentario_pai_id")
    private Comentarios comentarioPai;
    
    
    @OneToMany(mappedBy = "comentarioPai", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentarios> respostas = new ArrayList<>();
    
    
    @JsonIgnore
    @OneToMany(mappedBy = "comentario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LikesComentarios> likeComentarios = new HashSet<>();
	
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	
	@PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }
	
	
	public Comentarios() {}


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


	public Post getPost() {
		return post;
	}


	public void setPost(Post post) {
		this.post = post;
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


	public Comentarios getComentarioPai() {
		return comentarioPai;
	}


	public void setComentarioPai(Comentarios comentarioPai) {
		this.comentarioPai = comentarioPai;
	}


	public List<Comentarios> getRespostas() {
		return respostas;
	}


	public void setRespostas(List<Comentarios> respostas) {
		this.respostas = respostas;
	}


	public Set<LikesComentarios> getLikeComentarios() {
		return likeComentarios;
	}


	public void setLikeComentarios(Set<LikesComentarios> likeComentarios) {
		this.likeComentarios = likeComentarios;
	}
	
	
	
}
