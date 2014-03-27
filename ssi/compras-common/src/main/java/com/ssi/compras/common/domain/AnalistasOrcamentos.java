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
@Table(name="AnalistasOrcamentos", schema="compras")
public class AnalistasOrcamentos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6036225453169902572L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idanalistaorcamento")
	private Long idanalistaorcamento;		
	
	@OneToOne
	@JoinColumn(name="idparticipante")
	private Participantes participanteanalorca;	

	@OneToOne
	@JoinColumn(name="idgrupomodulo")
	private GruposModulos grupoModulo;
	
	@JoinColumn(name="flagcapex")
	private boolean flagcapex;
	
	@JoinColumn(name="flagopex")
	private boolean flagopex;
	
	public AnalistasOrcamentos() {
	}
	
	public Long getIdanalistaorcamento() {
		return idanalistaorcamento;
	}

	public void setIdanalistaorcamento(Long idanalistaorcamento) {
		this.idanalistaorcamento = idanalistaorcamento;
	}
	
	public Participantes getParticipanteanalorca() {
		return participanteanalorca;
	}

	public void setParticipanteanalorca(Participantes participanteanalorca) {
		this.participanteanalorca = participanteanalorca;
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
		return this.getIdanalistaorcamento() != null ? 
		this.getClass().hashCode() + this.getIdanalistaorcamento().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		AnalistasOrcamentos objint = (AnalistasOrcamentos)obj;
		
		if(this.getIdanalistaorcamento() != null && objint.getIdanalistaorcamento() != null){
			if(this.getIdanalistaorcamento().equals(objint.getIdanalistaorcamento())){
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
