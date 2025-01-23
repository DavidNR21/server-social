package com.cloud.Enterprise.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.Enterprise.dto.CardSeguidoresDTO;
import com.cloud.Enterprise.dto.LoginRequestDTO;
import com.cloud.Enterprise.dto.SeguidorResponseDTO;
import com.cloud.Enterprise.dto.UsuarioRequestDTO;
import com.cloud.Enterprise.dto.UsuarioResponseDTO;
import com.cloud.Enterprise.model.Usuario;
import com.cloud.Enterprise.services.UserServices;


@RestController
@RequestMapping(value = "/api/user")
public class UserController {
	
	
	@Autowired
	private UserServices services;
	
	
	@PostMapping
	public ResponseEntity<UsuarioResponseDTO> criarUsuario(@RequestBody UsuarioRequestDTO requestDTO) {

        // Salva a entidade no banco de dados
        UsuarioResponseDTO responseDTO = services.createUser(requestDTO);
        

        // Retorna o response DTO com status 201 (Created)
        return ResponseEntity.status(201).body(responseDTO);
    }
	
	
	@GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        // Busca todos os usuários no banco de dados
        List<UsuarioResponseDTO> responseDTO = services.findAll();


        return ResponseEntity.ok(responseDTO);
    }
	
	
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioResponseDTO> findUser(@PathVariable UUID id){
		
		UsuarioResponseDTO response = services.findById(id);
		
		return ResponseEntity.status(200).body(response);
		
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<UsuarioResponseDTO> updateUsuario(@PathVariable UUID id, @RequestBody UsuarioRequestDTO req){
		
		UsuarioResponseDTO response = services.updateUser(id, req);
		
		
		return ResponseEntity.status(200).body(response);
	}
	
	
	@PostMapping("/deleteFaker/{id}")
	public ResponseEntity<UsuarioResponseDTO> deleteFaker(@PathVariable UUID id){
		
		UsuarioResponseDTO newRes = services.fakeDelete(id);
		
		return ResponseEntity.status(200).body(newRes);
		
	}
	
	
	@PostMapping("/auth")
	public ResponseEntity<UsuarioResponseDTO> login(@RequestBody LoginRequestDTO form){
		
        UsuarioResponseDTO usuario = services.fazerLogin(form.getEmail(), form.getSenha(), form.getIpAdress());

        // Retorna o usuário autenticado
        return ResponseEntity.ok(usuario);
            
	}
	
	
	@PostMapping("/reset-password")
	public ResponseEntity<Map<String, Object>> resetSenha(@RequestBody Map<String, String> req){

        if (req.get("email") == null || req.get("email").isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("statusCode", 400);
            response.put("message", "Email is required");
            response.put("type", "error");
            return ResponseEntity.status(400).body(response);
        }
		
		Usuario u = services.resetPassword(req.get("email"), req.get("senha"));
		

        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", 200);
        response.put("message", "Password reset email sent successfully.");
        response.put("type", "resetPassword");
        response.put("userName", u.getSign());
        
        return ResponseEntity.ok(response);
		
	}
	
	
	@PostMapping("/reActive")
	public ResponseEntity<UsuarioResponseDTO> reativarUser(@RequestBody Map<String, UUID> req){
		
		UUID id = req.get("user");
		
		UsuarioResponseDTO user = services.reActive(id);
		
		return ResponseEntity.status(200).body(user);
		
	}
	
	
	@DeleteMapping
	public ResponseEntity<?> deletePermanent(@RequestBody Map<String, UUID> req){
		
		UUID id_user = req.get("user");
		
		String action = services.deletePermanent(id_user);
		
		Map<String, Object> response = new HashMap<>();
        response.put("statusCode", 200);
        response.put("message", "Usuário agora está deletado permanente.");
        response.put("type", "delete");
        response.put("result", action);
		
		return ResponseEntity.ok(response);
	}
	
	
	@PostMapping("/seguir")
	public ResponseEntity<?> seguirUsers(@RequestBody Map<String, UUID> req){
		
		UUID id_seguidor = req.get("seguidor");
		UUID id_seguido = req.get("seguido");
		
		String action = services.seguir(id_seguido, id_seguidor);
		
		int status = 200;
		String msg = "Usuário agora está seguindo.";
		
		if (action != "ok") {
			status = 400;
			msg = "error";
		}
		
		Map<String, Object> response = new HashMap<>();
        response.put("statusCode", status);
        response.put("message", msg);
        response.put("type", "seguir");
        response.put("seguidor", id_seguidor);
        response.put("seguido", id_seguido);
        response.put("result", action);
		
		return ResponseEntity.ok(response);
	}
	
	
	
	@GetMapping("/seguidoresAll")
	public ResponseEntity<List<SeguidorResponseDTO>> listarSeguidores() {
		
		List<SeguidorResponseDTO> seguidores = services.findAllSeguir();
		
	    return ResponseEntity.ok(seguidores);
    }
	
	
	@GetMapping("/seguidores/{seguidorId}")
    public ResponseEntity<List<CardSeguidoresDTO>> listarSeguidoresPorSeguidorId(@PathVariable UUID seguidorId) {
        // Chama o serviço para buscar os seguidores pelo seguidorId
        List<CardSeguidoresDTO> seguidores = services.findSeguidores(seguidorId);

        // Retorna os resultados
        return ResponseEntity.ok(seguidores);
    }
	
	
	@GetMapping("/seguindo/{seguidorId}")
	public ResponseEntity<List<CardSeguidoresDTO>> findUsuariosSeguindo(@PathVariable UUID seguidorId) {
	    List<CardSeguidoresDTO> response = services.findUsuariosSeguindo(seguidorId);
	    
	    return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/static/count")
	public ResponseEntity<?> usuarioEstatisticas(@RequestBody Map<String, UUID> req){
		
		UUID user = req.get("user");
		
		Long seguidores = services.contarSeguidores(user);
		Long seguindo = services.contarSeguindo(user);
		
		Map<String, Object> response = new HashMap<>();
        response.put("statusCode", 200);
        response.put("message", "numeros do usuario");
        response.put("type", "numbers");
        response.put("Seguidores", seguidores);
        response.put("Seguindo", seguindo);
		
		return ResponseEntity.ok(response);
	}
	
	
	@DeleteMapping("/{seguidorId}/unfollow/{seguidoId}")
    public ResponseEntity<?> deixarDeSeguir(@PathVariable UUID seguidorId, @PathVariable UUID seguidoId) {
        services.deixarDeSeguir(seguidorId, seguidoId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", 200);
        response.put("message", "Deixou de seguir com sucesso.");
        response.put("type", "unfollow");
        
        return ResponseEntity.ok(response);
    }
	

}
