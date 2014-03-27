package com.ssi.kernel.controller;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.kernel.controller.interfaces.GruposInt;
import com.ssi.kernel.model.Funcionalidades;
import com.ssi.kernel.model.Grupos;

@Stateless(mappedName="SLSBGrupos")
public class SLSBGrupos implements GruposInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
	public SLSBGrupos(){}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public boolean verificaExistente(Grupos obj) throws IndexOutOfBoundsException {
		try{
			String jpaquery = "SELECT obj FROM Grupos obj WHERE obj.cnmgrupo = '"+obj.getCnmgrupo().trim()+"'";
			TypedQuery<Grupos> q = em.createQuery(jpaquery, Grupos.class);
			q.getResultList().get(0);
		}
    	catch(IndexOutOfBoundsException e){
    		return true;
    	}
		
		return false;
	}
	
	@Override
	public List<Funcionalidades> retornarGruposFuncionalidades(Grupos obj){
		String jpaquery = "SELECT DISTINCT func FROM Funcionalidades func join fetch func.grupos WHERE :grupo in elements(func.grupos)";
		TypedQuery<Funcionalidades> q = em.createQuery(jpaquery,Funcionalidades.class);
		q.setParameter("grupo", obj);
		return q.getResultList();
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Grupos> recuperar() {
		final String jpaquery = "SELECT obj FROM Grupos obj";
		TypedQuery<Grupos> q = em.createQuery(jpaquery, Grupos.class);
		return q.getResultList();
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Grupos> recuperarFiltrado(Map<String, Object> filtros) {
		
		String jpaquery = "SELECT grupo FROM Grupos grupo WHERE 1=1 ";
		
		Funcionalidades func = (Funcionalidades) filtros.get("funcionalidade");
		Grupos grupo = (Grupos) filtros.get("grupo");
		
		if(grupo.getCnmgrupo()!=null && !grupo.getCnmgrupo().isEmpty()){
			jpaquery +="and grupo.cnmgrupo like '%"+grupo.getCnmgrupo()+"%' ";
		}
		
		if(func!=null){
			jpaquery +="and :funcionalidade in elements(grupo.gruposfuncionalidades)  ";
		}
		
		TypedQuery<Grupos> q = em.createQuery(jpaquery, Grupos.class);
		
		if(func!=null){
			
			q.setParameter("funcionalidade", func);
		}
		
		return q.getResultList();
	}
	
	@Override
	public void incluir(Grupos obj) {
		obj.setCnmgrupo(obj.getCnmgrupo().toUpperCase()); // For�a pra ficar em caixa alta, o nome do Grupo
		obj.remapearItens(obj);
		em.merge(obj);
		em.flush();
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Grupos recuperarUnico(Grupos obj) {
		return em.find(Grupos.class, obj);
	}

	@Override
	public void alterar(Grupos obj) {
		em.merge(obj);
		em.detach(obj);
	}
	
}
