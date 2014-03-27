package com.ssi.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.admin.utils.RecuperadorInstanciasBean;
import com.ssi.admin.utils.ValidadorCamposInvalidos;
import com.ssi.kernel.controller.interfaces.AreasInt;
import com.ssi.kernel.controller.interfaces.EmailsInt;
import com.ssi.kernel.controller.interfaces.PessoasInt;
import com.ssi.kernel.controller.interfaces.TelefonesInt;
import com.ssi.kernel.controller.interfaces.TiposTelefoneInt;
import com.ssi.kernel.model.Areas;
import com.ssi.kernel.model.Emails;
import com.ssi.kernel.model.Pessoas;
import com.ssi.kernel.model.Telefones;
import com.ssi.kernel.utils.MD5Hashing;
import com.ssi.kernel.utils.ValidadorCPF;
import com.ssi.kernel.utils.ValidadorEmail;


@ManagedBean
@ViewScoped
public class CadPessoasMB implements java.io.Serializable{

	private static final long serialVersionUID = -8262906888365591962L;

	@EJB 
	private PessoasInt pessoasint;
	
	@EJB 
	private EmailsInt emailsremote;
	
	@EJB 
	private TiposTelefoneInt tipostelefoneint;
	
	@EJB 
	private TelefonesInt telefonesint;
	
	@EJB 
	private AreasInt areasint;
	
	List<Areas> areas;
	private OptionTreeNode areanos = new OptionTreeNode(); 
	
	private Pessoas pessoa;
	private Emails email = new Emails();
	private Emails emailupdate = new Emails(); // Usado na edi��o (update)
	private boolean editemail;
	
	private Telefones telefone = new Telefones();
	private Telefones telefoneupdate = new Telefones(); // Usado na edi��o (update)
	private boolean edittelefone;
	
	//private boolean flagloginrede;
	
	private IndexMB indexmb;
	private ValidadorEmail validadoremail;
	private ValidadorCPF validadorcpf;
	private ValidadorCamposInvalidos validadorcamposinvalidos;
	
	private List<String>msgserro;
	
	
	@PostConstruct
    public void init() {
		
		pessoa  = new Pessoas();
		
		validadoremail = new ValidadorEmail();
		validadorcpf = new ValidadorCPF();
		validadorcamposinvalidos = new ValidadorCamposInvalidos();
		
		areanos = new OptionTreeNode();
		
		if(areas == null)
			areas   = areasint.recuperar();
		
    }
	
	
	// Etapa 1 para montar a �rvore de �reas (unidades organizacionais)
	public void initRichFacesTree() {
		for (Areas obj:areas){
			if(obj.getAreapai()==null){
				OptionTreeNode raiz = new OptionTreeNode();
				raiz.setArea(obj);
				areanos.addChild(obj.getIdarea(), raiz);
	            		
				if(obj.getAreasfilho().size()>=1){
					recuperarFilhos(obj,raiz);
				}
			}
		}
	}
	
	// Etapa 2 para montar a �rvore de �reas (unidades organizacionais)
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
	
	
	public void inicializarValidadores(){
		indexmb = RecuperadorInstanciasBean.recuperarInstanciaIndexBean();
    	msgserro = new ArrayList<String>();
	}
	
