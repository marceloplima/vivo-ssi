package com.ssi.kernel.redes.interfaces.service;

import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.redes.domain.DemandasRedes;
import com.ssi.kernel.model.Pessoas;

@Local
public interface EmailRedesServiceInt {

	void enviarEMail(List<Long> papeis, DemandasRedes demanda, String mensagem, String assunto, List<Pessoas> outrasPessoasParaEnvio);
	
}
