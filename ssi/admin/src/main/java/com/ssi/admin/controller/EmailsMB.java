package com.ssi.admin.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.admin.utils.ValidadorCamposInvalidos;
import com.ssi.kernel.controller.interfaces.EmailsInt;
import com.ssi.kernel.model.Emails;
import com.ssi.kernel.model.Pessoas;
import com.ssi.kernel.utils.ValidadorEmail;

@ManagedBean
@ViewScoped
public class EmailsMB implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 506182005547113707L;
	
	@EJB 
	private EmailsInt emailsint;
	private Emails email = new Emails();
	
	// Controladores de exibi��o de pain�s modais
	private boolean exibepopupeditaemail = false;
	private boolean modalconfirmaacaoemail = false;
	
	// A��o que o usu�rio selecionou na tela
	private String acao;
	
	// Controlador de exibi��o do select de a��es (disabled/enabled)
	private boolean ocultaacoes;
	
	private List<Emails> emailschecked = new ArrayList<Emails>();
	
	private ValidadorEmail validadoremail;
	private ValidadorCamposInvalidos validadorcamposinvalidos;
	private List<String>msgserro;
	
	private String msgpanel;
	
	@PostConstruct
    public void init() {
		// A��es vem inicialmente, ocultas
		ocultaacoes = true;
		
		// Caso navegue para outra tela e retorne, a lista de emails checados � zerada
		emailschecked = new ArrayList<Emails>();
		
		validadoremail = new ValidadorEmail();
		validadorcamposinvalidos = new ValidadorCamposInvalidos();
	}
	
	// Ao clicar em "Ativar/Desativar Selecionados" na view, seta o atributo "flagativo" pra true ou false em todos os marcados
	public void ativarDesativarSelecionados(){
		Iterator<Emails> itec = emailschecked.iterator();
		while(itec.hasNext()){
			Emails ec = itec.next();
			if(ec.isFlagativo())
				ec.setFlagativo(false);
			else
				ec.setFlagativo(true);
			emailsint.alterar(ec);
		}
		emailschecked = new ArrayList<Emails>();
		modalconfirmaacaoemail = false;
	}
	
	public void selecionarCheckboxEmail(Emails email){
		if(emailschecked.contains(email)){
			email.setChecked(false);
			emailschecked.remove(email);
		}else{
			email.setChecked(true);
			emailschecked.add(email);
		}
		
		// Verifica se apenas 1 checkbox est� checado. Caso positivo, habilita A��es
		ocultaacoes = true;
		if(emailschecked.size() == 1){
			ocultaacoes = false;
		}
	}
	
	// Quando o usu�rio seleciona uma a��o no select da view, esse m�todo � acionado
	public void efetuaracao(){
		// S� permite usar esse recurso caso tenha sido selecionado apenas um registro
		if(emailschecked.size() == 1){
			if(acao.equals("1")){ // A��o: Editar
				exibepopupeditaemail = true;
				
				// Seta o objeto referenciando Emails com o da List vinda do checkbox de emails
				email = emailschecked.get(0);
			}
			
			acao = "0"; // reseta o combobox de a��es
		}
	}
	
	public void incluir(Pessoas pessoa){
		email.setEmailspessoa(pessoa); // Completa o objeto de email com a pessoa dona do email
		emailsint.incluir(email);
	}
	
	public void alterar(Pessoas pessoa){
		setMsgpanel("");
		
		// Checa se o email � v�lido
		if(validadoremail.validar(email.getCnmemail())){
			// Verifica na base se est� tentando alterar pra um email j� existente
			if(emailsint.verificaExistente(email)){
    			setMsgpanel("Email j� existente.");
	    	}
			else{
				email.setEmailspessoa(pessoa);
				emailsint.alterar(email);
				exibepopupeditaemail = false;
			}
		}else{
			setMsgpanel("Email inv�lido - Favor corrigir.");
		}
	}
	
	public Emails getEmail() {
		return email;
	}
	public void setEmail(Emails email) {
		this.email = email;
	}

	public boolean isExibepopupeditaemail() {
		return exibepopupeditaemail;
	}

	public void setExibepopupeditaemail(boolean exibepopupeditaemail) {
		this.exibepopupeditaemail = exibepopupeditaemail;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public boolean isOcultaacoes() {
		return ocultaacoes;
	}

	public void setOcultaacoes(boolean ocultaacoes) {
		this.ocultaacoes = ocultaacoes;
	}

	public List<Emails> getEmailschecked() {
		return emailschecked;
	}

	public void setEmailschecked(List<Emails> emailschecked) {
		this.emailschecked = emailschecked;
	}

	public ValidadorEmail getValidadoremail() {
		return validadoremail;
	}

	public void setValidadoremail(ValidadorEmail validadoremail) {
		this.validadoremail = validadoremail;
	}

	public ValidadorCamposInvalidos getValidadorcamposinvalidos() {
		return validadorcamposinvalidos;
	}

	public void setValidadorcamposinvalidos(ValidadorCamposInvalidos validadorcamposinvalidos) {
		this.validadorcamposinvalidos = validadorcamposinvalidos;
	}

	public List<String> getMsgserro() {
		return msgserro;
	}

	public void setMsgserro(List<String> msgserro) {
		this.msgserro = msgserro;
	}

	public String getMsgpanel() {
		return msgpanel;
	}

	public void setMsgpanel(String msgpanel) {
		this.msgpanel = msgpanel;
	}

	public boolean isModalconfirmaacaoemail() {
		return modalconfirmaacaoemail;
	}

	public void setModalconfirmaacaoemail(boolean modalconfirmaacaoemail) {
		this.modalconfirmaacaoemail = modalconfirmaacaoemail;
	}

}
