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

import com.ssi.kernel.controller.interfaces.TiposTelefoneInt;
import com.ssi.kernel.model.TiposTelefone;

@ManagedBean
@ViewScoped
public class BuscarTiposTelefoneMB implements java.io.Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9149771641635733657L;

	@EJB 
	private TiposTelefoneInt tipostelefoneint;
	
	private TiposTelefone tipotelefone = new TiposTelefone();
	
	private Map<String,Object>filtros = new LinkedHashMap<String,Object>();
	
	private List<TiposTelefone> tipostelefone;
	private List<TiposTelefone> objschecked = new ArrayList<TiposTelefone>();
	
	// Filtros
	private String cnmtipotelefone;
	
	private boolean exibelistagem = false;
	private boolean modalconfirmaacao = false;
	private boolean exibepopupeditar = false;
	
	//Controlador de exibi��o do select de a��es (disabled/enabled)
	private boolean ocultaacoes;
	
	private String acao; // a��o selecionada no combo de A��es
	
	@PostConstruct
    public void init() {
		
		tipotelefone = new TiposTelefone();
		
		// A��es vem inicialmente, ocultas
		ocultaacoes = true;
		
		setTipostelefone(new ArrayList<TiposTelefone>());
		objschecked = new ArrayList<TiposTelefone>();
    }
	
	// Ao clicar em "Ativar/Desativar Selecionados" na view, seta o atributo "flagativo" pra true ou false em todos os marcados
	// E depois, destroi as marca��es
	public void ativarDesativarSelecionados(){
		Iterator<TiposTelefone> it = objschecked.iterator();
		while(it.hasNext()){
			TiposTelefone obj = it.next();
			if(obj.isFlagativo())
				obj.setFlagativo(false);
			else
				obj.setFlagativo(true);
			
			tipostelefoneint.alterar(obj);
			obj.setChecked(false);
		}
		objschecked = new ArrayList<TiposTelefone>();
		modalconfirmaacao = false;
	}
	
	public void selecionarCheckbox(TiposTelefone obj){
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
		tipostelefoneint.alterar(tipotelefone);
		exibepopupeditar = false;
	}
	
	public void buscar(){
		// Cria um objeto com o filtro informado e passa para o ejb
		TiposTelefone obj = new TiposTelefone();
		obj.setCnmtipotelefone(cnmtipotelefone);
		filtros.put("tipotelefone",obj);
		setTipostelefone(tipostelefoneint.recuperarFiltrado(filtros));
		exibelistagem = true;
	}
	
	// Quando o usu�rio seleciona uma a��o no select da view, esse m�todo � acionado
	public void efetuaracao(){
			
		// Mantem todos os popups com rendered=false antes do processameto das a��es para mante-los fechados
		fecharPopups();
			
		// S� permite usar esse recurso caso tenha sido selecionado apenas um registro
		if(objschecked.size() == 1){
				
			// Seta o objeto referenciando GruposModulos com o da List vinda do checkbox da busca
			tipotelefone = objschecked.get(0);
				
			if(acao.equals("1")){ // A��o: Editar
				exibepopupeditar = true;
			}
		}
			
		acao = "0"; // reseta o combobox de a��es
	}

	public List<TiposTelefone> getTipostelefone() {
		return tipostelefone;
	}

	public void setTipostelefone(List<TiposTelefone> tipostelefone) {
		this.tipostelefone = tipostelefone;
	}

	public String getCnmtipotelefone() {
		return cnmtipotelefone;
	}

	public void setCnmtipotelefone(String cnmtipotelefone) {
		this.cnmtipotelefone = cnmtipotelefone;
	}

	public boolean isExibelistagem() {
		return exibelistagem;
	}

	public void setExibelistagem(boolean exibelistagem) {
		this.exibelistagem = exibelistagem;
	}

	public List<TiposTelefone> getObjschecked() {
		return objschecked;
	}

	public void setObjschecked(List<TiposTelefone> objschecked) {
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

	public TiposTelefone getTipotelefone() {
		return tipotelefone;
	}

	public void setTipotelefone(TiposTelefone tipotelefone) {
		this.tipotelefone = tipotelefone;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

}
