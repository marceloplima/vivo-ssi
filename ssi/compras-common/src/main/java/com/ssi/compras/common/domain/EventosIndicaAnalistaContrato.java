package com.ssi.compras.common.domain;


import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ssi.kernel.model.Pessoas;

@Entity
@Cacheable
@Table(name="EventosIndicaAnalistaContrato", schema="compras")
public class EventosIndicaAnalistaContrato implements Serializable {

	private static final long serialVersionUID = 655093062929510179L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ideventoindicaanalistacontrato")
	private Long ideventoindicaanalistacontrato;
	
	@OneToOne
	@JoinColumn(name="idevento", referencedColumnName="idevento")
	private Eventos eventoindicaanalistacontrato;
	
	@ManyToOne
	@JoinColumn(name="idpessoaindicada", referencedColumnName="idpessoa")
	private Pessoas pessoaindicada;

	public EventosIndicaAnalistaContrato() {
		pessoaindicada = new Pessoas();
		eventoindicaanalistacontrato = new Eventos();
	}

	public Long getIdeventoindicaanalistacontrato() {
		return ideventoindicaanalistacontrato;
	}

	public void setIdeventoindicaanalistacontrato(
			Long ideventoindicaanalistacontrato) {
		this.ideventoindicaanalistacontrato = ideventoindicaanalistacontrato;
	}

	public Eventos getEventoindicaanalistacontrato() {
		return eventoindicaanalistacontrato;
	}

	public void setEventoindicaanalistacontrato(Eventos eventoindicaanalistacontrato) {
		this.eventoindicaanalistacontrato = eventoindicaanalistacontrato;
	}

	public Pessoas getPessoaindicada() {
		return pessoaindicada;
	}

	public void setPessoaindicada(Pessoas pessoaindicada) {
		this.pessoaindicada = pessoaindicada;
	}
	
	@Override
	public int hashCode() {
		return this.getIdeventoindicaanalistacontrato() != null ? 
		this.getClass().hashCode() + this.getIdeventoindicaanalistacontrato().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		EventosIndicaAnalistaContrato objint = (EventosIndicaAnalistaContrato)obj;
		
		if(this.getIdeventoindicaanalistacontrato() != null && objint.getIdeventoindicaanalistacontrato() != null){
			if(this.getIdeventoindicaanalistacontrato().equals(objint.getIdeventoindicaanalistacontrato())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

}
