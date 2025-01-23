package com.cloud.Enterprise.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.cloud.Enterprise.dto.CardSeguidoresDTO;
import com.cloud.Enterprise.dto.SeguidorResponseDTO;
import com.cloud.Enterprise.dto.UsuarioRequestDTO;
import com.cloud.Enterprise.dto.UsuarioResponseDTO;
import com.cloud.Enterprise.mapper.UsuarioMapper;
import com.cloud.Enterprise.model.Seguidores;
import com.cloud.Enterprise.model.Usuario;
import com.cloud.Enterprise.repository.SeguidoresRepository;
import com.cloud.Enterprise.repository.UserRepository;
import com.cloud.Enterprise.services.exceptions.DatabaseException;
import com.cloud.Enterprise.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;



@Service
public class UserServices {

	
	@Autowired
	private UserRepository repository;
	
	
	@Autowired
	private SeguidoresRepository seguidoresRepository;
	
	// Retorna o número de seguidores de um usuário
    public long contarSeguidores(UUID usuarioId) {
        return seguidoresRepository.countSeguidoresByUsuarioId(usuarioId);
    }

    // Retorna o número de usuários que o usuário está seguindo
    public long contarSeguindo(UUID usuarioId) {
        return seguidoresRepository.countSeguindoByUsuarioId(usuarioId);
    }
	
	
	public UsuarioResponseDTO createUser(UsuarioRequestDTO req) {
		
		if (req.getNomeCompleto() == null && req.getEmail() == null && req.getSenha() == null && req.getSign() == null) {
			throw new RuntimeException("Erro ao criar usuario: faltando dados");
		}
		
		try {
            Usuario usuario = UsuarioMapper.toEntity(req);
            usuario.setActive(true);

            Usuario usuarioSalvo = repository.save(usuario);

            return UsuarioMapper.toResponseDTO(usuarioSalvo); 
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar usuario: " + e.getMessage());
        }
		
	}
	
	
	public List<UsuarioResponseDTO> findAll(){
		List<Usuario> usuarios = repository.findAll();
		
		// Converte a lista de entidades para uma lista de response DTOs
        List<UsuarioResponseDTO> responseDTOs = usuarios.stream()
                .map(UsuarioMapper::toResponseDTO)
                .collect(Collectors.toList());
        
        return responseDTOs;
	}
	
	
	public UsuarioResponseDTO findById(UUID id) {
		Optional<Usuario> obj = repository.findById(id);
			
		UsuarioResponseDTO res = UsuarioMapper.toResponseDTO(obj.orElseThrow(() -> new ResourceNotFoundException(id)));
			
		return res;
			
	}
	
	
	public UsuarioResponseDTO updateUser(UUID id, UsuarioRequestDTO req) {
	    
	    try {
	    	Usuario usuario = repository.findById(id).orElseThrow(() ->  new ResourceNotFoundException(id));
			
			// Atualiza apenas os campos fornecidos na requisição
		    if (req.getNomeCompleto() != null) usuario.setNomeCompleto(req.getNomeCompleto());
		    if (req.getSign() != null) usuario.setSign(req.getSign());
		    if (req.getFotoPerfil() != null) usuario.setFotoPerfil(req.getFotoPerfil());
		    if (req.getEmail() != null) usuario.setEmail(req.getEmail());
		    if (req.getSenha() != null) usuario.setSenha(req.getSenha());
		    if (req.getBanner() != null) usuario.setBanner(req.getBanner());
		    if (req.getDataNascimento() != null) usuario.setDataNascimento(req.getDataNascimento());
		    if (req.getBio() != null) usuario.setBio(req.getBio());
		    if (req.getLocal() != null) usuario.setLocal(req.getLocal());
		    if (req.getLinks() != null) usuario.setLinks(req.getLinks());
		    if (req.getRule() != null) usuario.setRule(req.getRule());
		    if (req.getVerifed() != null) usuario.setVerifed(req.getVerifed());
		    if (req.getActive() != null) usuario.setActive(req.getActive());

		    // Atualiza o campo "editedAt" com a data/hora atual
		    usuario.setEditedAt(new Date());

		    // Salva o usuário atualizado no banco de dados
		    Usuario usuarioSalvo = repository.save(usuario);

		    // Converte a entidade salva para o DTO de resposta
		    return UsuarioMapper.toResponseDTO(usuarioSalvo);
		    
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}

	}
	
	
	public UsuarioResponseDTO fakeDelete(UUID id) {
		
		try {
			Optional<Usuario> entityDelete = repository.findById(id);
			
			entityDelete.orElseThrow(() ->  new ResourceNotFoundException(id)).setActive(false);
			entityDelete.orElseThrow(() ->  new ResourceNotFoundException(id)).setDeletedAt(new Date());
			
			Usuario updUser = repository.save(entityDelete.get());
			
			UsuarioResponseDTO newDto = UsuarioMapper.toResponseDTO(updUser);
			
			
			return newDto;
			
		} catch (Exception e) {
			throw new RuntimeException("Erro ao desativar usuário: " + e.getMessage());
		}
		
	}
	
	
	
