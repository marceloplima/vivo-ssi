package com.ssi.compras.web.controller;
/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
 
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.ExtendedDataModel;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;
import org.richfaces.model.Arrangeable;
import org.richfaces.model.ArrangeableState;

import com.ssi.kernel.compras.domain.Propostas;
import com.ssi.compras.common.interfaces.PropostasInt;
 
public class PropostasDataModel extends ExtendedDataModel<Propostas> implements java.io.Serializable, Arrangeable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7150607097972097259L;

	private PropostasInt propostaint;
	
	private Object rowKey;
    private ArrangeableState arrangeableState;
    private Map<String,Object> filtros;
    
    public PropostasDataModel(PropostasInt propostaint, Map<String,Object>filtros) {
        super();
        
        this.propostaint = propostaint;
        this.filtros = filtros;
    }
 
    
    
    public void arrange(FacesContext context, ArrangeableState state) {
        arrangeableState = state;
    }
 
    @Override
    public void setRowKey(Object key) {
        rowKey = key;
    }
 
    @Override
    public Object getRowKey() {
        return rowKey;
    }
 
    protected ArrangeableState getArrangeableState() {
        return arrangeableState;
    }
 
    @Override
    public void walk(FacesContext context, DataVisitor visitor, Range range, Object argument) {
    	int firstRow = ((SequenceRange)range).getFirstRow();
		int numberOfRows = ((SequenceRange)range).getRows();
		
		List<Propostas>data = propostaint.retornarPaginado(firstRow, numberOfRows, filtros);
		
        for (Propostas t : data) {
            visitor.process(context, getId(t), argument);
        }
    }
 
    @Override
    public boolean isRowAvailable() {
        return rowKey != null;
    }
 
    @Override
    public int getRowCount() {
    	return propostaint.getRowCount(filtros);
    }
 
    @Override
    public Propostas getRowData() {
    	
    	@SuppressWarnings("unchecked")
		List<Propostas>propchecked = (List<Propostas>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("propostaschecked");
		
    	if(propchecked != null){
    		Propostas obj = propostaint.getRowData(rowKey);
    		if(propchecked.contains(obj)){
    			obj.setChecked(true);
    		}
    		return obj;
    	}
    	else{
    		return propostaint.getRowData(rowKey);
    	}
    }
 
    @Override
    public int getRowIndex() {
        return -1;
    }
 
    @Override
    public void setRowIndex(int rowIndex) {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public Object getWrappedData() {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void setWrappedData(Object data) {
        throw new UnsupportedOperationException();
    }
    
	public Long getId(Object obj) {
		Propostas d = (Propostas)obj;
		return d.getIdproposta();
	}



	public Map<String,Object> getFiltros() {
		return filtros;
	}



	public void setFiltros(Map<String,Object> filtros) {
		this.filtros = filtros;
	}

}