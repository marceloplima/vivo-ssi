package com.ssi.regulatorio.web.controller;
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

import com.ssi.kernel.redes.domain.AnexosRedes;
import com.ssi.kernel.redes.interfaces.AnexosRedesInt;
 
public class AnexosDataModel extends ExtendedDataModel<AnexosRedes> implements java.io.Serializable, Arrangeable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6551263006861438819L;

	private AnexosRedesInt anexoint;
	
	private Object rowKey;
    private ArrangeableState arrangeableState;
    private Map<String,Object> filtros;
    
    public AnexosDataModel(AnexosRedesInt anexoint, Map<String,Object>filtros) {
        super();
        
        this.anexoint = anexoint;
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
		
		List<AnexosRedes>data = anexoint.retornarPaginado(firstRow, numberOfRows, filtros);
		
        for (AnexosRedes t : data) {
            visitor.process(context, getId(t), argument);
        }
    }
 
    @Override
    public boolean isRowAvailable() {
        return rowKey != null;
    }
 
    @Override
    public int getRowCount() {
    	return anexoint.getRowCount(filtros);
    }
 
    @Override
    public AnexosRedes getRowData() {
    	
//    	@SuppressWarnings("unchecked")
//		List<Demandas>eventoschecked = (List<Demandas>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("demandaschecked");
//		
//    	if(eventoschecked != null){
//    		Demandas e = demandaint.getRowData(rowKey);
//    		if(eventoschecked.contains(e)){
//    			e.setChecked(true);
//    		}
//    		return e;
//    	}
//    	else{
    	return anexoint.getRowData(rowKey);
    	//}
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
		AnexosRedes d = (AnexosRedes)obj;
		return d.getIdanexo();
	}



	public Map<String,Object> getFiltros() {
		return filtros;
	}



	public void setFiltros(Map<String,Object> filtros) {
		this.filtros = filtros;
	}

}