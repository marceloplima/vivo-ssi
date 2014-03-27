package com.ssi.admin.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.kernel.controller.interfaces.FornecedoresInt;
import com.ssi.kernel.controller.interfaces.TiposFornecedorInt;
import com.ssi.kernel.model.Fornecedores;
import com.ssi.kernel.model.TiposFornecedor;

@ManagedBean
@ViewScoped
public class BuscarFornecedoresMB implements java.io.Serializable{


	private static final long serialVersionUID = 9149771641635733657L;

	@EJB
	private FornecedoresInt fornecedoresint;
	
	@EJB
	private TiposFornecedorInt tiposfornecedorint;
	
	private Fornecedores fornecedor;
	
	private Map<String,Object>filtros = new LinkedHashMap<String,Object>();
	private List<Fornecedores> fornecedores;
	private List<Fornecedores> fornecedoreschecked = new ArrayList<Fornecedores>();
	private List<TiposFornecedor> tiposfornecedor;
	
	// Filtros
	private String cnmfornecedor;
	private TiposFornecedor tipofornecedor;
	
	private boolean exibelistagem = false;
	private boolean modalconfirmaacao = false;
	private boolean exibepopupeditar = false;
	
	//Controlador de exibi��o do select de a��es (disabled/enabled)
	private boolean ocultaacoes;
	
	private String acao; // a��o selecionada no combo de A��es
	
	@PostConstruct
    public void init() {
		
		fornecedor = new Fornecedores();
		
		// A��es vem inicialmente, ocultas
		ocultaacoes = true;
		
		setFornecedores(new ArrayList<Fornecedores>());
		fornecedoreschecked = new ArrayList<Fornecedores>();
    }
	
	// Ao clicar em "Ativar/Desativar Selecionados" na view, seta o atributo "flagativo" pra true ou false em todos os marcados
	// E depois, destroi as marca��es
	public void ativarDesativarSelecionados(){
		Iterator<Fornecedores> it = fornecedoreschecked.iterator();
		while(it.hasNext()){
			Fornecedores obj = it.next();
			if(obj.isFlagativo())
				obj.setFlagativo(false);
			else
				obj.setFlagativo(true);
			
			fornecedoresint.alterar(obj);
			obj.setChecked(false);
		}
		fornecedoreschecked = new ArrayList<Fornecedores>();
		modalconfirmaacao = false;
	}
	
	public void selecionarCheckbox(Fornecedores obj){
		if(fornecedoreschecked.contains(obj)){
			obj.setChecked(false);
			fornecedoreschecked.remove(obj);
		}else{
			obj.setChecked(true);
			fornecedoreschecked.add(obj);
		}
		
		// Verifica se apenas 1 checkbox est� checado. Caso positivo, habilita A��es
		setOcultaacoes(true);
		if(fornecedoreschecked.size() == 1){
			setOcultaacoes(false);
		}
	}
	
	public void fecharPopups(){
		setExibepopupeditar(false);
		modalconfirmaacao = false;
	}
	
	public void alterar(){
		
		List<TiposFornecedor>tiposchecked = new ArrayList<TiposFornecedor>();
		Iterator<TiposFornecedor>it = tiposfornecedor.iterator();
		while(it.hasNext()){
			TiposFornecedor t = it.next();
			if(t.isChecked()){
				tiposchecked.add(t);
			}
		}
		
		fornecedor.setTiposfornecedor(new HashSet<TiposFornecedor>(tiposchecked));
		fornecedoresint.alterar(fornecedor);
		exibepopupeditar = false;
		//fornecedoreschecked = new ArrayList<Fornecedores>();
	}
	
	public void buscar(){
		// Cria um objeto com o filtro informado e passa para o ejb
		Fornecedores fornecedor = new Fornecedores();
		fornecedor.setCnmfornecedor(cnmfornecedor);
		filtros.put("fornecedor",fornecedor);
		filtros.put("tipofornecedor",tipofornecedor);
		setFornecedores(fornecedoresint.recuperarFiltrado(filtros));
		fornecedor = null;
		exibelistagem = true;
		setOcultaacoes(true);
		acao = "0"; // reseta o combobox de a��es
		fornecedoreschecked = new ArrayList<Fornecedores>();
	}
	
	public List<TiposFornecedor> recuperarTiposfornecedorfornecedor(Fornecedores fornecedor) {
		return fornecedoresint.retornarTiposFornecedorFornecedor(fornecedor);
	}
	
	// Quando o usu�rio seleciona uma a��o no select da view, esse m�todo � acionado
	public void efetuaracao(){
			
		// Mantem todos os popups com rendered=false antes do processameto das a��es para mante-los fechados
		fecharPopups();
			
		// S� permite usar esse recurso caso tenha sido selecionado apenas um registro
		if(fornecedoreschecked.size() == 1){
				
			// Seta o objeto referenciando GruposModulos com o da List vinda do checkbox da busca
			fornecedor = fornecedoreschecked.get(0);
				
			if(acao.equals("1")){ // A��o: Editar
				exibepopupeditar = true;
			}
		}
			
		acao = "0"; // reseta o combobox de a��es
	}

	public List<Fornecedores> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedores> fornecedores) {
		this.fornecedores = fornecedores;
	}

	public String getCnmfornecedor() {
		return cnmfornecedor;
	}

	public void setCnmfornecedor(String cnmfornecedor) {
		this.cnmfornecedor = cnmfornecedor;
	}

	public boolean isExibelistagem() {
		return exibelistagem;
	}

	public void setExibelistagem(boolean exibelistagem) {
		this.exibelistagem = exibelistagem;
	}

	public List<Fornecedores> getFornecedoreschecked() {
		return fornecedoreschecked;
	}

	public void setFornecedoreschecked(List<Fornecedores> fornecedoreschecked) {
		this.fornecedoreschecked = fornecedoreschecked;
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

	public Fornecedores getFornecedor() {
		return fornecedor;
	}

	public void setFornecedores(Fornecedores fornecedor) {
		this.fornecedor = fornecedor;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public List<TiposFornecedor> getTiposfornecedor() {
		
		tiposfornecedor = tiposfornecedorint.recuperar(); // Lista completa
		
		if(fornecedor!=null && fornecedor.getIdfornecedor()!= null && fornecedor.getIdfornecedor()>=1){
			List<TiposFornecedor> fornecedoreschecked = fornecedoresint.retornarTiposFornecedorFornecedor(fornecedor); // Tipos desse forn espec�fico
			
			if(fornecedoreschecked!=null && fornecedoreschecked.size()>=1){
				Iterator<TiposFornecedor>it = tiposfornecedor.iterator();
				while(it.hasNext()){
					TiposFornecedor tf = it.next();
					if(fornecedoreschecked.contains(tf)){
						tf.setChecked(true);
					}
				}
			}
		}
		
		return tiposfornecedor;
	}

	public void setTiposfornecedor(List<TiposFornecedor> tiposfornecedor) {
		this.tiposfornecedor = tiposfornecedor;
	}

	public TiposFornecedor getTipofornecedor() {
		return tipofornecedor;
	}

	public void setTipofornecedor(TiposFornecedor tipofornecedor) {
		this.tipofornecedor = tipofornecedor;
	}

}
