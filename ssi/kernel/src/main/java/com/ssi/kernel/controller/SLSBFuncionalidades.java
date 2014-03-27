package com.ssi.kernel.controller;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.kernel.controller.interfaces.FuncionalidadesInt;
import com.ssi.kernel.model.Funcionalidades;
import com.ssi.kernel.model.Grupos;
import com.ssi.kernel.model.Modulos;
import com.ssi.kernel.model.Pessoas;


@Stateless(mappedName = "SLSBFuncionalidades")
public class SLSBFuncionalidades implements FuncionalidadesInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
    public SLSBFuncionalidades() {}
    
	@Override
    public List<Funcionalidades> retornarPaginado(int firstRow, int numberOfRows, Map<String,Object>filtros) {
		Modulos modulo = (Modulos) filtros.get("modulo");
		if(modulo != null){
			
			final String jpaquery = "SELECT func FROM Funcionalidades func WHERE func.modulofuncionalidade.idmodulo = "+modulo.getIdmodulo();
	    	TypedQuery<Funcionalidades> q = em.createQuery(jpaquery,Funcionalidades.class);
	        
	        if (firstRow >= 0 && numberOfRows > 0){
	        	q.setFirstResult(firstRow);
	        	q.setMaxResults(numberOfRows);
	        }
	        modulo = null;
	        return q.getResultList();
		}
		else
			return new ArrayList<Funcionalidades>();
    }
    
	@Override
    public Funcionalidades getRowData(Object rowKey){
    	return em.find(Funcionalidades.class, rowKey);
    }
    
    
    @Override
    public Integer getRowCount(Map<String,Object>filtros){
    	Modulos modulo = (Modulos) filtros.get("modulo");
    	if(modulo != null){
	    	final String jpaquery = "SELECT count(func.idfuncionalidade) FROM Funcionalidades func WHERE func.modulofuncionalidade.idmodulo = "+modulo.getIdmodulo();
	    	TypedQuery<Long> q = em.createQuery(jpaquery,Long.class);
	    	Long lid = q.getResultList().get(0);
	    	return lid.intValue();
    	}
    	else
    		return 0;
    }
    
    // O m�todo abaixo ir� recuperar todas as funcionalidades que n�o s�o do m�dulo passado como par�metro
    // mas s�o do grupo de acesso (tamb�m passado)
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Funcionalidades> recuperarDiferentes(Modulos modulo, Grupos grupo){
    	final String jpaquery = "SELECT func FROM Funcionalidades func WHERE func.modulofuncionalidade <> :modulo and :grupo in elements(func.grupos)";
    	TypedQuery<Funcionalidades> q = em.createQuery(jpaquery,Funcionalidades.class);
    	q.setParameter("modulo", modulo);
    	q.setParameter("grupo", grupo);
    	return q.getResultList();
    }
    
    // Mesma coisa do m�todo acima, s� que pra Pessoas
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Funcionalidades> recuperarDiferentesPessoas(Modulos modulo, Pessoas pessoa){
    	final String jpaquery = "SELECT func FROM Funcionalidades func WHERE func.modulofuncionalidade <> :modulo and :pessoa in elements(func.pessoas)";
    	TypedQuery<Funcionalidades> q = em.createQuery(jpaquery,Funcionalidades.class);
    	q.setParameter("modulo", modulo);
    	q.setParameter("pessoa", pessoa);
    	return q.getResultList();
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public void verificaExistente(Long idmodulo, String cnmfuncionalidade) throws IndexOutOfBoundsException {
    	final String jpaquery = "SELECT func FROM Funcionalidades func WHERE cnmfuncionalidade = '"+cnmfuncionalidade.trim()+"' and idmodulo = "+idmodulo;
    	em.createQuery(jpaquery, Funcionalidades.class).getResultList().get(0);
    }
    
    // Usado pra construir o menu.
    // A 1a query Recupera as funcionalidades com permiss�o de grupo gen�ricas e a 2a recupera as funcionalidades com permiss�es espec�ficas
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Funcionalidades> recuperar(Long idmodulo, Pessoas pessoa){
    	final String jpaqueryG = "SELECT func FROM Funcionalidades func join fetch func.grupos WHERE :grupo in elements(func.grupos) and func.modulofuncionalidade.idmodulo = "+idmodulo+" order by func.idfuncionalidade ";
    	TypedQuery<Funcionalidades> qG = em.createQuery(jpaqueryG,Funcionalidades.class);
    	qG.setParameter("grupo", pessoa.getUsuario().getGrupo());
    	List<Funcionalidades> listafuncG = qG.getResultList();
    	
    	final String jpaqueryP = "SELECT func FROM Funcionalidades func join fetch func.pessoas WHERE :pessoa in elements(func.pessoas) and func.modulofuncionalidade.idmodulo = "+idmodulo+" order by func.idfuncionalidade ";
    	TypedQuery<Funcionalidades> qP = em.createQuery(jpaqueryP,Funcionalidades.class);
    	qP.setParameter("pessoa", pessoa);
    	List<Funcionalidades> listafuncP = qP.getResultList();
    	
    	Set<Funcionalidades> listatodos = new LinkedHashSet<Funcionalidades>();
    	listatodos.addAll(listafuncG);
    	listatodos.addAll(listafuncP);
    	
    	return new ArrayList<Funcionalidades>(listatodos);
    }
    
    // Basicamente usado para construir a tree de funcionalidades para atribuir a grupos ou usu�rio
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Funcionalidades> recuperarPorModulo(Long idmodulo){
    	final String jpaquery = "SELECT func FROM Funcionalidades func WHERE func.modulofuncionalidade.idmodulo = "+idmodulo+" ";
    	TypedQuery<Funcionalidades> q = em.createQuery(jpaquery,Funcionalidades.class);
    	return q.getResultList();
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Funcionalidades recuperarUnico(Long idfuncionalidade){
    	final String jpaquery = "SELECT func FROM Funcionalidades func WHERE idfuncionalidade = "+idfuncionalidade;
    	return em.createQuery(jpaquery,Funcionalidades.class).getResultList().get(0);
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Funcionalidades> recuperarPossiveisPais(Funcionalidades funcionalidade){
    	final String jpaquery = "SELECT func FROM Funcionalidades func WHERE idfuncionalidade != "+funcionalidade.getIdfuncionalidade()+" AND func.modulofuncionalidade.idmodulo = "+funcionalidade.getModulofuncionalidade().getIdmodulo();
    	TypedQuery<Funcionalidades> q = em.createQuery(jpaquery,Funcionalidades.class);
    	return q.getResultList();
    }
    
    @Override
	public List<Funcionalidades> retornarFuncionalidadesGrupos(Grupos grupo){
		String jpaquery = "SELECT DISTINCT funcionalidade FROM Funcionalidades funcionalidade join fetch funcionalidade.grupos WHERE :grupo in elements(funcionalidade.grupos)";
		TypedQuery<Funcionalidades> q = em.createQuery(jpaquery,Funcionalidades.class);
		q.setParameter("grupo", grupo);
		return q.getResultList();
	}
    
    @Override
	public List<Funcionalidades> retornarFuncionalidadesPessoas(Pessoas pessoa){
		String jpaquery = "SELECT DISTINCT funcionalidade FROM Funcionalidades funcionalidade join fetch funcionalidade.pessoas WHERE :pessoa in elements(funcionalidade.pessoas)";
		TypedQuery<Funcionalidades> q = em.createQuery(jpaquery,Funcionalidades.class);
		q.setParameter("pessoa", pessoa);
		return q.getResultList();
	}

	@Override
	public Funcionalidades incluir(Funcionalidades funcionalidade) {
		em.persist(funcionalidade);
		em.flush();
		em.refresh(funcionalidade);
		
		return funcionalidade;
	}

	@Override
	public void alterar(Funcionalidades funcionalidade) {
		em.merge(funcionalidade);
		em.detach(funcionalidade);
	}


	@Override
	public Funcionalidades recuperarPai(Funcionalidades funcionalidade) {
		final String jpaquery = "SELECT func FROM Funcionalidades func WHERE idfuncionalidade = "+funcionalidade.getFuncionalidadepai().getIdfuncionalidade();
    	return em.createQuery(jpaquery,Funcionalidades.class).getResultList().get(0);
	}


	@Override
	public List<Funcionalidades> recuperarFilhos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remover(Funcionalidades funcionalidade) {
		//em.remove(em.contains(funcionalidade) ? funcionalidade : em.merge(funcionalidade));
		//em.flush();
		//em.detach(funcionalidade);
	}

	@Override
	public boolean verificarPermissaoFuncionalidadePessoa(Funcionalidades func,
			Pessoas pessoa) {
		final String jpaqueryP = "SELECT func FROM Funcionalidades func join fetch func.pessoas WHERE :pessoa in elements(func.pessoas) and func = :func";
    	TypedQuery<Funcionalidades> qP = em.createQuery(jpaqueryP,Funcionalidades.class);
    	qP.setParameter("pessoa", pessoa);
    	qP.setParameter("func", func);
    	try{
    		qP.getResultList().get(0);
    		return true;
    	}
    	catch(IndexOutOfBoundsException e){
    		return false;
    	}
	}

	@Override
	public boolean verificarPermissaoFuncionalidadeGrupo(Funcionalidades func,
			Grupos grupo) {
		final String jpaqueryG = "SELECT func FROM Funcionalidades func join fetch func.grupos WHERE :grupo in elements(func.grupos) and func = :func";
    	TypedQuery<Funcionalidades> qG = em.createQuery(jpaqueryG,Funcionalidades.class);
    	qG.setParameter("grupo", grupo);
    	qG.setParameter("func", func);
    	try{
    		qG.getResultList().get(0);
    		return true;
    	}
    	catch(IndexOutOfBoundsException e){
    		return false;
    	}
	}

}