    public void incluir() {
    	inicializarValidadores();
    	
    	// Cria um mapa com os campos que precisamos validar genericamente
    	Map<String,String> camposvalidar = new HashMap<String,String>();
    	
    	camposvalidar.put("Nome Completo", pessoa.getCnmnome());
    	camposvalidar.put("CPF", pessoa.getCdscpf());
    	//camposvalidar.put("Registro GAUS", pessoa.getIdgaus().toString());
    	if(pessoa.getUsuario()!=null){
	    	if(pessoa.getUsuario().getTipousuario()!=null)
	    		camposvalidar.put("Tipo do Usuario", pessoa.getUsuario().getTipousuario().getIdtipousuario().toString());
	    	else
	    		camposvalidar.put("Tipo do Usuario", "");
	    	
	    	if(pessoa.getUsuario().getGrupo()!=null)
	    		camposvalidar.put("Grupo", pessoa.getUsuario().getGrupo().getIdgrupo().toString());
	    	else if(pessoa.getUsuario()!=null && pessoa.getUsuario().getGrupo()!=null && pessoa.getUsuario().getGrupo().getIdgrupo()!=null)
	    		camposvalidar.put("Grupo", pessoa.getUsuario().getGrupo().getIdgrupo().toString());
	    	
	    	camposvalidar.put("Login", pessoa.getUsuario().getCnmlogin());
	    	
	    	
    	}
    	else{
    		camposvalidar.put("Login", "");
    		camposvalidar.put("Tipo do Usuario", "");
    	}
    	
    	if(camposvalidar.size()>=1 && camposvalidar !=null)
    		msgserro = validadorcamposinvalidos.validarCamposVazios(camposvalidar);
    	
    	if(msgserro.size()==0){
	    	if(validadorcpf.validar(pessoa.getCdscpf())){
	    	
			    if(!pessoasint.verificaExistente(pessoa)){
			    	List<Areas> areaschecked = new ArrayList<Areas>();
			    	
			    	for (Areas area : areas){
			    		if(area.isChecked()){
			    			areaschecked.add(area);
			    		}
			    	}
			    	
			    	if(!areaschecked.isEmpty()){
			    		pessoa.setPessoasareas(new HashSet<Areas>(areaschecked));
			    	}
			    	
			    	if(pessoa.getUsuario().getCnmsenha().length()>=1){
			    		pessoa.getUsuario().setCnmsenha(MD5Hashing.criptografar(pessoa.getUsuario().getCnmsenha()));
			    	}
			    	pessoa.getUsuario().setPessoa(getPessoa());
			    	pessoa.setFlagativo(true);
			    	pessoa.getUsuario().setFlagativo(true);
			    	pessoasint.incluir(pessoa);
			    	
			    	indexmb.setPanelexibesucesso(true);
			    	indexmb.setMsgpanel("Pessoa cadastrada com sucesso!");
			    	
			    	pessoa = new Pessoas();
			    	
			    }
			    else{
			    	indexmb.setPanelexibealerta(true);
			    	indexmb.setMsgpanel("Pessoa j� existe no sistema");
			    }
	    	}
	    	else{
	    		indexmb.setPanelexibealerta(true);
		    	indexmb.setMsgpanel("CPF inv�lido - favor corrigir");
	    	}
    	}
    	else{
    		indexmb.setPanelexibeerro(true);
    		indexmb.setRenderizalistadeerros(true);
    		indexmb.setMsgspanel(msgserro);
    	}
    	
    	pessoa = new Pessoas();
    }
    
    public void deleteemail(Emails email){
    	List<Emails> novalista = new ArrayList<Emails>();
    	
    	Iterator<Emails> it = pessoa.getPessoaemails().iterator();
    	
    	while(it.hasNext()){
    		Emails obj = it.next();
    		
    		if(!obj.getCnmemail().equals(email.getCnmemail())){
    			novalista.add(obj);
    		}
    	}
    	
    	pessoa.setPessoaemails(new HashSet<Emails>(novalista));
	}
    
    public void editemail(Emails email){
    	setEditemail(true);
		
		this.emailupdate.setCnmemail(email.getCnmemail());
		this.emailupdate.setIdemail(email.getIdemail());
		this.emailupdate.setFlagativo(email.isFlagativo());
		
		this.email = email;
    }
    
    public void saveemail(){
    	inicializarValidadores();
    	
    	if(!emailsremote.verificaExistente(emailupdate)){
    		
    		if(validadoremail.validar(emailupdate.getCnmemail())){
    		
	    		List<Emails> novalista = new ArrayList<Emails>();
	    		Iterator<Emails> it = pessoa.getPessoaemails().iterator();
	    		
	    		while(it.hasNext()){
	        		Emails obj = it.next();
	        		
	        		if(!obj.getCnmemail().equals(email.getCnmemail())){
	        			novalista.add(obj);
	        		}
	        		else{
	        			novalista.add(emailupdate);
	        		}
	        	}
	    		
	    		pessoa.setPessoaemails(new HashSet<Emails>(novalista));
	    		setEditemail(false);
	    		email = new Emails();
	    		
    		}
    		else{
    			indexmb.setPanelexibealerta(true);
    			indexmb.setMsgpanel("Email inv�lido - Favor corrigir.");
    		}
    	}
    	else{
    		indexmb.setPanelexibealerta(true);
    		indexmb.setMsgpanel("Email j� existente");
    	}
    }
    
