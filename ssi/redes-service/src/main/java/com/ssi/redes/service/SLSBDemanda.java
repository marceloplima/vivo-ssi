package com.ssi.redes.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.kernel.redes.domain.DemandasRedes;
import com.ssi.kernel.redes.domain.StatusRedes;
import com.ssi.kernel.redes.domain.Transferencia;
import com.ssi.kernel.redes.interfaces.AplicacaoNotesInt;
import com.ssi.kernel.redes.interfaces.DemandasRedesInt;
import com.ssi.kernel.redes.interfaces.InstrucaoRedesInt;
import com.ssi.kernel.controller.interfaces.AreasInt;
import com.ssi.kernel.model.Pessoas;
import com.ssi.kernel.model.Regionais;


@Stateless(mappedName = "SLSBDemanda")
public class SLSBDemanda implements DemandasRedesInt {
	
	@EJB
	private AreasInt areasInt;
	
	@EJB
	private InstrucaoRedesInt instrucaoRedesInt;

	@EJB
	private AplicacaoNotesInt aplicacaoNotesInt;
	
	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
	public SLSBDemanda() {}
	
	@Override
	public void incluir(DemandasRedes demanda) {				
		em.persist(demanda);
	}

	@Override
	public DemandasRedes persistir(DemandasRedes demanda) {
		em.merge(demanda);
		em.flush();
		em.detach(demanda);
		return demanda;
	}
	
