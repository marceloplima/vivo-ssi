package com.ssi.admin.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.kernel.controller.interfaces.GruposModulosInt;
import com.ssi.kernel.controller.interfaces.ModulosInt;
import com.ssi.kernel.model.GruposModulos;
import com.ssi.kernel.model.Modulos;

@ManagedBean
@ViewScoped
public class BuscarGruposModulosMB implements java.io.Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9149771641635733657L;

	@EJB
	private ModulosInt modulosint;
	
	@EJB
	private GruposModulosInt gruposmodulosint;
	
	private GruposModulos grupomodulo;
	
	private Map<String,Object>filtros = new LinkedHashMap<String,Object>();
	private List<Modulos> modulos;
	private List<GruposModulos> gruposmodulos;
	private List<GruposModulos> gruposmoduloschecked = new ArrayList<GruposModulos>();
	
	// Filtros
	private String cnmgrupomodulo;
	private Modulos modulo;
	
	private boolean exibelistagem = false;
	private boolean modalconfirmaacaogrupomodulo = false;
	private boolean exibepopupeditar = false;
	
	//Controlador de exibi��o do select de a��es (disabled/enabled)
	private boolean ocultaacoes;
	
	private String acao; // a��o selecionada no combo de A��es
	
	@PostConstruct
    public void init() {
		
		 grupomodulo = new GruposModulos();
		
		// A��es vem inicialmente, ocultas
		ocultaacoes = true;
		
		setGruposmodulos(new ArrayList<GruposModulos>());
		gruposmoduloschecked = new ArrayList<GruposModulos>();
    }
	
	// Ao clicar em "Ativar/Desativar Selecionados" na view, seta o atributo "flagativo" pra true ou false em todos os marcados
	// E depois, destroi as marca��es
	public void ativarDesativarSelecionados(){
		Iterator<GruposModulos> it = gruposmoduloschecked.iterator();
		while(it.hasNext()){
			GruposModulos obj = it.next();
			if(obj.isFlagativo())
				obj.setFlagativo(false);
			else
				obj.setFlagativo(true);
			
			gruposmodulosint.alterar(obj);
			obj.setChecked(false);
		}
		gruposmoduloschecked = new ArrayList<GruposModulos>();
		modalconfirmaacaogrupomodulo = false;
	}
	
	public void selecionarCheckboxGrupoModulo(GruposModulos grupomodulo){
		if(gruposmoduloschecked.contains(grupomodulo)){
			grupomodulo.setChecked(false);
			gruposmoduloschecked.remove(grupomodulo);
		}else{
			grupomodulo.setChecked(true);
			gruposmoduloschecked.add(grupomodulo);
		}
		
		// Verifica se apenas 1 checkbox est� checado. Caso positivo, habilita A��es
		setOcultaacoes(true);
		if(gruposmoduloschecked.size() == 1){
			setOcultaacoes(false);
		}
	}
	
	public void fecharPopups(){
		setExibepopupeditar(false);
		modalconfirmaacaogrupomodulo = false;
	}
	
	public void alterar(){
		gruposmodulosint.alterar(grupomodulo);
		// Seta o objeto completo do Modulo
		grupomodulo.setModulo(modulosint.recuperarUnico(grupomodulo.getModulo().getIdmodulo()));
		exibepopupeditar = false;
	}
	
	public void buscar(){
		// Cria um objeto com o filtro informado e passa para o ejb
		GruposModulos grupomodulo = new GruposModulos();
		grupomodulo.setCnmgrupomodulo(cnmgrupomodulo);
		grupomodulo.setModulo(modulo);
		filtros.put("grupomodulo",grupomodulo);
		setGruposmodulos(gruposmodulosint.recuperarFiltrado(filtros));
		grupomodulo = null;
		exibelistagem = true;
	}
	
	// Quando o usu�rio seleciona uma a��o no select da view, esse m�todo � acionado
	public void efetuaracao(){
			
		// Mantem todos os popups com rendered=false antes do processameto das a��es para mante-los fechados
		fecharPopups();
			
		// S� permite usar esse recurso caso tenha sido selecionado apenas um registro
		if(gruposmoduloschecked.size() == 1){
				
			// Seta o objeto referenciando GruposModulos com o da List vinda do checkbox da busca
			grupomodulo = gruposmoduloschecked.get(0);
				
			if(acao.equals("1")){ // A��o: Editar
				exibepopupeditar = true;
			}
		}
			
		acao = "0"; // reseta o combobox de a��es
	}

	public List<GruposModulos> getGruposmodulos() {
		return gruposmodulos;
	}

	public void setGruposmodulos(List<GruposModulos> gruposmodulos) {
		this.gruposmodulos = gruposmodulos;
	}

	public String getCnmgrupomodulo() {
		return cnmgrupomodulo;
	}

	public void setCnmgrupomodulo(String cnmgrupomodulo) {
		this.cnmgrupomodulo = cnmgrupomodulo;
	}

	public Modulos getModulo() {
		return modulo;
	}

	public void setModulo(Modulos modulo) {
		this.modulo = modulo;
	}

	public List<Modulos> getModulos() {
		modulos = modulosint.recuperar();
		return modulos;
	}

	public void setModulos(List<Modulos> modulos) {
		this.modulos = modulos;
	}

	public boolean isExibelistagem() {
		return exibelistagem;
	}

	public void setExibelistagem(boolean exibelistagem) {
		this.exibelistagem = exibelistagem;
	}

	public List<GruposModulos> getGruposmoduloschecked() {
		return gruposmoduloschecked;
	}

	public void setGruposmoduloschecked(List<GruposModulos> gruposmoduloschecked) {
		this.gruposmoduloschecked = gruposmoduloschecked;
	}

	public boolean isModalconfirmaacaogrupomodulo() {
		return modalconfirmaacaogrupomodulo;
	}

	public void setModalconfirmaacaogrupomodulo(boolean modalconfirmaacaogrupomodulo) {
		this.modalconfirmaacaogrupomodulo = modalconfirmaacaogrupomodulo;
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

	public GruposModulos getGrupomodulo() {
		return grupomodulo;
	}

	public void setGrupomodulo(GruposModulos grupomodulo) {
		this.grupomodulo = grupomodulo;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

}
