package com.ssi.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.ssi.kernel.controller.interfaces.AreasInt;
import com.ssi.kernel.model.Areas;

// Est� buscando FUNCIONALIDADES por enquanto (para testar)
// Mudar para AREAS quando OK

@ManagedBean
@RequestScoped
public class AreasTreeMB{  
    
	@EJB
	private AreasInt areasint;
	
	//List<Funcionalidades> funcionalidades = new ArrayList<Funcionalidades>();
	List<Areas> areas = new ArrayList<Areas>();
	private OptionTreeNode stationNodes = new OptionTreeNode(); 
      
	@PostConstruct
    public void init() {
		stationNodes = new OptionTreeNode();
		areas = new ArrayList<Areas>();
	}
	
	public OptionTreeNode getStationNodes() {
		initRichFacesTree();
		return stationNodes;  
	}
        
	public void recuperarFilhos(Areas pai,OptionTreeNode nopai){
		List<Areas> filhos = pai.getAreasfilho();
		for (Areas filho:filhos){
			OptionTreeNode child = new OptionTreeNode();
			child.setArea(filho);
			nopai.addChild(filho.getIdarea(), child);
        		 
			if(filho.getAreasfilho().size()>=1)
				recuperarFilhos(filho, child);
		}
	}
        
	public void initRichFacesTree() {
		
		List<Areas> areas = areasint.recuperar();
		
		for (Areas obj:areas){
			if(obj.getAreapai()==null){
				OptionTreeNode stationRoot = new OptionTreeNode();
				stationRoot.setArea(obj);
				stationNodes.addChild(obj.getIdarea(), stationRoot);
            		
				if(obj.getAreasfilho().size()>=1){
					recuperarFilhos(obj,stationRoot);
				}
			}
		}
	}
}  