	@Override
	public List<DemandasRedes> retornarPaginado(int firstRow, int numberOfRows, Map<String,Object> filtros) {
		
		Pessoas pessoa = (Pessoas) filtros.get("pessoa");
				
		String jpaquery = "SELECT demanda FROM DemandasRedes demanda ";
		
		jpaquery+=" where 1=1 ";
		
	    DemandasRedes demanda = (DemandasRedes)filtros.get("demanda");
	    Calendar datainicial = (Calendar)filtros.get("datainicial");
		Calendar datafinal = (Calendar)filtros.get("datafinal");
		StatusRedes filtrostatus = (StatusRedes)filtros.get("filtrostatus");
		
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
	   
	    
	    TypedQuery<DemandasRedes> q = em.createQuery(jpaquery,DemandasRedes.class);
	   
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
	    	return new ArrayList<DemandasRedes>();
	    }
	}
	
	@Override
	public DemandasRedes getRowData(Object rowKey) {
		return em.find(DemandasRedes.class, rowKey);
	}
	
	@Override
	public Integer getRowCount(Map<String,Object> filtros) {
		String jpaquery = "SELECT count(demanda.iddemanda) FROM DemandasRedes demanda ";
		
		Pessoas pessoa = (Pessoas) filtros.get("pessoa");
		
		jpaquery+=" where 1=1 ";
		
		DemandasRedes demanda = (DemandasRedes)filtros.get("demanda");
		Calendar datainicial = (Calendar)filtros.get("datainicial");
		Calendar datafinal = (Calendar)filtros.get("datafinal");
		StatusRedes filtrostatus = (StatusRedes)filtros.get("filtrostatus");
		
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
	public DemandasRedes recuperaUltimaDemandaComNumero() {
		final String jpaquery = "SELECT dm FROM DemandasRedes as dm WHERE dm.nnrultimonumero is not null ORDER BY dm.nnrultimonumero DESC";
		TypedQuery<DemandasRedes> q = em.createQuery(jpaquery, DemandasRedes.class);
		
		q.setMaxResults(1);
		
	    try{
	    	return q.getResultList().get(0);
		}
	    catch(IndexOutOfBoundsException e){
	    	return null;
	    }
		
	}
	
	@Override
	public List<DemandasRedes> recuperar(){

		final String jpaquery = "SELECT dm FROM DemandasRedes as dm WHERE dm.nnrultimonumero is not null ORDER BY dm.nnrultimonumero DESC";
		TypedQuery<DemandasRedes> q = em.createQuery(jpaquery, DemandasRedes.class);
		return q.getResultList();		
		
	}
		
	@Override
	public DemandasRedes recuperarDemandaEspecifica(Long iddemandaeditar) {
		return em.find(DemandasRedes.class, iddemandaeditar);
	}



	@Override
	public List<DemandasRedes> recuperarDemandasCriadasPeloAutor(Pessoas pessoa) {
		
		final String jpaquery = "SELECT dm FROM DemandasRedes as dm WHERE dm.autor = :autor";
		TypedQuery<DemandasRedes> q = em.createQuery(jpaquery, DemandasRedes.class);
		
		q.setParameter("autor", pessoa);

		return q.getResultList();

	}

	@Override
	public List<DemandasRedes> recuperarDemandasEstaoComPessoaLogada(Pessoas pessoa) {

		TypedQuery<DemandasRedes> q;
		String jpaquery;
		
		Set<DemandasRedes> demandasComigo = new HashSet<DemandasRedes>();
		
		jpaquery = "SELECT dm FROM DemandasRedes as dm WHERE dm.pessoacomquemesta = :pessoacomquemesta";
		q = em.createQuery(jpaquery, DemandasRedes.class);
		
		q.setParameter("pessoacomquemesta", pessoa);

		demandasComigo.addAll(q.getResultList());
				 		
		jpaquery = "SELECT dm from DemandasRedes dm JOIN FETCH dm.grupocomquemesta gm JOIN FETCH gm.gruposmodulospessoas WHERE :pessoa in elements(gm.gruposmodulospessoas)";
		q = em.createQuery(jpaquery, DemandasRedes.class);
		
		q.setParameter("pessoa", pessoa);		
		
		demandasComigo.addAll(q.getResultList());
		
		return new ArrayList<DemandasRedes>(demandasComigo);
		
		
	}

	@Override
	public List<Regionais> recuperarRegionais(DemandasRedes demandas) {
		String jpaquery = "SELECT DISTINCT regional FROM Regionais regional join fetch regional.demandasregionais WHERE :demanda in elements(regional.demandasregionais)";
		TypedQuery<Regionais> q = em.createQuery(jpaquery, Regionais.class);
		q.setParameter("demanda", demandas);
		return q.getResultList();
	}

	@Override
	public DemandasRedes recuperaRegistroDaAreaDeTransferencia(String universalId) {
		
		String jpaquery = "SELECT transf FROM Transferencia AS transf WHERE transf.iddocumento = :universalid";
		
		TypedQuery<Transferencia> q = em.createQuery(jpaquery, Transferencia.class);
		q.setParameter("universalid", universalId);
		 				
		
		Transferencia documentoTransferencia = q.getSingleResult();
		
		if(documentoTransferencia==null){
			return new DemandasRedes();
		}
        
		return recuperarDados(documentoTransferencia);
	}

	private DemandasRedes recuperarDados(Transferencia documentoTransferencia) {
				
		DemandasRedes demandasRedes = new DemandasRedes(documentoTransferencia.getIddocumento());
		
		demandasRedes.setAreasolicitante(areasInt.recuperaAreaPorSigla(documentoTransferencia.getSecaorequisitante()));
		demandasRedes.setArearequisitada(areasInt.recuperaAreaPorSigla(documentoTransferencia.getArearesponsavel()));
		demandasRedes.setInstrucao(instrucaoRedesInt.recuperaUnico(documentoTransferencia.getIdinstrucao()));
		demandasRedes.setNumeroassociado(documentoTransferencia.getNumerossi());
		demandasRedes.setAplicacaoNotes(aplicacaoNotesInt.recuperarUnico(documentoTransferencia.getIdaplicacaotransf()));
				
		return demandasRedes;
	}

	@Override
	public List<DemandasRedes> recuperaDemandaPeloUniversalId(String universalId) {

		String jpaquery = "SELECT dr FROM DemandasRedes AS dr WHERE dr.unid = :universalid";
		
		TypedQuery<DemandasRedes> q = em.createQuery(jpaquery, DemandasRedes.class);
		q.setParameter("universalid", universalId);
		 						
		return q.getResultList();
		
	}

	@Override
	public DemandasRedes alterar(DemandasRedes demanda) {
		return em.merge(demanda);
	}

	@Override
	public DemandasRedes recuperaDemandaPeloNumeroSSINotes(String numeroSSI) {
		final String jpaquery = "SELECT dm FROM DemandasRedes as dm WHERE dm.cnmnumero = :numerossi";
		TypedQuery<DemandasRedes> q = em.createQuery(jpaquery, DemandasRedes.class);

		q.setParameter("numerossi", numeroSSI);
		
	    try{
	    	return q.getResultList().get(0);
		}
	    catch(IndexOutOfBoundsException e){
	    	return null;
	    }				
		
	}
	
	

}
