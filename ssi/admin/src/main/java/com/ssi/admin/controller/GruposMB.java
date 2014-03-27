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
import com.ssi.kernel.controller.interfaces.FuncionalidadesInt;
import com.ssi.kernel.controller.interfaces.GruposInt;
import com.ssi.kernel.model.Grupos;
import com.ssi.kernel.utils.Mensageria;

@ManagedBean
@ViewScoped
public class GruposMB implements java.io.Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6484543073791027813L;
	
	@EJB 
	private GruposInt gruposint;
	
	@EJB 
	private FuncionalidadesInt funcionalidadesint;
	
	private Grupos grupo = new Grupos();
	
	// Controladores de exibi��o de pain�s modais
	private boolean exibepopupeditar = false;
	private boolean modalconfirmaacao = false;
	//private boolean exibepopupfunc = false;
	
	// A��o que o usu�rio selecionou na tela
	private String acao;
	
	// Controlador de exibi��o do select de a��es (disabled/enabled)
	private boolean ocultaacoes;
	
	private List<Grupos> objschecked = new ArrayList<Grupos>();
	private List<Grupos> grupos;
	
	private ValidadorCamposInvalidos validadorcamposinvalidos;
	private List<String>msgserro;
	private IndexMB indexmb;
	
	private String msgpanel;
	
	private String cnmtestea;
	private String cnmtesteb;
	private String cnmtestec;
	
//	private OptionTreeNode nosfunc = new OptionTreeNode();
//	private List<Funcionalidades> funcionalidades;
//	private List<Funcionalidades> funcionalidadesgrupos = new ArrayList<Funcionalidades>();
//	private boolean chamouinittree = false;
//	private boolean expandirnode = true;
	
	
	@PostConstruct
    public void init() {
		// A��es vem inicialmente, ocultas
		ocultaacoes = true;
		
		// Caso navegue para outra tela e retorne, a lista de checados � zerada
		objschecked = new ArrayList<Grupos>();

		validadorcamposinvalidos = new ValidadorCamposInvalidos();
	}
	
	public void testeemail(){
		// Envia email aos executores (pessoal do STRD que trata as solicita��es)
		Map<String,String>listaemailsstrd = new HashMap<String,String>();
		listaemailsstrd.put("Marcio","marcio.vianna.ext@telefonica.com");
		
		Mensageria mensageria = new Mensageria();
		String assuntostrd = "Teste SSI";
		String strmensagemstrd = "<b>teste</b> SSI adm<br>TESTANDO mensagem HTML";
		mensageria.enviarMensagem(strmensagemstrd, assuntostrd, listaemailsstrd,"10.126.111.184","ADM","111111/112013");
		
		mensageria = null;
		listaemailsstrd = null;
		assuntostrd = null;
		strmensagemstrd = null;
	}
	
	// Etapa 1 para montar a �rvore de Funcionalidades (�tens do menu horizontal)
