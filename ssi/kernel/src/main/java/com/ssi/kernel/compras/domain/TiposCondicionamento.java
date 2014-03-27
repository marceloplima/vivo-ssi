package com.ssi.kernel.compras.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Cacheable
@Table(name="TiposCondicionamento", schema="compras")
public class TiposCondicionamento implements Serializable {

	private static final long serialVersionUID = -235833884896597964L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idtipocondicionamento")
	private Long idtipocondicionamento;
	
	@Column(name="cnmtipocondicionamento")
	private String cnmtipocondicionamento;
	
	@OneToMany(mappedBy="tipoCondicionamento", cascade = CascadeType.ALL)
	private Set<Demandas> demandas;
	
	public TiposCondicionamento() {}
	
	public TiposCondicionamento(Long idtipocondicionamento) {
		this.idtipocondicionamento = idtipocondicionamento;
	}

	public Long getIdtipocondicionamento() {
		return idtipocondicionamento;
	}


	public void setIdtipocondicionamento(Long idtipocondicionamento) {
		this.idtipocondicionamento = idtipocondicionamento;
	}


	public String getCnmtipocondicionamento() {
		return cnmtipocondicionamento;
	}


	public void setCnmtipocondicionamento(String cnmtipocondicionamento) {
		this.cnmtipocondicionamento = cnmtipocondicionamento;
	}


	public Set<Demandas> getDemandas() {
		return demandas;
	}


	public void setDemandas(Set<Demandas> demandas) {
		this.demandas = demandas;
	}


	@Override
	public int hashCode() {
		return this.getIdtipocondicionamento() != null ? 
		this.getClass().hashCode() + this.getIdtipocondicionamento().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		TiposCondicionamento objint = (TiposCondicionamento)obj;
		
		if(this.getIdtipocondicionamento() != null && objint.getIdtipocondicionamento() != null){
			if(this.getIdtipocondicionamento().equals(objint.getIdtipocondicionamento())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

}
