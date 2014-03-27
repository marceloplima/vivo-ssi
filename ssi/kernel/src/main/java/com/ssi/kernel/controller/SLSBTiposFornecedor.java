package com.ssi.kernel.controller;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.kernel.controller.interfaces.TiposFornecedorInt;
import com.ssi.kernel.model.TiposFornecedor;

@Stateless(mappedName="SLSBTiposFornecedor")
public class SLSBTiposFornecedor implements TiposFornecedorInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
	public SLSBTiposFornecedor(){}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<TiposFornecedor> recuperar() {
		final String jpaquery = "SELECT tipofornecedor FROM TiposFornecedor tipofornecedor";
		TypedQuery<TiposFornecedor> q = em.createQuery(jpaquery, TiposFornecedor.class);
		return q.getResultList();
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public TiposFornecedor recuperarUnico(TiposFornecedor tipofornecedor) {
		return em.find(TiposFornecedor.class, tipofornecedor);
	}

	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public boolean verificaExistente(TiposFornecedor obj) throws IndexOutOfBoundsException {
		try{
			String jpaquery = "SELECT tf FROM TiposFornecedor tf WHERE tf.cnmtipofornecedor = '"+obj.getCnmtipofornecedor().trim()+"'";
			TypedQuery<TiposFornecedor> q = em.createQuery(jpaquery, TiposFornecedor.class);
			q.getResultList().get(0);
		}
    	catch(IndexOutOfBoundsException e){
    		return true;
    	}
		
		return false;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<TiposFornecedor> recuperarFiltrado(Map<String, Object> filtros) {
		
		String jpaquery = "SELECT tf FROM TiposFornecedor tf WHERE 1=1 ";
		
		TiposFornecedor tipofornecedor = (TiposFornecedor) filtros.get("tipofornecedor");
		
		
		if(tipofornecedor.getCnmtipofornecedor()!=null && !tipofornecedor.getCnmtipofornecedor().isEmpty()){
			jpaquery +="and tf.cnmtipofornecedor like '%"+tipofornecedor.getCnmtipofornecedor()+"%' ";
		}
		
		TypedQuery<TiposFornecedor> q = em.createQuery(jpaquery, TiposFornecedor.class);
		
		return q.getResultList();
	}
	
	@Override
	public void incluir(TiposFornecedor obj) {
		obj.setCnmtipofornecedor(obj.getCnmtipofornecedor().toUpperCase()); // For�a pra ficar em caixa alta
		em.merge(obj);
		em.flush();
	}

	@Override
	public void alterar(TiposFornecedor obj) {
		em.merge(obj);
		em.detach(obj);
	}
	
}
