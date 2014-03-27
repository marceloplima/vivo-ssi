package com.ssi.kernel.compras.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ssi.kernel.model.Fornecedores;

@Entity
@Cacheable
@Table(name="Fornecedores_Agendamentos", schema="compras")
public class FornecedoresAgendamentos implements Serializable {

	private static final long serialVersionUID = 5242092117359831623L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idfornecedoragendamento")
	private Long idfornecedoragendamento;
	
	@ManyToOne
	@JoinColumn(name="idfornecedor", referencedColumnName="idfornecedor")		
	private Fornecedores fornecedores;
	
	@ManyToOne
	@JoinColumn(name="idagendamento", referencedColumnName="idagendamento")
	private Agendamentos agendamentos;
	
	@Column(name="nnrvaloradjudicado")
	private BigDecimal nnrvaloradjudicado;
	
	@Transient
	private boolean checked;
	
	@Transient
	private boolean checkedPersistido;
		
	public FornecedoresAgendamentos() {
		this.fornecedores = new Fornecedores();
		this.agendamentos = new Agendamentos();
		this.nnrvaloradjudicado = new BigDecimal(0);				
	}
	
	
	public FornecedoresAgendamentos(Fornecedores fornecedores,
			Agendamentos agendamentos, BigDecimal nnrvaloradjudicado,boolean checked) {
		this.fornecedores = fornecedores;
		this.agendamentos = agendamentos;
		this.nnrvaloradjudicado = nnrvaloradjudicado;
		this.checked = checked;
	}

	public Long getIdfornecedoragendamento() {
		return idfornecedoragendamento;
	}

	public void setIdfornecedoragendamento(Long idfornecedoragendamento) {
		this.idfornecedoragendamento = idfornecedoragendamento;
	}

	public Fornecedores getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(Fornecedores fornecedores) {
		this.fornecedores = fornecedores;
	}

	public Agendamentos getAgendamentos() {
		return agendamentos;
	}

	public void setAgendamentos(Agendamentos agendamentos) {
		this.agendamentos = agendamentos;
	}

	public BigDecimal getNnrvaloradjudicado() {
		return nnrvaloradjudicado;
	}

	public void setNnrvaloradjudicado(BigDecimal nnrvaloradjudicado) {
		this.nnrvaloradjudicado = nnrvaloradjudicado;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
		
	public boolean isCheckedPersistido() {
		return checkedPersistido;
	}

	public void setCheckedPersistido(boolean checkedPersistido) {
		this.checkedPersistido = checkedPersistido;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((agendamentos == null) ? 0 : agendamentos.hashCode());
		result = prime * result
				+ ((fornecedores == null) ? 0 : fornecedores.hashCode());
		result = prime
				* result
				+ ((idfornecedoragendamento == null) ? 0
						: idfornecedoragendamento.hashCode());
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
		FornecedoresAgendamentos other = (FornecedoresAgendamentos) obj;
		if (agendamentos == null) {
			if (other.agendamentos != null)
				return false;
		} else if (!agendamentos.equals(other.agendamentos))
			return false;
		if (fornecedores == null) {
			if (other.fornecedores != null)
				return false;
		} else if (!fornecedores.equals(other.fornecedores))
			return false;
		if (idfornecedoragendamento == null) {
			if (other.idfornecedoragendamento != null)
				return false;
		} else if (!idfornecedoragendamento
				.equals(other.idfornecedoragendamento))
			return false;
		return true;
	}

	
	
			
}
