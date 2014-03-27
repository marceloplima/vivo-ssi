package com.ssi.kernel.redes.domain;

import java.io.Serializable;

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
@Table(name="RequisitadosRedes", schema="redes")
public class RequisitadosRedes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4588228707767939887L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idrequisitado")
	private Long idrequisitado;		
	
	@OneToOne
	@JoinColumn(name="idparticipante")
	private ParticipantesRedes participanterequisitado;	

	@OneToOne
	@JoinColumn(name="idgrupomodulo")
	private GruposModulos grupoModulo;
	
	public RequisitadosRedes() {
	}
	
	public Long getIdrequisitado() {
		return idrequisitado;
	}


	public void setIdrequisitado(Long idrequisitado) {
		this.idrequisitado = idrequisitado;
	}



	public ParticipantesRedes getParticipanterequisitado() {
		return participanterequisitado;
	}



	public void setParticipanterequisitado(ParticipantesRedes participanterequisitado) {
		this.participanterequisitado = participanterequisitado;
	}



	public GruposModulos getGrupoModulo() {
		return grupoModulo;
	}



	public void setGrupoModulo(GruposModulos grupoModulo) {
		this.grupoModulo = grupoModulo;
	}



	@Override
	public int hashCode() {
		return this.getIdrequisitado() != null ? 
		this.getClass().hashCode() + this.getIdrequisitado().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		RequisitadosRedes objint = (RequisitadosRedes)obj;
		
		if(this.getIdrequisitado() != null && objint.getIdrequisitado() != null){
			if(this.getIdrequisitado().equals(objint.getIdrequisitado())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}
	
}
