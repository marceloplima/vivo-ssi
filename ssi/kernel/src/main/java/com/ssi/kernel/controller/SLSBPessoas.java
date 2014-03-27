package com.ssi.kernel.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.kernel.controller.interfaces.PessoasInt;
import com.ssi.kernel.model.Areas;
import com.ssi.kernel.model.Emails;
import com.ssi.kernel.model.Funcionalidades;
import com.ssi.kernel.model.GruposModulos;
import com.ssi.kernel.model.Pessoas;
import com.ssi.kernel.model.Telefones;
import com.ssi.kernel.model.Usuarios;

@Stateless(mappedName = "SLSBPessoas")
public class SLSBPessoas implements PessoasInt {
		
	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
	@Override
	public Pessoas recuperarUnico(Long id) {
		String jpaquery = "SELECT pessoa FROM Pessoas pessoa WHERE idpessoa = '"+ id +"'";
		
		return em.createQuery(jpaquery, Pessoas.class).getResultList().get(0);
		
	}	
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Pessoas recuperarUnico(Pessoas pessoa) {
		return em.find(Pessoas.class, pessoa);
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public boolean verificaExistente(Pessoas pessoa) throws IndexOutOfBoundsException {
		Pessoas obj;
		try{
			String jpaquery = "SELECT pessoa FROM Pessoas pessoa WHERE pessoa.cdscpf = '"+pessoa.getCdscpf().trim()+"' or pessoa.usuario.cnmlogin = '"+pessoa.getUsuario().getCnmlogin().trim()+"'";
			
			obj = em.createQuery(jpaquery, Pessoas.class).getResultList().get(0);
		}
    	catch(IndexOutOfBoundsException e){
    		return false;
    	}
		
		if(obj!=null)
			return true;
		else
			return false;
	}
	
	@Override
	public Pessoas incluir(Pessoas pessoa) {
		
		pessoa.remapearItens(pessoa);
		
		em.merge(pessoa);
		em.flush();
		
		return pessoa;
	}

	@Override
	public void alterar(Pessoas pessoa) {
		
		pessoa.remapearItens(pessoa);
		
		em.merge(pessoa);
		em.flush();
	}
	
	@Override
	public Pessoas recuperarPorUsuario(Usuarios usuario) {
		final String jpaquery = "SELECT pessoa FROM Pessoas pessoa where pessoa.usuario.cnmlogin = '"+usuario.getCnmlogin()+"'";
		TypedQuery<Pessoas> q = em.createQuery(jpaquery, Pessoas.class);
		return q.getResultList().get(0);
	}
	
	@Override
	public List<Pessoas> recuperar() {
		final String jpaquery = "SELECT pessoa FROM Pessoas pessoa";
		TypedQuery<Pessoas> q = em.createQuery(jpaquery, Pessoas.class);
		return q.getResultList();
	}
	
	@Override
	public List<String> recuperarAutocomplete(String filtro) {
		final String jpaquery = "SELECT pessoa.cnmnome FROM Pessoas pessoa WHERE pessoa.cnmnome like '%"+filtro+"%'";
		TypedQuery<String> q = em.createQuery(jpaquery, String.class);
		return q.getResultList();
	}
	
	@Override
	public void remover(Pessoas pessoa){
		em.merge(pessoa);
		em.detach(pessoa);
	}

	@Override
	public Integer getRowCount(Map<String, Object> filtros) {
    	Pessoas pessoa = (Pessoas) filtros.get("pessoa");
    	if(pessoa != null){
	    	String jpaquery = "SELECT count(pessoa.idpessoa) FROM Pessoas pessoa WHERE 1=1 ";
	    	
	    	if(pessoa.isFlagativo())
	    		jpaquery +="and pessoa.flagativo = '1' ";
	    	else
	    		jpaquery +="and pessoa.flagativo = '0' ";
	    	
	    	if(pessoa.getCnmnome()!=null && !pessoa.getCnmnome().isEmpty()){
	    		jpaquery +="and pessoa.cnmnome like '%"+pessoa.getCnmnome()+"%' ";
	    	}
	    	
	    	if(pessoa.getCdscpf()!=null && !pessoa.getCdscpf().isEmpty()){
	    		jpaquery +="and pessoa.cdscpf like '%"+pessoa.getCdscpf()+"%' ";
	    	}
	    	
	    	if(pessoa.getUsuario()!=null && pessoa.getUsuario().getCnmlogin()!=null && !pessoa.getUsuario().getCnmlogin().isEmpty()){
	    		jpaquery +="and pessoa.usuario.cnmlogin like '%"+pessoa.getUsuario().getCnmlogin()+"%' ";
	    	}
	    	
	    	TypedQuery<Long> q = em.createQuery(jpaquery,Long.class);
	    	Long lid = q.getResultList().get(0);
	    	jpaquery = null;
	    	return lid.intValue();
    	}
    	else
    		return 0;
	}

	@Override
	public Pessoas getRowData(Object rowKey) {
		return em.find(Pessoas.class, rowKey);
	}
	
	@Override
	public List<Emails> retornarEmailsPessoa(Pessoas pessoa){
		String jpaquery = "SELECT email FROM Emails email WHERE emailspessoa = :pessoa";
		TypedQuery<Emails> q = em.createQuery(jpaquery,Emails.class);
		q.setParameter("pessoa", pessoa);
		return q.getResultList();
	}
	
	@Override
	public List<Telefones> retornarTelefonesPessoa(Pessoas pessoa){
		String jpaquery = "SELECT telefone FROM Telefones telefone WHERE telefonespessoa = :pessoa";
		TypedQuery<Telefones> q = em.createQuery(jpaquery,Telefones.class);
		q.setParameter("pessoa", pessoa);
		return q.getResultList();
	}
	
	@Override
	public List<Areas> retornarAreasPessoa(Pessoas pessoa){
		String jpaquery = "SELECT DISTINCT area FROM Areas area join fetch area.areaspessoas WHERE :pessoa in elements(area.areaspessoas) order by area.cnmsiglaarea";
		TypedQuery<Areas> q = em.createQuery(jpaquery,Areas.class);
		q.setParameter("pessoa", pessoa);
		return q.getResultList();
	}
	
	@Override
	public List<GruposModulos> retornarGruposModulosPessoa(Pessoas pessoa){
		String jpaquery = "SELECT DISTINCT grupomodulo FROM GruposModulos grupomodulo join fetch grupomodulo.gruposmodulospessoas WHERE :pessoa in elements(grupomodulo.gruposmodulospessoas)";
		TypedQuery<GruposModulos> q = em.createQuery(jpaquery,GruposModulos.class);
		q.setParameter("pessoa", pessoa);
		return q.getResultList();
	}
	
	@Override
	public List<Funcionalidades> retornarFuncionalidadesPessoa(Pessoas pessoa){
		String jpaquery = "SELECT DISTINCT func FROM Funcionalidades func join fetch func.pessoas WHERE :pessoa in elements(func.pessoas)";
		TypedQuery<Funcionalidades> q = em.createQuery(jpaquery,Funcionalidades.class);
		q.setParameter("pessoa", pessoa);
		return q.getResultList();
	}

	@Override
	public List<Pessoas> retornarPaginado(int firstRow, int numberOfRows, Map<String, Object> filtros) {
		Pessoas pessoa = (Pessoas) filtros.get("pessoa");
		if(pessoa != null){
			String jpaquery = "SELECT pessoa FROM Pessoas pessoa WHERE 1=1 ";
			
			if(pessoa.isFlagativo())
	    		jpaquery +="and pessoa.flagativo = '1' ";
	    	else
	    		jpaquery +="and pessoa.flagativo = '0' ";
			
			if(pessoa.getCnmnome()!=null && !pessoa.getCnmnome().isEmpty()){
	    		jpaquery +="and pessoa.cnmnome like '%"+pessoa.getCnmnome()+"%' ";
	    	}
	    	
	    	if(pessoa.getCdscpf()!=null && !pessoa.getCdscpf().isEmpty()){
	    		jpaquery +="and pessoa.cdscpf like '%"+pessoa.getCdscpf()+"%' ";
	    	}
	    	
	    	if(pessoa.getUsuario()!=null && pessoa.getUsuario().getCnmlogin()!=null && !pessoa.getUsuario().getCnmlogin().isEmpty()){
	    		jpaquery +="and pessoa.usuario.cnmlogin like '%"+pessoa.getUsuario().getCnmlogin()+"%' ";
	    	}
	    	
	    	jpaquery +=" order by pessoa.cnmnome ";
			
	    	TypedQuery<Pessoas> q = em.createQuery(jpaquery,Pessoas.class);
	        
	        if (firstRow >= 0 && numberOfRows > 0){
	        	q.setFirstResult(firstRow);
	        	q.setMaxResults(numberOfRows);
	        }
	        pessoa = null;
	        return q.getResultList();
		}
		else
			return new ArrayList<Pessoas>();
	}

	@Override
	public String retornaTelefone(List<Telefones> telefones,Long idTipoTelefone) {
		for (Telefones telefone : telefones) {

			if (telefone.getTipotelefone().getIdtipotelefone()
					.equals(idTipoTelefone)) {
				return telefone.getCnmtelefone();
			}

		}
		return "";
	}
	
	@Override
	public Pessoas recuperarPorNome(String cnmnome) {
		final String jpaquery = "SELECT pessoa from Pessoas pessoa WHERE pessoa.cnmnome = '"+cnmnome+"'";
		
		TypedQuery<Pessoas> q = em.createQuery(jpaquery, Pessoas.class);
		
		try{
			return q.getResultList().get(0);
		}
		catch(IndexOutOfBoundsException e){
			return new Pessoas();
		}
	}

}
