package com.ssi.compras.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.compras.common.interfaces.DemandaInt;
import com.ssi.kernel.compras.domain.CapexOpex;
import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.Status;
import com.ssi.kernel.compras.domain.TiposAditivo;
import com.ssi.kernel.model.Pessoas;


@Stateless(mappedName = "SLSBDemanda")
public class SLSBDemanda implements DemandaInt {
	
	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
	public SLSBDemanda() {}
	
	@Override
	public Demandas incluir(Demandas demanda) {				
				
		em.persist(demanda);
		em.flush();
		em.refresh(demanda);
										
		return demanda;
		
	}

	@Override
	public Demandas alterar(Demandas demanda) {
		em.merge(demanda);
		em.flush();
		em.detach(demanda);
		return demanda;
	}
	
	@Override
	public List<Demandas> retornarPaginado(int firstRow, int numberOfRows, Map<String,Object> filtros) {
		
		Pessoas pessoa = (Pessoas) filtros.get("pessoa");
				
		String jpaquery = "SELECT demanda FROM Demandas demanda ";
		
		jpaquery+=" where 1=1 ";
		
	    Demandas demanda = (Demandas)filtros.get("demanda");
	    Calendar datainicial = (Calendar)filtros.get("datainicial");
		Calendar datafinal = (Calendar)filtros.get("datafinal");
		Status filtrostatus = (Status)filtros.get("filtrostatus");
		
		if(pessoa!= null && pessoa.getIdpessoa()>=1){
	    	jpaquery+=" and demanda.autor = :pessoa";
	    }
		
	    if(demanda!= null && !demanda.getCnmnumero().contains("[")){
	    	jpaquery+=" and demanda.cnmnumero like '%"+demanda.getCnmnumero()+"%'";
	    }
		if(demanda!= null && demanda.getAutor()!=null && demanda.getAutor().getCnmnome()!=null && demanda.getAutor().getCnmnome().length()>=1){
	    	jpaquery+=" and demanda.autor.cnmnome = :autor";
	    }
		if(datainicial!=null){
			jpaquery+=" and demanda.datacadastro >= :datainicial ";
		}
		if(datafinal!=null){
			jpaquery+=" and demanda.datacadastro <= :datafinal ";
		}
		if(filtrostatus!=null){
			jpaquery+=" and demanda.status = :status";
		}
	    
	    jpaquery+=" order by demanda.iddemanda desc";
	   
	    
	    TypedQuery<Demandas> q = em.createQuery(jpaquery,Demandas.class);
	   
	    if(demanda!= null && demanda.getAutor()!=null && demanda.getAutor().getCnmnome()!=null && demanda.getAutor().getCnmnome().length()>=1){
	    	q.setParameter("autor", demanda.getAutor().getCnmnome());
	    }
	    if(datainicial!=null){
	    	q.setParameter("datainicial", datainicial);
		}
		if(datafinal!=null){
			q.setParameter("datafinal", datafinal);
		}
		if(filtrostatus!=null){
			q.setParameter("status", filtrostatus);
		}
		
		if(pessoa!= null && pessoa.getIdpessoa()>=1){
			q.setParameter("pessoa", pessoa);
	    }
		
		if(pessoa!= null && pessoa.getIdpessoa()>=1){
			q.setParameter("pessoa", pessoa);
	    }
	        
	    if (firstRow >= 0 && numberOfRows > 0){
	    	q.setFirstResult(firstRow);
	    	q.setMaxResults(numberOfRows);
	    }
	    try{
	    	return q.getResultList();
		}
	    catch(NoResultException nex){
	    	return new ArrayList<Demandas>();
	    }
	}
	
	@Override
	public Demandas getRowData(Object rowKey) {
		return em.find(Demandas.class, rowKey);
	}
	
