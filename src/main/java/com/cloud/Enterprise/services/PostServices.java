package com.cloud.Enterprise.services;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.Enterprise.dto.ComentarioResponseDTO;
import com.cloud.Enterprise.dto.PostRequestDTO;
import com.cloud.Enterprise.dto.PostResponseDTO;
import com.cloud.Enterprise.mapper.ComentarioMapper;
import com.cloud.Enterprise.mapper.PostMapper;
import com.cloud.Enterprise.model.Comentarios;
import com.cloud.Enterprise.model.LikesComentarios;
import com.cloud.Enterprise.model.Post;
import com.cloud.Enterprise.model.PostLike;
import com.cloud.Enterprise.model.Salvos;
import com.cloud.Enterprise.model.Usuario;
import com.cloud.Enterprise.repository.ComentariosRepository;
import com.cloud.Enterprise.repository.LikesComentariosRepository;
import com.cloud.Enterprise.repository.PostLikeRepository;
import com.cloud.Enterprise.repository.PostRepository;
import com.cloud.Enterprise.repository.SalvosRepository;
import com.cloud.Enterprise.repository.UserRepository;
import com.cloud.Enterprise.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;


@Service
public class PostServices {
	
	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private UserRepository usuarioRepository;
	
	@Autowired
	private PostLikeRepository likeRepository;
	
	@Autowired
	private SalvosRepository salvosRepository;
	
