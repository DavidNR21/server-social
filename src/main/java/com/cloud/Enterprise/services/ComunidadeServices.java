package com.cloud.Enterprise.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.Enterprise.constants.Permissoes;
import com.cloud.Enterprise.dto.ComunidadeRequestDTO;
import com.cloud.Enterprise.dto.PostResponseDTO;
import com.cloud.Enterprise.mapper.PostMapper;
import com.cloud.Enterprise.model.Comunidades;
import com.cloud.Enterprise.model.Membro;
import com.cloud.Enterprise.model.Post;
import com.cloud.Enterprise.model.Usuario;
import com.cloud.Enterprise.repository.ComunidadeRepository;
import com.cloud.Enterprise.repository.MembroRepository;
import com.cloud.Enterprise.repository.PostRepository;
import com.cloud.Enterprise.repository.UserRepository;
import com.cloud.Enterprise.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;


@Service
public class ComunidadeServices {

	
	@Autowired
	private ComunidadeRepository repository;
	
	
	@Autowired
	private UserRepository userRepo;
	
	
	@Autowired
	private MembroRepository membroRepo;
	
	
	@Autowired
	private PostRepository postRepo;
	
	
	// Retorna o número de seguidores de um usuário
	public Integer contarMembros(UUID comunidadeId) {
        return membroRepo.countByComunidadeId(comunidadeId);
    }
    
    
	
	// criar
	public Comunidades criarComunidade(UUID criadorId, ComunidadeRequestDTO req) {
		
		Usuario usuario = userRepo.findById(criadorId).orElseThrow(() -> new ResourceNotFoundException(criadorId));
		
		Comunidades c = new Comunidades();
		c.setNome(req.getNome());
		c.setBanner(req.getBanner());
		c.setCriadoId(criadorId);
		c.setDescricao(req.getDescricao());
		c.setLogo(req.getLogo());
		c.setRule(req.getRule());
		
		if(req.getRule() == "privado") {
			c.setCodigoDeAcesso(req.getCodigoDeAcesso());
		}
		
		
		Comunidades comunidade = repository.save(c);
		
		
		Membro m = new Membro();
		m.setComunidade(comunidade);
		m.setUsuario(usuario);
		m.setRole(Permissoes.ADMIN);
		
		membroRepo.save(m);
		
		return c;
	}
	
	
	// entrar
	public String entrar(UUID userId, UUID comunidadeId) {
		
		Integer atual_membros = contarMembros(comunidadeId);
		
		Comunidades c = repository.findById(comunidadeId).orElseThrow(() -> new ResourceNotFoundException(comunidadeId));
		
		if (c.getMax_membros() == atual_membros) {
			return "Limite Alcançado";
		}
		
		
		Optional<Membro> membro = membroRepo.findById(userId);
		
		if (membro.isPresent()) {
			
			membroRepo.delete(membro.get());
			
			return "Voçe nao faz mais parte da comunidade";
		}
		
		Usuario usuario = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(userId));
		
		
		Membro m = new Membro();
		m.setComunidade(c);
		m.setUsuario(usuario);
		m.setRole(Permissoes.MEMBRO);
		
		membroRepo.save(m);
		
