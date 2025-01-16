package com.cloud.Enterprise.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.Enterprise.dto.LoginRequestDTO;
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
	
	
	@PostMapping("/{id}")
	public ResponseEntity<UsuarioResponseDTO> deleteFaker(@PathVariable UUID id){
		
		UsuarioResponseDTO newRes = services.fakeDelete(id);
		
		return ResponseEntity.status(200).body(newRes);
		
	}
	
	
	@PostMapping("/auth")
	public ResponseEntity<UsuarioResponseDTO> login(@RequestBody LoginRequestDTO form){
		try {
            // Executa o processo de login
            UsuarioResponseDTO usuario = services.fazerLogin(form.getEmail(), form.getSenha(), form.getIpAdress());

            // Retorna o usuário autenticado
            return ResponseEntity.ok(usuario);
            
        } catch (RuntimeException e) {
            // Retorna erro 401 (não autorizado) em caso de falha
            return ResponseEntity.status(401).body(new UsuarioResponseDTO(null, "failed", null, null, null, null, null, null, null, null, null, null, null));
        }
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

}
