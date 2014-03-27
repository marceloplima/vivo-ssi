package com.ssi.kernel.compras.domain;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Cacheable;
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
import javax.persistence.Transient;

import com.ssi.kernel.model.Pessoas;

@Entity
@Cacheable
@Table(name="Anexos", schema="compras")
public class Anexos implements Serializable {

	private static final long serialVersionUID = -3602563604165187295L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idanexo")
	private Long idanexo;
	
	@ManyToOne
	@JoinColumn(name="iddemanda", referencedColumnName="iddemanda")
	private Demandas demanda;
	
	@ManyToOne
	@JoinColumn(name="idpessoaautor", referencedColumnName="idpessoa")
	private Pessoas autor;
	
	@ManyToOne
	@JoinColumn(name="idtipoanexo", referencedColumnName="idtipoanexo")
	private TiposAnexos tipoanexo;
	
	@Column(name="datacadastro", insertable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar datacadastro;
	
	@Column(name="cnmcaminhoanexo")
	private String cnmcaminhoanexo;
	
	@Column(name="cnmversao")
	private String cnmversao;
	
	@Transient
	private boolean checked;
	
	public Anexos(){}
	
	public Long getIdanexo() {
		return idanexo;
	}
	public void setIdanexo(Long idanexo) {
		this.idanexo = idanexo;
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
	public TiposAnexos getTipoanexo() {
		return tipoanexo;
	}
	public void setTipoanexo(TiposAnexos tipoanexo) {
		this.tipoanexo = tipoanexo;
	}
	public Calendar getDatacadastro() {
		return datacadastro;
	}
	public void setDatacadastro(Calendar datacadastro) {
		this.datacadastro = datacadastro;
	}
	public String getCnmcaminhoanexo() {
		return cnmcaminhoanexo;
	}
	public void setCnmcaminhoanexo(String cnmcaminhoanexo) {
		this.cnmcaminhoanexo = cnmcaminhoanexo;
	}
	public String getCnmversao() {
		return cnmversao;
	}
	public void setCnmversao(String cnmversao) {
		this.cnmversao = cnmversao;
	}
	
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	@Override
	public int hashCode() {
		return this.getIdanexo() != null ? 
		this.getClass().hashCode() + this.getIdanexo().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		Anexos objint = (Anexos)obj;
		
		if(this.getIdanexo() != null && objint.getIdanexo() != null){
			if(this.getIdanexo().equals(objint.getIdanexo())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

}
