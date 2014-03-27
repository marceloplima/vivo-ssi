package com.ssi.kernel.compras.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Cacheable
@Table(name="Papeis", schema="compras")
public class Papeis implements Serializable{

	private static final long serialVersionUID = -2624094383196060555L;
	
	public static final long ID_ANALISTA_AQUISICAO = 9L;
	public static final long ID_ANALISTA_CONTRATO = 11L;
	public static final long ID_ANALISTA_ORCAMENTO = 5L;
	public static final long ID_COMPRADOR = 7L;
	public static final long ID_DIRETOR = 3L;
	public static final long ID_EMISSOR = 1L;
	public static final long ID_GERENTE_AQUISICAO = 8L;
	public static final long ID_GERENTE_COMPRA = 6L;
	public static final long ID_GERENTE_CONTRATO = 10L;
	public static final long ID_GERENTE_ORCAMENTO = 4L;
	public static final long ID_RESPONSAVEL_TECNICO = 2L;
		
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idpapel")
	private Long idpapel;
	
	@Column(name="cnmpapel")
	private String cnmpapel;
	
	@OneToMany(mappedBy="papel", fetch = FetchType.EAGER, cascade = CascadeType.ALL)	
	private Set<Participantes> participantes;

	public Papeis() {}
		
	public Papeis(Long idpapel) {
		this.idpapel = idpapel;
	}

	public Long getIdpapel() {
		return idpapel;
	}

	public void setIdpapel(Long idpapel) {
		this.idpapel = idpapel;
	}

	public String getCnmpapel() {
		return cnmpapel;
	}

	public void setCnmpapel(String cnmpapel) {
		this.cnmpapel = cnmpapel;
	}

	public Set<Participantes> getParticipantes() {
		return participantes;
	}

	public void setParticipantes(Set<Participantes> participantes) {
		this.participantes = participantes;
	}

	@Override
	public int hashCode() {
		return this.getIdpapel() != null ? 
		this.getClass().hashCode() + this.getIdpapel().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		Papeis objint = (Papeis)obj;
		
		if(this.getIdpapel() != null && objint.getIdpapel() != null){
			if(this.getIdpapel().equals(objint.getIdpapel())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}	
	

}
