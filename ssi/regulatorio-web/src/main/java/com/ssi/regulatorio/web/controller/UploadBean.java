package com.ssi.regulatorio.web.controller;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.ssi.kernel.redes.domain.AnexosRedes;
import com.ssi.kernel.redes.domain.DemandasRedes;
import com.ssi.kernel.redes.domain.TiposAnexosRedes;
import com.ssi.kernel.redes.interfaces.AnexosRedesInt;
import com.ssi.regulatorio.common.utils.ParametrosSistema;

@ManagedBean
@RequestScoped
public class UploadBean {

	@EJB
	private AnexosRedesInt anexosint;
	
	private DemandasRedes demanda;
	private DemandasMB demandasmb = RecuperadorInstanciasBean.recuperarInstanciaDemandasBean();
	
    private File file;
    private ParametrosSistema parametrosistema = new ParametrosSistema();
    private boolean exibeupload;
    private String tipoupload;
    
    @PostConstruct
	public void init() {
    	if(demanda == null){
			demanda = demandasmb.getDemandasRedes();
		}
    }
    
    public void habilitaUpload(String tipoupload){
    	//System.out.println(">>Caiu aqui<<");
    	exibeupload = true;
    	this.setTipoupload(tipoupload);
    }
    
    public void submit() {
    	
    	String sufixofile  = "";
    	
    	Integer switchtipoupload = new Integer(tipoupload);
    	
    	TiposAnexosRedes tipoanexo = anexosint.recuperarTipoAnexo(new Long(switchtipoupload));
    	
    	
    	//switch(switchtipoupload){
//	    	case 1: // Minuta
//	    		Integer nnrnovaversao = 0;
//	        	Integer nnrversaoanterior = 0;
//	        	AnexosRedes ultimaminuta = anexosint.retornarUltimaMinutaDemanda(demanda);
//	        	
//	        	if(ultimaminuta.getCnmversao() == null || ultimaminuta.getCnmversao().equals("")){
//	        		nnrnovaversao = 1;
//	        	}
//	        	else{
//	        		nnrversaoanterior = new Integer(ultimaminuta.getCnmversao());
//	        		nnrnovaversao = nnrversaoanterior+1;
//	        	}
//	        	
//	        	if (file.getName().contains(".")) {
//	        		sufixofile = file.getName().substring(file.getName().lastIndexOf('.'));
//	            }
//	        	
//	        	//String novonome = file.getName();
//	        	//String novonome = tipoanexo.getCnmnomearquivo()+"_d"+demanda.getIddemanda()+"_v"+nnrnovaversao;
//	        		        	
//	        	String novonome = file.getName().substring(0,file.getName().length()-24);
//	        	
//	        	if(!sufixofile.equals("")){
//	        		novonome+=sufixofile;
//	        	}
//	        	
//	        	File newfile = new File(parametrosistema.recuperaCaminhoUploads()+novonome);
//	            file.renameTo(newfile);
//	        	
//	            incluirAnexo(novonome, tipoanexo, nnrnovaversao.toString());
//	        	
//	    	break;
	    	
	    	//default: // Os demais anexos seguem regras semelhantes, a �nica exce��o por enquanto � a Minuta
	    		if (file.getName().contains(".")) {
	        		sufixofile = file.getName().substring(file.getName().lastIndexOf('.'));
	            }
	        	
	    		String novonome2 = file.getName().substring(0,file.getName().length()-24);
	        	
	    		if(!sufixofile.equals("")){
	    			novonome2+=sufixofile;
	        	}
	    		
	        	File newfile2 = new File(parametrosistema.recuperaCaminhoUploads()+novonome2);
	            file.renameTo(newfile2);
	        	
	            incluirAnexo(novonome2, tipoanexo, "NA");
	    	//break;
    	//}
    }
    
    public void incluirAnexo(String caminho, TiposAnexosRedes tipoanexo, String versao){
    	AnexosRedes anexoincluir = new AnexosRedes();
    	
    	anexoincluir.setDemanda(demanda);
    	anexoincluir.setCnmcaminhoanexo(caminho);
    	
    	anexoincluir.setTipoanexo(tipoanexo);
    	anexoincluir.setAutor(demandasmb.recuperarPessoaLogada());
    	anexoincluir.setCnmversao(versao);
    	
    	anexosint.incluir(anexoincluir);
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

	public ParametrosSistema getParametrosistema() {
		return parametrosistema;
	}

	public void setParametrosistema(ParametrosSistema parametrosistema) {
		this.parametrosistema = parametrosistema;
	}


	public DemandasRedes getDemanda() {
		return demanda;
	}

	public void setDemanda(DemandasRedes demanda) {
		this.demanda = demanda;
	}

	public DemandasMB getDemandasmb() {
		return demandasmb;
	}

	public void setDemandasmb(DemandasMB demandasmb) {
		this.demandasmb = demandasmb;
	}

	public boolean isExibeupload() {
		return exibeupload;
	}

	public void setExibeupload(boolean exibeupload) {
		this.exibeupload = exibeupload;
	}

	public String getTipoupload() {
		return tipoupload;
	}

	public void setTipoupload(String tipoupload) {
		this.tipoupload = tipoupload;
	}
    
}