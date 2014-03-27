package com.ssi.compras.common.domain;


import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ssi.kernel.model.Pessoas;

@Entity
@Cacheable
@Table(name="TesteB", schema="compras")
public class TesteB implements Serializable {
	


	/**
	 * 
	 */
	private static final long serialVersionUID = -640359397103458864L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idtesteb")
	private Long idtesteb;
	
	@Column(name="cnmtesteb")
	private String cnmtesteb;
	
	@OneToOne
	@JoinColumn(name="idtestea")
	private TesteA testea;
	
	@OneToOne
	@JoinColumn(name="idpessoa")
	private Pessoas pessoa;
	
	public TesteB() {
		
	}
	
	

	@Override
	public int hashCode() {
		return this.getIdtesteb() != null ? 
		this.getClass().hashCode() + this.getIdtesteb().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		TesteB objint = (TesteB)obj;
		
		if(this.getIdtesteb() != null && objint.getIdtesteb() != null){
			if(this.getIdtesteb().equals(objint.getIdtesteb())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}



	public Long getIdtesteb() {
		return idtesteb;
	}



	public void setIdtesteb(Long idtesteb) {
		this.idtesteb = idtesteb;
	}



	public String getCnmtesteb() {
		return cnmtesteb;
	}



	public void setCnmtesteb(String cnmtesteb) {
		this.cnmtesteb = cnmtesteb;
	}



	public TesteA getTestea() {
		return testea;
	}



	public void setTestea(TesteA testea) {
		this.testea = testea;
	}



	public Pessoas getPessoa() {
		return pessoa;
	}



	public void setPessoa(Pessoas pessoa) {
		this.pessoa = pessoa;
	}
	
	
			
}
