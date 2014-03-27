package com.ssi.kernel.compras.domain;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Cacheable
@Table(name="EventosValidacoesTecnicas", schema="compras")
public class EventosValidacoesTecnicas implements Serializable {

	private static final long serialVersionUID = -7033081820467428093L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ideventovalidacaotecnica")
	private Long ideventovalidacaotecnica;

	@OneToOne
	@JoinColumn(name="idevento")
	private Eventos evaltec;
	
	@OneToOne
	@JoinColumn(name="idparecer", referencedColumnName="idparecer")
	private Pareceres parecer;
	
	@ManyToOne
	@JoinColumn(name="idlp", referencedColumnName="idlp")
	private Lps lp;
	
	public Long getIdeventovalidacaotecnica() {
		return ideventovalidacaotecnica;
	}

	public void setIdeventovalidacaotecnica(Long ideventovalidacaotecnica) {
		this.ideventovalidacaotecnica = ideventovalidacaotecnica;
	}

	public Eventos getEvaltec() {
		return evaltec;
	}

	public void setEvaltec(Eventos evaltec) {
		this.evaltec = evaltec;
	}

	public Pareceres getParecer() {
		return parecer;
	}

	public void setParecer(Pareceres parecer) {
		this.parecer = parecer;
	}

	public Lps getLp() {
		return lp;
	}

	public void setLp(Lps lp) {
		this.lp = lp;
	}
	
	@Override
	public int hashCode() {
		return this.getIdeventovalidacaotecnica() != null ? 
		this.getClass().hashCode() + this.getIdeventovalidacaotecnica().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		EventosValidacoesTecnicas objint = (EventosValidacoesTecnicas)obj;
		
		if(this.getIdeventovalidacaotecnica() != null && objint.getIdeventovalidacaotecnica() != null){
			if(this.getIdeventovalidacaotecnica().equals(objint.getIdeventovalidacaotecnica())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}
}
