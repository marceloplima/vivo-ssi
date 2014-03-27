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

import com.ssi.kernel.model.Pessoas;

@Entity
@Cacheable
@Table(name="EventosIndicaNovoCompradores", schema="compras")
public class EventosIndicaNovoCompradores implements Serializable {
	
	private static final long serialVersionUID = 2178875499094469974L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ideventoindicanovocomprador")
	private Long ideventoindicanovocomprador;
	
	@OneToOne
	@JoinColumn(name="idevento")
	private Eventos indicanovocomp;
	
	@ManyToOne

	@JoinColumn(name="idpessoanovocomprador", referencedColumnName="idpessoa")
	private Pessoas novocomprador;
		
	public EventosIndicaNovoCompradores() {
		novocomprador = new Pessoas();
		indicanovocomp = new Eventos();
	}

	public Long getIdeventoindicanovocomprador() {
		return ideventoindicanovocomprador;
	}

	public void setIdeventoindicanovocomprador(
			Long ideventoindicanovocomprador) {
		this.ideventoindicanovocomprador = ideventoindicanovocomprador;
	}

	public Eventos getIndicanovocomp() {
		return indicanovocomp;
	}

	public void setIndicanovocomp(Eventos indicanovocomp) {
		this.indicanovocomp = indicanovocomp;
	}
			
	public Pessoas getNovocomprador() {
		return novocomprador;
	}

	public void setNovocomprador(Pessoas novocomprador) {
		this.novocomprador = novocomprador;
	}
	
	@Override
	public int hashCode() {
		return this.getIdeventoindicanovocomprador() != null ? 
		this.getClass().hashCode() + this.getIdeventoindicanovocomprador().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		EventosIndicaNovoCompradores objint = (EventosIndicaNovoCompradores)obj;
		
		if(this.getIdeventoindicanovocomprador() != null && objint.getIdeventoindicanovocomprador() != null){
			if(this.getIdeventoindicanovocomprador().equals(objint.getIdeventoindicanovocomprador())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

}
