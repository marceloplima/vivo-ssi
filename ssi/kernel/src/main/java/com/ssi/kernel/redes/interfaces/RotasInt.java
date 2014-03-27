package com.ssi.kernel.redes.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.redes.domain.DemandasRedes;
import com.ssi.kernel.redes.domain.Rotas;

@Local
public interface RotasInt {
	Rotas persistir(Rotas rota);
	List<Rotas> recuperarRotasDemanda(DemandasRedes demanda);
	void remover(Rotas rota);
}