		return "Bem-Vindo!";
	}
	
	
	// retorna todas
	public List<Comunidades> allComunidades(){
		
		return repository.findByActiveTrue();
	}
	
	
	
	// Método para buscar uma lista de comunidades ativas pelo nome
    public List<Map<String, Object>> buscarComunidadesAtivasPorNome(String nome) {
    	
        List<Comunidades> comunidades = repository.findByNomeContainingIgnoreCaseAndActiveTrue(nome);
        
        List<Map<String, Object>> comunidadeMaps = new ArrayList<>();

        for (Comunidades c : comunidades) {
            Map<String, Object> comunidadeMap = new HashMap<>();
            
            comunidadeMap.put("id", c.getId());
            comunidadeMap.put("nome", c.getNome());
            comunidadeMap.put("logo", c.getLogo());
            comunidadeMap.put("membros", contarMembros(c.getId()));
            comunidadeMap.put("rule", c.getRule());

            comunidadeMaps.add(comunidadeMap);
        }

        return comunidadeMaps;
    }
	
	
	// dados pelo id
	public Map<String, Object> buscarInfoComunidade(UUID id){
		
		Comunidades c = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		
		
		Map<String, Object> comunidadeMap = new HashMap<>();
		
		
		if (c.getRule() == "privado") {
			comunidadeMap.put("codigoDeAcesso", c.getCodigoDeAcesso());
		}
		
		comunidadeMap.put("id", c.getId());
		comunidadeMap.put("nome", c.getNome());
		comunidadeMap.put("logo", c.getLogo());
		comunidadeMap.put("banner", c.getBanner());
		comunidadeMap.put("descricao", c.getDescricao());
		comunidadeMap.put("membros", contarMembros(id));
		comunidadeMap.put("criado", c.getCreatedAt());
		comunidadeMap.put("rule", c.getRule());
		
		
		return comunidadeMap;
		
	}
	
	
	public List<Map<String, Object>> membrosComunidade(UUID comunidadeId) {
	    
	    List<Membro> membros = membroRepo.findByComunidadeIdAndUsuarioActiveTrue(comunidadeId);
	    
	    List<Map<String, Object>> membrosMaps = new ArrayList<>();
	    
	    for (Membro m : membros) {
	        Map<String, Object> membroMaps = new HashMap<>();
	        
	        membroMaps.put("id", m.getUsuario().getId());
	        membroMaps.put("sign", m.getUsuario().getSign());
	        membroMaps.put("role", m.getRole());
	        membroMaps.put("perfil", m.getUsuario().getFotoPerfil());

	        membrosMaps.add(membroMaps);
	    }
	    
	    return membrosMaps;  // Retornando uma LISTA de mapas
	}
	
	
	public List<Map<String, Object>> buscarComunidadesUsuario(UUID usuarioId) {
		
        List<Membro> membros = membroRepo.findByUsuarioId(usuarioId);
        
        List<Map<String, Object>> comunidadesList = new ArrayList<>();

        for (Membro m : membros) {
            Map<String, Object> comunidadeMap = new HashMap<>();
            
            comunidadeMap.put("id", m.getComunidade().getId());
            comunidadeMap.put("nome", m.getComunidade().getNome());
            comunidadeMap.put("logo", m.getComunidade().getLogo());
            comunidadeMap.put("banner", m.getComunidade().getBanner());
            comunidadeMap.put("membros", contarMembros(m.getComunidade().getId()));

            comunidadesList.add(comunidadeMap);
        }

        return comunidadesList;
    }
	
	
	@Transactional
	public String tornarModerador(UUID comunidadeId, UUID usuarioId, UUID adminId) {

	    // Verificar se o ADMIN que está promovendo tem permissão
	    Membro admin = membroRepo.findByUsuarioIdAndComunidadeId(adminId, comunidadeId).orElseThrow(() -> new ResourceNotFoundException(adminId));

	    
	    if (admin.getRole() != Permissoes.ADMIN && admin.getRole() != Permissoes.MODERADOR) {
	        throw new RuntimeException("Apenas administradores ou moderadores podem promover membros a moderadores. " + admin.getRole());
	    }

	    
	    // Buscar o membro que será promovido
	    Membro membro = membroRepo.findByUsuarioIdAndComunidadeId(usuarioId, comunidadeId).orElseThrow(() -> new ResourceNotFoundException(usuarioId));

	    
	    // Atualizar a permissão para MODERADOR
	    membro.setRole(Permissoes.MODERADOR);
	    membroRepo.save(membro);
	    
	    return "O membro: " + membro.getUsuario().getSign() + " agora é um moderador";
	}
	
	
	
	@Transactional
	public String removerModerador(UUID comunidadeId, UUID usuarioId, UUID adminId) {

	    // Verificar se o ADMIN que está removendo tem permissão
	    Membro admin = membroRepo.findByUsuarioIdAndComunidadeId(adminId, comunidadeId).orElseThrow(() -> new ResourceNotFoundException(adminId));

	    if (admin.getRole() != Permissoes.ADMIN ) {
	        throw new RuntimeException("Apenas administradores podem remover moderadores.");
	    }

	    // Buscar o membro que será rebaixado
	    Membro membro = membroRepo.findByUsuarioIdAndComunidadeId(usuarioId, comunidadeId).orElseThrow(() -> new ResourceNotFoundException(usuarioId));

	    // Verificar se o usuário é realmente um moderador antes de rebaixá-lo
	    if (membro.getRole() != Permissoes.MODERADOR) {
	        throw new RuntimeException("Este usuário não é um moderador.");
	    }

	    // Atualizar a permissão para MEMBRO
	    membro.setRole(Permissoes.MEMBRO);
	    membroRepo.save(membro);

	    return "O membro " + membro.getUsuario().getSign() + " foi rebaixado para membro comum.";
	}
	
	
	
	public Comunidades atualizarComunidade(UUID comunidadeId, Comunidades req) {
		
	    try {
	        Comunidades comunidade = repository.findById(comunidadeId).orElseThrow(() -> new ResourceNotFoundException("Comunidade não encontrada com ID: " + comunidadeId));

	        // Atualiza apenas os campos fornecidos na requisição
	        if (req.getNome() != null) comunidade.setNome(req.getNome());
	        if (req.getBanner() != null) comunidade.setBanner(req.getBanner());
	        if (req.getLogo() != null) comunidade.setLogo(req.getLogo());
	        if (req.getDescricao() != null) comunidade.setDescricao(req.getDescricao());
	        if (req.getMax_membros() != null) comunidade.setMax_membros(req.getMax_membros());
	        if (req.getRule() != null) comunidade.setRule(req.getRule());
	        if (req.getCodigoDeAcesso() != null) comunidade.setCodigoDeAcesso(req.getCodigoDeAcesso());

	        // Salva a comunidade atualizada no banco de dados
	        return repository.save(comunidade);
	        
	    } catch (EntityNotFoundException e) {
	        throw new ResourceNotFoundException("Erro ao atualizar comunidade com ID: " + comunidadeId);
	    }
	}
	
	
	
	public Comunidades fakeDelete(UUID comunidadeId) {
		
		Comunidades comunidade = repository.findById(comunidadeId).orElseThrow(() -> new ResourceNotFoundException("Comunidade não encontrada com ID: " + comunidadeId));
		
		comunidade.setActive(false);
		
		Comunidades c = repository.save(comunidade);
		
		return c;
	}
	
	
	
	public Comunidades mudarPrivado(UUID comunidadeId, Integer cod) {
		
		Comunidades comunidade = repository.findById(comunidadeId).orElseThrow(() -> new ResourceNotFoundException("Comunidade não encontrada com ID: " + comunidadeId));
		
		comunidade.setCodigoDeAcesso(cod);
		comunidade.setRule("privado");
		
		Comunidades c = repository.save(comunidade);
		
		return c;
	}
	
	
	
	public Comunidades mudarPublico(UUID comunidadeId) {
		
		Comunidades comunidade = repository.findById(comunidadeId).orElseThrow(() -> new ResourceNotFoundException("Comunidade não encontrada com ID: " + comunidadeId));
		
		comunidade.setCodigoDeAcesso(null);
		comunidade.setRule("publico");
		
		Comunidades c = repository.save(comunidade);
		
		return c;
	}
	
	
	
	@Transactional
	public String excluirComunidade(UUID comunidadeId, UUID userId) {
	    
	    // Busca a comunidade
	    Comunidades comunidade = repository.findById(comunidadeId).orElseThrow(() -> new ResourceNotFoundException(comunidadeId));

	    // Verifica se o usuário é o criador da comunidade
	    if (!comunidade.getCriadoId().equals(userId)) {
	        throw new RuntimeException("Apenas o criador pode excluir esta comunidade.");
	    }

	    // Exclui todos os membros antes de remover a comunidade
	    membroRepo.deleteByComunidadeId(comunidadeId);

	    // Exclui a comunidade
	    repository.delete(comunidade);

	    return "Comunidade excluída com sucesso.";
	}
	
	
	
	@Transactional
	public String sairDaComunidade(UUID userId, UUID comunidadeId) {
	    
	    // Busca a comunidade
	    Comunidades comunidade = repository.findById(comunidadeId).orElseThrow(() -> new ResourceNotFoundException(comunidadeId));

	    // Verifica se o usuário é o criador da comunidade
	    if (comunidade.getCriadoId().equals(userId)) {
	        throw new RuntimeException("O criador não pode sair da própria comunidade. Exclua-a ou transfira a administração.");
	    }

	    // Busca o membro na comunidade
	    Membro membro = membroRepo.findByUsuarioIdAndComunidadeId(userId, comunidadeId).orElseThrow(() -> new ResourceNotFoundException(userId));

	    // Remove o usuário da comunidade
	    membroRepo.delete(membro);

	    return "Você saiu da comunidade.";
	}
	
	
	
	@Transactional
	public String transferirAdmin(UUID comunidadeId, UUID atualAdminId, UUID novoAdminId) {
	    
	    // Busca a comunidade
	    Comunidades comunidade = repository.findById(comunidadeId).orElseThrow(() -> new ResourceNotFoundException(comunidadeId));

	    // Verifica se o usuário atual é o criador da comunidade
	    if (!comunidade.getCriadoId().equals(atualAdminId)) {
	        throw new RuntimeException("Apenas o criador da comunidade pode transferir a administração.");
	    }

	    // Busca o novo administrador na comunidade
	    Membro novoAdmin = membroRepo.findByUsuarioIdAndComunidadeId(novoAdminId, comunidadeId).orElseThrow(() -> new ResourceNotFoundException(novoAdminId));

	    // Verifica se o novo admin tem um usuário associado
	    if (novoAdmin.getUsuario() == null) {
	        throw new RuntimeException("O novo administrador não possui uma conta de usuário válida.");
	    }

	    // Atualiza a comunidade para ter um novo criador
	    comunidade.setCriadoId(novoAdminId);
	    repository.save(comunidade);

	    // Atualiza permissões
	    novoAdmin.setRole(Permissoes.ADMIN);
	    membroRepo.save(novoAdmin);

	    // O antigo admin continua na comunidade como membro normal
	    Membro antigoAdmin = membroRepo.findByUsuarioIdAndComunidadeId(atualAdminId, comunidadeId).orElseThrow(() -> new ResourceNotFoundException(atualAdminId));
	    
	    antigoAdmin.setRole(Permissoes.MEMBRO);
	    membroRepo.save(antigoAdmin);

	    return "A administração foi transferida para " + novoAdmin.getUsuario().getSign();
	}


	
	public List<PostResponseDTO> buscarPostsDaComunidade(UUID comunidadeId) {
		
		List<Post> posts = postRepo.findByComunidadeId(comunidadeId);
		
		List<PostResponseDTO> responseDTOs = posts.stream().map(PostMapper::toResponseDTO).collect(Collectors.toList());
		
		return responseDTOs;
    }
	
	
	
	public Comunidades ActiveComunidade(UUID comunidadeId) {
		
		Comunidades comunidade = repository.findById(comunidadeId).orElseThrow(() -> new ResourceNotFoundException("Comunidade não encontrada com ID: " + comunidadeId));
		
		comunidade.setActive(true);
		
		Comunidades c = repository.save(comunidade);
		
		return c;
	}
	
	
}
