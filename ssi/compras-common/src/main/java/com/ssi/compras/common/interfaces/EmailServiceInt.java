package com.ssi.compras.common.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.model.Pessoas;

@Local
public interface EmailServiceInt {

	void enviarEMail(List<Long> papeis, Demandas demanda, String mensagem, String assunto, List<Pessoas> outrasPessoasParaEnvio);
	
}
