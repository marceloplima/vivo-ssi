package com.ssi.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.admin.utils.RecuperadorInstanciasBean;
import com.ssi.admin.utils.ValidadorCamposInvalidos;
import com.ssi.kernel.controller.interfaces.TiposUsuarioInt;
import com.ssi.kernel.model.TiposUsuario;

@ManagedBean
@ViewScoped
public class TiposUsuarioMB implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7645063997646629271L;
	@EJB 
	private TiposUsuarioInt tiposusuarioint;
	private TiposUsuario tipousuario = new TiposUsuario();
	
	// Controladores de exibi��o de pain�s modais
	private boolean exibepopupeditar = false;
	private boolean modalconfirmaacao = false;
	
	// A��o que o usu�rio selecionou na tela
	private String acao;
	
	// Controlador de exibi��o do select de a��es (disabled/enabled)
	private boolean ocultaacoes;
	
	private List<TiposUsuario> objschecked = new ArrayList<TiposUsuario>();
	private List<TiposUsuario> tiposusuarios;
	
	private ValidadorCamposInvalidos validadorcamposinvalidos;
	private List<String>msgserro;
	private IndexMB indexmb;
	
	private String msgpanel;
	
	@PostConstruct
    public void init() {
		// A��es vem inicialmente, ocultas
		ocultaacoes = true;
		
		// Caso navegue para outra tela e retorne, a lista de checados � zerada
		objschecked = new ArrayList<TiposUsuario>();

		validadorcamposinvalidos = new ValidadorCamposInvalidos();
	}
	
	// Ao clicar em "Ativar/Desativar Selecionados" na view, seta o atributo "flagativo" pra true ou false em todos os marcados
	public void ativarDesativarSelecionados(){
		Iterator<TiposUsuario> it = objschecked.iterator();
		while(it.hasNext()){
			TiposUsuario o = it.next();
			if(o.isFlagativo())
				o.setFlagativo(false);
			else
				o.setFlagativo(true);
			tiposusuarioint.alterar(o);
		}
		objschecked = new ArrayList<TiposUsuario>();
		setModalconfirmaacao(false);
	}
	
	public void selecionarCheckbox(TiposUsuario o){
		if(objschecked.contains(o)){
			o.setChecked(false);
			objschecked.remove(o);
		}else{
			o.setChecked(true);
			objschecked.add(o);
		}
		
		// Verifica se apenas 1 checkbox est� checado. Caso positivo, habilita A��es
		ocultaacoes = true;
		if(objschecked.size() == 1){
			ocultaacoes = false;
		}
	}
	
	// Quando o usu�rio seleciona uma a��o no select da view, esse m�todo � acionado
	public void efetuaracao(){
		// S� permite usar esse recurso caso tenha sido selecionado apenas um registro
		if(objschecked.size() == 1){
			if(acao.equals("1")){ // A��o: Editar
				exibepopupeditar = true;
				
				// Seta o objeto referenciando Telefones com o da List vinda do checkbox de telefones
				setTipousuario(objschecked.get(0));
			}
			
			acao = "0"; // reseta o combobox de a��es
		}
	}
	
	public void alterar(TiposUsuario o){
		setMsgpanel("");
		
		// Verifica na base se est� tentando alterar pra um j� existente
		if(tiposusuarioint.verificaExistente(o)){
    		setMsgpanel("Tipo de Usu�rio j� existente.");
	    }
		else{
			tiposusuarioint.alterar(o);
			exibepopupeditar = false;
		}
	}
	
	public void cadastrar(){
		inicializarValidadores();
		
		// Cria um mapa com os campos que precisamos validar genericamente
    	Map<String,String> camposvalidar = new HashMap<String,String>();
    	
    	camposvalidar.put("Tipo de Usu�rio", tipousuario.getCnmtipousuario());
    	
    	if(camposvalidar.size()>=1 && camposvalidar !=null)
    		msgserro = validadorcamposinvalidos.validarCamposVazios(camposvalidar);
    	
    	if(msgserro.size()==0){
    		if(tiposusuarioint.verificaExistente(tipousuario)){
    			tipousuario.setFlagativo(true);
    			tiposusuarioint.incluir(tipousuario);
	    		indexmb.setPanelexibesucesso(true);
		    	indexmb.setMsgpanel("Tipo de Usu�rio cadastrado com sucesso!");
		    	
		    	tipousuario = new TiposUsuario();
    		}
    		else{
    			indexmb.setPanelexibealerta(true);
		    	indexmb.setMsgpanel("Tipo de Usu�rio j� existe no sistema");
		    	
		    	tipousuario = new TiposUsuario();
    		}
    	}
    	else{
    		indexmb.setPanelexibeerro(true);
    		indexmb.setRenderizalistadeerros(true);
    		indexmb.setMsgspanel(msgserro);
    	}
	}

	public void inicializarValidadores(){
		indexmb = RecuperadorInstanciasBean.recuperarInstanciaIndexBean();
    	msgserro = new ArrayList<String>();
	}
	
	public List<TiposUsuario> recuperar(){
		tiposusuarios = tiposusuarioint.recuperar();
		return tiposusuarios;
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

	public boolean isExibepopupeditar() {
		return exibepopupeditar;
	}

	public void setExibepopupeditar(boolean exibepopupeditar) {
		this.exibepopupeditar = exibepopupeditar;
	}

	public boolean isModalconfirmaacao() {
		return modalconfirmaacao;
	}

	public void setModalconfirmaacao(boolean modalconfirmaacao) {
		this.modalconfirmaacao = modalconfirmaacao;
	}

	public TiposUsuario getTipousuario() {
		return tipousuario;
	}

	public void setTipousuario(TiposUsuario tipousuario) {
		this.tipousuario = tipousuario;
	}

	public List<TiposUsuario> getTiposusuarios() {
		return tiposusuarios;
	}

	public void setTiposusuarios(List<TiposUsuario> tiposusuarios) {
		this.tiposusuarios = tiposusuarios;
	}

}
