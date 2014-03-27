package com.ssi.kernel.compras.domain;

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
@Table(name="Diretores", schema="compras")
public class Diretores implements Serializable {


	private static final long serialVersionUID = -5333516386431346725L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="iddiretor")
	private Long iddiretor;		
	
	@OneToOne
	@JoinColumn(name="idparticipante")
	private Participantes participantediretor;	

	@OneToOne
	@JoinColumn(name="idgrupomodulo")
	private GruposModulos grupoModulo;
	
	public Diretores() {
	}

	public Participantes getParticipantediretor() {
		return participantediretor;
	}

	public void setParticipantediretor(Participantes participantediretor) {
		this.participantediretor = participantediretor;
	}

	public Long getIddiretor() {
		return iddiretor;
	}

	public void setIddiretor(Long iddiretor) {
		this.iddiretor = iddiretor;
	}
	
	@Override
	public int hashCode() {
		return this.getIddiretor() != null ? 
		this.getClass().hashCode() + this.getIddiretor().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		Diretores objint = (Diretores)obj;
		
		if(this.getIddiretor() != null && objint.getIddiretor() != null){
			if(this.getIddiretor().equals(objint.getIddiretor())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

	public GruposModulos getGrupoModulo() {
		return grupoModulo;
	}

	public void setGrupoModulo(GruposModulos grupoModulo) {
		this.grupoModulo = grupoModulo;
	}
	
}
