package com.ssi.kernel.redes.domain;

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

import com.ssi.kernel.model.Centrais;
import com.ssi.kernel.model.Pessoas;
import com.ssi.kernel.model.Ufs;

@Entity
@Cacheable
@Table(name="Rotas", schema="redes")
public class Rotas implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8577024742564780310L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idrota")
	private Long idrota;
	
	@ManyToOne
	@JoinColumn(name="iddemanda", referencedColumnName="iddemanda")
	private DemandasRedes demanda;
	
	@ManyToOne
	@JoinColumn(name="idpessoaautor", referencedColumnName="idpessoa")
	private Pessoas autor;
	
	@ManyToOne
	@JoinColumn(name="iduforigem", referencedColumnName="iduf")
	private Ufs uforigem;
	
	@ManyToOne
	@JoinColumn(name="idcentralorigem", referencedColumnName="idcentral")
	private Centrais centralorigem;
	
	@Column(name="cnmrota")
	private String cnmrota;
	
	@Column(name="cnmdescricao")
	private String cnmdescricao;
	
	@Column(name="cnmsentidorota")
	private String cnmsentidorota;
	
	@Column(name="cnmtiporota")
	private String cnmtiporota;
	
	@ManyToOne
	@JoinColumn(name="idufdestino", referencedColumnName="iduf")
	private Ufs ufdestino;
	
	@ManyToOne
	@JoinColumn(name="idcentraldestino", referencedColumnName="idcentral")
	private Centrais centraldestino;
	
	@Column(name="datacadastro", insertable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar datacadastro;

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	 
	public Long getIdrota() {
		return idrota;
	}

	public void setIdrota(Long idrota) {
		this.idrota = idrota;
	}

	public DemandasRedes getDemanda() {
		return demanda;
	}

	public void setDemanda(DemandasRedes demanda) {
		this.demanda = demanda;
	}

	public Pessoas getAutor() {
		return autor;
	}

	public void setAutor(Pessoas autor) {
		this.autor = autor;
	}

	public Ufs getUforigem() {
		return uforigem;
	}

	public void setUforigem(Ufs uforigem) {
		this.uforigem = uforigem;
	}

	public Centrais getCentralorigem() {
		return centralorigem;
	}

	public void setCentralorigem(Centrais centralorigem) {
		this.centralorigem = centralorigem;
	}

	public String getCnmrota() {
		return cnmrota;
	}

	public void setCnmrota(String cnmrota) {
		this.cnmrota = cnmrota;
	}

	public String getCnmdescricao() {
		return cnmdescricao;
	}

	public void setCnmdescricao(String cnmdescricao) {
		this.cnmdescricao = cnmdescricao;
	}
	
	public Calendar getDatacadastro() {
		return datacadastro;
	}

	public void setDatacadastro(Calendar datacadastro) {
		this.datacadastro = datacadastro;
	}
	
	public String getCnmsentidorota() {
		return cnmsentidorota;
	}

	public void setCnmsentidorota(String cnmsentidorota) {
		this.cnmsentidorota = cnmsentidorota;
	}

	public String getCnmtiporota() {
		return cnmtiporota;
	}

	public void setCnmtiporota(String cnmtiporota) {
		this.cnmtiporota = cnmtiporota;
	}

	public Ufs getUfdestino() {
		return ufdestino;
	}

	public void setUfdestino(Ufs ufdestino) {
		this.ufdestino = ufdestino;
	}

	public Centrais getCentraldestino() {
		return centraldestino;
	}

	public void setCentraldestino(Centrais centraldestino) {
		this.centraldestino = centraldestino;
	}

	@Override
	public int hashCode() {
		return this.getIdrota() != null ? 
		this.getClass().hashCode() + this.getIdrota().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		Rotas objint = (Rotas)obj;
		
		if(this.getIdrota() != null && objint.getIdrota() != null){
			if(this.getIdrota().equals(objint.getIdrota())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

}
