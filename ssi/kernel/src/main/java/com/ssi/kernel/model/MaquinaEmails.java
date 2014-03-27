package com.ssi.kernel.model;

import java.util.Calendar;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/** 
 * Entidade que representa a tabela que armazena os emails enviados pelo sistema
 * Todos os emails enviados pelo sistema devem ser gravados nessa tabela para controle
 * Se o JMS de envio conseguir enviar o email pelo Sendmail, o flagenviado � setado pra true(1)
 * Caso contr�rio o flag ser� setado como false(0)
 * 
 * @see Pessoas pessoaorigem
 * @see ConfigSMTP configsmtp
 * @see Modulos moduloenvio
 */

@Entity
@Cacheable
@Table(name="MaquinaEmails")
public class MaquinaEmails {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idmaquinaemail")
	private Long idmaquinaemail;
	
	@Column(name="cnmremetente")
	private String cnmremetente;
	
	@Column(name="cnmemailremetente")
	private String cnmemailremetente;
	
	@Column(name="cnmdestinatario")
	private String cnmdestinatario;
	
	@Column(name="cnmemaildestinatario")
	private String cnmemaildestinatario;
	
	@Column(name="cnmassunto")
	private String cnmassunto;
	
	@Column(name="cnmmensagem")
	private String cnmmensagem;
	
	@Column(name="flagenviado")
	private boolean flagenviado;
	
	@Column(name="datacadastro", insertable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar datacadastro;
	
	@Column(name="cnmipsmtp")
	private String cnmipsmtp;

	@Column(name="cnmmodulo")
	private String cnmmodulo;
	
	@Column(name="cnmssi")
	private String cnmssi;
	
	@Transient
	private boolean checked;
	
	public Long getIdmaquinaemail() {
		return idmaquinaemail;
	}

	public void setIdmaquinaemail(Long idmaquinaemail) {
		this.idmaquinaemail = idmaquinaemail;
	}

	public String getCnmremetente() {
		return cnmremetente;
	}

	public void setCnmremetente(String cnmremetente) {
		this.cnmremetente = cnmremetente;
	}

	public String getCnmemailremetente() {
		return cnmemailremetente;
	}

	public void setCnmemailremetente(String cnmemailremetente) {
		this.cnmemailremetente = cnmemailremetente;
	}

	public String getCnmdestinatario() {
		return cnmdestinatario;
	}

	public void setCnmdestinatario(String cnmdestinatario) {
		this.cnmdestinatario = cnmdestinatario;
	}

	public String getCnmemaildestinatario() {
		return cnmemaildestinatario;
	}

	public void setCnmemaildestinatario(String cnmemaildestinatario) {
		this.cnmemaildestinatario = cnmemaildestinatario;
	}

	public String getCnmassunto() {
		return cnmassunto;
	}

	public void setCnmassunto(String cnmassunto) {
		this.cnmassunto = cnmassunto;
	}

	public String getCnmmensagem() {
		return cnmmensagem;
	}

	public void setCnmmensagem(String cnmmensagem) {
		this.cnmmensagem = cnmmensagem;
	}

	public boolean isFlagenviado() {
		return flagenviado;
	}

	public void setFlagenviado(boolean flagenviado) {
		this.flagenviado = flagenviado;
	}

	public Calendar getDatacadastro() {
		return datacadastro;
	}

	public void setDatacadastro(Calendar datacadastro) {
		this.datacadastro = datacadastro;
	}

	public String getCnmipsmtp() {
		return cnmipsmtp;
	}

	public void setCnmipsmtp(String cnmipsmtp) {
		this.cnmipsmtp = cnmipsmtp;
	}
	
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	

	public String getCnmmodulo() {
		return cnmmodulo;
	}

	public void setCnmmodulo(String cnmmodulo) {
		this.cnmmodulo = cnmmodulo;
	}
	
	@Override
	public int hashCode() {
		return this.idmaquinaemail != null ? 
		this.getClass().hashCode() + this.idmaquinaemail.hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		MaquinaEmails objint = (MaquinaEmails)obj;
		
		if(this.getIdmaquinaemail() != null && objint.getIdmaquinaemail() != null){
			if(this.getIdmaquinaemail().equals(objint.getIdmaquinaemail())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

	public String getCnmssi() {
		return cnmssi;
	}

	public void setCnmssi(String cnmssi) {
		this.cnmssi = cnmssi;
	}
	
}