	@Override
	public Integer getRowCount(Map<String,Object> filtros) {
		String jpaquery = "SELECT count(demanda.iddemanda) FROM Demandas demanda ";
		
		Pessoas pessoa = (Pessoas) filtros.get("pessoa");
		
		jpaquery+=" where 1=1 ";
		
		Demandas demanda = (Demandas)filtros.get("demanda");
		Calendar datainicial = (Calendar)filtros.get("datainicial");
		Calendar datafinal = (Calendar)filtros.get("datafinal");
		Status filtrostatus = (Status)filtros.get("filtrostatus");
		
		if(pessoa!= null && pessoa.getIdpessoa()>=1){
	    	jpaquery+=" and demanda.autor = :pessoa";
	    }
		
		if(demanda!= null && !demanda.getCnmnumero().contains("[")){
	    	jpaquery+=" and demanda.cnmnumero like '%"+demanda.getCnmnumero()+"%'";
	    }
		if(demanda!= null && demanda.getAutor()!=null && demanda.getAutor().getCnmnome()!=null && demanda.getAutor().getCnmnome().length()>=1){
	    	jpaquery+=" and demanda.autor.cnmnome = :autor";
	    }
		if(datainicial!=null){
			jpaquery+=" and demanda.datacadastro >= :datainicial ";
		}
		if(datafinal!=null){
			jpaquery+=" and demanda.datacadastro <= :datafinal ";
		}
		if(filtrostatus!=null){
			jpaquery+=" and demanda.status = :status";
		}
		
	    TypedQuery<Long> q = em.createQuery(jpaquery,Long.class);
	    
	    if(demanda!= null && demanda.getAutor()!=null && demanda.getAutor().getCnmnome()!=null && demanda.getAutor().getCnmnome().length()>=1){
	    	q.setParameter("autor", demanda.getAutor().getCnmnome());
	    }
	    if(datainicial!=null){
	    	q.setParameter("datainicial", datainicial);
		}
		if(datafinal!=null){
			q.setParameter("datafinal", datafinal);
		}
		if(filtrostatus!=null){
			q.setParameter("status", filtrostatus);
		}
		
		if(pessoa!= null && pessoa.getIdpessoa()>=1){
			q.setParameter("pessoa", pessoa);
	    }	
		if(pessoa!= null && pessoa.getIdpessoa()>=1){
			q.setParameter("pessoa", pessoa);
	    }
	    		
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
	public Demandas recuperaUltimaDemandaComNumero() {
		final String jpaquery = "SELECT dm FROM Demandas as dm WHERE dm.nnrultimonumero is not null ORDER BY dm.nnrultimonumero DESC";
		TypedQuery<Demandas> q = em.createQuery(jpaquery, Demandas.class);
		
		q.setMaxResults(1);
		
	    try{
	    	return q.getResultList().get(0);
		}
	    catch(IndexOutOfBoundsException e){
	    	return null;
	    }
		
	}
	
	@Override
	public List<Demandas> recuperar(){

		final String jpaquery = "SELECT dm FROM Demandas as dm WHERE dm.nnrultimonumero is not null ORDER BY dm.nnrultimonumero DESC";
		TypedQuery<Demandas> q = em.createQuery(jpaquery, Demandas.class);
		return q.getResultList();		
		
	}
		
	@Override
	public Demandas recuperarDemandaEspecifica(Long iddemandaeditar) {
		return em.find(Demandas.class, iddemandaeditar);
	}

	@Override
	public List<CapexOpex> recuperarCapexOpex(Demandas demanda) {

		final String jpaquery = "SELECT dm FROM Demandas as dm INNER JOIN dm.capexOpex WHERE dm = :demanda";
		TypedQuery<Demandas> q = em.createQuery(jpaquery, Demandas.class);
		
		q.setParameter("demanda", demanda);
		
		Demandas demandaRecuperada;
		
		try{
			demandaRecuperada = q.getResultList().get(0);
		}
		catch(IndexOutOfBoundsException e){
			demandaRecuperada = new Demandas();
		}
		List<CapexOpex> retorno = new ArrayList<CapexOpex>();
		
		for(CapexOpex capOp:demandaRecuperada.getCapexOpex()){
			retorno.add(capOp);
		}
		
		return retorno;
		
	}

	@Override
	public List<TiposAditivo> recuperaTiposAditivos(Demandas demanda) {

		final String jpaquery = "SELECT dm FROM Demandas as dm INNER JOIN dm.tiposAditivo WHERE dm = :demanda";
		TypedQuery<Demandas> q = em.createQuery(jpaquery, Demandas.class);
		
		q.setParameter("demanda", demanda);
		
		Demandas demandaRecuperada;
		
		try{
			demandaRecuperada = q.getResultList().get(0);
		}
		catch(IndexOutOfBoundsException e){
			demandaRecuperada = new Demandas();
		}
		
		List<TiposAditivo> retorno = new ArrayList<TiposAditivo>();
		
		for(TiposAditivo tipo:demandaRecuperada.getTiposAditivo()){
			retorno.add(tipo);
		}
		
		return retorno;		
		

	}

	@Override
	public List<Demandas> recuperarDemandasCriadasPeloAutor(Pessoas pessoa) {
		
		final String jpaquery = "SELECT dm FROM Demandas as dm WHERE dm.autor = :autor";
		TypedQuery<Demandas> q = em.createQuery(jpaquery, Demandas.class);
		
		q.setParameter("autor", pessoa);

		return q.getResultList();

	}

	@Override
	public List<Demandas> recuperarDemandasEstaoComPessoaLogada(Pessoas pessoa) {

		TypedQuery<Demandas> q;
		String jpaquery;
		
		Set<Demandas> demandasComigo = new HashSet<Demandas>();
		
		jpaquery = "SELECT dm FROM Demandas as dm WHERE dm.pessoacomquemesta = :pessoacomquemesta";
		q = em.createQuery(jpaquery, Demandas.class);
		
		q.setParameter("pessoacomquemesta", pessoa);

		demandasComigo.addAll(q.getResultList());
				 		
		jpaquery = "SELECT dm from Demandas dm JOIN FETCH dm.grupocomquemesta gm JOIN FETCH gm.gruposmodulospessoas WHERE :pessoa in elements(gm.gruposmodulospessoas)";
		q = em.createQuery(jpaquery, Demandas.class);
		
		q.setParameter("pessoa", pessoa);		
		
		demandasComigo.addAll(q.getResultList());
		
		return new ArrayList<Demandas>(demandasComigo);
		
		
	}
	
	

}