	public UsuarioResponseDTO fazerLogin(String email, String senha, String ip) {
		try {
            Usuario usuario = repository.findByEmailAndActiveTrue(email).orElseThrow(() -> new ResourceNotFoundException(email));

            if (!usuario.getSenha().equals(senha)) {
                throw new RuntimeException("Senha inválida.");
            }

            usuario.setIpAdress(ip);
            Usuario usuarioAtualizado = repository.save(usuario);

            return UsuarioMapper.toResponseDTO(usuarioAtualizado);
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao realizar login: " + e.getMessage());
        }
    }
	
	
	public Usuario resetPassword(String email, String senha) {
		try {
            Usuario usuario = repository.findByEmailAndActiveTrue(email).orElseThrow(() -> new ResourceNotFoundException(email));

            usuario.setSenha(senha);

            return repository.save(usuario);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao redefinir senha: " + e.getMessage());
        }
	}
	
	
	public String deletePermanent(UUID id) {
		 try {
			 
			 repository.deleteById(id);
			 
			 return "ok";
	            
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
				
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
		
	}
	
	
	public UsuarioResponseDTO reActive(UUID id) {
		
		Usuario user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		
		user.setActive(true);
		user.setEditedAt(new Date());
		user.setDeletedAt(null);
		
		Usuario salvo = repository.save(user);
		
		return UsuarioMapper.toResponseDTO(salvo);
		
	}
	
	
	public String seguir(UUID seguido, UUID seguidor) {
		
		// Verifica se os usuários existem
	    Usuario user1 = repository.findById(seguidor).orElseThrow(() -> new ResourceNotFoundException(seguidor));
	    Usuario user2 = repository.findById(seguido).orElseThrow(() -> new ResourceNotFoundException(seguido));
	    
	    // Verifica se o relacionamento já existe
	    boolean jaSeguindo = seguidoresRepository.existsBySeguidorAndSeguido(user1, user2);
	    
	    if (jaSeguindo) {
	        return "Usuário já está seguindo a pessoa especificada.";
	    }

	    // Cria o relacionamento caso não exista
	    Seguidores relacionamento = new Seguidores();
	    relacionamento.setSeguidor(user1);
	    relacionamento.setSeguido(user2);
	    relacionamento.setCreatedAt(new Date());
	    
	    seguidoresRepository.save(relacionamento);
	    return "ok";
	}
	
	
	public List<SeguidorResponseDTO> findAllSeguir() {
	    // Busca todos os registros na tabela de seguidores
	    List<Seguidores> seguidores = seguidoresRepository.findAll();

	    
	    // Mapeia cada registro para um SeguidorResponseDTO
	    return seguidores.stream().map(seguidor -> new SeguidorResponseDTO(UsuarioMapper.toResponseDTO(seguidor.getSeguidor()), UsuarioMapper.toResponseDTO(seguidor.getSeguido()))).collect(Collectors.toList());
	}
	
	
	
	public List<CardSeguidoresDTO> findSeguidores(UUID seguidoId) {
		List<Seguidores> seguidores = seguidoresRepository.findSeguidoresWithDetails(seguidoId);

	    return seguidores.stream()
	            .map(seguidor -> {
	                Usuario seguidorDetails = seguidor.getSeguidor();
	                Long quantidadeSeguidores = seguidoresRepository.countSeguidoresByUsuarioId(seguidorDetails.getId());

	                return new CardSeguidoresDTO(
	                        seguidorDetails.getFotoPerfil(),
	                        seguidorDetails.getSign(),
	                        seguidorDetails.getId(),
	                        quantidadeSeguidores,
	                        seguidor.getSeguido().getId()
	                );
	            })
	            .collect(Collectors.toList());
	}
	
	
	public List<CardSeguidoresDTO> findUsuariosSeguindo(UUID seguidorId) {
		
	    List<Seguidores> usuariosSeguindo = seguidoresRepository.findSeguindoWithDetails(seguidorId);

	    // Mapeia os registros para CardSeguidoresDTO
	    return usuariosSeguindo.stream()
	            .map(seguindo -> {
	                Usuario seguidoDetails = seguindo.getSeguido();
	                Long quantidadeSeguidores = seguidoresRepository.countSeguidoresByUsuarioId(seguidoDetails.getId());

	                return new CardSeguidoresDTO(
	                        seguidoDetails.getFotoPerfil(), // Foto de perfil do seguido
	                        seguidoDetails.getSign(),       // Sign do seguido
	                        seguidoDetails.getId(),         // ID do seguido
	                        quantidadeSeguidores,           // Quantidade de seguidores do seguido
	                        seguidorId                      // ID do seguidor (quem está seguindo)
	                );
	            })
	            .collect(Collectors.toList());
	}
	
	
	public String deixarDeSeguir(UUID seguidorId, UUID seguidoId) {
	    try {
	        // Busca a relação no banco de dados
	        Seguidores seguidores = seguidoresRepository.findBySeguidorAndSeguido(seguidorId, seguidoId)
	                .orElseThrow(() -> new RuntimeException("Relacionamento de seguidor e seguido não encontrado."));

	        // Remove a relação
	        seguidoresRepository.delete(seguidores);

	        return "ok";

	    } catch (EmptyResultDataAccessException e) {
	        throw new ResourceNotFoundException("Relacionamento de seguidor e seguido não encontrado: " + seguidorId + " -> " + seguidoId);
	    } catch (DataIntegrityViolationException e) {
	        throw new DatabaseException("Erro de integridade ao remover o relacionamento: " + e.getMessage());
	    }
	}

}
