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
@Table(name="ParticipantesRedes", schema="redes")
public class ParticipantesRedes implements Serializable {

	private static final long serialVersionUID = -6186172476713745416L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idparticipante")
	private Long idparticipante;
	
	@ManyToOne
	@JoinColumn(name="idpessoaautor", referencedColumnName="idpessoa")
	private Pessoas autor;
	
	@ManyToOne
	@JoinColumn(name="idpapel", referencedColumnName="idpapel")
	private PapeisRedes papel;
	
	@OneToOne(mappedBy="participanteemissor", cascade=CascadeType.ALL)
	private EmissoresRedes emissor;
	
	@OneToOne(mappedBy="participanterequisitado", cascade=CascadeType.ALL)
	private RequisitadosRedes requisitado;
	
	@OneToOne(mappedBy="participantecopiado", cascade=CascadeType.ALL)
	private CopiadosRedes copiado;
	
	@OneToOne(mappedBy="participante", cascade=CascadeType.ALL)
	private ResponsaveisTecnicosRedes responsaveltecnico;
	
	@Column(name="flagativo")
	private boolean flagativo;

	@Transient
	private boolean checked;
	
	@Column(name="datacadastro", insertable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar datacadastro;
	
	public ParticipantesRedes(){}
	
	public ParticipantesRedes(int idpapel) {
		//System.out.println(">>>>>>>>>> "+idpapel+" <<<<<<<<<<");
		switch(idpapel){
		
			case 1:
				emissor = new EmissoresRedes();
			break;
			
			case 2:
				requisitado = new RequisitadosRedes();
			break;
			
			case 3:
				copiado = new CopiadosRedes();
			break;
			
			case 4:
				setResponsaveltecnico(new ResponsaveisTecnicosRedes());
			break;
			
		}
	}
	
	public Long getIdparticipante() {
		return idparticipante;
	}

	public void setIdparticipante(Long idparticipante) {
		this.idparticipante = idparticipante;
	}

	public Pessoas getAutor() {
		return autor;
	}

	public void setAutor(Pessoas autor) {
		this.autor = autor;
	}

	public PapeisRedes getPapel() {
		return papel;
	}

	public void setPapel(PapeisRedes papel) {
		this.papel = papel;
	}

	public EmissoresRedes getEmissor() {
		return emissor;
	}

	public void setEmissor(EmissoresRedes emissor) {
		this.emissor = emissor;
	}
		
	public RequisitadosRedes getRequisitado() {
		return requisitado;
	}

	public void setRequisitado(RequisitadosRedes requisitado) {
		this.requisitado = requisitado;
	}

	public boolean isFlagativo() {
		return flagativo;
	}

	public void setFlagativo(boolean flagativo) {
		this.flagativo = flagativo;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Calendar getDatacadastro() {
		return datacadastro;
	}

	public void setDatacadastro(Calendar datacadastro) {
		this.datacadastro = datacadastro;
	}
	
	public CopiadosRedes getCopiado() {
		return copiado;
	}

	public void setCopiado(CopiadosRedes copiado) {
		this.copiado = copiado;
	}
	
	public ResponsaveisTecnicosRedes getResponsaveltecnico() {
		return responsaveltecnico;
	}

	public void setResponsaveltecnico(ResponsaveisTecnicosRedes responsaveltecnico) {
		this.responsaveltecnico = responsaveltecnico;
	}
	
	@Override
	public int hashCode() {
		return this.getIdparticipante() != null ? 
		this.getClass().hashCode() + this.getIdparticipante().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		ParticipantesRedes objint = (ParticipantesRedes)obj;
		
		if(this.getIdparticipante() != null && objint.getIdparticipante() != null){
			if(this.getIdparticipante().equals(objint.getIdparticipante())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}
			
}