//	public void initRichFacesTree() {
//		nosfunc = new OptionTreeNode();
//		setFuncionalidades(funcionalidadesint.recuperar(329L));
//		getFuncionalidadesgrupos();
//		for (Funcionalidades obj:funcionalidades){
//			if(obj.getFuncionalidadepai()==null){
//				OptionTreeNode raiz = new OptionTreeNode();
//						
//				// Se o obj contiver na listagem, marca o checkbox
//				if(funcionalidadesgrupos != null && funcionalidadesgrupos.contains(obj)){
//					obj.setChecked(true);
//				}
//				raiz.setFuncionalidade(obj);
//				nosfunc.addChild(obj.getIdfuncionalidade(), raiz);
//				            		
//				if(obj.getFuncionalidadesfilhas().size()>=1){
//					recuperarFilhos(obj,raiz);
//				}
//			}
//		}
//	}
//				
//	// Etapa 2 para montar a �rvore de �reas (�tens do menu horizontal)
//	public void recuperarFilhos(Funcionalidades pai,OptionTreeNode nopai){
//		List<Funcionalidades> filhos = pai.getFuncionalidadesfilhas();
//		for (Funcionalidades filho:filhos){
//			OptionTreeNode child = new OptionTreeNode();
//				
//			// Se o obj contiver na listagem de areas da pessoa, marca o checkbox
//			if(funcionalidadesgrupos != null && funcionalidadesgrupos.contains(filho)){
//				filho.setChecked(true);
//			}
//			child.setFuncionalidade((filho));
//			nopai.addChild(filho.getIdfuncionalidade(), child);
//			        		 
//			if(filho.getFuncionalidadesfilhas().size()>=1)
//				recuperarFilhos(filho, child);
//		}
//	}
//	
//	// Altera as funcionalidades do grupo
//	public void alterarfunc(){
//		Iterator<Funcionalidades>it = funcionalidades.iterator();
//		Set<Funcionalidades> funcgruposalt = new HashSet<Funcionalidades>();
//			
//		// Itera sobre a lista de �reas submetida. Verifica qual esta checada e passa para o Set que ser� persistido na base
//		while(it.hasNext()){
//			Funcionalidades obj = it.next();
//			if(obj.isChecked()){
//				funcgruposalt.add(obj);
//			}
//		}
//		grupo.setGruposfuncionalidades(funcgruposalt);
//		gruposint.alterar(grupo);
//	}
	
	public void testea(){
		System.out.println("testando A");
		System.out.println(cnmtestea);
	}
	
	public void testeb(){
		System.out.println("testando B");
		System.out.println(cnmtesteb);
	}
	
	public void testec(){
		System.out.println("testando C");
		System.out.println(cnmtestec);
	}
	
	// Ao clicar em "Ativar/Desativar Selecionados" na view, seta o atributo "flagativo" pra true ou false em todos os marcados
	public void ativarDesativarSelecionados(){
		Iterator<Grupos> it = objschecked.iterator();
		while(it.hasNext()){
			Grupos o = it.next();
			if(o.isFlagativo())
				o.setFlagativo(false);
			else
				o.setFlagativo(true);
			gruposint.alterar(o);
		}
		objschecked = new ArrayList<Grupos>();
		setModalconfirmaacao(false);
	}
	
	public void selecionarCheckbox(Grupos o){
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
//	public void efetuaracao(){
//		// S� permite usar esse recurso caso tenha sido selecionado apenas um registro
//		if(objschecked.size() == 1){
//			if(acao.equals("1")){ // A��o: Editar
//				exibepopupeditar = true;
//				
//				// Seta o objeto com o da List vinda do checkbox
//				setGrupo(objschecked.get(0));
//			}
//			
//			if(acao.equals("2")){ // A��o: Funcionalidades
//				exibepopupfunc = true;
//				// Seta o objeto com o da List vinda do checkbox
//				setGrupo(objschecked.get(0));
//			}
//			
//			acao = "0"; // reseta o combobox de a��es
//		}
//	}
	
	public void alterar(Grupos o){
		setMsgpanel("");
		
		// Verifica na base se est� tentando alterar pra um j� existente
		if(gruposint.verificaExistente(o)){
    		setMsgpanel("Grupo j� existente.");
	    }
		else{
			gruposint.alterar(o);
			exibepopupeditar = false;
		}
	}
	
	public void cadastrar(){
		inicializarValidadores();
		
		// Cria um mapa com os campos que precisamos validar genericamente
    	Map<String,String> camposvalidar = new HashMap<String,String>();
    	
    	camposvalidar.put("Grupo", grupo.getCnmgrupo());
    	
    	if(camposvalidar.size()>=1 && camposvalidar !=null)
    		msgserro = validadorcamposinvalidos.validarCamposVazios(camposvalidar);
    	
    	if(msgserro.size()==0){
    		if(gruposint.verificaExistente(grupo)){
    			grupo.setFlagativo(true);
    			gruposint.incluir(grupo);
	    		indexmb.setPanelexibesucesso(true);
		    	indexmb.setMsgpanel("Grupo cadastrado com sucesso!");
		    	
		    	grupo = new Grupos();
    		}
    		else{
    			indexmb.setPanelexibealerta(true);
		    	indexmb.setMsgpanel("Grupo j� existe no sistema");
		    	
		    	grupo = new Grupos();
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
	
	public List<Grupos> recuperar(){
		grupos = gruposint.recuperar();
		return grupos;
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

	public Grupos getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupos grupo) {
		this.grupo = grupo;
	}

	public List<Grupos> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupos> grupos) {
		this.grupos = grupos;
	}

	public String getCnmtestea() {
		return cnmtestea;
	}

	public void setCnmtestea(String cnmtestea) {
		this.cnmtestea = cnmtestea;
	}

	public String getCnmtesteb() {
		return cnmtesteb;
	}

	public void setCnmtesteb(String cnmtesteb) {
		this.cnmtesteb = cnmtesteb;
	}

	public String getCnmtestec() {
		return cnmtestec;
	}

	public void setCnmtestec(String cnmtestec) {
		this.cnmtestec = cnmtestec;
	}

//	public boolean isExibepopupfunc() {
//		return exibepopupfunc;
//	}
//
//	public void setExibepopupfunc(boolean exibepopupfunc) {
//		this.exibepopupfunc = exibepopupfunc;
//	}

//	public OptionTreeNode getNosfunc() {
//		if(exibepopupfunc){ // se n�o tiver esse IF ele vai tentar carregar antes do tempo
//			initRichFacesTree();
//		}
//		return nosfunc;
//	}
//
//	public void setNosfunc(OptionTreeNode nosfunc) {
//		this.nosfunc = nosfunc;
//	}
//
//	public List<Funcionalidades> getFuncionalidades() {
//		return funcionalidades;
//	}
//
//	public void setFuncionalidades(List<Funcionalidades> funcionalidades) {
//		this.funcionalidades = funcionalidades;
//	}
//
//	public List<Funcionalidades> getFuncionalidadesgrupos() {
//		if(grupo!=null && grupo.getIdgrupo()!=null){
//			funcionalidadesgrupos = funcionalidadesint.retornarFuncionalidadesGrupos(grupo);
//		}
//		return funcionalidadesgrupos;
//	}
//
//	public void setFuncionalidadesgrupos(List<Funcionalidades> funcionalidadesgrupos) {
//		this.funcionalidadesgrupos = funcionalidadesgrupos;
//	}
//
//	public boolean isChamouinittree() {
//		return chamouinittree;
//	}
//
//	public void setChamouinittree(boolean chamouinittree) {
//		this.chamouinittree = chamouinittree;
//	}
//
//	public boolean isExpandirnode() {
//		return expandirnode;
//	}
//
//	public void setExpandirnode(boolean expandirnode) {
//		this.expandirnode = expandirnode;
//	}

}
