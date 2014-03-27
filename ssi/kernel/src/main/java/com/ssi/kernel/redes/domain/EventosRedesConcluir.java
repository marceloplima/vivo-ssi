package com.ssi.kernel.redes.domain;

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

@Entity
@Cacheable
@Table(name="EventosRedesConcluir", schema="redes")
public class EventosRedesConcluir implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4508832772477231634L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ideventoconcluir")
	private Long ideventoconcluir;

	@OneToOne
	@JoinColumn(name="idevento")
	private EventosRedes eventorc;
	
	@Column(name="cnmcorrecao")
	private String cnmcorrecao;
	
	@Column(name="cnmresponsaveltestes")
	private String cnmresponsaveltestes;
	
	@Column(name="dataconclusao")
	private Calendar dataconclusao;
			
	public EventosRedesConcluir() {
		eventorc = new EventosRedes();
	}

	public Long getIdeventoconcluir() {
		return ideventoconcluir;
	}

	public void setIdeventoconcluir(Long ideventoconcluir) {
		this.ideventoconcluir = ideventoconcluir;
	}

	public EventosRedes getEventorc() {
		return eventorc;
	}

	public void setEventorc(EventosRedes eventorc) {
		this.eventorc = eventorc;
	}

	public String getCnmcorrecao() {
		return cnmcorrecao;
	}

	public void setCnmcorrecao(String cnmcorrecao) {
		this.cnmcorrecao = cnmcorrecao;
	}
	
	public String getCnmresponsaveltestes() {
		return cnmresponsaveltestes;
	}

	public void setCnmresponsaveltestes(String cnmresponsaveltestes) {
		this.cnmresponsaveltestes = cnmresponsaveltestes;
	}
	
	public Calendar getDataconclusao() {
		return dataconclusao;
	}

	public void setDataconclusao(Calendar dataconclusao) {
		this.dataconclusao = dataconclusao;
	}
	
	@Override
	public int hashCode() {
		return this.getIdeventoconcluir() != null ? 
		this.getClass().hashCode() + this.getIdeventoconcluir().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		EventosRedesConcluir objint = (EventosRedesConcluir)obj;
		
		if(this.getIdeventoconcluir() != null && objint.getIdeventoconcluir() != null){
			if(this.getIdeventoconcluir().equals(objint.getIdeventoconcluir())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

}
