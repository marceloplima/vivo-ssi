package com.ssi.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.kernel.controller.interfaces.FuncionalidadesInt;
import com.ssi.kernel.controller.interfaces.ModulosInt;
import com.ssi.kernel.controller.interfaces.TiposFuncionalidadeInt;
import com.ssi.kernel.model.Funcionalidades;
import com.ssi.kernel.model.TiposFuncionalidade;

@ManagedBean
@ViewScoped
public class CadFuncionalidadesMB implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6015910187308681117L;

	@EJB 
	private ModulosInt modulosint;
	
	@EJB 
	private FuncionalidadesInt funcionalidadesint;
	
	@EJB 
	private TiposFuncionalidadeInt tiposfuncionalidadeint;
	
	private List<Funcionalidades> funcionalidades;
	private List<TiposFuncionalidade> tiposfuncionalidade;
	private Funcionalidades funcionalidade = new Funcionalidades();
	private String cnmmodulo;
	private boolean edit;
	
	@PostConstruct
    public void init() {
		funcionalidades = new ArrayList<Funcionalidades>();
    }
	
	public List<TiposFuncionalidade> recuperarTiposFunc(){
		tiposfuncionalidade = tiposfuncionalidadeint.recuperar();
		return tiposfuncionalidade;
	}
	
    public void add() {
    	funcionalidade.setFlagativo(1);
        Funcionalidades funcrefresh = funcionalidadesint.incluir(funcionalidade);
        funcionalidade.setModulofuncionalidade(modulosint.recuperarUnico(funcrefresh.getModulofuncionalidade().getIdmodulo()));
        funcionalidade.setTipofuncionalidade(tiposfuncionalidadeint.recuperarUnico(funcrefresh.getTipofuncionalidade().getIdtipofuncionalidade()));
        funcionalidades.add(funcrefresh);
        		
        funcrefresh = null;
    		
    	funcionalidade = new Funcionalidades();
    }
    
    public void edit(Funcionalidades funcionalidade) {
    	setEdit(true);
        this.funcionalidade = funcionalidade;
    }
    
    public void save() {
    	funcionalidadesint.alterar(getFuncionalidade());
    	funcionalidade.setModulofuncionalidade(modulosint.recuperarUnico(funcionalidade.getModulofuncionalidade().getIdmodulo()));
        funcionalidade.setTipofuncionalidade(tiposfuncionalidadeint.recuperarUnico(funcionalidade.getTipofuncionalidade().getIdtipofuncionalidade()));
        
        if(funcionalidade.getFuncionalidadepai()!=null && funcionalidade.getFuncionalidadepai().getIdfuncionalidade()!=null)
        	funcionalidade.setFuncionalidadepai(funcionalidadesint.recuperarPai(funcionalidade));
        
    	funcionalidade = new Funcionalidades();
        edit = false;
    }
    
    public void delete(Funcionalidades funcionalidade) {
    	funcionalidadesint.remover(funcionalidade);
    	funcionalidades.remove(funcionalidade);
    }
    	
	public List<Funcionalidades> getFuncionalidades() {
		return funcionalidades;
	}
	public void setFuncionalidades(List<Funcionalidades> funcionalidades) {
		this.funcionalidades = funcionalidades;
	}

	public Funcionalidades getFuncionalidade() {
		return funcionalidade;
	}

	public void setFuncionalidade(Funcionalidades funcionalidade) {
		this.funcionalidade = funcionalidade;
	}
	
	public boolean isEdit() {
        return edit;
    }
	
	public void setEdit(Boolean edit){
		this.edit = edit;
	}

	public String getCnmmodulo() {
		return cnmmodulo;
	}

	public void setCnmmodulo(String cnmmodulo) {
		this.cnmmodulo = cnmmodulo;
	}

	public List<TiposFuncionalidade> getTiposfuncionalidade() {
		return tiposfuncionalidade;
	}

	public void setTiposfuncionalidade(List<TiposFuncionalidade> tiposfuncionalidade) {
		this.tiposfuncionalidade = tiposfuncionalidade;
	}

}
