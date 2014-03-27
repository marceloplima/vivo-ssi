package com.ssi.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.admin.utils.RecuperadorInstanciasBean;
import com.ssi.admin.utils.ValidadorCamposInvalidos;
import com.ssi.kernel.controller.interfaces.FornecedoresInt;
import com.ssi.kernel.controller.interfaces.TiposFornecedorInt;
import com.ssi.kernel.model.Fornecedores;
import com.ssi.kernel.model.TiposFornecedor;

@ManagedBean
@ViewScoped
public class FornecedoresMB implements java.io.Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7602957488543750529L;
	
	@EJB 
	private FornecedoresInt fornecedoresint;
	@EJB 
	private TiposFornecedorInt tiposfornecedorint;
	
	private Fornecedores fornecedor = new Fornecedores();
	
	// Controladores de exibi��o de pain�s modais
	private boolean exibepopupeditafornecedor = false;
	private boolean modalconfirmaacaofornecedor = false;
	
	// A��o que o usu�rio selecionou na tela
	private String acao;
	
	// Controlador de exibi��o do select de a��es (disabled/enabled)
	private boolean ocultaacoes;
	
	private List<Fornecedores> fornecedoreschecked = new ArrayList<Fornecedores>();
	
	private ValidadorCamposInvalidos validadorcamposinvalidos;
	private List<String>msgserro;
	private IndexMB indexmb;
	
	private String msgpanel;
	
	private List<TiposFornecedor> tiposfornecedorfornecedor; // Lista de tipos de fornecedor de um fornecedor espec�fico
	private List<TiposFornecedor> tiposfornecedor;
	private List<TiposFornecedor> tiposfornecedorchecked; // Lista dos tipos de fornecedor checados na inclus�o de fornecedor
	
	@PostConstruct
    public void init() {
		// A��es vem inicialmente, ocultas
		ocultaacoes = true;
		
		// Caso navegue para outra tela e retorne, a lista de checados � zerada
		fornecedoreschecked = new ArrayList<Fornecedores>();

		validadorcamposinvalidos = new ValidadorCamposInvalidos();
	}
	
	// Ao clicar em "Ativar/Desativar Selecionados" na view, seta o atributo "flagativo" pra true ou false em todos os marcados
	public void ativarDesativarSelecionados(){
		Iterator<Fornecedores> it = fornecedoreschecked.iterator();
		while(it.hasNext()){
			Fornecedores o = it.next();
			if(o.isFlagativo())
				o.setFlagativo(false);
			else
				o.setFlagativo(true);
			fornecedoresint.alterar(o);
		}
		fornecedoreschecked = new ArrayList<Fornecedores>();
		setModalconfirmaacaofornecedor(false);
	}
	
	public void selecionarCheckbox(Fornecedores o){
		if(fornecedoreschecked.contains(o)){
			o.setChecked(false);
			fornecedoreschecked.remove(o);
		}else{
			o.setChecked(true);
			fornecedoreschecked.add(o);
		}
		
		// Verifica se apenas 1 checkbox est� checado. Caso positivo, habilita A��es
		ocultaacoes = true;
		if(fornecedoreschecked.size() == 1){
			ocultaacoes = false;
		}
	}
	
	// Quando o usu�rio seleciona uma a��o no select da view, esse m�todo � acionado
	public void efetuaracao(){
		// S� permite usar esse recurso caso tenha sido selecionado apenas um registro
		if(fornecedoreschecked.size() == 1){
			if(acao.equals("1")){ // A��o: Editar
				exibepopupeditafornecedor = true;
				
				// Seta o objeto referenciando  com o da List vinda do checkbox
				setFornecedor(fornecedoreschecked.get(0));
			}
			
			acao = "0"; // reseta o combobox de a��es
		}
	}
	
	public void alterar(Fornecedores o){
		setMsgpanel("");
		
		// Verifica na base se est� tentando alterar pra um j� existente
		if(fornecedoresint.verificaExistente(o)){
    		setMsgpanel("Fornecedor j� existente.");
	    }
		else{
			fornecedoresint.alterar(o);
			exibepopupeditafornecedor = false;
		}
	}
	
	public void cadastrar(){
		inicializarValidadores();
		
		// Cria um mapa com os campos que precisamos validar genericamente
    	Map<String,String> camposvalidar = new HashMap<String,String>();
    	
    	camposvalidar.put("Nome do Fornecedor", fornecedor.getCnmfornecedor());
    	
    	if(camposvalidar.size()>=1 && camposvalidar !=null)
    		msgserro = validadorcamposinvalidos.validarCamposVazios(camposvalidar);
    	
    	if(msgserro.size()==0){
    		if(fornecedoresint.verificaExistente(fornecedor)){
    			
    			tiposfornecedorchecked = new ArrayList<TiposFornecedor>();
    			
    			for (TiposFornecedor obj:tiposfornecedor){
    				if(obj.isChecked()){
    					tiposfornecedorchecked.add(obj);
    				}
    			}
    			
    			fornecedor.setTiposfornecedor(new HashSet<TiposFornecedor>(tiposfornecedorchecked));
    			fornecedor.setFlagativo(true);
    			fornecedoresint.incluir(fornecedor);
	    		indexmb.setPanelexibesucesso(true);
		    	indexmb.setMsgpanel("Fornecedor cadastrado com sucesso!");
		    	
		    	fornecedor = new Fornecedores();
    		}
    		else{
    			indexmb.setPanelexibealerta(true);
		    	indexmb.setMsgpanel("Fornecedor j� existe no sistema");
		    	
		    	fornecedor = new Fornecedores();
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

	public boolean isExibepopupeditafornecedor() {
		return exibepopupeditafornecedor;
	}

	public void setExibepopupeditafornecedor(boolean exibepopupeditafornecedor) {
		this.exibepopupeditafornecedor = exibepopupeditafornecedor;
	}

	public boolean isModalconfirmaacaofornecedor() {
		return modalconfirmaacaofornecedor;
	}

	public void setModalconfirmaacaofornecedor(boolean modalconfirmaacaofornecedor) {
		this.modalconfirmaacaofornecedor = modalconfirmaacaofornecedor;
	}

	public Fornecedores getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedores fornecedor) {
		this.fornecedor = fornecedor;
	}

	public List<TiposFornecedor> getTiposfornecedorfornecedor() {
		if(fornecedor!=null && fornecedor.getIdfornecedor()!=null){
			tiposfornecedorfornecedor = fornecedoresint.retornarTiposFornecedorFornecedor(fornecedor);
		}
		return tiposfornecedorfornecedor;
	}

	public void setTiposfornecedorfornecedor(
			List<TiposFornecedor> tiposfornecedorfornecedor) {
		this.tiposfornecedorfornecedor = tiposfornecedorfornecedor;
	}

	public List<TiposFornecedor> getTiposfornecedor() {
		if(tiposfornecedor == null)
			tiposfornecedor = tiposfornecedorint.recuperar();
		return tiposfornecedor;
	}

	public void setTiposfornecedor(List<TiposFornecedor> tiposfornecedor) {
		this.tiposfornecedor = tiposfornecedor;
	}

	public List<TiposFornecedor> getTiposfornecedorchecked() {
		return tiposfornecedorchecked;
	}

	public void setTiposfornecedorchecked(List<TiposFornecedor> tiposfornecedorchecked) {
		this.tiposfornecedorchecked = tiposfornecedorchecked;
	}

}
