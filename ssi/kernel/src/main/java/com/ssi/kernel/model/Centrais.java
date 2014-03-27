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
@Table(name="Centrais")
public class Centrais implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6972319862128228889L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idcentral")
	private Long idcentral;
	
	@ManyToOne
	@JoinColumn(name="iduf", referencedColumnName="iduf")
	private Ufs uf;
	
	@Column(name="cnmcentral")
	private String cnmcentral;
	
	@Column(name="cnmopc")
	private String cnmopc;
	
	@Column(name="cnmareaponta")
	private String cnmareaponta;
	
	@Column(name="cnmcodigoccc")
	private String cnmcodigoccc;
	
	@Column(name="cnmsigla")
	private String cnmsigla;
	
	@Column(name="cnmgrupooperadora")
	private String cnmgrupooperadora;
	
	@Column(name="cnmorigem")
	private String cnmorigem;
	
	@Column(name="cnmoperadora")
	private String cnmoperadora;

	public Long getIdcentral() {
		return idcentral;
	}

	public void setIdcentral(Long idcentral) {
		this.idcentral = idcentral;
	}

	public Ufs getUf() {
		return uf;
	}

	public void setUf(Ufs uf) {
		this.uf = uf;
	}

	public String getCnmcentral() {
		return cnmcentral;
	}

	public void setCnmcentral(String cnmcentral) {
		this.cnmcentral = cnmcentral;
	}

	public String getCnmopc() {
		return cnmopc;
	}

	public void setCnmopc(String cnmopc) {
		this.cnmopc = cnmopc;
	}

	public String getCnmareaponta() {
		return cnmareaponta;
	}

	public void setCnmareaponta(String cnmareaponta) {
		this.cnmareaponta = cnmareaponta;
	}

	public String getCnmcodigoccc() {
		return cnmcodigoccc;
	}

	public void setCnmcodigoccc(String cnmcodigoccc) {
		this.cnmcodigoccc = cnmcodigoccc;
	}
	
	public String getCnmsigla() {
		return cnmsigla;
	}

	public void setCnmsigla(String cnmsigla) {
		this.cnmsigla = cnmsigla;
	}

	public String getCnmgrupooperadora() {
		return cnmgrupooperadora;
	}

	public void setCnmgrupooperadora(String cnmgrupooperadora) {
		this.cnmgrupooperadora = cnmgrupooperadora;
	}

	public String getCnmorigem() {
		return cnmorigem;
	}

	public void setCnmorigem(String cnmorigem) {
		this.cnmorigem = cnmorigem;
	}
	
	public String getCnmoperadora() {
		return cnmoperadora;
	}

	public void setCnmoperadora(String cnmoperadora) {
		this.cnmoperadora = cnmoperadora;
	}
	
	@Override
	public int hashCode() {
		return this.getIdcentral() != null ? 
		this.getClass().hashCode() + this.getIdcentral().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		Centrais objint = (Centrais)obj;
		
		if(this.getIdcentral() != null && objint.getIdcentral() != null){
			if(this.getIdcentral().equals(objint.getIdcentral())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

}
