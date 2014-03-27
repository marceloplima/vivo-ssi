package com.ssi.compras.common.domain;


import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Cacheable
@Table(name="EventosEnvioAoMercado", schema="compras")
public class EventosEnvioAoMercado implements Serializable {

	private static final long serialVersionUID = 7765264803732024600L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ideventosenviosaomercado")
	private Long ideventosenviosaomercado;	
	
	@OneToOne
	@JoinColumn(name="idevento", referencedColumnName="idevento")
	private Eventos eventoem;
	
	@Column(name="dataenvio")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataenvio;
	
	public Long getIdeventosenviosaomercado() {
		return ideventosenviosaomercado;
	}

	public void setIdeventosenviosaomercado(Long ideventosenviosaomercado) {
		this.ideventosenviosaomercado = ideventosenviosaomercado;
	}
	
	public Eventos getEventoem() {
		return eventoem;
	}

	public void setEventoem(Eventos eventoem) {
		this.eventoem = eventoem;
	}

	public Calendar getDataenvio() {
		return dataenvio;
	}

	public void setDataenvio(Calendar dataenvio) {
		this.dataenvio = dataenvio;
	}
	
	@Override
	public int hashCode() {
		return this.getIdeventosenviosaomercado() != null ? 
		this.getClass().hashCode() + this.getIdeventosenviosaomercado().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		EventosEnvioAoMercado objint = (EventosEnvioAoMercado)obj;
		
		if(this.getIdeventosenviosaomercado() != null && objint.getIdeventosenviosaomercado() != null){
			if(this.getIdeventosenviosaomercado().equals(objint.getIdeventosenviosaomercado())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}
}
