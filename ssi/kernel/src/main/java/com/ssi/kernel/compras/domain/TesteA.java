package com.ssi.kernel.compras.domain;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Cacheable
@Table(name="TesteA", schema="compras")
public class TesteA implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 8282691495928327476L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idtestea")
	private Long idtestea;
	
	@Column(name="cnmtestea")
	private String cnmtestea;
	
	@OneToOne(mappedBy="testea", cascade=CascadeType.ALL)
	private TesteB testeb;
	
	@OneToOne(mappedBy="testeac", cascade=CascadeType.ALL)
	private TesteC testec;
	
	public TesteA() {
		if(testeb == null){
			testeb = new TesteB();
		}
	}
	
	

	@Override
	public int hashCode() {
		return this.getIdtestea() != null ? 
		this.getClass().hashCode() + this.getIdtestea().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		TesteA objint = (TesteA)obj;
		
		if(this.getIdtestea() != null && objint.getIdtestea() != null){
			if(this.getIdtestea().equals(objint.getIdtestea())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}



	public Long getIdtestea() {
		return idtestea;
	}



	public void setIdtestea(Long idtestea) {
		this.idtestea = idtestea;
	}



	public String getCnmtestea() {
		return cnmtestea;
	}



	public void setCnmtestea(String cnmtestea) {
		this.cnmtestea = cnmtestea;
	}



	public TesteB getTesteb() {
		return testeb;
	}



	public void setTesteb(TesteB testeb) {
		this.testeb = testeb;
	}
	
	
			
}
