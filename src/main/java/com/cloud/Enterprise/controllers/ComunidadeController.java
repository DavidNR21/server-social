package com.cloud.Enterprise.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.Enterprise.dto.ComunidadeRequestDTO;
import com.cloud.Enterprise.dto.PostResponseDTO;
import com.cloud.Enterprise.model.Comunidades;
import com.cloud.Enterprise.services.ComunidadeServices;
import com.cloud.Enterprise.services.exceptions.ResourceNotFoundException;



@RestController
@RequestMapping(value = "/api/comunidade")
public class ComunidadeController {
	
	
	@Autowired
	private ComunidadeServices services;

	
	
	@PostMapping
	public ResponseEntity<?> criarComunidade(@RequestBody ComunidadeRequestDTO req) {
	    Map<String, Object> response = new HashMap<>();
	    
	    try {
	        UUID id = req.getCriadoId();
	        Comunidades comu = services.criarComunidade(id, req);
	        
	        response.put("statusCode", 200);
	        response.put("message", "Comunidade criada com sucesso.");
	        response.put("result", comu);

	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        response.put("statusCode", 500);
	        response.put("error", "Erro ao criar comunidade: " + e.getMessage());
	        return ResponseEntity.status(500).body(response);
	    }
	}

	
	@PostMapping("/join")
	public ResponseEntity<?> join(@RequestBody Map<String, Object> req) {
	    Map<String, Object> response = new HashMap<>();
	    
	    try {
	        UUID userId = UUID.fromString(req.get("userId").toString());
	        UUID comunidadeId = UUID.fromString(req.get("id").toString());
	        
	        String join = services.entrar(userId, comunidadeId);

	        response.put("statusCode", 200);
	        response.put("result", join);

	        return ResponseEntity.ok(response);
	        
	    } catch (IllegalArgumentException e) {
	        response.put("statusCode", 400);
	        response.put("error", "Formato de UUID inválido.");
	        return ResponseEntity.status(400).body(response);
	        
	    } catch (Exception e) {
	        response.put("statusCode", 500);
	        response.put("error", "Erro ao entrar na comunidade: " + e.getMessage());
	        return ResponseEntity.status(500).body(response);
	    }
	}

	
	@GetMapping
	public ResponseEntity<?> todasComunidades() {
	    Map<String, Object> response = new HashMap<>();

	    try {
	        List<Comunidades> comunidades = services.allComunidades();
	        return ResponseEntity.ok(comunidades);
	        
	    } catch (Exception e) {
	        response.put("statusCode", 500);
	        response.put("error", "Erro ao buscar comunidades: " + e.getMessage());
	        return ResponseEntity.status(500).body(response);
	    }
	}

	
	@GetMapping("/buscar")
	public ResponseEntity<?> buscarComunidades(@RequestParam String nome) {
	    Map<String, Object> response = new HashMap<>();

	    try {
	        List<Map<String, Object>> comunidades = services.buscarComunidadesAtivasPorNome(nome);
	        return ResponseEntity.ok(comunidades);
	        
	    } catch (Exception e) {
	        response.put("statusCode", 500);
	        response.put("error", "Erro ao buscar comunidades: " + e.getMessage());
	        return ResponseEntity.status(500).body(response);
	    }
	}
	
	
	@GetMapping("/{comunidadeId}")
	public ResponseEntity<?> buscarComunidadeId(@PathVariable UUID comunidadeId) {
	    Map<String, Object> response = new HashMap<>();

	    try {
	        response = services.buscarInfoComunidade(comunidadeId);
	        return ResponseEntity.ok(response);
	    } catch (ResourceNotFoundException e) {
	        response.put("statusCode", 404);
	        response.put("error", "Comunidade não encontrada: " + e.getMessage());
	        return ResponseEntity.status(404).body(response);
	    } catch (Exception e) {
	        response.put("statusCode", 500);
	        response.put("error", "Erro ao buscar comunidade: " + e.getMessage());
	        return ResponseEntity.status(500).body(response);
	    }
	}

	
	@GetMapping("/membros/{comunidadeId}")
	public ResponseEntity<?> buscarMembros(@PathVariable UUID comunidadeId) {
	    Map<String, Object> response = new HashMap<>();

	    try {
	        List<Map<String, Object>> membros = services.membrosComunidade(comunidadeId);
	        return ResponseEntity.ok(membros);
	        
	    } catch (ResourceNotFoundException e) {
	        response.put("statusCode", 404);
	        response.put("error", "Comunidade não encontrada: " + e.getMessage());
	        return ResponseEntity.status(404).body(response);
	        
	    } catch (Exception e) {
	        response.put("statusCode", 500);
	        response.put("error", "Erro ao buscar membros: " + e.getMessage());
	        return ResponseEntity.status(500).body(response);
	    }
	}

	
	@GetMapping("/user/{userId}")
	public ResponseEntity<?> buscarComunidadesUser(@PathVariable UUID userId) {
	    Map<String, Object> response = new HashMap<>();

	    try {
	        List<Map<String, Object>> comunidades = services.buscarComunidadesUsuario(userId);
	        return ResponseEntity.ok(comunidades);
	        
	    } catch (ResourceNotFoundException e) {
	        response.put("statusCode", 404);
	        response.put("error", "Usuário não encontrado: " + e.getMessage());
	        return ResponseEntity.status(404).body(response);
	        
	    } catch (Exception e) {
	        response.put("statusCode", 500);
	        response.put("error", "Erro ao buscar comunidades do usuário: " + e.getMessage());
	        return ResponseEntity.status(500).body(response);
	    }
	}
	
	
	
