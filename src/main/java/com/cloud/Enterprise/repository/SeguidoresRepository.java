package com.cloud.Enterprise.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cloud.Enterprise.model.Seguidores;
import com.cloud.Enterprise.model.Usuario;

@Repository
public interface SeguidoresRepository extends JpaRepository<Seguidores, Long> {
	// Busca os seguidores do usuário com active = true
	@Query("SELECT s FROM Seguidores s JOIN FETCH s.seguidor seg WHERE s.seguido.id = :seguidoId AND seg.active = true")
	List<Seguidores> findSeguidoresWithDetails(@Param("seguidoId") UUID seguidoId);
	
	// Busca os usuários que o usuário está seguindo com active = true
	@Query("SELECT s FROM Seguidores s JOIN FETCH s.seguido seg WHERE s.seguidor.id = :seguidorId AND seg.active = true")
	List<Seguidores> findSeguindoWithDetails(@Param("seguidorId") UUID seguidorId);
	
	@Query("SELECT s FROM Seguidores s WHERE s.seguidor.id = :seguidorId AND s.seguido.id = :seguidoId")
	Optional<Seguidores> findBySeguidorAndSeguido(@Param("seguidorId") UUID seguidorId, @Param("seguidoId") UUID seguidoId);
    
    // Conta quantos seguidores o usuário tem
    @Query("SELECT COUNT(s) FROM Seguidores s WHERE s.seguido.id = :seguidoId")
    long countSeguidoresByUsuarioId(@Param("seguidoId") UUID seguidoId);
    
    @Query("SELECT COUNT(s) FROM Seguidores s WHERE s.seguidor.id = :seguidorId")
    long countSeguindoByUsuarioId(@Param("seguidorId") UUID seguidorId);
    
    @Query("SELECT COUNT(s) > 0 FROM Seguidores s WHERE s.seguidor = :seguidor AND s.seguido = :seguido")
    boolean existsBySeguidorAndSeguido(@Param("seguidor") Usuario seguidor, @Param("seguido") Usuario seguido);
    
    void delete(Seguidores seguidores);
}
