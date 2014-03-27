package com.ssi.kernel.controller;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.kernel.controller.interfaces.TiposTelefoneInt;
import com.ssi.kernel.model.TiposTelefone;


@Stateless(mappedName = "SLSBTiposTelefone")
public class SLSBTiposTelefone implements TiposTelefoneInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
    public SLSBTiposTelefone() {}
    
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public TiposTelefone recuperarUnico(Long idtipotelefone){
    	
    	final String jpaquery = "SELECT tt FROM TiposTelefone tt WHERE idtipotelefone = "+idtipotelefone;
    	
    	return em.createQuery(jpaquery,TiposTelefone.class).getResultList().get(0);
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<TiposTelefone> recuperar(){
    	
    	final String jpaquery = "SELECT tt FROM TiposTelefone tt";
    	
    	TypedQuery<TiposTelefone> q = em.createQuery(jpaquery,TiposTelefone.class);
    	
    	return q.getResultList();
    }
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public boolean verificaExistente(TiposTelefone obj) throws IndexOutOfBoundsException {
		try{
			String jpaquery = "SELECT obj FROM TiposTelefone obj WHERE obj.cnmtipotelefone = '"+obj.getCnmtipotelefone().trim()+"'";
			TypedQuery<TiposTelefone> q = em.createQuery(jpaquery, TiposTelefone.class);
			q.getResultList().get(0);
		}
    	catch(IndexOutOfBoundsException e){
    		return true;
    	}
		
		return false;
	}
	
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<TiposTelefone> recuperarFiltrado(Map<String, Object> filtros) {
		
		String jpaquery = "SELECT obj FROM TiposTelefone obj WHERE 1=1 ";
		
		TiposTelefone obj = (TiposTelefone) filtros.get("tipotelefone");
		
		
		if(obj.getCnmtipotelefone()!=null && !obj.getCnmtipotelefone().isEmpty()){
			jpaquery +="and obj.cnmtipotelefone like '%"+obj.getCnmtipotelefone()+"%' ";
		}
		
		TypedQuery<TiposTelefone> q = em.createQuery(jpaquery, TiposTelefone.class);
		
		return q.getResultList();
	}
	
	@Override
	public void incluir(TiposTelefone obj) {
		obj.setCnmtipotelefone(obj.getCnmtipotelefone().toUpperCase()); // For�a pra ficar em caixa alta
		em.merge(obj);
		em.flush();
	}

	@Override
	public void alterar(TiposTelefone obj) {
		em.merge(obj);
		em.detach(obj);
	}

}
