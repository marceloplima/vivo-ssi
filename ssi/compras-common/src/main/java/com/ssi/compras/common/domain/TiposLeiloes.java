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
@Table(name="TiposLeiloes", schema="compras")
public class TiposLeiloes implements Serializable {

	private static final long serialVersionUID = 2810797365952343960L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idtipoleilao")
	private Long idtipoleilao;
	
	@Column(name="cnmtipoleilao")
	private String cnmtipoleilao;
	

	public TiposLeiloes(){}
	
	public TiposLeiloes getByid(Long id){
		TiposLeiloes obj = new TiposLeiloes();
		obj.setIdtipoleilao(id);
		return obj;
	}
	
	public Long getIdtipoleilao() {
		return idtipoleilao;
	}

	public void setIdtipoleilao(Long idtipoleilao) {
		this.idtipoleilao = idtipoleilao;
	}

	public String getCnmtipoleilao() {
		return cnmtipoleilao;
	}

	public void setCnmtipoleilao(String cnmtipoleilao) {
		this.cnmtipoleilao = cnmtipoleilao;
	}
	
	@Override
	public int hashCode() {
		return this.getIdtipoleilao() != null ? 
		this.getClass().hashCode() + this.getIdtipoleilao().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		TiposLeiloes objint = (TiposLeiloes)obj;
		
		if(this.getIdtipoleilao() != null && objint.getIdtipoleilao() != null){
			if(this.getIdtipoleilao().equals(objint.getIdtipoleilao())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

}