    public void addemail() {
    	inicializarValidadores();
    	
    	List<Emails>emailsexistentes = pessoa.getPessoaemails();
    	
    	if(validadoremail.validar(email.getCnmemail())){
    		
	    	Iterator<Emails> itm = emailsexistentes.iterator();
	    	
	    	boolean existe = false;
	    	while(itm.hasNext()){
	    		Emails obj = (Emails)itm.next();
	    		
		    		if(obj.getCnmemail().equals(email.getCnmemail())){
		    			existe = true;
		    		}
	    		
	    	}
	    	
	    	
	    	// Se n�o existe na listagem preliminar, procede para as verifica��es no banco
	    	if(!existe){
	    		
	    		// Verifica se j� n�o existe um email
	    		if(emailsremote.verificaExistente(email)){
	    			existe = true;
	    		}else{
		    		email.setFlagativo(true);
		    		
		    		emailsexistentes.add(email);
		    		
		    		pessoa.setPessoaemails(new HashSet<Emails>(emailsexistentes));
	    		}
	    		
	    		email = new Emails();
	    	}
	    	
	    	if(existe){
	    		indexmb.setPanelexibealerta(true);
    			indexmb.setMsgpanel("Email j� existente.");
	    	}
    	}
		else{
			indexmb.setPanelexibealerta(true);
			indexmb.setMsgpanel("Email inv�lido - Favor corrigir.");
		}
    	
    }
    
    public void deletetel(Telefones telefone){
    	
    	List<Telefones> novalista = new ArrayList<Telefones>();
    	
    	Iterator<Telefones> it = pessoa.getPessoatelefones().iterator();
    	
    	while(it.hasNext()){
    		Telefones obj = it.next();
    		
    		if(!obj.getCnmtelefone().equals(telefone.getCnmtelefone())){
    			novalista.add(obj);
    		}
    	}
    	
    	pessoa.setPessoatelefones(new HashSet<Telefones>(novalista));
    	
	}
    
    public void edittel(Telefones telefone){
    	setEdittelefone(true);
		
		this.telefoneupdate.setCnmtelefone(telefone.getCnmtelefone());
		this.telefoneupdate.setIdtelefone(telefone.getIdtelefone());
		this.telefoneupdate.setFlagativo(telefone.isFlagativo());
		this.telefoneupdate.setTipotelefone(telefone.getTipotelefone());
		
		this.telefone = telefone;
    }
    
    public void savetel(){
    	inicializarValidadores();
    	
    	telefoneupdate.setTipotelefone(tipostelefoneint.recuperarUnico(telefoneupdate.getTipotelefone().getIdtipotelefone()));
    	
    	if(!telefonesint.verificaExistente(telefoneupdate)){
    		List<Telefones> novalista = new ArrayList<Telefones>();
    		Iterator<Telefones> it = pessoa.getPessoatelefones().iterator();
    		
    		while(it.hasNext()){
    			Telefones obj = it.next();
    			
        		if(!obj.getCnmtelefone().equals(telefone.getCnmtelefone())){
        			novalista.add(obj);
        		}
        		else{
        			novalista.add(telefoneupdate);
        		}
        	}
    		
    		pessoa.setPessoatelefones(new HashSet<Telefones>(novalista));
    		setEdittelefone(false);
    		telefone = new Telefones();
    	}
    	else{
    		indexmb.setPanelexibealerta(true);
			indexmb.setMsgpanel("Telefone j� existente.");
    	}
    }
    
