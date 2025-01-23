package com.cloud.Enterprise.model;

import java.io.Serializable;
import java.util.Date;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


@Entity
@Table(name = "seguidores")
public class Seguidores implements Serializable {
	
	private static final long serialVersionUID = 1L;
	

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	
    // quem clicou pra seguir vai o id
	@ManyToOne() // Relacionamento com a entidade Usuario
    @JoinColumn(name = "seguidor_id", nullable = false)
    private Usuario seguidor; // Deve ser a entidade Usuario e não um UUID

	
    @ManyToOne()
    @JoinColumn(name = "seguido_id", nullable = false) // quem foi seguido vai o obj
    private Usuario seguido;

    
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    
    
    public Seguidores() {}

    
    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Usuario getSeguidor() {
		return seguidor;
	}


	public void setSeguidor(Usuario seguidor) {
		this.seguidor = seguidor;
	}


	public Usuario getSeguido() {
		return seguido;
	}


	public void setSeguido(Usuario seguido) {
		this.seguido = seguido;
	}


	public Date getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
    
    
    

}
