package com.ssi.compras.common.interfaces;


import javax.ejb.Local;

import com.ssi.kernel.compras.domain.Lps;
import com.ssi.kernel.model.Pessoas;

@Local
public interface PermissoesInt{
	public boolean verificarPermissaoEmissor(Pessoas pessoa);
	public boolean verificarPermissaoResponsavelTecnico(Pessoas pessoa);
	public boolean verificarPermissaoGerenteOrcamentos(Pessoas pessoa);
	public boolean verificarPermissaoAnalistaOrcamentos(Pessoas pessoa);
	public boolean verificarPermissaoGerenteCompras(Pessoas pessoa, Lps lp);
	public boolean verificarPermissaoDiretor(Pessoas pessoa);
	public boolean verificarPermissaoGerenteAquisicoes(Pessoas pessoa);
	public boolean verificarPermissaoAnalistaAquisicoes(Pessoas pessoa);
	public boolean verificarPermissaoGerenteContrato(Pessoas pessoa);
	public boolean verificarPermissaoAnalistaContrato(Pessoas pessoa);
	//public boolean verificarPermissaoComprador(Pessoas pessoa);
	boolean verificarPermissaoCompradorLP4(Pessoas pessoa);
	boolean verificarPermissaoCompradorLP3(Pessoas pessoa);
	boolean verificarPermissaoCompradorLP2ro(Pessoas pessoa);
	boolean verificarPermissaoCompradorLP2s(Pessoas pessoa);
	boolean verificarPermissaoCompradorLP1(Pessoas pessoa);
}
