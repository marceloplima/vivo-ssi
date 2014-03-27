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
@Table(name="GerentesContratos", schema="compras")
public class GerentesContratos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1111945914822373626L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idgerentecontrato")
	private Long idgerentecontrato;
	
	@OneToOne
	@JoinColumn(name="idgrupomodulo")
	private GruposModulos grupoModulo;
	
	@OneToOne
	@JoinColumn(name="idparticipante")
	private Participantes participantegerentecontrato;
	
	public GerentesContratos(){}

	public Long getIdgerentecontrato() {
		return idgerentecontrato;
	}

	public void setIdgerentecontrato(Long idgerentecontrato) {
		this.idgerentecontrato = idgerentecontrato;
	}

	public GruposModulos getGrupoModulo() {
		return grupoModulo;
	}

	public void setGrupoModulo(GruposModulos grupoModulo) {
		this.grupoModulo = grupoModulo;
	}

	public Participantes getParticipantegerentecontrato() {
		return participantegerentecontrato;
	}

	public void setParticipantegerentecontrato(
			Participantes participantegerentecontrato) {
		this.participantegerentecontrato = participantegerentecontrato;
	}
	
	@Override
	public int hashCode() {
		return this.getIdgerentecontrato() != null ? 
		this.getClass().hashCode() + this.getIdgerentecontrato().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		GerentesContratos objint = (GerentesContratos)obj;
		
		if(this.getIdgerentecontrato() != null && objint.getIdgerentecontrato() != null){
			if(this.getIdgerentecontrato().equals(objint.getIdgerentecontrato())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

}
