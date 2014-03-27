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
import com.ssi.kernel.controller.interfaces.ModulosInt;
import com.ssi.kernel.controller.interfaces.NoticiasInt;
import com.ssi.kernel.model.Modulos;
import com.ssi.kernel.model.Noticias;

@ManagedBean
@ViewScoped
public class NoticiasMB implements java.io.Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6484543073791027813L;
	@EJB 
	private NoticiasInt noticiasint;
	@EJB 
	private ModulosInt modulosint;
	private Noticias noticia = new Noticias();
	
	// Controladores de exibi��o de pain�s modais
	private boolean exibepopupeditar = false;
	private boolean modalconfirmaacao = false;
	
	// A��o que o usu�rio selecionou na tela
	private String acao;
	
	// Controlador de exibi��o do select de a��es (disabled/enabled)
	private boolean ocultaacoes;
	
	private List<Noticias> objschecked = new ArrayList<Noticias>();
	private List<Noticias> noticias;
	
	private List<Modulos> modulosnoticias;
	private List<Modulos> modulos;
	private List<Modulos> moduloschecked;
	
	private ValidadorCamposInvalidos validadorcamposinvalidos;
	private List<String>msgserro;
	private IndexMB indexmb;
	
	private String msgpanel;
	
	@PostConstruct
    public void init() {
		// A��es vem inicialmente, ocultas
		ocultaacoes = true;
		
		// Caso navegue para outra tela e retorne, a lista de checados � zerada
		objschecked = new ArrayList<Noticias>();

		validadorcamposinvalidos = new ValidadorCamposInvalidos();
	}
	
	// Ao clicar em "Ativar/Desativar Selecionados" na view, seta o atributo "flagativo" pra true ou false em todos os marcados
	public void ativarDesativarSelecionados(){
		Iterator<Noticias> it = objschecked.iterator();
		while(it.hasNext()){
			Noticias o = it.next();
			if(o.isFlagativo())
				o.setFlagativo(false);
			else
				o.setFlagativo(true);
			noticiasint.alterar(o);
		}
		objschecked = new ArrayList<Noticias>();
		setModalconfirmaacao(false);
	}
	
	public void selecionarCheckbox(Noticias o){
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
				
				// Seta o objeto referenciando Telefones com o da List vinda do checkbox
				setNoticia(objschecked.get(0));
			}
			
			acao = "0"; // reseta o combobox de a��es
		}
	}
	
	public void alterar(Noticias o){
		setMsgpanel("");
		
		// Verifica na base se est� tentando alterar pra um j� existente
		if(noticiasint.verificaExistente(o)){
    		setMsgpanel("Not�cia j� existente.");
	    }
		else{
			noticiasint.alterar(o);
			exibepopupeditar = false;
		}
	}
	
	public void cadastrar(){
		inicializarValidadores();
		
		// Cria um mapa com os campos que precisamos validar genericamente
    	Map<String,String> camposvalidar = new HashMap<String,String>();
    	
    	camposvalidar.put("Not�cia", noticia.getCnmnoticia());
    	
    	if(camposvalidar.size()>=1 && camposvalidar !=null)
    		msgserro = validadorcamposinvalidos.validarCamposVazios(camposvalidar);
    	
    	if(msgserro.size()==0){
    		if(noticiasint.verificaExistente(noticia)){
    			
    			moduloschecked = new ArrayList<Modulos>();
    			
    			for(Modulos obj:modulos){
    				if(obj.isChecked()){
    					moduloschecked.add(obj);
    				}
    			}
    			
    			noticia.setNoticiasmodulos(new HashSet<Modulos>(moduloschecked));
    			noticia.setFlagativo(true);
    			noticiasint.incluir(noticia);
	    		indexmb.setPanelexibesucesso(true);
		    	indexmb.setMsgpanel("Not�cia cadastrada com sucesso!");
		    	
		    	noticia = new Noticias();
    		}
    		else{
    			indexmb.setPanelexibealerta(true);
		    	indexmb.setMsgpanel("Not�cia j� existe no sistema");
		    	
		    	noticia = new Noticias();
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
	
	public List<Noticias> recuperar(){
		noticias = noticiasint.recuperar();
		return noticias;
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

	public Noticias getNoticia() {
		return noticia;
	}

	public void setNoticia(Noticias noticia) {
		this.noticia = noticia;
	}

	public List<Noticias> getNoticias() {
		return noticias;
	}

	public void setNoticias(List<Noticias> noticias) {
		this.noticias = noticias;
	}

	public List<Modulos> getModulosnoticias() {
		if(noticia!=null && noticia.getIdnoticia()!=null){
			modulosnoticias = noticiasint.retornarModulosNoticias(noticia);
		}
		return modulosnoticias;
	}

	public void setModulosnoticias(List<Modulos> modulosnoticias) {
		this.modulosnoticias = modulosnoticias;
	}

	public List<Modulos> getModulos() {
		if(modulos == null)
			modulos = modulosint.recuperar();
		return modulos;
	}

	public void setModulos(List<Modulos> modulos) {
		this.modulos = modulos;
	}

	public List<Modulos> getModuloschecked() {
		return moduloschecked;
	}

	public void setModuloschecked(List<Modulos> moduloschecked) {
		this.moduloschecked = moduloschecked;
	}

}
