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

@Entity
@Cacheable
@Table(name="EventosGenericos", schema="compras")
public class EventosGenericos implements Serializable {

	private static final long serialVersionUID = -355358011023800996L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ideventogenerico")
	private Long ideventogenerico;
	
	@OneToOne
	@JoinColumn(name="idevento", referencedColumnName="idevento")
	private Eventos eventogenerico;
	
	@OneToOne
	@JoinColumn(name="idparecer", referencedColumnName="idparecer")
	private Pareceres parecer;

	public Long getIdeventogenerico() {
		return ideventogenerico;
	}

	public void setIdeventogenerico(Long ideventogenerico) {
		this.ideventogenerico = ideventogenerico;
	}

	public Eventos getEventogenerico() {
		return eventogenerico;
	}

	public void setEventogenerico(Eventos eventogenerico) {
		this.eventogenerico = eventogenerico;
	}

	public Pareceres getParecer() {
		return parecer;
	}

	public void setParecer(Pareceres parecer) {
		this.parecer = parecer;
	}
	
	@Override
	public int hashCode() {
		return this.getIdeventogenerico() != null ? 
		this.getClass().hashCode() + this.getIdeventogenerico().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		EventosGenericos objint = (EventosGenericos)obj;
		
		if(this.getIdeventogenerico() != null && objint.getIdeventogenerico() != null){
			if(this.getIdeventogenerico().equals(objint.getIdeventogenerico())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}
}
