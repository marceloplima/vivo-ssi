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
@Table(name="TiposAnexos", schema="compras")
public class TiposAnexos implements Serializable {

	private static final long serialVersionUID = 3770715998811550935L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idtipoanexo")
	private Long idtipoanexo;
	
	@Column(name="cnmtipoanexo")
	private String cnmtipoanexo;
	
	@Column(name="cnmnomearquivo")
	private String cnmnomearquivo;
	
	public TiposAnexos(){}

	public TiposAnexos getByid(Long id){
		TiposAnexos obj = new TiposAnexos();
		obj.setIdtipoanexo(id);
		return obj;
	}
	
	public Long getIdtipoanexo() {
		return idtipoanexo;
	}

	public void setIdtipoanexo(Long idtipoanexo) {
		this.idtipoanexo = idtipoanexo;
	}

	public String getCnmtipoanexo() {
		return cnmtipoanexo;
	}

	public void setCnmtipoanexo(String cnmtipoanexo) {
		this.cnmtipoanexo = cnmtipoanexo;
	}
	
	@Override
	public int hashCode() {
		return this.getIdtipoanexo() != null ? 
		this.getClass().hashCode() + this.getIdtipoanexo().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		TiposAnexos objint = (TiposAnexos)obj;
		
		if(this.getIdtipoanexo() != null && objint.getIdtipoanexo() != null){
			if(this.getIdtipoanexo().equals(objint.getIdtipoanexo())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

	public String getCnmnomearquivo() {
		return cnmnomearquivo;
	}

	public void setCnmnomearquivo(String cnmnomearquivo) {
		this.cnmnomearquivo = cnmnomearquivo;
	}

}
