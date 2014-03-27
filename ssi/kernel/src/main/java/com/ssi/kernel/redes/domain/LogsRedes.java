package com.ssi.kernel.redes.domain;

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
@Table(name="LogsRedes", schema="redes")
public class LogsRedes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1650019407744215172L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idlog")
	private Long idlog;
	
	@ManyToOne
	@JoinColumn(name="idpessoaautor", referencedColumnName="idpessoa")
	private Pessoas autor;
	
	@Column(name="datacadastro", insertable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar datacadastro;
	
	@Column(name="cnmdescricao")
	private String cnmdescricao;
	
	@ManyToOne
	@JoinColumn(name="idtipolog", referencedColumnName="idtipolog")
	private TiposLogRedes tipoLog;
	
	@ManyToOne
	@JoinColumn(name="iddemanda", referencedColumnName="iddemanda")
	private DemandasRedes demanda;

	public LogsRedes() {}
	
	
	
	public Calendar getDatacadastro() {
		return datacadastro;
	}



	public void setDatacadastro(Calendar datacadastro) {
		this.datacadastro = datacadastro;
	}



	public Long getIdlog() {
		return idlog;
	}

	public void setIdlog(Long idlog) {
		this.idlog = idlog;
	}

	public Pessoas getAutor() {
		return autor;
	}

	public void setAutor(Pessoas autor) {
		this.autor = autor;
	}

	public String getCnmdescricao() {
		return cnmdescricao;
	}

	public void setCnmdescricao(String cnmdescricao) {
		this.cnmdescricao = cnmdescricao;
	}

	public TiposLogRedes getTipoLog() {
		return tipoLog;
	}

	public void setTipoLog(TiposLogRedes tipoLog) {
		this.tipoLog = tipoLog;
	}

	public DemandasRedes getDemanda() {
		return demanda;
	}

	public void setDemanda(DemandasRedes demanda) {
		this.demanda = demanda;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((datacadastro == null) ? 0 : datacadastro.hashCode());
		result = prime * result + ((idlog == null) ? 0 : idlog.hashCode());
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
		LogsRedes other = (LogsRedes) obj;
		if (datacadastro == null) {
			if (other.datacadastro != null)
				return false;
		} else if (!datacadastro.equals(other.datacadastro))
			return false;
		if (idlog == null) {
			if (other.idlog != null)
				return false;
		} else if (!idlog.equals(other.idlog))
			return false;
		return true;
	}

	
	
}


