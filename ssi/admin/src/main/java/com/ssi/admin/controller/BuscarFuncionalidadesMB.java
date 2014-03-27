package com.ssi.admin.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.kernel.controller.interfaces.FuncionalidadesInt;
import com.ssi.kernel.controller.interfaces.ModulosInt;
import com.ssi.kernel.controller.interfaces.TiposFuncionalidadeInt;
import com.ssi.kernel.model.Funcionalidades;
import com.ssi.kernel.model.Modulos;
import com.ssi.kernel.model.TiposFuncionalidade;

@ManagedBean
@ViewScoped
public class BuscarFuncionalidadesMB implements java.io.Serializable{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7537393602398363651L;

	@EJB
	private ModulosInt modulosint;
	
	@EJB
	private FuncionalidadesInt funcionalidadesint;
	
	@EJB
	private TiposFuncionalidadeInt tiposfuncionalidadeint;
	
	private List<Funcionalidades> funcionalidades;
	private Funcionalidades funcionalidade = new Funcionalidades();
	private List<Modulos> modulos;
	private List<TiposFuncionalidade> tiposfuncionalidade;
	private List<Funcionalidades> funcionalidadespai;
	
	private Map<String,Object>filtros = new LinkedHashMap<String,Object>();
	
	// Filtros
	private String cnmmodulo;
	private Modulos modulo = new Modulos();
	
	private boolean edit;
	
	@PostConstruct
    public void init() {
		funcionalidades = new ArrayList<Funcionalidades>();
		modulos = new ArrayList<Modulos>();
		tiposfuncionalidade = new ArrayList<TiposFuncionalidade>();
		funcionalidadespai = new ArrayList<Funcionalidades>();
    }
	
	public void zerarEditorFuncionalidades(){
		funcionalidade      = new Funcionalidades();
		modulos             = new ArrayList<Modulos>();
		tiposfuncionalidade = new ArrayList<TiposFuncionalidade>();
		funcionalidadespai  = new ArrayList<Funcionalidades>();
		setEdit(false);
	}
	
	public void buscarPorModulo(Modulos modulo){
		zerarEditorFuncionalidades();
		setModulo(modulo);
		
		Map<String,Object> filtros = new LinkedHashMap<String,Object>();
		filtros.put("modulo", getModulo());
		this.filtros = filtros;
	}
	
	public void delete(Funcionalidades funcionalidade) {
    	funcionalidades.remove(funcionalidade);
    	funcionalidadesint.remover(funcionalidade);
    }
	
    public void edit(Funcionalidades funcionalidade) {
    	setEdit(true);
        this.funcionalidade = funcionalidade;
        modulos = modulosint.recuperar();
        tiposfuncionalidade = tiposfuncionalidadeint.recuperar();
        funcionalidadespai = funcionalidadesint.recuperarPossiveisPais(funcionalidade);
    }
    
    public void save() {
    	funcionalidadesint.alterar(getFuncionalidade());
    	funcionalidade.setModulofuncionalidade(modulosint.recuperarUnico(funcionalidade.getModulofuncionalidade().getIdmodulo()));
        funcionalidade.setTipofuncionalidade(tiposfuncionalidadeint.recuperarUnico(funcionalidade.getTipofuncionalidade().getIdtipofuncionalidade()));
        
        if(funcionalidade.getFuncionalidadepai()!=null && funcionalidade.getFuncionalidadepai().getIdfuncionalidade()!=null)
        	funcionalidade.setFuncionalidadepai(funcionalidadesint.recuperarPai(funcionalidade));
        
        // Remove da lista da view caso o modulo tenha sido alterado
        if(getModulo().getIdmodulo().toString().equals(funcionalidade.getModulofuncionalidade().getIdmodulo().toString())==false)
        	funcionalidades.remove(funcionalidade);
        
    	funcionalidade = new Funcionalidades();
        edit = false;
    }
    
	public FuncionalidadesDataModel getDataModel() {
        return new FuncionalidadesDataModel(funcionalidadesint,filtros);
    }

	public Modulos getModulo() {
		return modulo;
	}

	public void setModulo(Modulos modulo) {
		this.modulo = modulo;
	}

	public List<Funcionalidades> getFuncionalidades() {
		return funcionalidades;
	}

	public void setFuncionalidades(List<Funcionalidades> funcionalidades) {
		this.funcionalidades = funcionalidades;
	}

	public String getCnmmodulo() {
		return cnmmodulo;
	}

	public void setCnmmodulo(String cnmmodulo) {
		this.cnmmodulo = cnmmodulo;
	}

	public Funcionalidades getFuncionalidade() {
		return funcionalidade;
	}

	public void setFuncionalidade(Funcionalidades funcionalidade) {
		this.funcionalidade = funcionalidade;
	}

	public List<Modulos> getModulos() {
		return modulos;
	}

	public void setModulos(List<Modulos> modulos) {
		this.modulos = modulos;
	}

	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	public List<TiposFuncionalidade> getTiposfuncionalidade() {
		return tiposfuncionalidade;
	}

	public void setTiposfuncionalidade(List<TiposFuncionalidade> tiposfuncionalidade) {
		this.tiposfuncionalidade = tiposfuncionalidade;
	}

	public List<Funcionalidades> getFuncionalidadespai() {
		return funcionalidadespai;
	}

	public void setFuncionalidadespai(List<Funcionalidades> funcionalidadespai) {
		this.funcionalidadespai = funcionalidadespai;
	}

	public Map<String,Object> getFiltros() {
		return filtros;
	}

	public void setFiltros(Map<String,Object> filtros) {
		this.filtros = filtros;
	}

}