	@Autowired
	private ComentariosRepository comentariosRepository;
	
	
	@Autowired
	private LikesComentariosRepository likesComentariosRepository;
	
	
	public PostResponseDTO criarPost(PostRequestDTO dto) {
        
        Usuario usuario = usuarioRepository.findById(dto.getUserId()).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + dto.getUserId()));

        Post post = new Post();
        post.setContent(dto.getContent());
        post.setMedia(dto.getMedia());
        post.setRule(dto.getRule());
        post.setType(dto.getType());
        post.setUsuario(usuario);

        Post postSalvo = postRepo.save(post);

        return PostMapper.toResponseDTO(postSalvo);
    }
	
	
	public List<PostResponseDTO> findAll(){
		List<Post> posts = postRepo.findAll();
		
        List<PostResponseDTO> responseDTOs = posts.stream().map(PostMapper::toResponseDTO).collect(Collectors.toList());
        
        return responseDTOs;
	}
	
	
	public List<PostResponseDTO> findAllPostUser(UUID id){
		List<Post> posts = postRepo.findPostsByUsuarioId(id);
		
        List<PostResponseDTO> responseDTOs = posts.stream().map(PostMapper::toResponseDTO).collect(Collectors.toList());
        
        return responseDTOs;
	}
	

	
	public PostResponseDTO editar(UUID id, PostRequestDTO req) {
		try {
			Post p = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
			
			if (req.getContent() != null) p.setContent(req.getContent());
			if (req.getMedia() != null) p.setMedia(req.getMedia());
			if (req.getRule() != null) p.setRule(req.getRule());
			if (req.getType() != null) p.setType(req.getType());
			
			
			Post pSave = postRepo.save(p);
			
			return PostMapper.toResponseDTO(pSave);
			
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
		
	}
	
	
	public void deletarPost(UUID postId) {
	    Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post não encontrado com ID: " + postId));

	    postRepo.delete(post);
	}
	
	
	public PostResponseDTO alternarLike(UUID postId, UUID userId) {
	    // Busca o post pelo ID
	    Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post não encontrado com ID: " + postId));

	    // Busca o usuário pelo ID
	    Usuario usuario = usuarioRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + userId));

	    // Verifica se o usuário já deu like no post
	    Optional<PostLike> likeExistente = likeRepository.findByPostAndUsuario(post, usuario);

	    if (likeExistente.isPresent()) {
	    	likeRepository.delete(likeExistente.get());
	        post.setLikes(post.getLikes() != null && post.getLikes() > 0 ? post.getLikes() - 1 : 0);
	        
	    } else {
	        // Caso contrário, adiciona o like
	        PostLike novoLike = new PostLike();
	        novoLike.setPost(post);
	        novoLike.setUsuario(usuario);
	        likeRepository.save(novoLike);
	        post.setLikes(post.getLikes() != null ? post.getLikes() + 1 : 1);
	    }

	    // Salva o post atualizado
	    Post postAtualizado = postRepo.save(post);

	    // Retorna o PostResponseDTO usando o mapper
	    return PostMapper.toResponseDTO(postAtualizado);
	}
	
	
	@Transactional
	public String alternarSalvarPost(UUID postId, UUID userId) {
	    // Verifica se o post existe
	    Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post não encontrado com ID: " + postId));

	    // Verifica se o usuário existe
	    Usuario usuario = usuarioRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + userId));

	    // Verifica se o post já está salvo pelo usuário
	    Optional<Salvos> salvoExistente = salvosRepository.findByPostAndUsuario(postId, userId);

	    if (salvoExistente.isPresent()) {
	        // Caso o post já esteja salvo, remove o registro e decrementa o número de salvos
	        salvosRepository.delete(salvoExistente.get());
	        post.setSaves(post.getSaves() - 1);
	        postRepo.save(post); // Atualiza o número de salvos no post
	        
	        return "Post desmarcado como salvo.";
	        
	    } else {
	        // Caso contrário, cria um novo registro e incrementa o número de salvos
	        Salvos novoSalvo = new Salvos();
	        novoSalvo.setPosts(post);
	        novoSalvo.setUsuario(usuario);
	        salvosRepository.save(novoSalvo);

	        post.setSaves(post.getSaves() + 1);
	        postRepo.save(post); // Atualiza o número de salvos no post
	        
	        return "Post marcado como salvo.";
	    }
	}
	
	
	
	public void compartilharPost(UUID postId) {
		
	    Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post não encontrado com ID: " + postId));
	    
	    // Incrementa o número de compartilhamentos
	    post.setShare(post.getShare() + 1);
	    
	    // Salva a alteração no banco
	    postRepo.save(post);
	}
	
	
	// testar a partir daqui
	
	
	
	public ComentarioResponseDTO criarComentario(UUID postId, UUID usuarioId, String conteudo, UUID comentarioPaiId, String img) {
	    // Busca o post
	    Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post não encontrado com ID: " + postId));

	    // Busca o usuário
	    Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + usuarioId));

	    // Criação do comentário
	    Comentarios comentario = new Comentarios();
	    comentario.setPost(post);
	    comentario.setUsuario(usuario);
	    comentario.setConteudo(conteudo);
	    comentario.setImg(img);

	    // Verifica se é uma resposta a outro comentário
	    if (comentarioPaiId != null) {
	        Comentarios comentarioPai = comentariosRepository.findById(comentarioPaiId).orElseThrow(() -> new ResourceNotFoundException("Comentário pai não encontrado com ID: " + comentarioPaiId));
	        comentario.setComentarioPai(comentarioPai);
	    }

	    // Salva o comentário
	    Comentarios comentarioSalvo = comentariosRepository.save(comentario);

	    // Incrementa o número de comentários no post
	    post.setNumberComentarios(post.getNumberComentarios() + 1);
	    postRepo.save(post);

	    return ComentarioMapper.toResponseDTO(comentarioSalvo);
	}
	
	
	
	public List<ComentarioResponseDTO> buscarComentariosPorPost(UUID postId) {
	    List<Comentarios> comentariosPrincipais = comentariosRepository.findComentariosPrincipaisByPostId(postId);

	    // Converte os comentários principais em DTOs
	    return comentariosPrincipais.stream().map(ComentarioMapper::toResponseDTO).collect(Collectors.toList());
	}
	
	
	public void likeOuDeslikeComentario(UUID comentarioId, UUID userId) {
	    Optional<LikesComentarios> likeExistente = likesComentariosRepository.findByComentarioIdAndUserId(comentarioId, userId);
	    
	    Comentarios comentario = comentariosRepository.findById(comentarioId).orElseThrow(() -> new ResourceNotFoundException("Comentário não encontrado"));
        
        Usuario usuario = usuarioRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

	    if (likeExistente.isPresent()) {
	        // Se o like já existe, remove o like
	        likesComentariosRepository.delete(likeExistente.get());
	        comentario.setLikes(comentario.getLikes() != null && comentario.getLikes() > 0 ? comentario.getLikes() - 1 : 0);
	        
	    } else {
	        // Se o like não existe, adiciona um novo
	        
	    	comentario.setLikes(comentario.getLikes() != null ? comentario.getLikes() + 1 : 1);

	        LikesComentarios novoLike = new LikesComentarios();
	        novoLike.setComentario(comentario);
	        novoLike.setUsuario(usuario);

	        likesComentariosRepository.save(novoLike);
	    }
	    
	    comentariosRepository.save(comentario);
	}
	
	
	public void deletarComentario(UUID comentarioId) {
	    Comentarios comentario = comentariosRepository.findById(comentarioId).orElseThrow(() -> new ResourceNotFoundException("Comentário não encontrado com ID: " + comentarioId));

	    // Atualiza o número de comentários do post relacionado
	    Post post = comentario.getPost();
	    
	    if (post.getNumberComentarios() > 0) {
	        post.setNumberComentarios(post.getNumberComentarios() - 1);
	        postRepo.save(post);
	    }

	    comentariosRepository.delete(comentario);
	}

}
