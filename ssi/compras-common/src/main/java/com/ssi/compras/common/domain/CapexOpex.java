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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Cacheable
@Table(name="CapexOpex", schema="compras")
public class CapexOpex implements Serializable {

	private static final long serialVersionUID = 4401339433398276753L;
	
	public static final long ID_CAPEX = 1L;
	public static final long ID_OPEX = 2L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idcapexopex")
	private Long idcapexopex;
	
	@Column(name="cnmcapexopex")
	private String cnmcapexopex;
	
	@ManyToMany(mappedBy = "capexOpex", cascade=CascadeType.ALL)
	private Set<Demandas> demandas;
	
	@OneToMany(mappedBy="capexopex", cascade = CascadeType.ALL)
	private Set<Orcamentos> orcamentos;
		
	@Transient
	private Boolean checked;
		
	public CapexOpex() {}
	
	public CapexOpex(Long idcapexopex) {
		super();
		this.idcapexopex = idcapexopex;
	}



	public Long getIdcapexopex() {
		return idcapexopex;
	}


	public void setIdcapexopex(Long idcapexopex) {
		this.idcapexopex = idcapexopex;
	}


	public String getCnmcapexopex() {
		return cnmcapexopex;
	}


	public void setCnmcapexopex(String cnmcapexopex) {
		this.cnmcapexopex = cnmcapexopex;
	}
	
		
	public Set<Demandas> getDemandas() {
		return demandas;
	}


	public void setDemandas(Set<Demandas> demandas) {
		this.demandas = demandas;
	}

	
	public Boolean getChecked() {
		if(checked==null){
			return false;
		}
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Set<Orcamentos> getOrcamentos() {
		return orcamentos;
	}

	public void setOrcamentos(Set<Orcamentos> orcamentos) {
		this.orcamentos = orcamentos;
	}

	@Override
	public int hashCode() {
		return this.getIdcapexopex() != null ? 
		this.getClass().hashCode() + this.getIdcapexopex().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		CapexOpex objint = (CapexOpex)obj;
		
		if(this.getIdcapexopex() != null && objint.getIdcapexopex() != null){
			if(this.getIdcapexopex().equals(objint.getIdcapexopex())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}
	
	
	

}
