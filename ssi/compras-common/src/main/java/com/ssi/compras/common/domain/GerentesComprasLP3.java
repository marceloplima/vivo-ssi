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

import com.ssi.kernel.model.GruposModulos;

@Entity
@Cacheable
@Table(name="GerentesComprasLP3", schema="compras")
public class GerentesComprasLP3 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2466482725068773144L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idgerentecompra")
	private Long idgerentecompra;		
	
	@OneToOne
	@JoinColumn(name="idparticipante")
	private Participantes participantegercomplp3;	

	@OneToOne
	@JoinColumn(name="idgrupomodulo")
	private GruposModulos grupoModulo;
	
	@Column(name="flaglp1")
	private boolean flaglp1;
	
	@Column(name="flaglp2a")
	private boolean flaglp2a;
	
	@Column(name="flaglp2b")
	private boolean flaglp2b;
	
	@Column(name="flaglp3")
	private boolean flaglp3;
	
	@Column(name="flaglp4")
	private boolean flaglp4;
	
	public GerentesComprasLP3() {
	}
	
	public Long getIdgerentecompra() {
		return idgerentecompra;
	}

	public void setIdgerentecompra(Long idgerentecompra) {
		this.idgerentecompra = idgerentecompra;
	}

	
	public Participantes getParticipantegercomplp3() {
		return participantegercomplp3;
	}

	public void setParticipantegercomplp3(Participantes participantegercomplp3) {
		this.participantegercomplp3 = participantegercomplp3;
	}
	
	public boolean isFlaglp1() {
		return flaglp1;
	}

	public void setFlaglp1(boolean flaglp1) {
		this.flaglp1 = flaglp1;
	}

	public boolean isFlaglp2a() {
		return flaglp2a;
	}

	public void setFlaglp2a(boolean flaglp2a) {
		this.flaglp2a = flaglp2a;
	}
	
	public boolean isFlaglp2b() {
		return flaglp2b;
	}

	public void setFlaglp2b(boolean flaglp2b) {
		this.flaglp2b = flaglp2b;
	}

	public boolean isFlaglp3() {
		return flaglp3;
	}

	public void setFlaglp3(boolean flaglp3) {
		this.flaglp3 = flaglp3;
	}

	public boolean isFlaglp4() {
		return flaglp4;
	}

	public void setFlaglp4(boolean flaglp4) {
		this.flaglp4 = flaglp4;
	}
	
	@Override
	public int hashCode() {
		return this.getIdgerentecompra() != null ? 
		this.getClass().hashCode() + this.getIdgerentecompra().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		GerentesComprasLP3 objint = (GerentesComprasLP3)obj;
		
		if(this.getIdgerentecompra() != null && objint.getIdgerentecompra() != null){
			if(this.getIdgerentecompra().equals(objint.getIdgerentecompra())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

	public GruposModulos getGrupoModulo() {
		return grupoModulo;
	}

	public void setGrupoModulo(GruposModulos grupoModulo) {
		this.grupoModulo = grupoModulo;
	}
	
}
