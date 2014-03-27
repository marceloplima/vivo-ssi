package com.ssi.compras.common.domain;


import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Cacheable
@Table(name="EventoSolicitacaoRevisaoAquisicao", schema="compras")
public class EventosSolicitacaoRevisaoAquisicao implements Serializable{


	private static final long serialVersionUID = -6122921469055724786L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ideventosolicitacaorevisaoaquisicao")
	private Long ideventosolicitacaorevisaoaquisicao;
	
	@OneToOne
	@JoinColumn(name="idevento")
	private Eventos eventosolicitacaorevisaoaquisicao;
	
	@ManyToOne
	@JoinColumn(name="idstatusdemandamomentodasolicitacao", referencedColumnName="idstatus")
	private Status statusdemandamomentosolicitacao;
					
	public Long getIdeventosolicitacaorevisaoaquisicao() {
		return ideventosolicitacaorevisaoaquisicao;
	}

	public void setIdeventosolicitacaorevisaoaquisicao(
			Long ideventosolicitacaorevisaoaquisicao) {
		this.ideventosolicitacaorevisaoaquisicao = ideventosolicitacaorevisaoaquisicao;
	}

	public Eventos getEventosolicitacaorevisaoaquisicao() {
		return eventosolicitacaorevisaoaquisicao;
	}

	public void setEventosolicitacaorevisaoaquisicao(
			Eventos eventosolicitacaorevisaoaquisicao) {
		this.eventosolicitacaorevisaoaquisicao = eventosolicitacaorevisaoaquisicao;
	}

	public Status getStatusdemandamomentosolicitacao() {
		return statusdemandamomentosolicitacao;
	}

	public void setStatusdemandamomentosolicitacao(
			Status statusdemandamomentosolicitacao) {
		this.statusdemandamomentosolicitacao = statusdemandamomentosolicitacao;
	}

	@Override
	public int hashCode() {
		return this.getIdeventosolicitacaorevisaoaquisicao() != null ? 
		this.getClass().hashCode() + this.getIdeventosolicitacaorevisaoaquisicao().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		EventosSolicitacaoRevisaoAquisicao objint = (EventosSolicitacaoRevisaoAquisicao)obj;
		
		if(this.getIdeventosolicitacaorevisaoaquisicao() != null && objint.getIdeventosolicitacaorevisaoaquisicao() != null){
			if(this.getIdeventosolicitacaorevisaoaquisicao().equals(objint.getIdeventosolicitacaorevisaoaquisicao())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}
	
}
