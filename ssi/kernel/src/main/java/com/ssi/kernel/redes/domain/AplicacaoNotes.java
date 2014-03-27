package com.ssi.kernel.redes.domain;

import java.io.Serializable;
import java.util.HashSet;
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
@Table(name="AplicacoesNotes", schema="redes")
public class AplicacaoNotes implements Serializable {

	private static final long serialVersionUID = 3365258004551049217L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idaplicacaonotes")
	private Long idaplicacaonotes;
	
	@OneToMany(mappedBy="aplicacaoNotes", cascade = CascadeType.ALL)
	private Set<DemandasRedes> demandas;
	
	@Column(name="caminhobase")
	private String caminhobase;
	
	@Column(name="nomeagente")
	private String nomeagente;
		
	public AplicacaoNotes(){
		demandas = new HashSet<DemandasRedes>();
	}

	public Long getIdaplicacaonotes() {
		return idaplicacaonotes;
	}

	public void setIdaplicacaonotes(Long idaplicacaonotes) {
		this.idaplicacaonotes = idaplicacaonotes;
	}

	public Set<DemandasRedes> getDemandas() {
		return demandas;
	}

	public void setDemandas(Set<DemandasRedes> demandas) {
		this.demandas = demandas;
	}

	public String getCaminhobase() {
		return caminhobase;
	}

	public void setCaminhobase(String caminhobase) {
		this.caminhobase = caminhobase;
	}

	public String getNomeagente() {
		return nomeagente;
	}

	public void setNomeagente(String nomeagente) {
		this.nomeagente = nomeagente;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((idaplicacaonotes == null) ? 0 : idaplicacaonotes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AplicacaoNotes other = (AplicacaoNotes) obj;
		if (idaplicacaonotes == null) {
			if (other.idaplicacaonotes != null)
				return false;
		} else if (!idaplicacaonotes.equals(other.idaplicacaonotes))
			return false;
		return true;
	}
	
	
	
	
}
