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

@Entity
@Cacheable
@Table(name="EventosAnaliseMinuta", schema="compras")
public class EventosAnaliseMinuta implements Serializable {

	private static final long serialVersionUID = -2141699045547292240L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ideventoanaliseminuta")
	private Long ideventoanaliseminuta;
	
	@OneToOne
	@JoinColumn(name="idevento", referencedColumnName="idevento")
	private Eventos eventoam;
	
	@OneToOne
	@JoinColumn(name="idparecer", referencedColumnName="idparecer")
	private Pareceres parecer;
	
	@Column(name="cnmversao")
	private String cnmversao;
	
	public EventosAnaliseMinuta(){}
	
	public Long getIdeventoanaliseminuta() {
		return ideventoanaliseminuta;
	}
	public void setIdeventoanaliseminuta(Long ideventoanaliseminuta) {
		this.ideventoanaliseminuta = ideventoanaliseminuta;
	}
	public Eventos getEventoam() {
		return eventoam;
	}
	public void setEventoam(Eventos eventoam) {
		this.eventoam = eventoam;
	}
	public Pareceres getParecer() {
		return parecer;
	}
	public void setParecer(Pareceres parecer) {
		this.parecer = parecer;
	}
	public String getCnmversao() {
		return cnmversao;
	}
	public void setCnmversao(String cnmversao) {
		this.cnmversao = cnmversao;
	}
	
	@Override
	public int hashCode() {
		return this.getIdeventoanaliseminuta() != null ? 
		this.getClass().hashCode() + this.getIdeventoanaliseminuta().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		EventosAnaliseMinuta objint = (EventosAnaliseMinuta)obj;
		
		if(this.getIdeventoanaliseminuta() != null && objint.getIdeventoanaliseminuta() != null){
			if(this.getIdeventoanaliseminuta().equals(objint.getIdeventoanaliseminuta())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

}
