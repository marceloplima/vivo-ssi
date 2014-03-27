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

import com.ssi.kernel.controller.interfaces.AreasInt;
import com.ssi.kernel.controller.interfaces.AtividadesInt;
import com.ssi.kernel.model.Areas;
import com.ssi.kernel.model.Atividades;
import com.ssi.kernel.model.Pessoas;

@ManagedBean
@ViewScoped
public class BuscarAtividadesMB implements java.io.Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9149771641635733657L;

	@EJB
	private AtividadesInt interfaceint;
	
	@EJB
	private AreasInt areasint;
	
	private Atividades obj = new Atividades();
	
	private Map<String,Object>filtros = new LinkedHashMap<String,Object>();
	
	private List<Atividades> objs;
	private List<Atividades> objschecked = new ArrayList<Atividades>();
	
	// Filtros
	private String cnmatividade;
	
	private boolean exibelistagem = false;
	private boolean modalconfirmaacao = false;
	private boolean exibepopupeditar = false;
	
	private boolean exibepopupareas = false;
	private boolean exibepopupcopiados = false;
	
	//Controlador de exibi��o do select de a��es (disabled/enabled)
	private boolean ocultaacoes;
	
	private String acao; // a��o selecionada no combo de A��es
	
	private List<Areas> areasatividade = new ArrayList<Areas>();
	private List<Areas> areas;
	private OptionTreeNode areanos = new OptionTreeNode();
	private boolean chamouinittree = false;
	private boolean expandirnode = true;
	
	private List<Pessoas> pessoasGruposCopiados = new ArrayList<Pessoas>();
	
	@PostConstruct
    public void init() {
		
		obj = new Atividades();
		
		// A��es vem inicialmente, ocultas
		ocultaacoes = true;
		
		setObjs(new ArrayList<Atividades>());
		objschecked = new ArrayList<Atividades>();
    }
	
	public List<Atividades> recuperar(){
		return interfaceint.recuperar();
	}
	
	// Ao clicar em "Ativar/Desativar Selecionados" na view, seta o atributo "flagativo" pra true ou false em todos os marcados
	// E depois, destroi as marca��es
	public void ativarDesativarSelecionados(){
		Iterator<Atividades> it = objschecked.iterator();
		while(it.hasNext()){
			Atividades obj = it.next();
			if(obj.isFlagativo())
				obj.setFlagativo(false);
			else
				obj.setFlagativo(true);
			
			interfaceint.alterar(obj);
			obj.setChecked(false);
		}
		objschecked = new ArrayList<Atividades>();
		modalconfirmaacao = false;
	}
	
	public void selecionarCheckbox(Atividades obj){
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
		setExibepopupeditar(false);
		modalconfirmaacao = false;
		exibepopupareas = false;
		exibepopupcopiados = false;
	}
	
	public void alterar(){
		interfaceint.alterar(obj);
		// Seta o objeto completo, pra que possa aparecer corretamente na view
		//obj.setAreapai(interfaceint.recuperarUnico(obj.getAreapai()));
		exibepopupeditar = false;
	}
	
	public void buscar(){
		// Cria um objeto com o filtro informado e passa para o ejb
		Atividades obj = new Atividades();
		obj.setCnmatividade(cnmatividade);
		filtros.put("obj", obj);
		setObjs(interfaceint.recuperarFiltrado(filtros));
		exibelistagem = true;
	}
	
	// Quando o usu�rio seleciona uma a��o no select da view, esse m�todo � acionado
	public void efetuaracao(){
			
		// Mantem todos os popups com rendered=false antes do processameto das a��es para mante-los fechados
		fecharPopups();
			
		// S� permite usar esse recurso caso tenha sido selecionado apenas um registro
		if(objschecked.size() == 1){
				
			// Seta o objeto referenciando GruposModulos com o da List vinda do checkbox da busca
			obj = objschecked.get(0);
				
			if(acao.equals("1")){ // A��o: Editar
				exibepopupeditar = true;
			}
			if(acao.equals("2")){ // A��o: Areas
				exibepopupareas = true;
			}
			if(acao.equals("3")){ // A��o: Copiados
				exibepopupcopiados = true;
			}
			
		}
		
		acao = "0"; // reseta o combobox de a��es
	}
	
	// Etapa 1 para montar a �rvore de �reas (unidades organizacionais)
	public void initRichFacesTree() {
		areanos = new OptionTreeNode();
		areas = areasint.recuperar();
		getAreasatividade();
		for (Areas obj:areas){
			if(obj.getAreapai()==null){
				OptionTreeNode raiz = new OptionTreeNode();
						
				// Se o obj contiver na listagem de areas, marca o checkbox
				if(areasatividade != null && areasatividade.contains(obj)){
					obj.setChecked(true);
				}
				raiz.setArea(obj);
				areanos.addChild(obj.getIdarea(), raiz);
				            		
				if(obj.getAreasfilho().size()>=1){
					recuperarFilhos(obj,raiz);
				}
			}
		}
	}
				
	// Etapa 2 para montar a �rvore de �reas (unidades organizacionais)
	public void recuperarFilhos(Areas pai,OptionTreeNode nopai){
		List<Areas> filhos = pai.getAreasfilho();
		for (Areas filho:filhos){
			OptionTreeNode child = new OptionTreeNode();
				
			// Se o obj contiver na listagem de areas da pessoa, marca o checkbox
			if(areasatividade != null && areasatividade.contains(filho)){
				filho.setChecked(true);
			}
			child.setArea(filho);
			nopai.addChild(filho.getIdarea(), child);
			        		 
			if(filho.getAreasfilho().size()>=1)
				recuperarFilhos(filho, child);
		}
	}	
	
	// Altera a �rea
	public void alterararea(){
		Iterator<Areas>it = areas.iterator();
		Set<Areas> areasaalt = new HashSet<Areas>();
			
		// Itera sobre a lista de �reas submetida. Verifica qual esta checada e passa para o Set que ser� persistido na base
		while(it.hasNext()){
			Areas obj = it.next();
			if(obj.isChecked()){
				areasaalt.add(obj);
			}
		}
		obj.setAtividadesareas(areasaalt);
		interfaceint.alterar(obj);
	}
	
	// Altera os copiados
	public void alterarcopiados(){
		Iterator<Pessoas>it = pessoasGruposCopiados.iterator();
		Set<Pessoas> pessoasGruposCopiadosAlt = new HashSet<Pessoas>();
				
		// Itera sobre a lista submetida. Verifica qual esta checado e passa para o Set que ser� persistido na base
		while(it.hasNext()){
			Pessoas obj = it.next();
			if(obj.isChecked()){
				pessoasGruposCopiadosAlt.add(obj);
			}
		}
		obj.setCopiadosatividades(pessoasGruposCopiadosAlt);
		interfaceint.alterar(obj);
	}
		
	public OptionTreeNode getAreanos() {
		if(exibepopupareas){ // se n�o tiver esse IF ele vai tentar carregar antes do tempo
			initRichFacesTree();
		}
		return areanos;
	}
	public void setAreanos(OptionTreeNode areanos) {
		this.areanos = areanos;
	}
		
	public boolean isChamouinittree() {
		return chamouinittree;
	}
	public void setChamouinittree(boolean chamouinittree) {
		this.chamouinittree = chamouinittree;
	}

	public List<Atividades> getObjs() {
		return objs;
	}

	public void setObjs(List<Atividades> objs) {
		this.objs = objs;
	}

	public String getCnmatividade() {
		return cnmatividade;
	}

	public void setCnmatividade(String cnmatividade) {
		this.cnmatividade = cnmatividade;
	}

	public boolean isExibelistagem() {
		return exibelistagem;
	}

	public void setExibelistagem(boolean exibelistagem) {
		this.exibelistagem = exibelistagem;
	}

	public List<Atividades> getObjschecked() {
		return objschecked;
	}

	public void setObjschecked(List<Atividades> objschecked) {
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

	public Atividades getObj() {
		return obj;
	}

	public void setObj(Atividades obj) {
		this.obj = obj;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public boolean isExibepopupareas() {
		return exibepopupareas;
	}

	public void setExibepopupareas(boolean exibepopupareas) {
		this.exibepopupareas = exibepopupareas;
	}

	public List<Areas> getAreasatividade() {
		if(obj!=null && obj.getIdatividade()!=null){
			areasatividade = interfaceint.retornarAreasAtividade(obj);
		}
		return areasatividade;
	}
	
	public void setAreasatividade(List<Areas> areasatividade) {
		this.areasatividade = areasatividade;
	}

	public boolean isExpandirnode() {
		return expandirnode;
	}

	public void setExpandirnode(boolean expandirnode) {
		this.expandirnode = expandirnode;
	}

	public boolean isExibepopupcopiados() {
		return exibepopupcopiados;
	}

	public void setExibepopupcopiados(boolean exibepopupcopiados) {
		this.exibepopupcopiados = exibepopupcopiados;
	}

	public List<Pessoas> getPessoasGruposCopiados() {
		if(obj!=null && obj.getIdatividade()!=null){
			//if(pessoasGruposCopiados == null || pessoasGruposCopiados.size() == 0){
				List<Areas> areasatividade = interfaceint.retornarAreasAtividade(obj);
				Iterator<Areas> it = areasatividade.iterator();
				pessoasGruposCopiados = new ArrayList<Pessoas>();
				while(it.hasNext()){
					Areas a = it.next();
					pessoasGruposCopiados.addAll(areasint.recuperarPessoasArea(a));
				}
				
				// Recupera os j� gravados (se houver)
				List<Pessoas> pessoascopiadosgravados = interfaceint.retornarCopiadosAtividade(obj);
				
				for(Pessoas p:pessoasGruposCopiados){
					if(pessoascopiadosgravados!=null && pessoascopiadosgravados.contains(p)){
						p.setChecked(true);
					}
					
				}
			//}
		}
		return pessoasGruposCopiados;
	}

	public void setPessoasGruposCopiados(List<Pessoas> pessoasGruposCopiados) {
		this.pessoasGruposCopiados = pessoasGruposCopiados;
	}

}
