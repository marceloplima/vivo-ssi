package com.ssi.kernel.compras.domain;

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
@Table(name="TesteC", schema="compras")
public class TesteC implements Serializable {
	


	/**
	 * 
	 */
	private static final long serialVersionUID = -640359397103458864L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idtestec")
	private Long idtestec;
	
	@Column(name="cnmtestec")
	private String cnmtestec;
	
	@OneToOne
	@JoinColumn(name="idtestea")
	private TesteA testeac;
	
	@OneToOne
	@JoinColumn(name="idpessoa")
	private Pessoas pessoa;
	
	public TesteC() {
		
	}
	
	

	@Override
	public int hashCode() {
		return this.getIdtestec() != null ? 
		this.getClass().hashCode() + this.getIdtestec().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		TesteC objint = (TesteC)obj;
		
		if(this.getIdtestec() != null && objint.getIdtestec() != null){
			if(this.getIdtestec().equals(objint.getIdtestec())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}



	public Long getIdtestec() {
		return idtestec;
	}



	public void setIdtestec(Long idtestec) {
		this.idtestec = idtestec;
	}



	public String getCnmtestec() {
		return cnmtestec;
	}



	public void setCnmtestec(String cnmtestec) {
		this.cnmtestec = cnmtestec;
	}



	public TesteA getTesteac() {
		return testeac;
	}



	public void setTesteac(TesteA testeac) {
		this.testeac = testeac;
	}



	public Pessoas getPessoa() {
		return pessoa;
	}



	public void setPessoa(Pessoas pessoa) {
		this.pessoa = pessoa;
	}
	
	
			
}
