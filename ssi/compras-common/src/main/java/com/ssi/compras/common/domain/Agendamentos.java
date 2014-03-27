package com.ssi.compras.common.domain;


import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.ssi.kernel.model.Pessoas;

@Entity
@Cacheable
@Table(name="Agendamentos", schema="compras")
public class Agendamentos implements Serializable {
	
	private static final long serialVersionUID = -1262226245679837106L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idagendamento")
	private Long idagendamento;
	
	@ManyToOne
	@JoinColumn(name="idpessoaautor", referencedColumnName="idpessoa")
	private Pessoas autor;
	
	@Column(name="datacadastro", insertable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar datacadastro;
	
	@ManyToOne
	@JoinColumn(name="idstatus", referencedColumnName="idstatus")
	private Status status;
	
	@Column(name="datamesa")
	private Calendar datamesa;
		
	@Column(name="nnrhoramesa")
	private int nnrhoramesa;
	
	@Column(name="nnrminutomesa")
	private int nnrminutomesa;	
		
	@Column(name="cnmnumeroacm")
	private String cnmnumeroacm;
	
	@Column(name="cnmobjeto")
	private String cnmobjeto;
	
	@Column(name="cnmcomentario")
	private String cnmcomentario;

	@Column(name="cnmcomentarioresultado")
	private String cnmcomentarioresultado;
	
	@Column(name="boolmesarealizada")
	private Boolean boolmesarealizada;
	
	@Column(name="boolhouveadjudicacao")
	private Boolean boolhouveadjudicacao;

	@Column(name="booladjudicacao")
	private Boolean booladjudicacao;
	
	@ManyToOne
	@JoinColumn(name="iddemanda", referencedColumnName="iddemanda")
	private Demandas demanda;
	
	@OneToMany(mappedBy="agendamentos")
	private Set<FornecedoresAgendamentos> agendamentosfornecedores;
	
	@Transient
	private boolean checked;
	
	public Agendamentos() {
		demanda = new Demandas();
		agendamentosfornecedores = new HashSet<FornecedoresAgendamentos>();
	}

	public Long getIdagendamento() {
		return idagendamento;
	}

	public void setIdagendamento(Long idagendamento) {
		this.idagendamento = idagendamento;
	}

	public Pessoas getAutor() {
		return autor;
	}

	public void setAutor(Pessoas autor) {
		this.autor = autor;
	}

	public Calendar getDatacadastro() {
		return datacadastro;
	}

	public void setDatacadastro(Calendar datacadastro) {
		this.datacadastro = datacadastro;
	}

	public Calendar getDatamesa() {
		return datamesa;
	}

	public void setDatamesa(Calendar datamesa) {
		this.datamesa = datamesa;
	}

	public String getCnmnumeroacm() {
		return cnmnumeroacm;
	}

	public void setCnmnumeroacm(String cnmnumeroacm) {
		this.cnmnumeroacm = cnmnumeroacm;
	}

	public String getCnmobjeto() {
		return cnmobjeto;
	}

	public void setCnmobjeto(String cnmobjeto) {
		this.cnmobjeto = cnmobjeto;
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
			
	public int getNnrhoramesa() {
		return nnrhoramesa;
	}

	public void setNnrhoramesa(int nnrhoramesa) {
		this.nnrhoramesa = nnrhoramesa;
	}

	public int getNnrminutomesa() {
		return nnrminutomesa;
	}

	public void setNnrminutomesa(int nnrminutomesa) {
		this.nnrminutomesa = nnrminutomesa;
	}
		
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public String getCnmcomentarioresultado() {
		return cnmcomentarioresultado;
	}

	public void setCnmcomentarioresultado(String cnmcomentarioresultado) {
		this.cnmcomentarioresultado = cnmcomentarioresultado;
	}
		
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	public Boolean getBoolmesarealizada() {
		return boolmesarealizada;
	}

	public void setBoolmesarealizada(Boolean boolmesarealizada) {
		this.boolmesarealizada = boolmesarealizada;
	}

	public Boolean getBoolhouveadjudicacao() {
		return boolhouveadjudicacao;
	}

	public void setBoolhouveadjudicacao(Boolean boolhouveadjudicacao) {
		this.boolhouveadjudicacao = boolhouveadjudicacao;
	}

	public Boolean getBooladjudicacao() {
		return booladjudicacao;
	}

	public void setBooladjudicacao(Boolean booladjudicacao) {
		this.booladjudicacao = booladjudicacao;
	}
		
	public Set<FornecedoresAgendamentos> getAgendamentosfornecedores() {
		return agendamentosfornecedores;
	}

	public void setAgendamentosfornecedores(
			Set<FornecedoresAgendamentos> agendamentosfornecedores) {
		this.agendamentosfornecedores = agendamentosfornecedores;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((datacadastro == null) ? 0 : datacadastro.hashCode());
		result = prime * result
				+ ((idagendamento == null) ? 0 : idagendamento.hashCode());
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
		Agendamentos other = (Agendamentos) obj;
		if (datacadastro == null) {
			if (other.datacadastro != null)
				return false;
		} else if (!datacadastro.equals(other.datacadastro))
			return false;
		if (idagendamento == null) {
			if (other.idagendamento != null)
				return false;
		} else if (!idagendamento.equals(other.idagendamento))
			return false;
		return true;
	}

	
	
}
