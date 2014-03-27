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
@Table(name="ResponsaveisTecnicos", schema="compras")
public class ResponsaveisTecnicos implements Serializable {

	private static final long serialVersionUID = -5693817028214986711L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idresponsaveltecnico")
	private Long idresponsaveltecnico;
	
	@OneToOne
	@JoinColumn(name="idparticipante")
	private Participantes participante;	
	
	@OneToOne
	@JoinColumn(name="idgrupomodulo")
	private GruposModulos grupoModulo;
	
	public ResponsaveisTecnicos() {}

	public Long getIdresponsaveltecnico() {
		return idresponsaveltecnico;
	}

	public void setIdresponsaveltecnico(Long idresponsaveltecnico) {
		this.idresponsaveltecnico = idresponsaveltecnico;
	}

	public Participantes getParticipante() {
		return participante;
	}

	public void setParticipante(Participantes participante) {
		this.participante = participante;
	}

	public GruposModulos getGrupoModulo() {
		return grupoModulo;
	}

	public void setGrupoModulo(GruposModulos grupoModulo) {
		this.grupoModulo = grupoModulo;
	}
	
	@Override
	public int hashCode() {
		return this.getIdresponsaveltecnico() != null ? 
		this.getClass().hashCode() + this.getIdresponsaveltecnico().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		ResponsaveisTecnicos objint = (ResponsaveisTecnicos)obj;
		
		if(this.getIdresponsaveltecnico() != null && objint.getIdresponsaveltecnico() != null){
			if(this.getIdresponsaveltecnico().equals(objint.getIdresponsaveltecnico())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

}
