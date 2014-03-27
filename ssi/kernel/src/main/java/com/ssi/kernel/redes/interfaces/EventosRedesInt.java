package com.ssi.kernel.redes.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.redes.domain.DemandasRedes;
import com.ssi.kernel.redes.domain.EventosRedes;
import com.ssi.kernel.redes.domain.EventosRedesConcluir;

@Local
public interface EventosRedesInt {
	public List<EventosRedes> recuperarEventos();
	public void registrar(EventosRedes obj);
	public List<EventosRedes> retornarPaginado(int firstRow, int numberOfRows, DemandasRedes demanda);
	public EventosRedes getRowData(Object rowKey);
	public Integer getRowCount(DemandasRedes demanda);
	public EventosRedes recuperarUltimoEventoDemanda(Long iddemanda);
	EventosRedesConcluir recuperarEventosRedesConcluir(EventosRedes evento);
	
}
