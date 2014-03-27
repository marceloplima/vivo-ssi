package com.ssi.kernel.controller.interfaces;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.ssi.kernel.model.Areas;
import com.ssi.kernel.model.Emails;
import com.ssi.kernel.model.Funcionalidades;
import com.ssi.kernel.model.GruposModulos;
import com.ssi.kernel.model.Pessoas;
import com.ssi.kernel.model.Telefones;
import com.ssi.kernel.model.Usuarios;

@Local
public interface PessoasInt {
	public Pessoas recuperarUnico(Long id);
	public Pessoas recuperarUnico(Pessoas pessoa);
	public boolean verificaExistente(Pessoas pessoa);
	public Pessoas incluir(Pessoas pessoa);
	public void alterar(Pessoas pessoa);
	public List<Pessoas> recuperar();
	public void remover(Pessoas pessoa);
	public Integer getRowCount(Map<String,Object>filtros);
	public Pessoas getRowData(Object rowKey);
	public List<Pessoas> retornarPaginado(int firstRow, int numberOfRows, Map<String,Object>filtros);
	public List<Emails> retornarEmailsPessoa(Pessoas pessoa);
	public List<Telefones> retornarTelefonesPessoa(Pessoas pessoa);
	public List<Areas> retornarAreasPessoa(Pessoas pessoa);
	public List<GruposModulos> retornarGruposModulosPessoa(Pessoas pessoa);
	public Pessoas recuperarPorUsuario(Usuarios usuario);
	public List<Funcionalidades> retornarFuncionalidadesPessoa(Pessoas pessoa);
	public String retornaTelefone(List<Telefones> telefones,Long idTipoTelefone);
	public List<String> recuperarAutocomplete(String filtro);
	Pessoas recuperarPorNome(String cnmnome);
}
