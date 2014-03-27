package com.ssi.kernel.compras.domain;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Cacheable
@Table(name="TiposCompras", schema="compras")
public class TiposCompras implements Serializable{

	private static final long serialVersionUID = -8290592219146682510L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idtipocompra")
	private Long idtipocompra;
	
	@Column(name="cnmtipocompra")
	private String cnmtipocompra;
	
	public TiposCompras(){}
	
	public Long getIdtipocompra() {
		return idtipocompra;
	}
	public void setIdtipocompra(Long idtipocompra) {
		this.idtipocompra = idtipocompra;
	}
	public String getCnmtipocompra() {
		return cnmtipocompra;
	}
	public void setCnmtipocompra(String cnmtipocompra) {
		this.cnmtipocompra = cnmtipocompra;
	}
	
	public TiposCompras getByid(Long id){
		TiposCompras obj = new TiposCompras();
		obj.setIdtipocompra(id);
		return obj;
	}
	
	@Override
	public int hashCode() {
		return this.getIdtipocompra() != null ? 
		this.getClass().hashCode() + this.getIdtipocompra().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		TiposCompras objint = (TiposCompras)obj;
		
		if(this.getIdtipocompra() != null && objint.getIdtipocompra() != null){
			if(this.getIdtipocompra().equals(objint.getIdtipocompra())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

}
