package com.cloud.Enterprise.controllers;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.Enterprise.dto.ComentarioResponseDTO;
import com.cloud.Enterprise.dto.PostRequestDTO;
import com.cloud.Enterprise.dto.PostResponseDTO;
import com.cloud.Enterprise.services.PostServices;
import com.cloud.Enterprise.services.exceptions.ResourceNotFoundException;



@RestController
@RequestMapping(value = "/api/post")
public class PostController {
	
	
	@Autowired
	private PostServices services;
	
	
	
	@PostMapping
    public ResponseEntity<?> criarPost(@RequestBody PostRequestDTO dto) {
		try {
            // Cria o post e retorna o DTO
            PostResponseDTO postCriado = services.criarPost(dto);
            
            return ResponseEntity.status(201).body(postCriado);

        } catch (Exception e) {
            // Trata exceções
            return ResponseEntity.status(500).body(null);
        }
    }
	
	
	@GetMapping()
	public ResponseEntity<List<PostResponseDTO>> listarAll(){
		
		// Busca todos os usuários no banco de dados
        List<PostResponseDTO> responseDTO = services.findAll();


        return ResponseEntity.ok(responseDTO);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<List<PostResponseDTO>> postesDoUsuario(@PathVariable UUID id){
		
		// Busca todos os usuários no banco de dados
        List<PostResponseDTO> responseDTO = services.findAllPostUser(id);


        return ResponseEntity.ok(responseDTO);
	}
	
	
	 
	
	@PutMapping("/edit/{id}")
	public ResponseEntity<PostResponseDTO> editarPostagem(@RequestBody PostRequestDTO req, @PathVariable UUID id){
		
		PostResponseDTO p = services.editar(id, req);
		
		return ResponseEntity.status(200).body(p);
		
	}
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deletarPost(@PathVariable UUID id) {
	    try {
	        // Chama o serviço para deletar o post
	        services.deletarPost(id);

	        // Retorna uma resposta de sucesso
	        Map<String, Object> response = new HashMap<>();
	        response.put("statusCode", 200);
	        response.put("message", "Post deletado com sucesso.");
	        response.put("postId", id);

	        return ResponseEntity.ok(response);
	        
	    } catch (ResourceNotFoundException e) {
	        // Caso o post não seja encontrado, retorna um erro 404
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	    } catch (Exception e) {
	        // Caso ocorra outro erro, retorna um erro genérico 500
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar o post.");
	    }
	}
	
	

	@PatchMapping("/like/{postId}")
	public ResponseEntity<?> alternarLike(@PathVariable UUID postId, @RequestBody Map<String, UUID> request) {
	    try {
	        // Obtém o ID do usuário do corpo da requisição
	        UUID userId = request.get("userId");

	        // Chama o serviço para alternar o like
	        PostResponseDTO postAtualizado = services.alternarLike(postId, userId);

	        // Retorna o post atualizado
	        return ResponseEntity.ok(postAtualizado);
	    } catch (ResourceNotFoundException e) {
	        // Caso post ou usuário não sejam encontrados
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	    } catch (Exception e) {
	        // Caso ocorra outro erro, retorna erro genérico
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao alternar o like no post.");
	    }
	}
	
	
	@PostMapping("/salvar/{postId}")
	public ResponseEntity<?> alternarSalvarPost(@PathVariable UUID postId, @RequestBody Map<String, UUID> body) {
	    UUID userId = body.get("userId");
	    
	    if (userId == null) {
	        return ResponseEntity.badRequest().body("ID do usuário é obrigatório.");
	    }

	    try {
	        String resultado = services.alternarSalvarPost(postId, userId);

	        Map<String, Object> response = new HashMap<>();
	        response.put("message", resultado);
	        response.put("postId", postId);

	        return ResponseEntity.ok(response);
	        
	    } catch (ResourceNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar/desmarcar o post.");
	    }
	}
	
	
	@PostMapping("/compartilhar/{postId}")
	public ResponseEntity<?> compartilharPost(@PathVariable UUID postId) {
	    try {
	        services.compartilharPost(postId);

	        Map<String, Object> response = new HashMap<>();
	        response.put("status", "success");
	        response.put("message", "Post compartilhado com sucesso!");
	        
	        return ResponseEntity.ok(response);
	        
	    } catch (ResourceNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao compartilhar post: " + e.getMessage());
	    }
	}
	
	
	
	@PostMapping("/comentarios/criar/{postId}")
	public ResponseEntity<?> adicionarComentario(@PathVariable UUID postId, @RequestBody Map<String, Object> request) {
		
	    try {
	        UUID usuarioId = UUID.fromString(request.get("usuarioId").toString());
	        
	        String conteudo = request.get("conteudo").toString();
	   
	        String img = request.get("img").toString();
	        
	        UUID comentarioPaiId = request.containsKey("comentarioPaiId") ? UUID.fromString(request.get("comentarioPaiId").toString()) : null;

	        // Chama o serviço para criar o comentário
	        ComentarioResponseDTO comentario = services.criarComentario(postId, usuarioId, conteudo, comentarioPaiId, img);

	        return ResponseEntity.ok(comentario);
	        
	    } catch (ResourceNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao adicionar comentário: " + e.getMessage());
	    }
	}
	
	
	
	@GetMapping("/comentarios/{postId}")
	public ResponseEntity<?> buscarComentariosPorPost(@PathVariable UUID postId) {
		
	    List<ComentarioResponseDTO> comentarios = services.buscarComentariosPorPost(postId);

	    Map<String, Object> response = new HashMap<>();
	    response.put("statusCode", 200);
	    response.put("message", "Comentários carregados com sucesso.");
	    response.put("comentarios", comentarios);

	    return ResponseEntity.ok(response);
	}
	
	
	
	@PostMapping("/comentarios/like/{comentarioId}")
	public ResponseEntity<String> curtirComentario(@PathVariable UUID comentarioId, @RequestBody Map<String, UUID> request) {
	    UUID userId = request.get("userId");
	    try {
	        services.likeOuDeslikeComentario(comentarioId, userId);
	        
	        return ResponseEntity.ok("Like atualizado com sucesso!");
	        
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao curtir o comentário: " + e.getMessage());
	    }
	}
	
	
	@DeleteMapping("/comentarios/delete/{comentarioId}")
	public ResponseEntity<?> deletarComentario(@PathVariable UUID comentarioId) {
		
	    services.deletarComentario(comentarioId);
	    
	    Map<String, Object> response = new HashMap<>();
	    response.put("statusCode", 200);
	    response.put("message", "Comentários deletado com sucesso.");

	    return ResponseEntity.ok(response);
	}
	
	
}
