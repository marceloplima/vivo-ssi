package com.ssi.regulatorio.web.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
	
	private boolean exibelistagem = false;
	private boolean modalconfirmaacao = false;
	
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
		
		Modulos modulo = new Modulos();
		modulo.setIdmodulo(338L);
		filtros.put("modulo", modulo);
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
		modalconfirmaacao = false;
	}
	
	
	public void buscar(){
		// Cria um objeto com o filtro informado e passa para o ejb
		
		setNoticias(noticiasint.recuperarFiltrado(filtros));
		exibelistagem = true;
	}
	

	public List<Noticias> getNoticias() {
		buscar();
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

}
