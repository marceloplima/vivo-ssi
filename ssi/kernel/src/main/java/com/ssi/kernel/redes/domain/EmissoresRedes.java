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
@Table(name="EmissoresRedes", schema="redes")
public class EmissoresRedes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8428624933399425678L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idemissor")
	private Long idemissor;		
	
	@OneToOne
	@JoinColumn(name="idparticipante")
	private ParticipantesRedes participanteemissor;	

	@OneToOne
	@JoinColumn(name="idgrupomodulo")
	private GruposModulos grupoModulo;
	
	public EmissoresRedes() {
	}
	
	public Long getIdemissor() {
		return idemissor;
	}


	public void setIdemissor(Long idemissor) {
		this.idemissor = idemissor;
	}



	public ParticipantesRedes getParticipanteemissor() {
		return participanteemissor;
	}



	public void setParticipanteemissor(ParticipantesRedes participanteemissor) {
		this.participanteemissor = participanteemissor;
	}



	public GruposModulos getGrupoModulo() {
		return grupoModulo;
	}



	public void setGrupoModulo(GruposModulos grupoModulo) {
		this.grupoModulo = grupoModulo;
	}



	@Override
	public int hashCode() {
		return this.getIdemissor() != null ? 
		this.getClass().hashCode() + this.getIdemissor().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		EmissoresRedes objint = (EmissoresRedes)obj;
		
		if(this.getIdemissor() != null && objint.getIdemissor() != null){
			if(this.getIdemissor().equals(objint.getIdemissor())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}
	
}
