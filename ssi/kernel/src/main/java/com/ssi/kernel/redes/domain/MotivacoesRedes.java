package com.ssi.kernel.redes.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
@Table(name="MotivacoesRedes", schema="redes")
public class MotivacoesRedes implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -562507234629751069L;
	
			
			
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idmotivacao")
	private Long idmotivacao;
	
	@Column(name="cnmmotivacao")
	private String cnmmotivacao;
	
	@OneToMany(mappedBy="motivacao", cascade = CascadeType.ALL)
	private Set<DemandasRedes> demandas;

	public MotivacoesRedes() {}

	public Long getIdmotivacao() {
		return idmotivacao;
	}

	public void setIdmotivacao(Long idmotivacao) {
		this.idmotivacao = idmotivacao;
	}
	
	public String getCnmmotivacao() {
		return cnmmotivacao;
	}

	public void setCnmmotivacao(String cnmmotivacao) {
		this.cnmmotivacao = cnmmotivacao;
	}
	
	public List<DemandasRedes> getDemandas() {
		return new ArrayList<DemandasRedes>(demandas);
	}

	public void setDemandas(Set<DemandasRedes> demandas) {
		this.demandas = demandas;
	}


	@Override
	public int hashCode() {
		return this.getIdmotivacao() != null ? 
		this.getClass().hashCode() + this.getIdmotivacao().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		MotivacoesRedes objint = (MotivacoesRedes)obj;
		
		if(this.getIdmotivacao() != null && objint.getIdmotivacao() != null){
			if(this.getIdmotivacao().equals(objint.getIdmotivacao())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

}
