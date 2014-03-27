package com.ssi.compras.common.domain;


import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ssi.kernel.model.GruposModulos;

@Entity
@Cacheable
@Table(name="AnalistasContratos", schema="compras")
public class AnalistasContratos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7579467177254872538L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idanalistacontrato")
	private Long idanalistacontrato;
	
	@OneToOne
	@JoinColumn(name="idgrupomodulo")
	private GruposModulos grupoModulo;
	
	@OneToOne
	@JoinColumn(name="idparticipante")
	private Participantes participanteanalistacontrato;
	
	public AnalistasContratos(){}

	public Long getIdanalistacontrato() {
		return idanalistacontrato;
	}

	public void setIdanalistacontrato(Long idanalistacontrato) {
		this.idanalistacontrato = idanalistacontrato;
	}

	public GruposModulos getGrupoModulo() {
		return grupoModulo;
	}

	public void setGrupoModulo(GruposModulos grupoModulo) {
		this.grupoModulo = grupoModulo;
	}

	public Participantes getParticipanteanalistacontrato() {
		return participanteanalistacontrato;
	}

	public void setParticipanteanalistacontrato(
			Participantes participanteanalistacontrato) {
		this.participanteanalistacontrato = participanteanalistacontrato;
	}
	
	@Override
	public int hashCode() {
		return this.getIdanalistacontrato() != null ? 
		this.getClass().hashCode() + this.getIdanalistacontrato().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		AnalistasContratos objint = (AnalistasContratos)obj;
		
		if(this.getIdanalistacontrato() != null && objint.getIdanalistacontrato() != null){
			if(this.getIdanalistacontrato().equals(objint.getIdanalistacontrato())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}
}
