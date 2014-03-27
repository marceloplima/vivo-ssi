package com.ssi.kernel.model;

import java.util.Calendar;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Cacheable
@Table(name="LogAcesso")
public class LogAcesso implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1150856908364113412L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idlogacesso")
	private Long idlogacesso;
	
	@Column(name="cnmlogin")
	private String cnmlogin;
	
	@Column(name="cnmmodulo")
	private String cnmmodulo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="datacriado", insertable=false, updatable=false)
	private Calendar datacriado;

	public Calendar getDatacriado() {
		return datacriado;
	}

	public void setDatacriado(Calendar datacriado) {
		this.datacriado = datacriado;
	}

	public Long getIdlogacesso() {
		return idlogacesso;
	}

	public void setIdlogacesso(Long idlogacesso) {
		this.idlogacesso = idlogacesso;
	}
	
	public String getCnmlogin() {
		return cnmlogin;
	}

	public void setCnmlogin(String cnmlogin) {
		this.cnmlogin = cnmlogin;
	}

	public String getCnmmodulo() {
		return cnmmodulo;
	}

	public void setCnmmodulo(String cnmmodulo) {
		this.cnmmodulo = cnmmodulo;
	}
	
	@Override
	public int hashCode() {
		return this.idlogacesso != null ? 
		this.getClass().hashCode() + this.idlogacesso.hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		LogAcesso objint = (LogAcesso)obj;
		
		if(this.getIdlogacesso() != null && objint.getIdlogacesso() != null){
			if(this.getIdlogacesso().equals(objint.getIdlogacesso())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

}
