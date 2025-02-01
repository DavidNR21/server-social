package com.cloud.Enterprise.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
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
@Table(name = "comunidades")
public class Comunidades implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	private String nome;
	private String banner;
	private String logo;
	private String descricao;
	private Boolean active = true;
	private UUID criadoId; // so id mesmo
	private Integer max_membros = 150;
	private String rule; // privado ou publico
	private Integer codigoDeAcesso; // quando for privado
	private Date deletedAt;
	
	
	@JsonIgnore
	@OneToMany(mappedBy = "comunidade", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Membro> membros = new HashSet<>();
	
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	


	public Comunidades() {}
	
	
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


	public Boolean getActive() {
		return active;
	}


	public void setActive(Boolean active) {
		this.active = active;
	}


	public UUID getCriadoId() {
		return criadoId;
	}


	public void setCriadoId(UUID criadoId) {
		this.criadoId = criadoId;
	}


	public Integer getMax_membros() {
		return max_membros;
	}


	public void setMax_membros(Integer max_membros) {
		this.max_membros = max_membros;
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


	public Date getDeletedAt() {
		return deletedAt;
	}


	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}


	public Date getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}


	public Set<Membro> getMembros() {
		return membros;
	}


	public void setMembros(Set<Membro> membros) {
		this.membros = membros;
	}
	
	
	
	

}
