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
@Table(name="EventosAberturasRequisicoesCompras", schema="compras")
public class EventosAberturasRequisicoesCompras implements Serializable {

	private static final long serialVersionUID = 2203769646421170813L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ideventoaberturarequisicaocompra")
	private Long ideventoaberturarequisicaocompra;

	@OneToOne
	@JoinColumn(name="idevento")
	private Eventos eventoarc;
	
	@Column(name="cnmrc")
	private String cnmrc;
			
	public EventosAberturasRequisicoesCompras() {
		eventoarc = new Eventos();
	}

	public Long getIdeventoaberturarequisicaocompra() {
		return ideventoaberturarequisicaocompra;
	}

	public void setIdeventoaberturarequisicaocompra(
			Long ideventoaberturarequisicaocompra) {
		this.ideventoaberturarequisicaocompra = ideventoaberturarequisicaocompra;
	}
	
	public Eventos getEventoarc() {
		return eventoarc;
	}

	public void setEventoarc(Eventos eventoarc) {
		this.eventoarc = eventoarc;
	}
	
	public String getCnmrc() {
		return cnmrc;
	}

	public void setCnmrc(String cnmrc) {
		this.cnmrc = cnmrc;
	}
	
	@Override
	public int hashCode() {
		return this.getIdeventoaberturarequisicaocompra() != null ? 
		this.getClass().hashCode() + this.getIdeventoaberturarequisicaocompra().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		EventosAberturasRequisicoesCompras objint = (EventosAberturasRequisicoesCompras)obj;
		
		if(this.getIdeventoaberturarequisicaocompra() != null && objint.getIdeventoaberturarequisicaocompra() != null){
			if(this.getIdeventoaberturarequisicaocompra().equals(objint.getIdeventoaberturarequisicaocompra())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

}