	@PatchMapping("/moderador")
	public ResponseEntity<Map<String, Object>> virarMod(@RequestBody Map<String, Object> req) {
		
		UUID adminId = UUID.fromString(req.get("adminId").toString());
		
		UUID comunidadeId = UUID.fromString(req.get("comunidadeId").toString());
		
		UUID userId = UUID.fromString(req.get("userId").toString());
		
		
		String result = services.tornarModerador(comunidadeId, userId, adminId);
		
		Map<String, Object> response = new HashMap<>();
		
        response.put("statusCode", 200);
        response.put("result", result);
		
        return ResponseEntity.ok(response);
	}
	
	
	
	@PatchMapping("/rebaixar/moderador")
	public ResponseEntity<Map<String, Object>> rebaixarMod(@RequestBody Map<String, Object> req) {
		
		UUID adminId = UUID.fromString(req.get("adminId").toString());
		
		UUID comunidadeId = UUID.fromString(req.get("comunidadeId").toString());
		
		UUID userId = UUID.fromString(req.get("userId").toString());
		
		
		String result = services.removerModerador(comunidadeId, userId, adminId);
		
		Map<String, Object> response = new HashMap<>();
		
        response.put("statusCode", 200);
        response.put("result", result);
		
        return ResponseEntity.ok(response);
	}
	

	@PutMapping("/{id}")
    public ResponseEntity<Comunidades> atualizarComunidade(@PathVariable UUID id, @RequestBody Comunidades novaComunidade) {
		
        Comunidades comunidadeAtualizada = services.atualizarComunidade(id, novaComunidade);
        
        return ResponseEntity.ok(comunidadeAtualizada);
    }
	
	
	
	@PostMapping("/fakeDelete")
	public ResponseEntity<Map<String, Object>> softDelete(@RequestBody Map<String, Object> req){
		
		UUID comunidadeId = UUID.fromString(req.get("comunidadeId").toString());
		
		Comunidades c = services.fakeDelete(comunidadeId);
		
		Map<String, Object> response = new HashMap<>();
		
        response.put("statusCode", 200);
        response.put("result", c);
		
        return ResponseEntity.ok(response);
	}
	
	
	
	@PostMapping("/reAtivar")
	public ResponseEntity<Map<String, Object>> reActive(@RequestBody Map<String, Object> req){
		
		UUID comunidadeId = UUID.fromString(req.get("comunidadeId").toString());
		
		Comunidades c = services.ActiveComunidade(comunidadeId);
		
		Map<String, Object> response = new HashMap<>();
		
        response.put("statusCode", 200);
        response.put("result", c);
		
        return ResponseEntity.ok(response);
	}
	
	
	@PostMapping("/tornaPrivado")
	public ResponseEntity<Map<String, Object>> tornarPrivado(@RequestBody Map<String, Object> req){
		
		UUID comunidadeId = UUID.fromString(req.get("comunidadeId").toString());
		
		Integer cod = (Integer) req.get("codigo");
		
				
		Comunidades c = services.mudarPrivado(comunidadeId, cod);
		
		
		Map<String, Object> response = new HashMap<>();
		
        response.put("statusCode", 200);
        response.put("result", c);
		
        return ResponseEntity.ok(response);
	}
	
	
	@PostMapping("/tornaPublico")
	public ResponseEntity<Map<String, Object>> tornarPublico(@RequestBody Map<String, Object> req){
		
		UUID comunidadeId = UUID.fromString(req.get("comunidadeId").toString());
		
				
		Comunidades c = services.mudarPublico(comunidadeId);
		
		
		Map<String, Object> response = new HashMap<>();
		
        response.put("statusCode", 200);
        response.put("result", c);
		
        return ResponseEntity.ok(response);
	}
	
	
	
	@DeleteMapping("/{comunidadeId}")
	public ResponseEntity<String> excluirComunidade(@PathVariable UUID comunidadeId, @RequestParam UUID userId) {
		
	    String resposta = services.excluirComunidade(comunidadeId, userId);
	    
	    return ResponseEntity.ok(resposta);
	}
	
	
	
	@DeleteMapping("/sair/{comunidadeId}")
	public ResponseEntity<String> sairDaComunidade(@PathVariable UUID comunidadeId, @RequestParam UUID userId) {
		
	    String resposta = services.sairDaComunidade(userId, comunidadeId);
	    
	    return ResponseEntity.ok(resposta);
	}
	
	
	
	@PutMapping("/transferir-admin/{comunidadeId}")
	public ResponseEntity<String> transferirAdmin(@PathVariable UUID comunidadeId, @RequestBody Map<String, Object> req) {
		
		UUID atualAdminId = UUID.fromString(req.get("atualAdminId").toString());
		
		UUID novoAdminId = UUID.fromString(req.get("novoAdminId").toString());
		
	    String resposta = services.transferirAdmin(comunidadeId, atualAdminId, novoAdminId);
	    
	    return ResponseEntity.ok(resposta);
	}
	
	
	
	@GetMapping("/posts/{comunidadeId}")
    public List<PostResponseDTO> getPostsDaComunidade(@PathVariable UUID comunidadeId) {
        return services.buscarPostsDaComunidade(comunidadeId);
    }
	
	
}
