package com.ssi.kernel.model;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Cacheable
@Table(name="Faturamentos")
public class Faturamentos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2349528467418690491L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idfaturamento")
	private Long idfaturamento;
	
	@ManyToOne
	@JoinColumn(name="idcentral", referencedColumnName="idcentral")
	private Centrais central;
	
	@ManyToOne
	@JoinColumn(name="idatividade", referencedColumnName="idatividade")
	private Atividades atividade;
	
	@Column(name="cnmcentral")
	private String cnmcentral;

	public Long getIdfaturamento() {
		return idfaturamento;
	}

	public void setIdfaturamento(Long idfaturamento) {
		this.idfaturamento = idfaturamento;
	}

	public Centrais getCentral() {
		return central;
	}

	public void setCentral(Centrais central) {
		this.central = central;
	}

	public Atividades getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividades atividade) {
		this.atividade = atividade;
	}

	public String getCnmcentral() {
		return cnmcentral;
	}

	public void setCnmcentral(String cnmcentral) {
		this.cnmcentral = cnmcentral;
	}

}
