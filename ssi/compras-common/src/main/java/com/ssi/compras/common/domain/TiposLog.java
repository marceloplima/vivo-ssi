package com.ssi.compras.common.domain;


import java.io.Serializable;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Cacheable
@Table(name="TiposLog", schema="compras")
public class TiposLog implements Serializable {

	private static final long serialVersionUID = -7885592647371476560L;
	
	public static final long ID_DEMANDA = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idtipolog")
	private Long idtipolog;
	
	@Column(name="cnmTipoLog")
	private String cnmTipoLog;
	
	@OneToMany(mappedBy="tipoLog", cascade = CascadeType.ALL)
	private Set<Logs> logs;
	
	public TiposLog() {}
		
	public TiposLog(Long idtipolog) {
		this.idtipolog = idtipolog;
	}

	public Long getIdtipolog() {
		return idtipolog;
	}

	public void setIdtipolog(Long idtipolog) {
		this.idtipolog = idtipolog;
	}

	public String getCnmTipoLog() {
		return cnmTipoLog;
	}

	public void setCnmTipoLog(String cnmTipoLog) {
		this.cnmTipoLog = cnmTipoLog;
	}

	public Set<Logs> getLogs() {
		return logs;
	}

	public void setLogs(Set<Logs> logs) {
		this.logs = logs;
	}

	@Override
	public int hashCode() {
		return this.getIdtipolog() != null ? 
		this.getClass().hashCode() + this.getIdtipolog().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		TiposLog objint = (TiposLog)obj;
		
		if(this.getIdtipolog() != null && objint.getIdtipolog() != null){
			if(this.getIdtipolog().equals(objint.getIdtipolog())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

}
