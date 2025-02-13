package com.cloud.Enterprise.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	
	@Column
	private String nomeCompleto;
	private String sign;
	private String email;
	private String senha;
	private Date dataNascimento;
	private Boolean active;
	private String bio;
	private String fotoPerfil;
	private String banner;
	private String local;
	private String links;
	private String ipAdress;
	private Boolean verifed;
	private Date editedAt;
	private Date deletedAt;
	private String rule;

	@JsonIgnore
    @OneToMany(mappedBy = "seguidor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Seguidores> seguindo = new HashSet<>();
    
    // Relacionamento com a tabela de seguidores (quem segue este usuário)
	@JsonIgnore
    @OneToMany(mappedBy = "seguido", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Seguidores> seguidores = new HashSet<>();
	
	
	// posts do usuario
	@JsonIgnore
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Post> posts = new HashSet<>();
	
	
	@JsonIgnore
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Salvos> salvos = new HashSet<>();
	
	@JsonIgnore
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PostLike> likes = new HashSet<>();
	
	
	@JsonIgnore
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comentarios> comentarios = new HashSet<>();
	
	
	@JsonIgnore
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LikesComentarios> likesComentarios = new HashSet<>();
	
	
	@JsonIgnore
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Membro> comunidades = new HashSet<>();
	
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	
	
	public Usuario () {}

	
	@PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
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
	


	public String getRule() {
		return rule;
	}



	public void setRule(String rule) {
		this.rule = rule;
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



	public String getSenha() {
		return senha;
	}



	public void setSenha(String senha) {
		this.senha = senha;
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



	public String getIpAdress() {
		return ipAdress;
	}



	public void setIpAdress(String ipAdress) {
		this.ipAdress = ipAdress;
	}



	public Boolean getVerifed() {
		return verifed;
	}



	public void setVerifed(Boolean verifed) {
		this.verifed = verifed;
	}
	


	@Override
	public int hashCode() {
		return Objects.hash(senha);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(senha, other.senha);
	}


	public Date getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}


	public Date getEditedAt() {
		return editedAt;
	}


	public void setEditedAt(Date editedAt) {
		this.editedAt = editedAt;
	}


	public Date getDeletedAt() {
		return deletedAt;
	}



	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}



	public Set<Seguidores> getSeguidores() {
		return seguidores;
	}


	public void setSeguidores(Set<Seguidores> seguidores) {
		this.seguidores = seguidores;
	}


	public Set<Seguidores> getSeguindo() {
		return seguindo;
	}


	public void setSeguindo(Set<Seguidores> seguindo) {
		this.seguindo = seguindo;
	}


	public Set<Post> getPosts() {
		return posts;
	}


	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}


	public Set<Salvos> getSalvos() {
		return salvos;
	}


	public void setSalvos(Set<Salvos> salvos) {
		this.salvos = salvos;
	}


	public Set<PostLike> getLikes() {
		return likes;
	}


	public void setLikes(Set<PostLike> likes) {
		this.likes = likes;
	}


	public Set<Comentarios> getComentarios() {
		return comentarios;
	}


	public void setComentarios(Set<Comentarios> comentarios) {
		this.comentarios = comentarios;
	}


	public Set<LikesComentarios> getLikesComentarios() {
		return likesComentarios;
	}


	public void setLikesComentarios(Set<LikesComentarios> likesComentarios) {
		this.likesComentarios = likesComentarios;
	}


	public Set<Membro> getComunidades() {
		return comunidades;
	}


	public void setComunidades(Set<Membro> comunidades) {
		this.comunidades = comunidades;
	}
	
	
	

}
