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
@Table(name="CopiadosRedes", schema="redes")
public class CopiadosRedes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5699449532635988753L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idcopiado")
	private Long idcopiado;		
	
	@OneToOne
	@JoinColumn(name="idparticipante")
	private ParticipantesRedes participantecopiado;	

	@OneToOne
	@JoinColumn(name="idgrupomodulo")
	private GruposModulos grupoModulo;
	
	public CopiadosRedes() {
	}
	
	public Long getIdcopiado() {
		return idcopiado;
	}


	public void setIdcopiado(Long idcopiado) {
		this.idcopiado = idcopiado;
	}



	public ParticipantesRedes getParticipantecopiado() {
		return participantecopiado;
	}



	public void setParticipantecopiado(ParticipantesRedes participantecopiado) {
		this.participantecopiado = participantecopiado;
	}



	public GruposModulos getGrupoModulo() {
		return grupoModulo;
	}



	public void setGrupoModulo(GruposModulos grupoModulo) {
		this.grupoModulo = grupoModulo;
	}



	@Override
	public int hashCode() {
		return this.getIdcopiado() != null ? 
		this.getClass().hashCode() + this.getIdcopiado().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		CopiadosRedes objint = (CopiadosRedes)obj;
		
		if(this.getIdcopiado() != null && objint.getIdcopiado() != null){
			if(this.getIdcopiado().equals(objint.getIdcopiado())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}
	
}
