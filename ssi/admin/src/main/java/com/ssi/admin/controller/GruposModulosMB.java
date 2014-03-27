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
import com.ssi.kernel.controller.interfaces.GruposModulosInt;
import com.ssi.kernel.model.GruposModulos;

@ManagedBean
@ViewScoped
public class GruposModulosMB implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7645063997646629271L;
	@EJB 
	private GruposModulosInt gruposmodulosint;
	private GruposModulos grupomodulo = new GruposModulos();
	
	// Controladores de exibi��o de pain�s modais
	private boolean exibepopupeditagrupomodulo = false;
	private boolean modalconfirmaacaogrupomodulo = false;
	
	// A��o que o usu�rio selecionou na tela
	private String acao;
	
	// Controlador de exibi��o do select de a��es (disabled/enabled)
	private boolean ocultaacoes;
	
	private List<GruposModulos> gruposmoduloschecked = new ArrayList<GruposModulos>();
	
	private ValidadorCamposInvalidos validadorcamposinvalidos;
	private List<String>msgserro;
	private IndexMB indexmb;
	
	private String msgpanel;
	
	@PostConstruct
    public void init() {
		// A��es vem inicialmente, ocultas
		ocultaacoes = true;
		
		// Caso navegue para outra tela e retorne, a lista de checados � zerada
		gruposmoduloschecked = new ArrayList<GruposModulos>();

		validadorcamposinvalidos = new ValidadorCamposInvalidos();
	}
	
	// Ao clicar em "Ativar/Desativar Selecionados" na view, seta o atributo "flagativo" pra true ou false em todos os marcados
	public void ativarDesativarSelecionados(){
		Iterator<GruposModulos> it = gruposmoduloschecked.iterator();
		while(it.hasNext()){
			GruposModulos o = it.next();
			if(o.isFlagativo())
				o.setFlagativo(false);
			else
				o.setFlagativo(true);
			gruposmodulosint.alterar(o);
		}
		gruposmoduloschecked = new ArrayList<GruposModulos>();
		setModalconfirmaacaogrupomodulo(false);
	}
	
	public void selecionarCheckbox(GruposModulos o){
		if(gruposmoduloschecked.contains(o)){
			o.setChecked(false);
			gruposmoduloschecked.remove(o);
		}else{
			o.setChecked(true);
			gruposmoduloschecked.add(o);
		}
		
		// Verifica se apenas 1 checkbox est� checado. Caso positivo, habilita A��es
		ocultaacoes = true;
		if(gruposmoduloschecked.size() == 1){
			ocultaacoes = false;
		}
	}
	
	// Quando o usu�rio seleciona uma a��o no select da view, esse m�todo � acionado
	public void efetuaracao(){
		// S� permite usar esse recurso caso tenha sido selecionado apenas um registro
		if(gruposmoduloschecked.size() == 1){
			if(acao.equals("1")){ // A��o: Editar
				exibepopupeditagrupomodulo = true;
				
				// Seta o objeto referenciando Telefones com o da List vinda do checkbox de telefones
				setGrupomodulo(gruposmoduloschecked.get(0));
			}
			
			acao = "0"; // reseta o combobox de a��es
		}
	}
	
	public void alterar(GruposModulos o){
		setMsgpanel("");
		
		// Verifica na base se est� tentando alterar pra um j� existente
		if(gruposmodulosint.verificaExistente(o)){
    		setMsgpanel("Grupo j� existente.");
	    }
		else{
			gruposmodulosint.alterar(o);
			exibepopupeditagrupomodulo = false;
		}
	}
	
	public void cadastrar(){
		inicializarValidadores();
		
		// Cria um mapa com os campos que precisamos validar genericamente
    	Map<String,String> camposvalidar = new HashMap<String,String>();
    	
    	camposvalidar.put("Nome do Grupo", grupomodulo.getCnmgrupomodulo());
    	if(grupomodulo.getModulo()!=null)
    		camposvalidar.put("Modulo", grupomodulo.getModulo().getIdmodulo().toString());
    	else
    		camposvalidar.put("Modulo", "");
    	
    	if(camposvalidar.size()>=1 && camposvalidar !=null)
    		msgserro = validadorcamposinvalidos.validarCamposVazios(camposvalidar);
    	
    	if(msgserro.size()==0){
    		if(gruposmodulosint.verificaExistente(grupomodulo)){
    			grupomodulo.setFlagativo(true);
	    		gruposmodulosint.incluir(grupomodulo);
	    		indexmb.setPanelexibesucesso(true);
		    	indexmb.setMsgpanel("Grupo M�dulo cadastrado com sucesso!");
		    	
		    	grupomodulo = new GruposModulos();
    		}
    		else{
    			indexmb.setPanelexibealerta(true);
		    	indexmb.setMsgpanel("Grupo M�dulo j� existe no sistema");
		    	
		    	grupomodulo = new GruposModulos();
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

	public boolean isExibepopupeditagrupomodulo() {
		return exibepopupeditagrupomodulo;
	}

	public void setExibepopupeditagrupomodulo(boolean exibepopupeditagrupomodulo) {
		this.exibepopupeditagrupomodulo = exibepopupeditagrupomodulo;
	}

	public boolean isModalconfirmaacaogrupomodulo() {
		return modalconfirmaacaogrupomodulo;
	}

	public void setModalconfirmaacaogrupomodulo(boolean modalconfirmaacaogrupomodulo) {
		this.modalconfirmaacaogrupomodulo = modalconfirmaacaogrupomodulo;
	}

	public GruposModulos getGrupomodulo() {
		return grupomodulo;
	}

	public void setGrupomodulo(GruposModulos grupomodulo) {
		this.grupomodulo = grupomodulo;
	}

}
