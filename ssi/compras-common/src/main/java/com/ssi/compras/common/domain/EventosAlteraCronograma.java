package com.ssi.compras.common.domain;


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
@Table(name="EventosAlteraCronograma", schema="compras")
public class EventosAlteraCronograma implements Serializable {

	private static final long serialVersionUID = 4861113774674238908L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ideventoalteracronograma")
	private Long ideventoalteracronograma;

	@OneToOne
	@JoinColumn(name="idevento")
	private Eventos eventoAlteraCronograma;
	
	@Column(name="dataanterior")
	private Calendar dataanterior;
			
	public EventosAlteraCronograma() {
		eventoAlteraCronograma = new Eventos();
	}

	public Long getIdeventoalteracronograma() {
		return ideventoalteracronograma;
	}

	public void setIdeventoalteracronograma(Long ideventoalteracronograma) {
		this.ideventoalteracronograma = ideventoalteracronograma;
	}

	public Eventos getEventoAlteraCronograma() {
		return eventoAlteraCronograma;
	}

	public void setEventoAlteraCronograma(Eventos eventoAlteraCronograma) {
		this.eventoAlteraCronograma = eventoAlteraCronograma;
	}

	public Calendar getDataanterior() {
		return dataanterior;
	}

	public void setDataanterior(Calendar dataanterior) {
		this.dataanterior = dataanterior;
	}	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((ideventoalteracronograma == null) ? 0
						: ideventoalteracronograma.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventosAlteraCronograma other = (EventosAlteraCronograma) obj;
		if (ideventoalteracronograma == null) {
			if (other.ideventoalteracronograma != null)
				return false;
		} else if (!ideventoalteracronograma
				.equals(other.ideventoalteracronograma))
			return false;
		return true;
	}
	
	

}
