package com.ssi.compras.common.domain;


import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ssi.kernel.model.Pessoas;

@Entity
@Table(name="RespostasQuestionamentos", schema="compras")
public class RespostasQuestionamentos implements Serializable {
	
	private static final long serialVersionUID = -1523145880683836956L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idrespostaquestionamento")
	private Long idrespostaquestionamento;
	
	@ManyToOne
	@JoinColumn(name="idautorresposta", referencedColumnName="idpessoa")
	private Pessoas autorresposta;
	
	@Column(name="datacadastro", insertable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar datacadastro;
	
	@Column(name="cnmcomentario")
	private String cnmcomentario;
		
	@ManyToOne
	@JoinColumn(name="iddemanda", referencedColumnName="iddemanda")
	private Demandas demanda;

	@Column(name="cnmanexo")
	private String cnmanexo;
	
	
	public RespostasQuestionamentos() {}


	public Long getIdrespostaquestionamento() {
		return idrespostaquestionamento;
	}


	public void setIdrespostaquestionamento(Long idrespostaquestionamento) {
		this.idrespostaquestionamento = idrespostaquestionamento;
	}


	public Pessoas getAutorresposta() {
		return autorresposta;
	}


	public void setAutorresposta(Pessoas autorresposta) {
		this.autorresposta = autorresposta;
	}


	public Calendar getDatacadastro() {
		return datacadastro;
	}


	public void setDatacadastro(Calendar datacadastro) {
		this.datacadastro = datacadastro;
	}


	public String getCnmcomentario() {
		return cnmcomentario;
	}


	public void setCnmcomentario(String cnmcomentario) {
		this.cnmcomentario = cnmcomentario;
	}


	public Demandas getDemanda() {
		return demanda;
	}


	public void setDemanda(Demandas demanda) {
		this.demanda = demanda;
	}


	public String getCnmanexo() {
		return cnmanexo;
	}


	public void setCnmanexo(String cnmanexo) {
		this.cnmanexo = cnmanexo;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((idrespostaquestionamento == null) ? 0
						: idrespostaquestionamento.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RespostasQuestionamentos other = (RespostasQuestionamentos) obj;
		if (idrespostaquestionamento == null) {
			if (other.idrespostaquestionamento != null)
				return false;
		} else if (!idrespostaquestionamento
				.equals(other.idrespostaquestionamento))
			return false;
		return true;
	}
	
	
	
	
}


