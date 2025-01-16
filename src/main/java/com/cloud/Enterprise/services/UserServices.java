package com.cloud.Enterprise.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.Enterprise.dto.UsuarioRequestDTO;
import com.cloud.Enterprise.dto.UsuarioResponseDTO;
import com.cloud.Enterprise.mapper.UsuarioMapper;
import com.cloud.Enterprise.model.Usuario;
import com.cloud.Enterprise.repository.UserRepository;



@Service
public class UserServices {

	
	@Autowired
	private UserRepository repository;
	
	
	
	public UsuarioResponseDTO createUser(UsuarioRequestDTO req) {
		
		Usuario usuario = UsuarioMapper.toEntity(req);
		
		usuario.setActive(true);
		
		Usuario usuarioSalvo = repository.save(usuario);
		
		UsuarioResponseDTO userDto = UsuarioMapper.toResponseDTO(usuarioSalvo);
		
		return userDto;
		
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
		
		UsuarioResponseDTO res = UsuarioMapper.toResponseDTO(obj.get());
		
		return res;
	}
	
	
	public UsuarioResponseDTO updateUser(UUID id, UsuarioRequestDTO req) {
		Usuario usuario = repository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id));
		
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

	}
	
	
	public UsuarioResponseDTO fakeDelete(UUID id) {
		
		Optional<Usuario> entityDelete = repository.findById(id);
		
		entityDelete.get().setActive(false);
		entityDelete.get().setDeletedAt(new Date());
		
		Usuario updUser = repository.save(entityDelete.get());
		
		UsuarioResponseDTO newDto = UsuarioMapper.toResponseDTO(updUser);
		
		
		return newDto;
		
	}
	
	
	
	public UsuarioResponseDTO fazerLogin(String email, String senha, String ip) {
        // Busca o usuário pelo e-mail e verifica se está ativo
        Usuario usuario = repository.findByEmailAndActiveTrue(email).orElseThrow(() -> new RuntimeException("Usuário não encontrado ou inativo."));

        // Verifica se a senha está correta (você pode implementar a lógica de hash/senha segura aqui)
        if (!usuario.getSenha().equals(senha)) {
            throw new RuntimeException("Senha inválida.");
        }
        
        usuario.setIpAdress(ip);
        
        Usuario logUser = repository.save(usuario);

        // Retorna o usuário como um DTO de resposta
        return UsuarioMapper.toResponseDTO(logUser);
    }
	
	
	public Usuario resetPassword(String email, String senha) {
		Usuario usuarioActive = repository.findByEmailAndActiveTrue(email).orElseThrow(() -> new RuntimeException("Usuário não encontrado ou inativo."));
		
		usuarioActive.setSenha(senha);
		
		
		Usuario logUser = repository.save(usuarioActive);
		
		
		
		return logUser;
	}

}
