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
@Table(name="GerentesOrcamentos", schema="compras")
public class GerentesOrcamentos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1366185092299446705L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idgerenteorcamento")
	private Long idgerenteorcamento;		
	
	@OneToOne
	@JoinColumn(name="idparticipante")
	private Participantes participantegerorca;	

	@OneToOne
	@JoinColumn(name="idgrupomodulo")
	private GruposModulos grupoModulo;
	
	@JoinColumn(name="flagcapex")
	private boolean flagcapex;
	
	@JoinColumn(name="flagopex")
	private boolean flagopex;
	
	public GerentesOrcamentos() {
	}
	
	public Long getIdgerenteorcamento() {
		return idgerenteorcamento;
	}

	public void setIdgerenteorcamento(Long idgerenteorcamento) {
		this.idgerenteorcamento = idgerenteorcamento;
	}
	
	public Participantes getParticipantegerorca() {
		return participantegerorca;
	}

	public void setParticipantegerorca(Participantes participantegerorca) {
		this.participantegerorca = participantegerorca;
	}
	
	public boolean isFlagcapex() {
		return flagcapex;
	}

	public void setFlagcapex(boolean flagcapex) {
		this.flagcapex = flagcapex;
	}

	public boolean isFlagopex() {
		return flagopex;
	}

	public void setFlagopex(boolean flagopex) {
		this.flagopex = flagopex;
	}
	
	@Override
	public int hashCode() {
		return this.getIdgerenteorcamento() != null ? 
		this.getClass().hashCode() + this.getIdgerenteorcamento().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		GerentesOrcamentos objint = (GerentesOrcamentos)obj;
		
		if(this.getIdgerenteorcamento() != null && objint.getIdgerenteorcamento() != null){
			if(this.getIdgerenteorcamento().equals(objint.getIdgerenteorcamento())){
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
