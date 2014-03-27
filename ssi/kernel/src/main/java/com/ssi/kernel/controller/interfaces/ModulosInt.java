package com.ssi.kernel.controller.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.model.Modulos;

@Local
public interface ModulosInt {
	public Modulos incluir(Modulos modulo);
	public void alterar(Modulos modulo);
	public List<Modulos> recuperar();
	public Modulos recuperarUnico(Long idmodulo);
	public List<Modulos> buscarModulos(Modulos modulo);
	public boolean verificaExistente(Modulos modulo);
	public void remover(Modulos modulo);
}
