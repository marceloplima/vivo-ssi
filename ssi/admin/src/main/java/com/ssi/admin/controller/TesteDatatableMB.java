package com.ssi.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.kernel.model.Pessoas;

@ManagedBean
@ViewScoped
public class TesteDatatableMB {

	private List<Pessoas> listateste = new ArrayList<Pessoas>();
	
	public List<Pessoas> testar(){
		
		Pessoas pessoa1 = new Pessoas();
		pessoa1.setCdscpf("000.000.000-00");
		pessoa1.setCnmnome("DENIS ZUBIETA");
		
		Pessoas pessoa2 = new Pessoas();
		pessoa2.setCdscpf("111.111.111-11");
		pessoa2.setCnmnome("M�RCIO PEIXE");
		
		Pessoas pessoa3 = new Pessoas();
		pessoa3.setCdscpf("222.222.222-22");
		pessoa3.setCnmnome("DANY GLOVER");
		
		Pessoas pessoa4 = new Pessoas();
		pessoa4.setCdscpf("333.333.333-33");
		pessoa4.setCnmnome("RICO");
		
		listateste.add(pessoa1);
		listateste.add(pessoa2);
		listateste.add(pessoa3);
		listateste.add(pessoa4);
		
		return listateste;
	}
}
