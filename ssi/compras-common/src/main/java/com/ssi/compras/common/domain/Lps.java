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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Cacheable
@Table(name="LPS", schema="compras")
public class Lps implements Serializable {
	
	private static final long serialVersionUID = 8287867879946870887L;
	
	public static final long ID_LP1_EQUIPAMENTOS_E_MATERIAIS = 1L;
	public static final long ID_LP2_SERVICOS = 2L;
	public static final long ID_LP3_PRODUTO_DO_MERCADO = 3L;
	public static final long ID_LP4_SISTEMA_DE_INFORMACAO = 4L;
	public static final long ID_LP2_OBRA_E_INFRA = 5L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idlp")
	private Long idlp;
	
	@Column(name="cnmlp")
	private String cnmlp;
	
	@OneToMany(mappedBy="lp", cascade = CascadeType.ALL)
	private Set<Demandas> demandas;
		
	public Lps() {
		
	}
			
	public Lps(Long idlp) {
		this.idlp = idlp;
	}

	public Long getIdlp() {
		return idlp;
	}
	
	public void setIdlps(Long idlps) {
		this.idlp = idlps;
	}
	
	public String getCnmlp() {
		return cnmlp;
	}
	
	public void setCnmlp(String cnmlp) {
		this.cnmlp = cnmlp;
	}
	
	public Set<Demandas> getDemandas() {
		return demandas;
	}

	public void setDemandas(Set<Demandas> demandas) {
		this.demandas = demandas;
	}

	@Override
	public int hashCode() {
		return this.getIdlp() != null ? 
		this.getClass().hashCode() + this.getIdlp().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		Lps objint = (Lps)obj;
		
		if(this.getIdlp() != null && objint.getIdlp() != null){
			if(this.getIdlp().equals(objint.getIdlp())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}
	
	
			
}
