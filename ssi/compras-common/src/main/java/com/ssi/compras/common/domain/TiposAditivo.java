package com.ssi.compras.common.domain;


import java.io.Serializable;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Cacheable
@Table(name="TiposAditivo", schema="compras")
public class TiposAditivo implements Serializable {
		
	private static final long serialVersionUID = -6943618046553734964L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idtipoaditivo")
	private Long idtipoaditivo;
	
	@Column(name="cnmtipoaditivo")
	private String cnmtipoaditivo;
	
	@ManyToMany(mappedBy = "tiposAditivo", cascade=CascadeType.ALL)
	private Set<Demandas> demandas;	

	@Transient
	private Boolean checked;
	
	public TiposAditivo() {
	}
		
	public Long getIdtipoaditivo() {
		return idtipoaditivo;
	}

	public void setIdtipoaditivo(Long idtipoaditivo) {
		this.idtipoaditivo = idtipoaditivo;
	}

	public String getCnmtipoaditivo() {
		return cnmtipoaditivo;
	}

	public void setCnmtipoaditivo(String cnmtipoaditivo) {
		this.cnmtipoaditivo = cnmtipoaditivo;
	}

	public Set<Demandas> getDemandas() {
		return demandas;
	}

	public void setDemandas(Set<Demandas> demandas) {
		this.demandas = demandas;
	}

	@Override
	public int hashCode() {
		return this.getIdtipoaditivo() != null ? 
		this.getClass().hashCode() + this.getIdtipoaditivo().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		TiposAditivo objint = (TiposAditivo)obj;
		
		if(this.getIdtipoaditivo() != null && objint.getIdtipoaditivo() != null){
			if(this.getIdtipoaditivo().equals(objint.getIdtipoaditivo())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	

}
