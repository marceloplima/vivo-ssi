package com.ssi.kernel.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.kernel.controller.interfaces.GruposModulosInt;
import com.ssi.kernel.model.GruposModulos;
import com.ssi.kernel.model.Modulos;
import com.ssi.kernel.model.Pessoas;

@Stateless(mappedName="SLSBGruposModulos")
public class SLSBGruposModulos implements GruposModulosInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
	public SLSBGruposModulos(){}

	@Override
	public List<Pessoas> recuperaPessoasDoGrupo(GruposModulos grupoModulo) {

		String jpaquery = "SELECT DISTINCT pessoa FROM Pessoas pessoa join fetch pessoa.pessoasgruposmodulos WHERE :grupomodulo in elements(pessoa.pessoasgruposmodulos)";
		TypedQuery<Pessoas> q = em.createQuery(jpaquery,Pessoas.class);
		q.setParameter("grupomodulo", grupoModulo);
		
		try{
			return q.getResultList();
		}
		catch(NoResultException e){
			return new ArrayList<Pessoas>();
		}			
		
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public boolean verificaExistente(GruposModulos grupomodulo) throws IndexOutOfBoundsException {
		try{
			String jpaquery = "SELECT grupomodulo FROM GruposModulos grupomodulo WHERE grupomodulo.cnmgrupomodulo = '"+grupomodulo.getCnmgrupomodulo().trim()+"' and grupomodulo.modulo.idmodulo = "+grupomodulo.getModulo().getIdmodulo();
			
			em.createQuery(jpaquery, GruposModulos.class).getResultList().get(0);
		}
    	catch(IndexOutOfBoundsException e){
    		return true;
    	}
		
		return false;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<GruposModulos> recuperar() {
		final String jpaquery = "SELECT grupomodulo FROM GruposModulos grupomodulo";
		TypedQuery<GruposModulos> q = em.createQuery(jpaquery, GruposModulos.class);
		return q.getResultList();
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<GruposModulos> recuperarFiltrado(Map<String, Object> filtros) {
		
		String jpaquery = "SELECT grupomodulo FROM GruposModulos grupomodulo WHERE 1=1 ";
		
		GruposModulos grupomodulo = (GruposModulos) filtros.get("grupomodulo");
		Modulos modulo = grupomodulo.getModulo();
		
		if(grupomodulo!=null){
			
			if(!grupomodulo.getCnmgrupomodulo().isEmpty()){
				jpaquery +="and grupomodulo.cnmgrupomodulo like '%"+grupomodulo.getCnmgrupomodulo()+"%' ";
			}
			
			if(modulo !=null){
				jpaquery +="and grupomodulo.modulo = :modulo ";
			}
			
		}
		
		TypedQuery<GruposModulos> q = em.createQuery(jpaquery, GruposModulos.class);
		
		if(modulo !=null){
			q.setParameter("modulo", modulo);
		}
		return q.getResultList();
	}
	
	@Override
	public GruposModulos incluir(GruposModulos gm) {
		gm.setCnmgrupomodulo(gm.getCnmgrupomodulo().toUpperCase()); // For�a pra ficar em caixa alta, o nome do Grupo
		em.persist(gm);
		em.flush();
		em.refresh(gm);
		
		return gm;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public GruposModulos recuperarUnico(GruposModulos grupo) {
		return em.find(GruposModulos.class, grupo);
	}

	@Override
	public void alterar(GruposModulos gm) {
		em.merge(gm);
		em.flush();
		em.detach(gm);
	}
	
}
