package com.cloud.Enterprise.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import com.cloud.Enterprise.constants.Permissoes;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;



@Entity
@Table(name = "membros")
public class Membro implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne()
    @JoinColumn(name = "comunidade_id", nullable = false)
    private Comunidades comunidade;

    @ManyToOne()
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private Permissoes role = Permissoes.MEMBRO;

    @Temporal(TemporalType.TIMESTAMP)
    private Date joinedAt = new Date();
    
    
    public Membro() {}


	public UUID getId() {
		return id;
	}


	public void setId(UUID id) {
		this.id = id;
	}


	public Comunidades getComunidade() {
		return comunidade;
	}


	public void setComunidade(Comunidades comunidade) {
		this.comunidade = comunidade;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public Permissoes getRole() {
		return role;
	}


	public void setRole(Permissoes role) {
		this.role = role;
	}


	public Date getJoinedAt() {
		return joinedAt;
	}


	public void setJoinedAt(Date joinedAt) {
		this.joinedAt = joinedAt;
	}
    
    
    
}
