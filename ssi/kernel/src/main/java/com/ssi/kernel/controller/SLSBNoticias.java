package com.ssi.kernel.controller;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.kernel.controller.interfaces.NoticiasInt;
import com.ssi.kernel.model.Modulos;
import com.ssi.kernel.model.Noticias;

@Stateless(mappedName="SLSBNoticias")
public class SLSBNoticias implements NoticiasInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
	public SLSBNoticias(){}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public boolean verificaExistente(Noticias noticia) throws IndexOutOfBoundsException {
		try{
			String jpaquery = "SELECT noticia FROM Noticias noticia WHERE noticia.cnmnoticia = '"+noticia.getCnmnoticia().trim()+"'";
			
			em.createQuery(jpaquery, Noticias.class).getResultList().get(0);
		}
    	catch(IndexOutOfBoundsException e){
    		return true;
    	}
		
		return false;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Noticias> recuperar() {
		final String jpaquery = "SELECT noticia FROM Noticias noticia";
		TypedQuery<Noticias> q = em.createQuery(jpaquery, Noticias.class);
		return q.getResultList();
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Noticias> recuperarFiltrado(Map<String, Object> filtros) {
		
		String jpaquery = "SELECT noticia FROM Noticias noticia WHERE 1=1 ";
		
		Noticias noticia = (Noticias) filtros.get("noticia");
		Modulos modulo = (Modulos) filtros.get("modulo");
		
		if(noticia!=null){
			
			if(noticia.isFlagativo())
	    		jpaquery +="and noticia.flagativo = '1' ";
	    	else
	    		jpaquery +="and pessoa.flagativo = '0' ";
			
			if(!noticia.getCnmnoticia().isEmpty()){
				jpaquery +="and noticia.cnmnoticia like '%"+noticia.getCnmnoticia()+"%' ";
			}
			
			if(!noticia.getCnmtitulo().isEmpty()){
				jpaquery +="and noticia.cnmtitulo like '%"+noticia.getCnmtitulo()+"%' ";
			}
			
		}
		else{
			jpaquery +="and noticia.flagativo = '1' ";
		}
		
		if(modulo!=null){
			jpaquery +="and :modulo in elements(noticia.noticiasmodulos) ";
		}
		
		jpaquery +=" order by noticia.idnoticia DESC";
		
		TypedQuery<Noticias> q = em.createQuery(jpaquery, Noticias.class);
		
		if(modulo!=null){
			q.setParameter("modulo", modulo);
		}
		
		return q.getResultList();
	}
	
	@Override
	public List<Modulos> retornarModulosNoticias(Noticias noticia){
		String jpaquery = "SELECT DISTINCT modulo FROM Modulos modulo join fetch modulo.modulosnoticias WHERE :noticia in elements(modulo.modulosnoticias)";
		TypedQuery<Modulos> q = em.createQuery(jpaquery,Modulos.class);
		q.setParameter("noticia", noticia);
		return q.getResultList();
	}
	
	@Override
	public Noticias incluir(Noticias noticia) {
		em.merge(noticia);
		em.flush();
		
		return noticia;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Noticias recuperarUnico(Noticias noticia) {
		return em.find(Noticias.class, noticia);
	}

	@Override
	public void alterar(Noticias noticia) {
		em.merge(noticia);
		em.flush();
		em.detach(noticia);
	}
	
}
