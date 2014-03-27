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

import com.ssi.kernel.controller.interfaces.TiposFornecedorInt;
import com.ssi.kernel.model.TiposFornecedor;

@ManagedBean
@ViewScoped
public class BuscarTiposFornecedorMB implements java.io.Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9149771641635733657L;

	@EJB 
	private TiposFornecedorInt tiposfornecedorint;
	
	private TiposFornecedor tipofornecedor = new TiposFornecedor();
	
	private Map<String,Object>filtros = new LinkedHashMap<String,Object>();
	
	private List<TiposFornecedor> tiposfornecedor;
	private List<TiposFornecedor> objschecked = new ArrayList<TiposFornecedor>();
	
	// Filtros
	private String cnmtipofornecedor;
	
	private boolean exibelistagem = false;
	private boolean modalconfirmaacao = false;
	private boolean exibepopupeditar = false;
	
	//Controlador de exibi��o do select de a��es (disabled/enabled)
	private boolean ocultaacoes;
	
	private String acao; // a��o selecionada no combo de A��es
	
	@PostConstruct
    public void init() {
		
		tipofornecedor = new TiposFornecedor();
		
		// A��es vem inicialmente, ocultas
		ocultaacoes = true;
		
		setTiposfornecedor(new ArrayList<TiposFornecedor>());
		objschecked = new ArrayList<TiposFornecedor>();
    }
	
	// Ao clicar em "Ativar/Desativar Selecionados" na view, seta o atributo "flagativo" pra true ou false em todos os marcados
	// E depois, destroi as marca��es
	public void ativarDesativarSelecionados(){
		Iterator<TiposFornecedor> it = objschecked.iterator();
		while(it.hasNext()){
			TiposFornecedor obj = it.next();
			if(obj.isFlagativo())
				obj.setFlagativo(false);
			else
				obj.setFlagativo(true);
			
			tiposfornecedorint.alterar(obj);
			obj.setChecked(false);
		}
		objschecked = new ArrayList<TiposFornecedor>();
		modalconfirmaacao = false;
	}
	
	public void selecionarCheckbox(TiposFornecedor obj){
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
	}
	
	public void alterar(){
		tiposfornecedorint.alterar(tipofornecedor);
		exibepopupeditar = false;
	}
	
	public void buscar(){
		// Cria um objeto com o filtro informado e passa para o ejb
		TiposFornecedor obj = new TiposFornecedor();
		obj.setCnmtipofornecedor(cnmtipofornecedor);
		filtros.put("tipofornecedor",obj);
		setTiposfornecedor(tiposfornecedorint.recuperarFiltrado(filtros));
		exibelistagem = true;
	}
	
	// Quando o usu�rio seleciona uma a��o no select da view, esse m�todo � acionado
	public void efetuaracao(){
			
		// Mantem todos os popups com rendered=false antes do processameto das a��es para mante-los fechados
		fecharPopups();
			
		// S� permite usar esse recurso caso tenha sido selecionado apenas um registro
		if(objschecked.size() == 1){
				
			// Seta o objeto referenciando GruposModulos com o da List vinda do checkbox da busca
			tipofornecedor = objschecked.get(0);
				
			if(acao.equals("1")){ // A��o: Editar
				exibepopupeditar = true;
			}
		}
			
		acao = "0"; // reseta o combobox de a��es
	}

	public List<TiposFornecedor> getTiposfornecedor() {
		return tiposfornecedor;
	}

	public void setTiposfornecedor(List<TiposFornecedor> tiposfornecedor) {
		this.tiposfornecedor = tiposfornecedor;
	}

	public String getCnmtipofornecedor() {
		return cnmtipofornecedor;
	}

	public void setCnmtipofornecedor(String cnmtipofornecedor) {
		this.cnmtipofornecedor = cnmtipofornecedor;
	}

	public boolean isExibelistagem() {
		return exibelistagem;
	}

	public void setExibelistagem(boolean exibelistagem) {
		this.exibelistagem = exibelistagem;
	}

	public List<TiposFornecedor> getObjschecked() {
		return objschecked;
	}

	public void setObjschecked(List<TiposFornecedor> objschecked) {
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

	public TiposFornecedor getTipofornecedor() {
		return tipofornecedor;
	}

	public void setTipofornecedor(TiposFornecedor tipofornecedor) {
		this.tipofornecedor = tipofornecedor;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

}
