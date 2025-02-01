package com.cloud.Enterprise.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cloud.Enterprise.constants.Permissoes;
import com.cloud.Enterprise.model.Membro;


@Repository
public interface MembroRepository extends JpaRepository<Membro, UUID> {

	// 🔹 Busca todos os membros de uma comunidade específica
    //List<Membro> findByComunidadeId(UUID comunidadeId);
	
	// 🔹 Busca todos os membros de uma comunidade específica que têm usuários ativos
    List<Membro> findByComunidadeIdAndUsuarioActiveTrue(UUID comunidadeId);

    // 🔹 Busca membros de uma comunidade por role (ADMIN, MEMBRO)
    List<Membro> findByComunidadeIdAndRole(UUID comunidadeId, Permissoes role);
    
    // Busca todas as comunidades onde o usuário é membro
    List<Membro> findByUsuarioId(UUID usuarioId);
    
    // Buscar um membro específico na comunidade pelo ID do usuário e da comunidade
    Optional<Membro> findByUsuarioIdAndComunidadeId(UUID usuarioId, UUID comunidadeId);
	
    
    @Modifying
    @Query("DELETE FROM Membro m WHERE m.comunidade.id = :comunidadeId")
    void deleteByComunidadeId(@Param("comunidadeId") UUID comunidadeId);
    
    Integer countByComunidadeId(UUID comunidadeId);
}
