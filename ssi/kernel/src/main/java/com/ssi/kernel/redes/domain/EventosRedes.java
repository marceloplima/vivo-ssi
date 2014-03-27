package com.ssi.kernel.redes.domain;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.ssi.kernel.model.Pessoas;

@Entity
@Cacheable
@Table(name="EventosRedes", schema="redes")
public class EventosRedes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6184462380304498970L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idevento")
	private Long idevento;
	
	@ManyToOne
	@JoinColumn(name="idpessoaautor", referencedColumnName="idpessoa")
	private Pessoas autor;
	
	@Column(name="cnmcomentario")
	private String cnmcomentario;
	
	@ManyToOne
	@JoinColumn(name="iddemanda", referencedColumnName="iddemanda")
	private DemandasRedes demanda;
	
	@ManyToOne
	@JoinColumn(name="idparecer", referencedColumnName="idparecer")
	private PareceresRedes parecer;
	
	@Column(name="datacadastro", insertable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar datacadastro;
	
	@ManyToOne
	@JoinColumn(name="idstatus", referencedColumnName="idstatus")
	private StatusRedes status;
	
	@OneToOne(mappedBy="eventorc", cascade=CascadeType.ALL)
	private EventosRedesConcluir erc;
	
	@Transient
	private boolean checked;
	
	public EventosRedes(){
	}
	
	public EventosRedes(int idstatus){
		switch(idstatus){
			case 6:
				erc = new EventosRedesConcluir();
			break;
		}
		
	}
	
	public Long getIdevento() {
		return idevento;
	}

	public void setIdevento(Long idevento) {
		this.idevento = idevento;
	}
	
	public Pessoas getAutor() {
		return autor;
	}

	public void setAutor(Pessoas autor) {
		this.autor = autor;
	}

	public DemandasRedes getDemanda() {
		return demanda;
	}

	public void setDemanda(DemandasRedes demanda) {
		this.demanda = demanda;
	}
	
	
	public Calendar getDatacadastro() {
		return datacadastro;
	}

	public void setDatacadastro(Calendar datacadastro) {
		this.datacadastro = datacadastro;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getCnmcomentario() {
		return cnmcomentario;
	}

	public void setCnmcomentario(String cnmcomentario) {
		this.cnmcomentario = cnmcomentario;
	}
	
	public PareceresRedes getParecer() {
		return parecer;
	}

	public void setParecer(PareceresRedes parecer) {
		this.parecer = parecer;
	}
	
	public StatusRedes getStatus() {
		return status;
	}

	public void setStatus(StatusRedes status) {
		this.status = status;
	}
	
	public EventosRedesConcluir getErc() {
		return erc;
	}

	public void setErc(EventosRedesConcluir erc) {
		this.erc = erc;
	}

	@Override
	public int hashCode() {
		return this.getIdevento() != null ? 
		this.getClass().hashCode() + this.getIdevento().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		EventosRedes objint = (EventosRedes)obj;
		
		if(this.getIdevento() != null && objint.getIdevento() != null){
			if(this.getIdevento().equals(objint.getIdevento())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

}
