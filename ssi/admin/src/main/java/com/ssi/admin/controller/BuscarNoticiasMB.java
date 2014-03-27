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

import com.ssi.kernel.controller.interfaces.ModulosInt;
import com.ssi.kernel.controller.interfaces.NoticiasInt;
import com.ssi.kernel.model.Modulos;
import com.ssi.kernel.model.Noticias;

@ManagedBean
@ViewScoped
public class BuscarNoticiasMB implements java.io.Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9149771641635733657L;

	@EJB
	private NoticiasInt noticiasint;
	
	@EJB
	private ModulosInt modulosint;
	
	private Noticias noticia = new Noticias();
	
	private Map<String,Object>filtros = new LinkedHashMap<String,Object>();
	
	private List<Noticias> noticias;
	private List<Noticias> objschecked = new ArrayList<Noticias>();
	
	// Filtros
	private String cnmnoticia;
	private String cnmtitulo;
	private Modulos modulo;
	private String flagativo;
	
	private boolean exibelistagem = false;
	private boolean modalconfirmaacao = false;
	private boolean exibepopupeditar = false;
	private boolean exibepopupmodulos = false;
	
	private List<Modulos> modulosnoticias = new ArrayList<Modulos>();
	private List<Modulos> modulos;
	private List<Modulos> moduloschecked = new ArrayList<Modulos>();
	
	//Controlador de exibi��o do select de a��es (disabled/enabled)
	private boolean ocultaacoes;
	
	private String acao; // a��o selecionada no combo de A��es
	
	@PostConstruct
    public void init() {
		
		noticia = new Noticias();
		
		// A��es vem inicialmente, ocultas
		ocultaacoes = true;
		
		setNoticias(new ArrayList<Noticias>());
		objschecked = new ArrayList<Noticias>();
    }
	
	// Configura os modulos da not�cia selecionada
	public void configurarModulos(){
		Iterator<Modulos>it = modulos.iterator();
		Set<Modulos> alt = new HashSet<Modulos>();
				
		// Itera sobre a lista submetida. Verifica qual esta checado e passa para o Set que ser� persistido na base
		while(it.hasNext()){
			Modulos obj = it.next();
			if(obj.isChecked()){
				alt.add(obj);
			}
		}
		noticia.setNoticiasmodulos(alt);
		noticiasint.alterar(noticia);
	}
	
	// Ao clicar em "Ativar/Desativar Selecionados" na view, seta o atributo "flagativo" pra true ou false em todos os marcados
	// E depois, destroi as marca��es
	public void ativarDesativarSelecionados(){
		Iterator<Noticias> it = objschecked.iterator();
		while(it.hasNext()){
			Noticias obj = it.next();
			if(obj.isFlagativo())
				obj.setFlagativo(false);
			else
				obj.setFlagativo(true);
			
			noticiasint.alterar(obj);
			obj.setChecked(false);
		}
		objschecked = new ArrayList<Noticias>();
		modalconfirmaacao = false;
	}
	
	public void selecionarCheckbox(Noticias obj){
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
		exibepopupmodulos = false;
	}
	
	public void alterar(){
		List<Modulos> moduloschecked = new ArrayList<Modulos>();
		Iterator<Modulos>it = modulos.iterator();
		while(it.hasNext()){
			Modulos o = it.next();
			if(o.isChecked()){
				moduloschecked.add(o);
			}
		}
		noticia.setNoticiasmodulos(new HashSet<Modulos>(moduloschecked));
		noticiasint.alterar(noticia);
		exibepopupeditar = false;
	}
	
	public void buscar(){
		// Cria um objeto com o filtro informado e passa para o ejb
		Noticias obj = new Noticias();
		obj.setCnmnoticia(cnmnoticia);
		obj.setCnmtitulo(cnmtitulo);
		if(flagativo!=null && flagativo.length()>=1)
			obj.setFlagativo(new Boolean(flagativo));
		filtros.put("noticia",obj);
		filtros.put("modulo", modulo);
		setNoticias(noticiasint.recuperarFiltrado(filtros));
		exibelistagem = true;
	}
	
	// Quando o usu�rio seleciona uma a��o no select da view, esse m�todo � acionado
	public void efetuaracao(){
			
		// Mantem todos os popups com rendered=false antes do processameto das a��es para mante-los fechados
		fecharPopups();
			
		// S� permite usar esse recurso caso tenha sido selecionado apenas um registro
		if(objschecked.size() == 1){
				
			// Seta o objeto referenciando GruposModulos com o da List vinda do checkbox da busca
			noticia = objschecked.get(0);
				
			if(acao.equals("1")){ // A��o: Editar
				exibepopupeditar = true;
			}
			if(acao.equals("2")){ // A��o: M�dulos
				exibepopupmodulos = true;
			}
		}
			
		acao = "0"; // reseta o combobox de a��es
	}

	public List<Noticias> getNoticias() {
		return noticias;
	}

	public void setNoticias(List<Noticias> noticias) {
		this.noticias = noticias;
	}
	
	public String getCnmtitulo() {
		return cnmtitulo;
	}

	public void setCnmtitulo(String cnmtitulo) {
		this.cnmtitulo = cnmtitulo;
	}

	public String getCnmnoticia() {
		return cnmnoticia;
	}

	public void setCnmnoticia(String cnmnoticia) {
		this.cnmnoticia = cnmnoticia;
	}

	public boolean isExibelistagem() {
		return exibelistagem;
	}

	public void setExibelistagem(boolean exibelistagem) {
		this.exibelistagem = exibelistagem;
	}

	public List<Noticias> getObjschecked() {
		return objschecked;
	}

	public void setObjschecked(List<Noticias> objschecked) {
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

	public Noticias getNoticia() {
		return noticia;
	}

	public void setNoticias(Noticias noticia) {
		this.noticia = noticia;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public boolean isExibepopupmodulos() {
		return exibepopupmodulos;
	}

	public void setExibepopupmodulos(boolean exibepopupmodulos) {
		this.exibepopupmodulos = exibepopupmodulos;
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
		modulos = modulosint.recuperar();
		Iterator<Modulos> it = modulos.iterator();
		while(it.hasNext()){
			Modulos o = it.next();
			if(getModulosnoticias().contains(o)){
				o.setChecked(true);
			}
		}
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

	public Modulos getModulo() {
		return modulo;
	}

	public void setModulo(Modulos modulo) {
		this.modulo = modulo;
	}

	public String getFlagativo() {
		return flagativo;
	}

	public void setFlagativo(String flagativo) {
		this.flagativo = flagativo;
	}

}