    public void addtel() {	
    	inicializarValidadores();
    	List<Telefones> telsexistentes = pessoa.getPessoatelefones();
    	Iterator<Telefones> itm = telsexistentes.iterator();
    	
    	// Cria um mapa com os campos que precisamos validar genericamente
    	Map<String,String> camposvalidar = new HashMap<String,String>();
    	
    	boolean existe = false;
    	while(itm.hasNext()){
    		Telefones obj = (Telefones)itm.next();
    		if(obj.getCnmtelefone().equals(telefone.getCnmtelefone())){
    			existe = true;
    		}
    	}
    	
    	// Se n�o existe na listagem preliminar, procede para as verifica��es no banco
    	if(!existe){
    		// Verifica se j� n�o existe um telefone com esse nome no banco
    		if(telefonesint.verificaExistente(telefone)){
    			existe = true;
    		}else{
    			
    			// Verifica se o tipo do telefone foi preenchido
    			inicializarValidadores();
    			if(telefone.getTipotelefone()==null)
    	    		camposvalidar.put("Tipo do Telefone", "");
    			
    			if(camposvalidar.size()>=1 && camposvalidar !=null)
    	    		msgserro = validadorcamposinvalidos.validarCamposVazios(camposvalidar);
    			
    			if(!(msgserro.size()>=1)){
		    		telefone.setFlagativo(true);
		    		telefone.setTipotelefone(tipostelefoneint.recuperarUnico(telefone.getTipotelefone().getIdtipotelefone()));
		    		telsexistentes.add(telefone);
		    		pessoa.setPessoatelefones(new HashSet<Telefones>(telsexistentes));
    			}
    			else{
    				indexmb.setPanelexibeerro(true);
    	    		indexmb.setRenderizalistadeerros(true);
    	    		indexmb.setMsgspanel(msgserro);
    			}
    		}
    		
    		telefone = new Telefones();
    	}
    	
    	if(existe){
    		indexmb.setPanelexibealerta(true);
			indexmb.setMsgpanel("Telefone j� existente.");
    	}
    	
    }

	public Pessoas getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoas pessoa) {
		this.pessoa = pessoa;
	}

//	public boolean isFlagloginrede() {
//		return flagloginrede;
//	}
//
//	public void setFlagloginrede(boolean flagloginrede) {
//		this.flagloginrede = flagloginrede;
//	}	

	public Emails getEmail() {
		return email;
	}

	public void setEmail(Emails email) {
		this.email = email;
	}

	public boolean isEditemail() {
		return editemail;
	}

	public void setEditemail(boolean editemail) {
		this.editemail = editemail;
	}

	public Emails getEmailupdate() {
		return emailupdate;
	}

	public void setEmailupdate(Emails emailupdate) {
		this.emailupdate = emailupdate;
	}

	public Telefones getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefones telefone) {
		this.telefone = telefone;
	}

	public Telefones getTelefoneupdate() {
		return telefoneupdate;
	}

	public void setTelefoneupdate(Telefones telefoneupdate) {
		this.telefoneupdate = telefoneupdate;
	}

	public boolean isEdittelefone() {
		return edittelefone;
	}

	public void setEdittelefone(boolean edittelefone) {
		this.edittelefone = edittelefone;
	}

	public OptionTreeNode getAreanos() {
		initRichFacesTree();
		return areanos;
	}

	public void setAreanos(OptionTreeNode areanos) {
		this.areanos = areanos;
	}


	public ValidadorEmail getValidadoremail() {
		return validadoremail;
	}


	public void setValidadoremail(ValidadorEmail validadoremail) {
		this.validadoremail = validadoremail;
	}


	public ValidadorCPF getValidadorcpf() {
		return validadorcpf;
	}


	public void setValidadorcpf(ValidadorCPF validadorcpf) {
		this.validadorcpf = validadorcpf;
	}


	public ValidadorCamposInvalidos getValidadorcamposinvalidos() {
		return validadorcamposinvalidos;
	}


	public void setValidadorcamposinvalidos(ValidadorCamposInvalidos validadorcamposinvalidos) {
		this.validadorcamposinvalidos = validadorcamposinvalidos;
	}


	public List<String> getMsgserro() {
		return msgserro;
	}


	public void setMsgserro(List<String> msgserro) {
		this.msgserro = msgserro;
	}
	
}
