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
@Table(name="EventosAnaliseGerenteCompra", schema="compras")
public class EventosAnaliseGerenteCompra implements Serializable{

	private static final long serialVersionUID = 8124157236507721774L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ideventoanalisegerentecompra")
	private Long ideventoanalisegerentecompra;
	
	@OneToOne
	@JoinColumn(name="idevento", referencedColumnName="idevento")
	private Eventos eventoagc;
	
	@OneToOne
	@JoinColumn(name="idparecer", referencedColumnName="idparecer")
	private Pareceres parecer;
	
	@ManyToOne
	@JoinColumn(name="idlp", referencedColumnName="idlp")
	private Lps lp;
	
	@ManyToOne
	@JoinColumn(name="idpessoacomprador", referencedColumnName="idpessoa")
	private Pessoas comprador;
		
	public EventosAnaliseGerenteCompra() {
		lp = new Lps();
		comprador = new Pessoas();
		parecer = new Pareceres();
	}

	public Long getIdeventoanalisegerentecompra() {
		return ideventoanalisegerentecompra;
	}

	public void setIdeventoanalisegerentecompra(
			Long ideventoanalisegerentecompra) {
		this.ideventoanalisegerentecompra = ideventoanalisegerentecompra;
	}

	public Eventos getEventoagc() {
		return eventoagc;
	}

	public void setEventoagc(Eventos eventoagc) {
		this.eventoagc = eventoagc;
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
	
	public Pessoas getComprador() {
		return comprador;
	}

	public void setComprador(Pessoas comprador) {
		this.comprador = comprador;
	}

	@Override
	public int hashCode() {
		return this.getIdeventoanalisegerentecompra() != null ? 
		this.getClass().hashCode() + this.getIdeventoanalisegerentecompra().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		EventosAnaliseGerenteCompra objint = (EventosAnaliseGerenteCompra)obj;
		
		if(this.getIdeventoanalisegerentecompra() != null && objint.getIdeventoanalisegerentecompra() != null){
			if(this.getIdeventoanalisegerentecompra().equals(objint.getIdeventoanalisegerentecompra())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

}
