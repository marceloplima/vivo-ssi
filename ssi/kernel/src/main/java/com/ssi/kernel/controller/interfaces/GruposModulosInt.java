package com.ssi.kernel.controller.interfaces;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.ssi.kernel.model.GruposModulos;
import com.ssi.kernel.model.Pessoas;

@Local
public interface GruposModulosInt {

	public List<GruposModulos> recuperar();
	public GruposModulos recuperarUnico(GruposModulos grupo);
	public void alterar(GruposModulos gm);
	public boolean verificaExistente(GruposModulos grupomodulo) throws IndexOutOfBoundsException;
	public List<GruposModulos> recuperarFiltrado(Map<String, Object> filtros);
	public GruposModulos incluir(GruposModulos gm);
	public List<Pessoas> recuperaPessoasDoGrupo(GruposModulos grupoModulo);
	
}
