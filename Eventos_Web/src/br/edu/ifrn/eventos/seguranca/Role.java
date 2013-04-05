package br.edu.ifrn.eventos.seguranca;

import java.security.Principal;

public class Role implements Principal{
	
	private String name;
	
	 public Role(String name){  
	        this.name = name;  
	    }  
	
	@Override
	public String getName() {
		
		return name;
	}

	
}
