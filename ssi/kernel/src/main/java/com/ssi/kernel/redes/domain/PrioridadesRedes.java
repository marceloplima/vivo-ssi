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
@Table(name="PrioridadesRedes", schema="redes")
public class PrioridadesRedes implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1115618020677499975L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idprioridade")
	private Long idprioridade;
	
	@Column(name="cnmprioridade")
	private String cnmprioridade;
	
	public Long getIdprioridade() {
		return idprioridade;
	}
	public void setIdprioridade(Long idprioridade) {
		this.idprioridade = idprioridade;
	}
	public String getCnmprioridade() {
		return cnmprioridade;
	}
	public void setCnmprioridade(String cnmprioridade) {
		this.cnmprioridade = cnmprioridade;
	}
	
	@Override
	public int hashCode() {
		return this.getIdprioridade() != null ? 
		this.getClass().hashCode() + this.getIdprioridade().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		PrioridadesRedes objint = (PrioridadesRedes)obj;
		
		if(this.getIdprioridade() != null && objint.getIdprioridade() != null){
			if(this.getIdprioridade().equals(objint.getIdprioridade())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

}
