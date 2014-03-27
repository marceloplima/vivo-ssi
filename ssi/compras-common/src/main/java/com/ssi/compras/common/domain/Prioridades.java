package com.ssi.compras.common.domain;


import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Prioridades", schema="compras")
public class Prioridades implements Serializable {

	private static final long serialVersionUID = -4742958638724839137L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idprioridade")
	private Long idprioridade;
	
	@Column(name="cnmprioridade")
	private String cnmprioridade;
	
	@OneToMany(mappedBy="prioridade", cascade = CascadeType.ALL)
	private Set<Demandas> demandas;
	
	public Prioridades() {}
		
	public Prioridades(Long idprioridade) {
		this.idprioridade = idprioridade;
	}

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
		
		Prioridades objint = (Prioridades)obj;
		
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
