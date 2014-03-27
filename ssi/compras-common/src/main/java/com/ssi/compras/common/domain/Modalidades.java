package com.ssi.compras.common.domain;


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
@Table(name="Modalidades", schema="compras")
public class Modalidades implements Serializable{

	private static final long serialVersionUID = 8850270817818115162L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idmodalidade")
	private Long idmodalidade;
	
	@Column(name="cnmmodalidade")
	private String cnmmodalidade;
	
	public Long getIdmodalidade() {
		return idmodalidade;
	}
	public void setIdmodalidade(Long idmodalidade) {
		this.idmodalidade = idmodalidade;
	}
	public String getCnmmodalidade() {
		return cnmmodalidade;
	}
	public void setCnmmodalidade(String cnmmodalidade) {
		this.cnmmodalidade = cnmmodalidade;
	}
	
	public Modalidades getByid(Long id){
		Modalidades obj = new Modalidades();
		obj.setIdmodalidade(id);
		return obj;
	}
	
	@Override
	public int hashCode() {
		return this.getIdmodalidade() != null ? 
		this.getClass().hashCode() + this.getIdmodalidade().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		Modalidades objint = (Modalidades)obj;
		
		if(this.getIdmodalidade() != null && objint.getIdmodalidade() != null){
			if(this.getIdmodalidade().equals(objint.getIdmodalidade())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

}
