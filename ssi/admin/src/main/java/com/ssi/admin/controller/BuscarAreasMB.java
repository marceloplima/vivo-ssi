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

import com.ssi.kernel.controller.interfaces.AreasInt;
import com.ssi.kernel.controller.interfaces.PapeisAreasInt;
import com.ssi.kernel.model.Areas;

@ManagedBean
@ViewScoped
public class BuscarAreasMB implements java.io.Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9149771641635733657L;

	@EJB
	private AreasInt interfaceint;
	
	@EJB
	private PapeisAreasInt papelareaint;
	
	private Areas obj = new Areas();
	
	private Map<String,Object>filtros = new LinkedHashMap<String,Object>();
	
	private List<Areas> objs;
	private List<Areas> objschecked = new ArrayList<Areas>();
	
	// Filtros
	private String cnmsiglaarea;
	
	private boolean exibelistagem = false;
	private boolean modalconfirmaacao = false;
	private boolean exibepopupeditar = false;
	
	//Controlador de exibi��o do select de a��es (disabled/enabled)
	private boolean ocultaacoes;
	
	private String acao; // a��o selecionada no combo de A��es
	
	@PostConstruct
    public void init() {
		
		obj = new Areas();
		
		// A��es vem inicialmente, ocultas
		ocultaacoes = true;
		
		setObjs(new ArrayList<Areas>());
		objschecked = new ArrayList<Areas>();
    }
	
	public List<Areas> recuperar(){
		return interfaceint.recuperar();
	}
	
	// Ao clicar em "Ativar/Desativar Selecionados" na view, seta o atributo "flagativo" pra true ou false em todos os marcados
	// E depois, destroi as marca��es
	public void ativarDesativarSelecionados(){
		Iterator<Areas> it = objschecked.iterator();
		while(it.hasNext()){
			Areas obj = it.next();
			if(obj.isFlagativo())
				obj.setFlagativo(false);
			else
				obj.setFlagativo(true);
			
			interfaceint.alterar(obj);
			obj.setChecked(false);
		}
		objschecked = new ArrayList<Areas>();
		modalconfirmaacao = false;
	}
	
	public void selecionarCheckbox(Areas obj){
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
		interfaceint.alterar(obj);
		// Seta o objeto completo, pra que possa aparecer corretamente na view
		obj.setAreapai(interfaceint.recuperarUnico(obj.getAreapai()));
		obj.setPapelarea(papelareaint.recuperarUnico(obj.getPapelarea()));
		exibepopupeditar = false;
	}
	
	public void buscar(){
		// Cria um objeto com o filtro informado e passa para o ejb
		Areas obj = new Areas();
		obj.setCnmsiglaarea(cnmsiglaarea);
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
		}
		
		acao = "0"; // reseta o combobox de a��es
	}

	public List<Areas> getObjs() {
		return objs;
	}

	public void setObjs(List<Areas> objs) {
		this.objs = objs;
	}

	public String getCnmsiglaarea() {
		return cnmsiglaarea;
	}

	public void setCnmsiglaarea(String cnmsiglaarea) {
		this.cnmsiglaarea = cnmsiglaarea;
	}

	public boolean isExibelistagem() {
		return exibelistagem;
	}

	public void setExibelistagem(boolean exibelistagem) {
		this.exibelistagem = exibelistagem;
	}

	public List<Areas> getObjschecked() {
		return objschecked;
	}

	public void setObjschecked(List<Areas> objschecked) {
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

	public Areas getObj() {
		return obj;
	}

	public void setObj(Areas obj) {
		this.obj = obj;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

}
