package com.ssi.compras.common.domain;


import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.ssi.kernel.model.Fornecedores;
import com.ssi.kernel.model.Pessoas;

@Entity
@Cacheable
@Table(name="Propostas", schema="compras")
public class Propostas implements Serializable {

	private static final long serialVersionUID = -1940571455736178978L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idproposta")
	private Long idproposta;
	
	@ManyToOne
	@JoinColumn(name="iddemanda", referencedColumnName="iddemanda")
	private Demandas demanda;
	
	@ManyToOne
	@JoinColumn(name="idpessoaautor", referencedColumnName="idpessoa")
	private Pessoas autor;
	
	@Column(name="datacadastro", insertable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar datacadastro;
	
	@ManyToOne
	@JoinColumn(name="idstatus", referencedColumnName="idstatus")
	private Status status;
	
	@Column(name="cnmcomentario")
	private String cnmcomentario;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="idfornecedor", referencedColumnName="idfornecedor")
	private Fornecedores fornecedorprop;
	
	@ManyToMany(mappedBy="proppart", cascade=CascadeType.REFRESH)
	private List<Leiloes> leiloesproppart;
	
	@ManyToMany(mappedBy="propvenc", cascade=CascadeType.REFRESH)
	private List<Leiloes> leiloespropvenc;

	@Transient
	private boolean checked;
	
	@Version
	@Transient
	private Long version;
	
	public Long getIdproposta() {
		return idproposta;
	}

	public void setIdproposta(Long idproposta) {
		this.idproposta = idproposta;
	}

	public Demandas getDemanda() {
		return demanda;
	}

	public void setDemanda(Demandas demanda) {
		this.demanda = demanda;
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getCnmcomentario() {
		return cnmcomentario;
	}

	public void setCnmcomentario(String cnmcomentario) {
		this.cnmcomentario = cnmcomentario;
	}

	public Fornecedores getFornecedorprop() {
		return fornecedorprop;
	}

	public void setFornecedorprop(Fornecedores fornecedorprop) {
		this.fornecedorprop = fornecedorprop;
	}
	
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	@Override
	public int hashCode() {
		return this.getIdproposta() != null ? 
		this.getClass().hashCode() + this.getIdproposta().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		Propostas objint = (Propostas)obj;
		
		if(this.getIdproposta() != null && objint.getIdproposta() != null){
			if(this.getIdproposta().equals(objint.getIdproposta())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

	public List<Leiloes> getLeiloesproppart() {
		return leiloesproppart;
	}

	public void setLeiloesproppart(List<Leiloes> leiloesproppart) {
		this.leiloesproppart = leiloesproppart;
	}

	public List<Leiloes> getLeiloespropvenc() {
		return leiloespropvenc;
	}

	public void setLeiloespropvenc(List<Leiloes> leiloespropvenc) {
		this.leiloespropvenc = leiloespropvenc;
	}

}
