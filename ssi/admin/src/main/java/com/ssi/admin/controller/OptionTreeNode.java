package com.ssi.admin.controller;

import org.richfaces.model.TreeNodeImpl;

import com.ssi.kernel.model.Areas;
import com.ssi.kernel.model.Funcionalidades;

public class OptionTreeNode extends TreeNodeImpl {
  
    private Funcionalidades funcionalidade;
    private Areas area;
    
	public Funcionalidades getFuncionalidade() {
		return funcionalidade;
	}

	public void setFuncionalidade(Funcionalidades funcionalidade) {
		this.funcionalidade = funcionalidade;
	}

	public Areas getArea() {
		return area;
	}

	public void setArea(Areas area) {
		this.area = area;
	}  
  
} 