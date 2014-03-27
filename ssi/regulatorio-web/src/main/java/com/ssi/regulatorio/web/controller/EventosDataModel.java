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

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.ExtendedDataModel;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;
import org.richfaces.model.Arrangeable;
import org.richfaces.model.ArrangeableState;

import com.ssi.kernel.redes.domain.DemandasRedes;
import com.ssi.kernel.redes.domain.EventosRedes;
import com.ssi.kernel.redes.interfaces.EventosRedesInt;
 
public class EventosDataModel extends ExtendedDataModel<EventosRedes> implements java.io.Serializable, Arrangeable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -5114954703031219608L;
	
	private EventosRedesInt eventosint;
	
	private Object rowKey;
    private ArrangeableState arrangeableState;
    private EventosRedes evento;
    private DemandasRedes demanda;
    
    public EventosDataModel(EventosRedesInt eventosint, DemandasRedes demanda) {
        super();
        
        this.eventosint = eventosint;
        this.demanda = demanda;
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
 
    protected EventosRedes getEvento() {
        return evento;
    }
 
    @Override
    public void walk(FacesContext context, DataVisitor visitor, Range range, Object argument) {
    	int firstRow = ((SequenceRange)range).getFirstRow();
		int numberOfRows = ((SequenceRange)range).getRows();
		
		List<EventosRedes>data = eventosint.retornarPaginado(firstRow, numberOfRows, demanda);
		
        for (EventosRedes t : data) {
            visitor.process(context, getId(t), argument);
        }
    }
 
    @Override
    public boolean isRowAvailable() {
        return rowKey != null;
    }
 
    @Override
    public int getRowCount() {
    	return eventosint.getRowCount(demanda);
    }
 
    @Override
    public EventosRedes getRowData() {
    	
    	@SuppressWarnings("unchecked")
		List<EventosRedes>eventoschecked = (List<EventosRedes>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("eventoschecked");
		
    	if(eventoschecked != null){
    		EventosRedes e = eventosint.getRowData(rowKey);
    		if(eventoschecked.contains(e)){
    			e.setChecked(true);
    		}
    		return e;
    	}
    	else{
    		return eventosint.getRowData(rowKey);
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
		EventosRedes e = (EventosRedes)obj;
		return e.getIdevento();
	}

}