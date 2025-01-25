package com.cloud.Enterprise.dto;

import java.util.List;
import java.util.UUID;



public class PostRequestDTO {

    private String content;
    private List<String> media; // URLs das mídias (imagens/vídeos)
    private String rule;
    private String type;
    private UUID userId; // ID do usuário que está criando o post
    
    
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public List<String> getMedia() {
		return media;
	}
	
	public void setMedia(List<String> media) {
		this.media = media;
	}
	
	public String getRule() {
		return rule;
	}
	
	public void setRule(String rule) {
		this.rule = rule;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public UUID getUserId() {
		return userId;
	}
	
	public void setUserId(UUID userId) {
		this.userId = userId;
	}
    
}
