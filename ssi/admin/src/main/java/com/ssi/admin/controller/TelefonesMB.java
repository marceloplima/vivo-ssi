package com.ssi.admin.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.admin.utils.ValidadorCamposInvalidos;
import com.ssi.kernel.controller.interfaces.TelefonesInt;
import com.ssi.kernel.model.Pessoas;
import com.ssi.kernel.model.Telefones;

@ManagedBean
@ViewScoped
public class TelefonesMB implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7645063997646629271L;
	@EJB 
	private TelefonesInt telefonesint;
	private Telefones telefone = new Telefones();
	
	// Controladores de exibi��o de pain�s modais
	private boolean exibepopupeditatel = false;
	private boolean modalconfirmaacaotel = false;
	
	// A��o que o usu�rio selecionou na tela
	private String acao;
	
	// Controlador de exibi��o do select de a��es (disabled/enabled)
	private boolean ocultaacoes;
	
	private List<Telefones> telschecked = new ArrayList<Telefones>();
	
	private ValidadorCamposInvalidos validadorcamposinvalidos;
	private List<String>msgserro;
	
	private String msgpanel;
	
	@PostConstruct
    public void init() {
		// A��es vem inicialmente, ocultas
		ocultaacoes = true;
		
		// Caso navegue para outra tela e retorne, a lista de emails checados � zerada
		telschecked = new ArrayList<Telefones>();

		validadorcamposinvalidos = new ValidadorCamposInvalidos();
	}
	
	// Ao clicar em "Ativar/Desativar Selecionados" na view, seta o atributo "flagativo" pra true ou false em todos os marcados
	public void ativarDesativarSelecionados(){
		Iterator<Telefones> ittc = telschecked.iterator();
		while(ittc.hasNext()){
			Telefones tc = ittc.next();
			if(tc.isFlagativo())
				tc.setFlagativo(false);
			else
				tc.setFlagativo(true);
				telefonesint.alterar(tc);
		}
		telschecked = new ArrayList<Telefones>();
		setModalconfirmaacaotel(false);
	}
	
	public void selecionarCheckboxTel(Telefones telefone){
		if(telschecked.contains(telefone)){
			telefone.setChecked(false);
			telschecked.remove(telefone);
		}else{
			telefone.setChecked(true);
			telschecked.add(telefone);
		}
		
		// Verifica se apenas 1 checkbox est� checado. Caso positivo, habilita A��es
		ocultaacoes = true;
		if(telschecked.size() == 1){
			ocultaacoes = false;
		}
	}
	
	// Quando o usu�rio seleciona uma a��o no select da view, esse m�todo � acionado
	public void efetuaracao(){
		// S� permite usar esse recurso caso tenha sido selecionado apenas um registro
		if(telschecked.size() == 1){
			if(acao.equals("1")){ // A��o: Editar
				exibepopupeditatel = true;
				
				// Seta o objeto referenciando Telefones com o da List vinda do checkbox de telefones
				telefone = telschecked.get(0);
			}
			
			acao = "0"; // reseta o combobox de a��es
		}
	}
	
	public void alterar(Pessoas pessoa){
		setMsgpanel("");
		
		// Verifica na base se est� tentando alterar pra um j� existente
		if(telefonesint.verificaExistente(telefone)){
    		setMsgpanel("Telefone j� existente.");
	    }
		else{
			telefone.setTelefonespessoa(pessoa);
			telefonesint.alterar(telefone);
			exibepopupeditatel = false;
		}
	}
	
	public void incluir(Pessoas pessoa){
		telefone.setTelefonespessoa(pessoa); // Completa o objeto com a pessoa dona do �tem
		telefonesint.incluir(telefone);
	}
	
	public Telefones getTelefone() {
		return telefone;
	}
	public void setTelefone(Telefones telefone) {
		this.telefone = telefone;
	}

	public boolean isExibepopupeditatel() {
		return exibepopupeditatel;
	}

	public void setExibepopupeditatel(boolean exibepopupeditatel) {
		this.exibepopupeditatel = exibepopupeditatel;
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

	public List<Telefones> getTelschecked() {
		return telschecked;
	}

	public void setTelschecked(List<Telefones> telschecked) {
		this.telschecked = telschecked;
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

	public boolean isModalconfirmaacaotel() {
		return modalconfirmaacaotel;
	}

	public void setModalconfirmaacaotel(boolean modalconfirmaacaotel) {
		this.modalconfirmaacaotel = modalconfirmaacaotel;
	}

}
