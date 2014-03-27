package com.ssi.kernel.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Cacheable
@Table(name="FeriadosLocais")
public class FeriadosLocais implements Serializable{

	private static final long serialVersionUID = 8468840611880374588L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idferiadolocal")
	private Long idferiadolocal;
	
	@Temporal(TemporalType.DATE)
	@Column(name="data")
	private Calendar data;
	
	@OneToOne
	@JoinColumn(name="iduf")
	private Ufs ufferiado;
	
	@OneToOne
	@JoinColumn(name="idferiado")
	private Feriados feriadolocal;
				
	public FeriadosLocais() {}

	public Feriados getFeriadolocal() {
		return feriadolocal;
	}

	public void setFeriadolocal(Feriados feriadolocal) {
		this.feriadolocal = feriadolocal;
	}

	public Long getIdferiadolocal() {
		return idferiadolocal;
	}

	public void setIdferiadolocal(Long idferiadolocal) {
		this.idferiadolocal = idferiadolocal;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}
	
	public Ufs getUfferiado() {
		return ufferiado;
	}

	public void setUfferiado(Ufs ufferiado) {
		this.ufferiado = ufferiado;
	}

	@Override
	public int hashCode() {
		return this.getIdferiadolocal() != null ? 
		this.getClass().hashCode() + this.getIdferiadolocal().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		FeriadosLocais objint = (FeriadosLocais)obj;
		
		if(this.getIdferiadolocal() != null && objint.getIdferiadolocal() != null){
			if(this.getIdferiadolocal().equals(objint.getIdferiadolocal())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}	

}
