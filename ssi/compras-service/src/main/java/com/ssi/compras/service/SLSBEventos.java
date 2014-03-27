package com.ssi.compras.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.compras.common.interfaces.EventosInt;
import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.Eventos;
import com.ssi.kernel.compras.domain.EventosAberturasRequisicoesCompras;
import com.ssi.kernel.compras.domain.EventosAcionaRequisicoesCompras;
import com.ssi.kernel.compras.domain.EventosAnaliseGerenteCompra;
import com.ssi.kernel.compras.domain.EventosAnaliseMinuta;
import com.ssi.kernel.compras.domain.EventosEnvioAoMercado;
import com.ssi.kernel.compras.domain.EventosGenericos;
import com.ssi.kernel.compras.domain.EventosIndicaNovoCompradores;
import com.ssi.kernel.compras.domain.EventosLeiloes;
import com.ssi.kernel.compras.domain.EventosSolicitacaoRevisaoAquisicao;
import com.ssi.kernel.compras.domain.EventosValidacoesTecnicas;

@Stateless(mappedName = "SLSBEventos")
public class SLSBEventos implements EventosInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
	public SLSBEventos(){}

	@Override
	public void registrar(Eventos obj) {
		em.merge(obj);
	}
	
	@Override
	public List<Eventos> retornarPaginado(int firstRow, int numberOfRows, Demandas demanda) {
		if(demanda.getIddemanda()==null){
			return new ArrayList<Eventos>(); 
		}
		String jpaquery = "SELECT evento FROM Eventos evento where evento.demanda = :demanda";
	    TypedQuery<Eventos> q = em.createQuery(jpaquery,Eventos.class);
	    q.setParameter("demanda", demanda);
	        
	    if (firstRow >= 0 && numberOfRows > 0){
	    	q.setFirstResult(firstRow);
	    	q.setMaxResults(numberOfRows);
	    }
	    try{
	    	return q.getResultList();
		}
	    catch(NoResultException nex){
	    	return new ArrayList<Eventos>();
	    }
	}
	
	@Override
	public Eventos getRowData(Object rowKey) {
		return em.find(Eventos.class, rowKey);
	}
	
	@Override
	public Integer getRowCount(Demandas demanda) {
		if(demanda.getIddemanda()==null){
			return 0;
		}
		String jpaquery = "SELECT count(evento.idevento) FROM Eventos evento where evento.demanda = :demanda";
	    TypedQuery<Long> q = em.createQuery(jpaquery,Long.class);
	    q.setParameter("demanda", demanda);
	    		
	    Long lid = q.getResultList().get(0);
	    jpaquery = null;
	    
	    try{
	    	return lid.intValue();
	    }
	    catch(IndexOutOfBoundsException e){
	    	return 0;
	    }
	}
	
	@Override
	public List<Eventos> recuperarEventos() {
		final String jpaquery = "SELECT eventos FROM Eventos eventos";
		TypedQuery<Eventos> q = em.createQuery(jpaquery, Eventos.class);
		try{
			return q.getResultList();
		}
		catch(NoResultException ex){
			return null;
		}
	}
	
	@Override
	public EventosAberturasRequisicoesCompras recuperarEventosAberturasRequisicoesCompras(Eventos evento) {
		final String jpaquery = "SELECT arc FROM EventosAberturasRequisicoesCompras arc where arc.eventoarc = :evento";
		TypedQuery<EventosAberturasRequisicoesCompras> q = em.createQuery(jpaquery, EventosAberturasRequisicoesCompras.class);
		q.setParameter("evento", evento);
		try{
			return q.getResultList().get(0);
		}
		catch(IndexOutOfBoundsException e){
			return null;
		}
	}	

	@Override
	public EventosAnaliseGerenteCompra recuperarEventosAnaliseGerenteCompra(
			Eventos evento) {
		final String jpaquery = "SELECT agc FROM EventosAnaliseGerenteCompra agc where agc.eventoagc = :evento";
		TypedQuery<EventosAnaliseGerenteCompra> q = em.createQuery(jpaquery, EventosAnaliseGerenteCompra.class);
		q.setParameter("evento", evento);
		try{
			return q.getResultList().get(0);
		}
		catch(IndexOutOfBoundsException e){
			return null;
		}
	}

	@Override
	public EventosAnaliseMinuta recuperarEventosAnaliseMinuta(Eventos evento) {
		// TODO Auto-generated method stub
		final String jpaquery = "SELECT am FROM EventosAnaliseMinuta am where am.eventoam = :evento";
		TypedQuery<EventosAnaliseMinuta> q = em.createQuery(jpaquery, EventosAnaliseMinuta.class);
		q.setParameter("evento", evento);
		try{
			return q.getResultList().get(0);
		}
		catch(IndexOutOfBoundsException e){
			return null;
		}
	}

	@Override
	public EventosEnvioAoMercado recuperarEventosEnvioAoMercado(Eventos evento) {
		// TODO Auto-generated method stub
		final String jpaquery = "SELECT em FROM EventosEnvioAoMercado em where em.eventoem = :evento";
		TypedQuery<EventosEnvioAoMercado> q = em.createQuery(jpaquery, EventosEnvioAoMercado.class);
		q.setParameter("evento", evento);
		try{
			return q.getResultList().get(0);
		}
		catch(IndexOutOfBoundsException e){
			return null;
		}
	}

	@Override
	public EventosAcionaRequisicoesCompras recuperarEventosAcionaRequisicoesCompras(
			Eventos evento) {
		// TODO Auto-generated method stub
		final String jpaquery = "SELECT acionareqcomp FROM EventosAcionaRequisicoesCompras acionareqcomp where acionareqcomp.acreqcomp = :evento";
		TypedQuery<EventosAcionaRequisicoesCompras> q = em.createQuery(jpaquery, EventosAcionaRequisicoesCompras.class);
		q.setParameter("evento", evento);
		try{
			return q.getResultList().get(0);
		}
		catch(IndexOutOfBoundsException e){
			return null;
		}
	}

	@Override
	public EventosIndicaNovoCompradores recuperarEventosIndicaNovoCompradores(
			Eventos evento) {
		// TODO Auto-generated method stub
		final String jpaquery = "SELECT eindicanovocomp FROM EventosIndicaNovoCompradores eindicanovocomp where eindicanovocomp.indicanovocomp = :evento";
		TypedQuery<EventosIndicaNovoCompradores> q = em.createQuery(jpaquery, EventosIndicaNovoCompradores.class);
		q.setParameter("evento", evento);
		try{
			return q.getResultList().get(0);
		}
		catch(IndexOutOfBoundsException e){
			return null;
		}
	}

	@Override
	public EventosLeiloes recuperarEventosLeiloes(Eventos evento) {
		// TODO Auto-generated method stub
		final String jpaquery = "SELECT evleilao FROM EventosLeiloes evleilao where evleilao.eleilao = :evento";
		TypedQuery<EventosLeiloes> q = em.createQuery(jpaquery, EventosLeiloes.class);
		q.setParameter("evento", evento);
		try{
			return q.getResultList().get(0);
		}
		catch(IndexOutOfBoundsException e){
			return null;
		}
	}

	@Override
	public EventosValidacoesTecnicas recuperarEventosValidacoesTecnicas(
			Eventos evento) {
		// TODO Auto-generated method stub
		final String jpaquery = "SELECT evvaltec FROM EventosValidacoesTecnicas evvaltec where evvaltec.evaltec = :evento";
		TypedQuery<EventosValidacoesTecnicas> q = em.createQuery(jpaquery, EventosValidacoesTecnicas.class);
		q.setParameter("evento", evento);
		try{
			return q.getResultList().get(0);
		}
		catch(IndexOutOfBoundsException e){
			return null;
		}
	}

	@Override
	public EventosGenericos recuperarEventosGenericos(Eventos evento) {
		// TODO Auto-generated method stub
		final String jpaquery = "SELECT evg FROM EventosGenericos evg where evg.eventogenerico = :evento";
		TypedQuery<EventosGenericos> q = em.createQuery(jpaquery, EventosGenericos.class);
		q.setParameter("evento", evento);
		try{
			return q.getResultList().get(0);
		}
		catch(IndexOutOfBoundsException e){
			return null;
		}
	}
	
	@Override
	public Eventos recuperarUltimoEventoDemanda(Long iddemanda) {
		// TODO Auto-generated method stub
		final String jpaquery = "SELECT evento FROM Eventos evento where evento.demanda.iddemanda = :iddemanda ORDER BY evento.idevento DESC";
		TypedQuery<Eventos> q = em.createQuery(jpaquery, Eventos.class);
		q.setParameter("iddemanda", iddemanda);
		q.setMaxResults(1);
		try{
			return q.getResultList().get(0);
		}
		catch(IndexOutOfBoundsException e){
			return null;
		}
	}

	@Override
	public EventosAnaliseGerenteCompra recuperaUltimoEventosAnaliseGerenteComprasComCompradorIndicado(Demandas demanda) {			
		final String jpaquery = "SELECT agc FROM EventosAnaliseGerenteCompra agc "
				+ "INNER JOIN FETCH agc.eventoagc evento "
				+ "WHERE agc.comprador IS NOT NULL AND "
				+ "evento.demanda = :demanda ORDER BY evento.datacadastro DESC";
		TypedQuery<EventosAnaliseGerenteCompra> q = em.createQuery(jpaquery, EventosAnaliseGerenteCompra.class);
		
		q.setParameter("demanda", demanda);
		q.setMaxResults(1);
		try{
			return q.getResultList().get(0);
		}
		catch(IndexOutOfBoundsException e){
			return null;
		}
	}

	@Override
	public EventosSolicitacaoRevisaoAquisicao recuperaUltimaSolicitacaoRevisaoAquisicao(Demandas demanda) {
				
		final String jpaquery = "SELECT sra FROM EventosSolicitacaoRevisaoAquisicao sra "
				+ "INNER JOIN FETCH sra.eventosolicitacaorevisaoaquisicao evento WHERE "
				+ "evento.demanda = :demanda ORDER BY evento.datacadastro DESC";
		TypedQuery<EventosSolicitacaoRevisaoAquisicao> q = em.createQuery(jpaquery, EventosSolicitacaoRevisaoAquisicao.class);
		
		q.setParameter("demanda", demanda);
		q.setMaxResults(1);
		try{
			return q.getResultList().get(0);
		}
		catch(IndexOutOfBoundsException e){
			return null;
		}
		
		
	}
}
