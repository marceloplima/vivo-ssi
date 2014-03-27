package com.ssi.admin.controller;
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

import com.ssi.kernel.controller.interfaces.PessoasInt;
import com.ssi.kernel.model.Pessoas;
 
public class PessoasDataModel extends ExtendedDataModel<Pessoas> implements java.io.Serializable, Arrangeable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -5114954703031219608L;
	
	private PessoasInt pessoasint;
	
	private Object rowKey;
    private ArrangeableState arrangeableState;
    private Pessoas pessoa;
    private Map<String,Object> filtros;
    
    public PessoasDataModel(PessoasInt pessoasint, Map<String,Object>filtros) {
        super();
        
        this.pessoasint = pessoasint;
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
 
    protected Pessoas getPessoa() {
        return pessoa;
    }
 
    @Override
    public void walk(FacesContext context, DataVisitor visitor, Range range, Object argument) {
    	int firstRow = ((SequenceRange)range).getFirstRow();
		int numberOfRows = ((SequenceRange)range).getRows();
		
		List<Pessoas>data = pessoasint.retornarPaginado(firstRow, numberOfRows,filtros);
		
        for (Pessoas t : data) {
            visitor.process(context, getId(t), argument);
        }
    }
 
    @Override
    public boolean isRowAvailable() {
        return rowKey != null;
    }
 
    @Override
    public int getRowCount() {
    	return pessoasint.getRowCount(filtros);
    }
 
    @Override
    public Pessoas getRowData() {
    	
//    	@SuppressWarnings("unchecked")
//		List<Pessoas>pessoaschecked = (List<Pessoas>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("pessoaschecked");
//		
//    	if(pessoaschecked != null){
//    		Pessoas p = pessoasint.getRowData(rowKey);
//    		if(pessoaschecked.contains(p)){
//    			p.setChecked(true);
//    		}
//    		return p;
//    	}
    	//else{
    	return pessoasint.getRowData(rowKey);
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
		Pessoas p = (Pessoas)obj;
		return p.getIdpessoa();
	}

	public Map<String,Object> getFiltros() {
		return filtros;
	}

	public void setFiltros(Map<String,Object> filtros) {
		this.filtros = filtros;
	}
}