package com.ssi.admin.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.kernel.controller.interfaces.PapeisAreasInt;
import com.ssi.kernel.model.PapeisAreas;

@ManagedBean
@ViewScoped
public class PapeisAreasMB implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6258878498541861103L;
	
	@EJB 
	private PapeisAreasInt papelareaint;
	
	private List<PapeisAreas> objs;
	private PapeisAreas obj;
	
	@PostConstruct
    public void init() {
		obj = new PapeisAreas();
	}
	
	public List<PapeisAreas> buscar(){
		
		objs = papelareaint.recuperar();
		return objs;
	}

	public List<PapeisAreas> getObjs() {
		return objs;
	}

	public void setObjs(List<PapeisAreas> objs) {
		this.objs = objs;
	}

	public PapeisAreas getObj() {
		return obj;
	}

	public void setObj(PapeisAreas obj) {
		this.obj = obj;
	}

}
