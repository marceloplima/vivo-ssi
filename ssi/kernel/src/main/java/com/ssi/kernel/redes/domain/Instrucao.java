package com.ssi.kernel.redes.domain;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Cacheable
@Table(name="Instrucao", schema="redes")
public class Instrucao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6259992997934130216L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idinstrucao")
	private Long idinstrucao;
	
	@Column(name="cnminstrucao")
	private String cnminstrucao;
	
	public Long getIdinstrucao() {
		return idinstrucao;
	}
	public void setIdinstrucao(Long idinstrucao) {
		this.idinstrucao = idinstrucao;
	}
	public String getCnminstrucao() {
		return cnminstrucao;
	}
	public void setCnminstrucao(String cnminstrucao) {
		this.cnminstrucao = cnminstrucao;
	}
	
	@Override
	public int hashCode() {
		return this.getIdinstrucao() != null ? 
		this.getClass().hashCode() + this.getIdinstrucao().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		Instrucao objint = (Instrucao)obj;
		
		if(this.getIdinstrucao() != null && objint.getIdinstrucao() != null){
			if(this.getIdinstrucao().equals(objint.getIdinstrucao())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

}
