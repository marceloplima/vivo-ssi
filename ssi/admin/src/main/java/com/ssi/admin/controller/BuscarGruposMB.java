package com.ssi.admin.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.kernel.controller.interfaces.FuncionalidadesInt;
import com.ssi.kernel.controller.interfaces.GruposInt;
import com.ssi.kernel.model.Funcionalidades;
import com.ssi.kernel.model.Grupos;
import com.ssi.kernel.model.Modulos;

@ManagedBean
@ViewScoped
public class BuscarGruposMB implements java.io.Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9149771641635733657L;

	@EJB
	private GruposInt gruposint;
	
	@EJB
	private FuncionalidadesInt funcionalidadesint;
	
	private Grupos grupo = new Grupos();
	
	private Map<String,Object>filtros = new LinkedHashMap<String,Object>();
	
	private List<Grupos> grupos;
	private List<Grupos> objschecked = new ArrayList<Grupos>();
	
	// Filtros
	private String cnmgrupo;
	
	private boolean exibelistagem = false;
	private boolean modalconfirmaacao = false;
	private boolean exibepopupeditar = false;
	private boolean exibepopupfunc = false;
	
	//Controlador de exibi��o do select de a��es (disabled/enabled)
	private boolean ocultaacoes;
	
	private String acao; // a��o selecionada no combo de A��es
	
	private OptionTreeNode nosfunc = new OptionTreeNode();
	private List<Funcionalidades> funcionalidades;
	private List<Funcionalidades> funcionalidadesgrupos = new ArrayList<Funcionalidades>();
	private boolean chamouinittree = false;
	private boolean exibetreefunc = false; // inicialmente a tree de Funcionalidades vir� vazia, ser� preenchida posteriormente quando a busca de modulos for efetuada
	private Modulos modulofunc; // Usado no popup de funcionalidades (para filtrar as funcionalidades de um m�dulo espec�fico)
	
	@PostConstruct
    public void init() {
		
		grupo = new Grupos();
		
		// A��es vem inicialmente, ocultas
		ocultaacoes = true;
		
		setGrupos(new ArrayList<Grupos>());
		objschecked = new ArrayList<Grupos>();
    }
	
	// Ao clicar em "Ativar/Desativar Selecionados" na view, seta o atributo "flagativo" pra true ou false em todos os marcados
	// E depois, destroi as marca��es
	public void ativarDesativarSelecionados(){
		Iterator<Grupos> it = objschecked.iterator();
		while(it.hasNext()){
			Grupos obj = it.next();
			if(obj.isFlagativo())
				obj.setFlagativo(false);
			else
				obj.setFlagativo(true);
			
			gruposint.alterar(obj);
			obj.setChecked(false);
		}
		objschecked = new ArrayList<Grupos>();
		modalconfirmaacao = false;
	}
	
	public void selecionarCheckbox(Grupos obj){
		if(objschecked.contains(obj)){
			obj.setChecked(false);
			objschecked.remove(obj);
		}else{
			obj.setChecked(true);
			objschecked.add(obj);
		}
		
		// Verifica se apenas 1 checkbox est� checado. Caso positivo, habilita A��es
		setOcultaacoes(true);
		if(objschecked.size() == 1){
			setOcultaacoes(false);
		}
	}
	
	public void fecharPopups(){
		exibepopupeditar  = false;
		modalconfirmaacao = false;
		exibepopupfunc    = false;
	}
	
	public void alterar(){
		gruposint.alterar(grupo);
		fecharPopups();
	}
	
	public void buscar(){
		// Cria um objeto com o filtro informado e passa para o ejb
		Grupos obj = new Grupos();
		obj.setCnmgrupo(cnmgrupo);
		filtros.put("grupo",obj);
		setGrupos(gruposint.recuperarFiltrado(filtros));
		exibelistagem = true;
	}
	
	// Faz um filtro no popup de funcionalidades que � aberto ao informar a A��o correspondente
	public void filtrarmodulofunc(){
		exibetreefunc = true; // se filtrou, exibe o tree
	}
	
	// Quando o usu�rio seleciona uma a��o no select da view, esse m�todo � acionado
	public void efetuaracao(){
			
		// Mantem todos os popups com rendered=false antes do processameto das a��es para mante-los fechados
		fecharPopups();
			
		// S� permite usar esse recurso caso tenha sido selecionado apenas um registro
		if(objschecked.size() == 1){
				
			// Seta o objeto referenciando GruposModulos com o da List vinda do checkbox da busca
			grupo = objschecked.get(0);
				
			if(acao.equals("1")){ // A��o: Editar
				exibepopupeditar = true;
			}
			if(acao.equals("2")){ // A��o: Funcionalidades
				exibepopupfunc = true;
			}
		}
			
		acao = "0"; // reseta o combobox de a��es
	}
	
	// Etapa 1 para montar a �rvore de Funcionalidades (�tens do menu horizontal)
	public void initRichFacesTree() {
		nosfunc = new OptionTreeNode();
		if(modulofunc != null){
			setFuncionalidades(funcionalidadesint.recuperarPorModulo(modulofunc.getIdmodulo()));
			getFuncionalidadesgrupos();
			for (Funcionalidades obj:funcionalidades){
				if(obj.getFuncionalidadepai()==null){
					OptionTreeNode raiz = new OptionTreeNode();
									
					// Se o obj contiver na listagem, marca o checkbox
					if(funcionalidadesgrupos != null && funcionalidadesgrupos.contains(obj)){
						obj.setChecked(true);
					}
					raiz.setFuncionalidade(obj);
					nosfunc.addChild(obj.getIdfuncionalidade(), raiz);
							            		
					if(obj.getFuncionalidadesfilhas().size()>=1){
						recuperarFilhos(obj,raiz);
					}
				}
			}
		}
	}
					
	// Etapa 2 para montar a �rvore de �reas (�tens do menu horizontal)
	public void recuperarFilhos(Funcionalidades pai,OptionTreeNode nopai){
		List<Funcionalidades> filhos = pai.getFuncionalidadesfilhas();
		for (Funcionalidades filho:filhos){
			OptionTreeNode child = new OptionTreeNode();
					
			// Se o obj contiver na listagem de areas da pessoa, marca o checkbox
			if(funcionalidadesgrupos != null && funcionalidadesgrupos.contains(filho)){
				filho.setChecked(true);
			}
			child.setFuncionalidade((filho));
			nopai.addChild(filho.getIdfuncionalidade(), child);
				        		 
			if(filho.getFuncionalidadesfilhas().size()>=1)
				recuperarFilhos(filho, child);
		}
	}
		
	// Altera as funcionalidades do grupo
	public void alterarfunc(){
		Iterator<Funcionalidades>it = funcionalidades.iterator();
		Set<Funcionalidades> funcgruposalt = new HashSet<Funcionalidades>();
				
		// Itera sobre a lista de �reas submetida. Verifica qual esta checada e passa para o Set que ser� persistido na base
		while(it.hasNext()){
			Funcionalidades obj = it.next();
			if(obj.isChecked()){
				funcgruposalt.add(obj);
			}
		}
		
		
		// Recupera funcionalidades setadas de outros m�dulos, pois ao submeter esse, ir� apagar os demais caso n�o recuperemos
		List<Funcionalidades> funcoutras = funcionalidadesint.recuperarDiferentes(modulofunc,grupo);
		Iterator<Funcionalidades>ito = funcoutras.iterator();
		while(ito.hasNext()){
			Funcionalidades obj = ito.next();
			funcgruposalt.add(obj);
		}
		
		grupo.setGruposfuncionalidades(funcgruposalt);
		
		gruposint.alterar(grupo);
		//exibepopupfunc    = false;
	}

	public List<Grupos> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupos> grupos) {
		this.grupos = grupos;
	}

	public String getCnmgrupo() {
		return cnmgrupo;
	}

	public void setCnmgrupo(String cnmgrupo) {
		this.cnmgrupo = cnmgrupo;
	}

	public boolean isExibelistagem() {
		return exibelistagem;
	}

	public void setExibelistagem(boolean exibelistagem) {
		this.exibelistagem = exibelistagem;
	}

	public List<Grupos> getObjschecked() {
		return objschecked;
	}

	public void setObjschecked(List<Grupos> objschecked) {
		this.objschecked = objschecked;
	}

	public boolean isModalconfirmaacao() {
		return modalconfirmaacao;
	}

	public void setModalconfirmaacao(boolean modalconfirmaacao) {
		this.modalconfirmaacao = modalconfirmaacao;
	}

	public boolean isOcultaacoes() {
		return ocultaacoes;
	}

	public void setOcultaacoes(boolean ocultaacoes) {
		this.ocultaacoes = ocultaacoes;
	}

	public boolean isExibepopupeditar() {
		return exibepopupeditar;
	}

	public void setExibepopupeditar(boolean exibepopupeditar) {
		this.exibepopupeditar = exibepopupeditar;
	}

	public Grupos getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupos grupo) {
		this.grupo = grupo;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public boolean isExibepopupfunc() {
		return exibepopupfunc;
	}

	public void setExibepopupfunc(boolean exibepopupfunc) {
		this.exibepopupfunc = exibepopupfunc;
	}

	public GruposInt getGruposint() {
		return gruposint;
	}

	public void setGruposint(GruposInt gruposint) {
		this.gruposint = gruposint;
	}

	public FuncionalidadesInt getFuncionalidadesint() {
		return funcionalidadesint;
	}

	public void setFuncionalidadesint(FuncionalidadesInt funcionalidadesint) {
		this.funcionalidadesint = funcionalidadesint;
	}

	public Map<String, Object> getFiltros() {
		return filtros;
	}

	public void setFiltros(Map<String, Object> filtros) {
		this.filtros = filtros;
	}

	public OptionTreeNode getNosfunc() {
		if(exibepopupfunc){ // se n�o tiver esse IF ele vai tentar carregar antes do tempo
			initRichFacesTree();
		}
		return nosfunc;
	}

	public void setNosfunc(OptionTreeNode nosfunc) {
		this.nosfunc = nosfunc;
	}

	public List<Funcionalidades> getFuncionalidades() {
		return funcionalidades;
	}

	public void setFuncionalidades(List<Funcionalidades> funcionalidades) {
		this.funcionalidades = funcionalidades;
	}

	public List<Funcionalidades> getFuncionalidadesgrupos() {
		if(grupo!=null && grupo.getIdgrupo()!=null){
			funcionalidadesgrupos = funcionalidadesint.retornarFuncionalidadesGrupos(grupo);
		}
		return funcionalidadesgrupos;
	}

	public void setFuncionalidadesgrupos(List<Funcionalidades> funcionalidadesgrupos) {
		this.funcionalidadesgrupos = funcionalidadesgrupos;
	}

	public boolean isChamouinittree() {
		return chamouinittree;
	}

	public void setChamouinittree(boolean chamouinittree) {
		this.chamouinittree = chamouinittree;
	}

	public Modulos getModulofunc() {
		return modulofunc;
	}

	public void setModulofunc(Modulos modulofunc) {
		this.modulofunc = modulofunc;
	}

	public boolean isExibetreefunc() {
		return exibetreefunc;
	}

	public void setExibetreefunc(boolean exibetreefunc) {
		this.exibetreefunc = exibetreefunc;
	}

}
