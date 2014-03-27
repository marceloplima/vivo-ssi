package com.ssi.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.admin.utils.RecuperadorInstanciasBean;
import com.ssi.admin.utils.ValidadorCamposInvalidos;
import com.ssi.kernel.controller.interfaces.AreasInt;
import com.ssi.kernel.controller.interfaces.FuncionalidadesInt;
import com.ssi.kernel.controller.interfaces.GruposModulosInt;
import com.ssi.kernel.controller.interfaces.PessoasInt;
import com.ssi.kernel.model.Areas;
import com.ssi.kernel.model.Emails;
import com.ssi.kernel.model.Funcionalidades;
import com.ssi.kernel.model.GruposModulos;
import com.ssi.kernel.model.Modulos;
import com.ssi.kernel.model.Pessoas;
import com.ssi.kernel.model.Telefones;
import com.ssi.kernel.model.Usuarios;
import com.ssi.kernel.utils.MD5Hashing;
import com.ssi.kernel.utils.ValidadorCPF;
import com.ssi.kernel.utils.ValidadorEmail;

@ManagedBean
@ViewScoped
public class BuscarPessoasMB implements java.io.Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -4590372263276855593L;

	@EJB 
	private PessoasInt pessoasint;
	
	@EJB 
	private AreasInt areasint;
	
	@EJB 
	private GruposModulosInt gruposmodulosint;
	
	@EJB 
	private FuncionalidadesInt funcionalidadesint;
	
	private Pessoas pessoa = new Pessoas();
	
	private Map<String,Object>filtros = new LinkedHashMap<String,Object>();
	
	// Filtros
	private String cnmnome;
	private String cdscpf;
	private String cnmlogin;
	private String flagativo;
	
	private boolean edit;
	private boolean exibelistagem;
	
	//Controlador de exibi��o do select de a��es (disabled/enabled)
	private boolean ocultaacoes;
	
	private String acao; // a��o selecionada no combo de A��es
	
	private List<Pessoas> pessoaschecked = new ArrayList<Pessoas>();
	
	// Controladores de exibi��o de pain�s modais
	private boolean exibepopupemails = false;
	private boolean exibepopuptels = false;
	private boolean exibepopupareas = false;
	private boolean modalconfirmaacaopessoas = false;
	private boolean exibepopupeditar = false;
	private boolean exibepopupgruposmodulos = false;
	private boolean exibepopupfunc = false;
	
	private List<Emails> emailspessoa = new ArrayList<Emails>();
	private List<Telefones> telefonespessoa = new ArrayList<Telefones>();
	private List<Areas> areaspessoa = new ArrayList<Areas>();
	private List<GruposModulos> gruposmodulospessoa = new ArrayList<GruposModulos>();
	
	private List<Areas> areas;
	private List<GruposModulos> gruposmodulos;
	private OptionTreeNode areanos = new OptionTreeNode();
	//private boolean chamouinittree = false;
	private boolean expandirnode = true;
	
	private OptionTreeNode nosfunc = new OptionTreeNode();
	private List<Funcionalidades> funcionalidades;
	private List<Funcionalidades> funcionalidadespessoas = new ArrayList<Funcionalidades>();
	//private boolean chamouinittree = false;
	private boolean exibetreefunc = false; // inicialmente a tree de Funcionalidades vir� vazia, ser� preenchida posteriormente quando a busca de modulos for efetuada
	private Modulos modulofunc; // Usado no popup de funcionalidades (para filtrar as funcionalidades de um m�dulo espec�fico)
	
	private List<GruposModulos> gruposmoduloschecked = new ArrayList<GruposModulos>();

	private IndexMB indexmb;
	private ValidadorEmail validadoremail;
	private ValidadorCPF validadorcpf;
	private ValidadorCamposInvalidos validadorcamposinvalidos;
	
	private List<String>msgserro;
	
	@PostConstruct
    public void init() {
		// A��es vem inicialmente, ocultas
		ocultaacoes = true;
		
		validadoremail = new ValidadorEmail();
		validadorcpf = new ValidadorCPF();
		validadorcamposinvalidos = new ValidadorCamposInvalidos();
		
		// Caso navegue para outra tela e retorne, a lista de pessoas checadas � zerada
		pessoaschecked = new ArrayList<Pessoas>();
		
		// Usado para logo ao abrir, aparecerem as Pessoas sem filtragem, sem precisar clicar em "buscar"
		if(this.filtros.isEmpty()){
			exibelistagem = true;
			flagativo = "true";
			buscar();
		}
		
		//destroiPessoasChecked();
    }
	
	// Faz um filtro no popup de funcionalidades que � aberto ao informar a A��o correspondente
	public void filtrarmodulofunc(){
		exibetreefunc = true; // se filtrou, exibe o tree
	}
	
//	public void destroiPessoasChecked(){
//		// Recupera a Sess�o para eliminar o list de pessoas checadas caso exista
//		// Esse artif�cio � necess�rio para lidar com o mecanismo de pagina��o verdadeira
//		Map<String,Object> sessao = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
//		sessao.remove("pessoaschecked");
//	}
	
	// Configura os grupos (modulos) da pessoa selecionada
	public void configurarGruposModulos(){
		Iterator<GruposModulos>it = gruposmodulos.iterator();
		Set<GruposModulos> gmpalt = new HashSet<GruposModulos>();
			
		// Itera sobre a lista submetida. Verifica qual esta checado e passa para o Set que ser� persistido na base
		while(it.hasNext()){
			GruposModulos obj = it.next();
			if(obj.isChecked()){
				gmpalt.add(obj);
			}
		}
		
		pessoa = pessoasint.recuperarUnico(pessoa.getIdpessoa());
		
		pessoa.setPessoaemails(new HashSet<Emails>(pessoasint.retornarEmailsPessoa(pessoa)));
		pessoa.setPessoatelefones(new HashSet<Telefones>(pessoasint.retornarTelefonesPessoa(pessoa)));
		pessoa.setPessoasareas(new HashSet<Areas>(pessoasint.retornarAreasPessoa(pessoa)));
		pessoa.setPessoasfuncionalidades(new HashSet<Funcionalidades>(pessoasint.retornarFuncionalidadesPessoa(pessoa)));
		pessoa.setPessoasgruposmodulos(gmpalt);
		pessoasint.alterar(pessoa);
	}
	
	// Ao clicar em "Ativar/Desativar Selecionados" na view, seta o atributo "flagativo" pra true ou false em todos os marcados
	// E depois, destroi a sess�o e as marca��es
	public void ativarDesativarSelecionados(){
		Iterator<Pessoas> itpc = pessoaschecked.iterator();
		while(itpc.hasNext()){
			Pessoas pc = itpc.next();
			if(pc.isFlagativo())
				pc.setFlagativo(false);
			else
				pc.setFlagativo(true);
			
			pc.setPessoaemails(new HashSet<Emails>(pessoasint.retornarEmailsPessoa(pc)));
			pc.setPessoatelefones(new HashSet<Telefones>(pessoasint.retornarTelefonesPessoa(pc)));
			pc.setPessoasareas(new HashSet<Areas>(pessoasint.retornarAreasPessoa(pc)));
			pc.setPessoasgruposmodulos(new HashSet<GruposModulos>(pessoasint.retornarGruposModulosPessoa(pc)));
			pc.setPessoasfuncionalidades(new HashSet<Funcionalidades>(pessoasint.retornarFuncionalidadesPessoa(pc)));
			
			pessoasint.alterar(pc);
		}
		pessoaschecked = new ArrayList<Pessoas>();
		//destroiPessoasChecked();
		modalconfirmaacaopessoas = false;
	}
	
	public void buscar(){
		Pessoas pessoafiltro = new Pessoas();
		if(cnmnome!=null)
			pessoafiltro.setCnmnome(cnmnome);
		if(cdscpf!=null)
			pessoafiltro.setCdscpf(cdscpf);
		if(cnmlogin!=null)
			pessoafiltro.getUsuario().setCnmlogin(cnmlogin);
		if(flagativo!=null && flagativo.length()>=1)
			pessoafiltro.setFlagativo(new Boolean(flagativo));
		
		Map<String,Object> filtros = new LinkedHashMap<String,Object>();
		filtros.put("pessoa", pessoafiltro);
		this.filtros = filtros;
		exibelistagem = true;
		pessoaschecked = new ArrayList<Pessoas>();
	}
	
	public void selecionarCheckboxPessoa(Pessoas pessoa){
		if(pessoaschecked.contains(pessoa)){
			pessoa.setChecked(false);
			pessoaschecked.remove(pessoa);
		}else{
			pessoa.setChecked(true);
			pessoaschecked.add(pessoa);
		}
		
		// Armazena os checados em sess�o para que no caso de paginar usando a pagina��o sob demanda, n�o perca as sele��es feitas
		//Map<String,Object> sessao = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		//sessao.put("pessoaschecked", pessoaschecked);
		
		// Verifica se apenas 1 checkbox est� checado. Caso positivo, habilita A��es
		ocultaacoes = true;
		if(pessoaschecked.size() == 1){
			ocultaacoes = false;
		}
	}
	
	public void fecharPopups(){
		exibepopupemails = false;
		exibepopuptels = false;
		exibepopupareas = false;
		exibepopupeditar = false;
		modalconfirmaacaopessoas = false;
		exibepopupgruposmodulos = false;
		exibepopupfunc = false;
	}

	// Quando o usu�rio seleciona uma a��o no select da view, esse m�todo � acionado
	public void efetuaracao(){
		
		// Mantem todos os popups com rendered=false antes do processameto das a��es para mante-los fechados
		fecharPopups();
		
		// S� permite usar esse recurso caso tenha sido selecionado apenas um registro
		if(pessoaschecked.size() == 1){
			
			// Seta o objeto referenciando Pessoas com o da List vinda do checkbox da busca de pessoas
			pessoa = pessoaschecked.get(0);
			
			if (pessoa.getUsuario() == null){
				pessoa.setUsuario(new Usuarios());
			}
			
			
			if(acao.equals("1")){ // A��o: Editar
				exibepopupeditar = true;
			}
			if(acao.equals("2")){ // A��o: Emails
				exibepopupemails = true;
			}
			if(acao.equals("3")){ // A��o: Telefones
				exibepopuptels = true;
			}
			if(acao.equals("4")){ // A��o: Areas
				exibepopupareas = true;
			}
			if(acao.equals("5")){ // A��o: Grupos M�dulos
				exibepopupgruposmodulos = true;
			}
			if(acao.equals("6")){ // A��o: Funcionalidades
				exibepopupfunc = true;
			}
		}
		
		acao = "0"; // reseta o combobox de a��es
	}
	
	// BuscarPessoas recebe o primeiro request pra incluir um novo email, no caso de uma inclus�o efetuada
	// no popup de emails da tela de busca de pessoas
	// Por�m esse bean recupera a instancia do bean especifico de emails, para tratar a inclus�o.
	public void incluiremail(){
		EmailsMB mb = RecuperadorInstanciasBean.recuperarInstanciaEmailsBean();
		mb.incluir(pessoa);
	}
	
	// BuscarPessoas recebe o primeiro request pra incluir um novo email, no caso de uma inclus�o efetuada
	// no popup de emails da tela de busca de pessoas
	// Por�m esse bean recupera a instancia do bean especifico de emails, para tratar a inclus�o.
	public void incluirtel(){
		TelefonesMB mb = RecuperadorInstanciasBean.recuperarInstanciaTelsBean();
		mb.incluir(pessoa);
	}
	
	// BuscarPessoas recebe o primeiro request pra alterar um email, no caso de uma altera��o efetuada
	// no popup de emails da tela de busca de pessoas
	// Por�m esse bean recupera a instancia do bean especifico de emails, para tratar a altera��o.
	public void alteraremail(){
		EmailsMB mb = RecuperadorInstanciasBean.recuperarInstanciaEmailsBean();
		mb.alterar(pessoa);
	}
	
	// BuscarPessoas recebe o primeiro request pra incluir um novo email, no caso de uma inclus�o efetuada
	// no popup de emails da tela de busca de pessoas
	// Por�m esse bean recupera a instancia do bean especifico de emails, para tratar a inclus�o.
	public void alterartel(){
		TelefonesMB mb = RecuperadorInstanciasBean.recuperarInstanciaTelsBean();
		mb.alterar(pessoa);
	}
	
	// Etapa 1 para montar a �rvore de �reas (unidades organizacionais)
	public void initRichFacesTree() {
		areanos = new OptionTreeNode();
		areas = areasint.recuperar();
		getAreaspessoa();
		for (Areas obj:areas){
			if(obj.getAreapai()==null){
				OptionTreeNode raiz = new OptionTreeNode();
					
				// Se o obj contiver na listagem de areas da pessoa, marca o checkbox
				if(areaspessoa != null && areaspessoa.contains(obj)){
					obj.setChecked(true);
				}
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
			
			// Se o obj contiver na listagem de areas da pessoa, marca o checkbox
			if(areaspessoa != null && areaspessoa.contains(filho)){
				filho.setChecked(true);
			}
			child.setArea(filho);
			nopai.addChild(filho.getIdarea(), child);
		        		 
			if(filho.getAreasfilho().size()>=1)
				recuperarFilhos(filho, child);
		}
	}
	
	// Etapa 1 para montar a �rvore de Funcionalidades (�tens do menu horizontal)
	public void initRichFacesTreeFunc() {
			nosfunc = new OptionTreeNode();
			if(modulofunc != null){
				setFuncionalidades(funcionalidadesint.recuperarPorModulo(modulofunc.getIdmodulo()));
				getFuncionalidadespessoas();
				for (Funcionalidades obj:funcionalidades){
					if(obj.getFuncionalidadepai()==null){
						OptionTreeNode raiz = new OptionTreeNode();
										
						// Se o obj contiver na listagem, marca o checkbox
						if(funcionalidadespessoas != null && funcionalidadespessoas.contains(obj)){
							obj.setChecked(true);
						}
						raiz.setFuncionalidade(obj);
						nosfunc.addChild(obj.getIdfuncionalidade(), raiz);
								            		
						if(obj.getFuncionalidadesfilhas().size()>=1){
							recuperarFilhosFunc(obj,raiz);
						}
					}
				}
			}
	}
	
	// Etapa 2 para montar a �rvore de �reas (�tens do menu horizontal)
		public void recuperarFilhosFunc(Funcionalidades pai,OptionTreeNode nopai){
			List<Funcionalidades> filhos = pai.getFuncionalidadesfilhas();
			for (Funcionalidades filho:filhos){
				OptionTreeNode child = new OptionTreeNode();
						
				// Se o obj contiver na listagem de areas da pessoa, marca o checkbox
				if(funcionalidadespessoas != null && funcionalidadespessoas.contains(filho)){
					filho.setChecked(true);
				}
				child.setFuncionalidade((filho));
				nopai.addChild(filho.getIdfuncionalidade(), child);
					        		 
				if(filho.getFuncionalidadesfilhas().size()>=1)
					recuperarFilhosFunc(filho, child);
			}
		}
		
	public void inicializarValidadores(){
		setIndexmb(RecuperadorInstanciasBean.recuperarInstanciaIndexBean());
	    setMsgserro(new ArrayList<String>());
	}
	
	// Alterar a Pessoa (dados iniciais)
	public void alterarpessoa(){
		pessoa.getUsuario().setCnmsenha(MD5Hashing.criptografar(pessoa.getUsuario().getCnmsenha()));
		pessoa.getUsuario().setFlagativo(true);
		pessoa.getUsuario().setPessoa(pessoa);
		
		pessoa.setPessoaemails(new HashSet<Emails>(pessoasint.retornarEmailsPessoa(pessoa)));
		pessoa.setPessoatelefones(new HashSet<Telefones>(pessoasint.retornarTelefonesPessoa(pessoa)));
		pessoa.setPessoasareas(new HashSet<Areas>(pessoasint.retornarAreasPessoa(pessoa)));
		pessoa.setPessoasgruposmodulos(new HashSet<GruposModulos>(pessoasint.retornarGruposModulosPessoa(pessoa)));
		pessoa.setPessoasfuncionalidades(new HashSet<Funcionalidades>(pessoasint.retornarFuncionalidadesPessoa(pessoa)));
		
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
	    	else
	    		camposvalidar.put("Grupo", "");
	    	
	    	camposvalidar.put("Login", pessoa.getUsuario().getCnmlogin());
	    	
	    	
    	}
    	else{
    		camposvalidar.put("Login", "");
    		camposvalidar.put("Tipo do Usuario", "");
    	}
    	
    	if(camposvalidar.size()>=1 && camposvalidar !=null)
    		setMsgserro(validadorcamposinvalidos.validarCamposVazios(camposvalidar));
    	
		
    	if(msgserro.size()==0){
	    	if(validadorcpf.validar(pessoa.getCdscpf())){
	    	
			    //if(!pessoasint.verificaExistente(pessoa)){
			    	pessoasint.alterar(pessoa);
			    	//pessoasint.incluirusuario(pessoa.getUsuario());
//			    }
//			    else{
//			    	indexmb.setPanelexibealerta(true);
//			    	indexmb.setMsgpanel("Pessoa j� existe no sistema");
//			    	
//			    	pessoa = new Pessoas();
//			    }
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
		exibepopupeditar = false;
		pessoa = new Pessoas();
	}
	
	// Altera a �rea
	public void alterararea(){
		Iterator<Areas>it = areas.iterator();
		Set<Areas> areaspessoaalt = new HashSet<Areas>();
		
		// Itera sobre a lista de �reas submetida. Verifica qual esta checada e passa para o Set que ser� persistido na base
		while(it.hasNext()){
			Areas obj = it.next();
			if(obj.isChecked()){
				areaspessoaalt.add(obj);
			}
		}
		
		pessoa = pessoasint.recuperarUnico(pessoa.getIdpessoa());
		
		pessoa.setPessoaemails(new HashSet<Emails>(pessoasint.retornarEmailsPessoa(pessoa)));
		pessoa.setPessoatelefones(new HashSet<Telefones>(pessoasint.retornarTelefonesPessoa(pessoa)));
		pessoa.setPessoasgruposmodulos(new HashSet<GruposModulos>(pessoasint.retornarGruposModulosPessoa(pessoa)));
		pessoa.setPessoasfuncionalidades(new HashSet<Funcionalidades>(pessoasint.retornarFuncionalidadesPessoa(pessoa)));
		
		pessoa.setPessoasareas(areaspessoaalt);
		pessoasint.alterar(pessoa);
	}
	
	public OptionTreeNode getAreanos() {
		if(exibepopupareas){ // se n�o tiver esse IF ele vai tentar carregar antes do tempo
			initRichFacesTree();
		}
		return areanos;
	}
	public void setAreanos(OptionTreeNode areanos) {
		this.areanos = areanos;
	}
	
	// Altera as funcionalidades da pessoa
	public void alterarfunc(){
		Iterator<Funcionalidades>it = funcionalidades.iterator();
		Set<Funcionalidades> funcalt = new HashSet<Funcionalidades>();
					
		// Itera sobre a lista submetida. Verifica qual esta checada e passa para o Set que ser� persistido na base
		while(it.hasNext()){
			Funcionalidades obj = it.next();
			if(obj.isChecked()){
				funcalt.add(obj);
			}
		}
			
			
		// Recupera funcionalidades setadas de outras pessoa, pois ao submeter esse, ir� apagar os demais caso n�o recuperemos
		List<Funcionalidades> funcoutras = funcionalidadesint.recuperarDiferentesPessoas(modulofunc,pessoa);
		Iterator<Funcionalidades>ito = funcoutras.iterator();
		while(ito.hasNext()){
			Funcionalidades obj = ito.next();
			funcalt.add(obj);
		}
		
		pessoa = pessoasint.recuperarUnico(pessoa.getIdpessoa());
		
		pessoa.setPessoasfuncionalidades(funcalt);
		
		pessoa.setPessoaemails(new HashSet<Emails>(pessoasint.retornarEmailsPessoa(pessoa)));
		pessoa.setPessoatelefones(new HashSet<Telefones>(pessoasint.retornarTelefonesPessoa(pessoa)));
		pessoa.setPessoasareas(new HashSet<Areas>(pessoasint.retornarAreasPessoa(pessoa)));
		pessoa.setPessoasgruposmodulos(new HashSet<GruposModulos>(pessoasint.retornarGruposModulosPessoa(pessoa)));
		
		pessoasint.alterar(pessoa);
		//exibepopupfunc    = false;
	}
	
//	public boolean isChamouinittree() {
//		return chamouinittree;
//	}
//	public void setChamouinittree(boolean chamouinittree) {
//		this.chamouinittree = chamouinittree;
//	}
	
	public void delete(Pessoas pessoa){
    	pessoasint.remover(pessoa);
    }
	
    public void edit(Pessoas pessoa) {
    	setEdit(true);
        this.pessoa = pessoa;
    }
    
    public void save() {
    	pessoasint.alterar(pessoa);
    	pessoa = new Pessoas();
        edit = false;
    }
    
	public PessoasDataModel getDataModel() {
        return new PessoasDataModel(pessoasint,filtros);
    }

	public Pessoas getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoas pessoa) {
		this.pessoa = pessoa;
	}

	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	public Map<String,Object> getFiltros() {
		return filtros;
	}

	public void setFiltros(Map<String,Object> filtros) {
		this.filtros = filtros;
	}
	
	public String getCnmnome() {
		return cnmnome;
	}

	public void setCnmnome(String cnmnome) {
		this.cnmnome = cnmnome;
	}
	
	public String getCdscpf() {
		return cdscpf;
	}

	public void setCdscpf(String cdscpf) {
		this.cdscpf = cdscpf;
	}
	
	public String getCnmlogin() {
		return cnmlogin;
	}

	public void setCnmlogin(String cnmlogin) {
		this.cnmlogin = cnmlogin;
	}
	
	public String getFlagativo() {
		return flagativo;
	}

	public void setFlagativo(String flagativo) {
		this.flagativo = flagativo;
	}

	public boolean isExibelistagem() {
		return exibelistagem;
	}

	public void setExibelistagem(boolean exibelistagem) {
		this.exibelistagem = exibelistagem;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public List<Pessoas> getPessoaschecked() {
		return pessoaschecked;
	}

	public void setPessoaschecked(List<Pessoas> pessoaschecked) {
		this.pessoaschecked = pessoaschecked;
	}

	public boolean isOcultaacoes() {
		return ocultaacoes;
	}

	public void setOcultaacoes(boolean ocultaacoes) {
		this.ocultaacoes = ocultaacoes;
	}

	public boolean isExibepopupemails() {
		return exibepopupemails;
	}

	public void setExibepopupemails(boolean exibepopupemails) {
		this.exibepopupemails = exibepopupemails;
	}

	public boolean isExibepopuptels() {
		return exibepopuptels;
	}

	public void setExibepopuptels(boolean exibepopuptels) {
		this.exibepopuptels = exibepopuptels;
	}
	
	public boolean isExibepopupareas() {
		return exibepopupareas;
	}

	public void setExibepopupareas(boolean exibepopupareas) {
		this.exibepopupareas = exibepopupareas;
	}

	public List<Emails> getEmailspessoa() {
		if(pessoa!=null && pessoa.getIdpessoa()!=null){
			emailspessoa = pessoasint.retornarEmailsPessoa(pessoa);
		}
		return emailspessoa;
	}

	public void setEmailspessoa(List<Emails> emailspessoa) {
		this.emailspessoa = emailspessoa;
	}

	public List<Telefones> getTelefonespessoa() {
		if(pessoa!=null && pessoa.getIdpessoa()!=null){
			telefonespessoa = pessoasint.retornarTelefonesPessoa(pessoa);
		}
		return telefonespessoa;
	}

	public void setTelefonespessoa(List<Telefones> telefonespessoa) {
		this.telefonespessoa = telefonespessoa;
	}

	public List<Areas> getAreaspessoa() {
		if(pessoa!=null && pessoa.getIdpessoa()!=null){
			areaspessoa = pessoasint.retornarAreasPessoa(pessoa);
		}
		return areaspessoa;
	}

	public void setAreaspessoa(List<Areas> areaspessoa) {
		this.areaspessoa = areaspessoa;
	}

	public boolean isExpandirnode() {
		return expandirnode;
	}

	public void setExpandirnode(boolean expandirnode) {
		this.expandirnode = expandirnode;
	}

	public boolean isModalconfirmaacaopessoas() {
		return modalconfirmaacaopessoas;
	}

	public void setModalconfirmaacaopessoas(
			boolean modalconfirmaacaopessoas) {
		this.modalconfirmaacaopessoas = modalconfirmaacaopessoas;
	}

	public boolean isExibepopupeditar() {
		return exibepopupeditar;
	}

	public void setExibepopupeditar(boolean exibepopupeditar) {
		this.exibepopupeditar = exibepopupeditar;
	}

	public boolean isExibepopupgruposmodulos() {
		return exibepopupgruposmodulos;
	}

	public void setExibepopupgruposmodulos(boolean exibepopupgruposmodulos) {
		this.exibepopupgruposmodulos = exibepopupgruposmodulos;
	}

	public List<GruposModulos> getGruposmoduloschecked() {
		return gruposmoduloschecked;
	}

	public void setGruposmoduloschecked(List<GruposModulos> gruposmoduloschecked) {
		this.gruposmoduloschecked = gruposmoduloschecked;
	}

	public List<GruposModulos> getGruposmodulospessoa() {
		if(pessoa!=null && pessoa.getIdpessoa()!=null){
			gruposmodulospessoa = pessoasint.retornarGruposModulosPessoa(pessoa);
		}
		return gruposmodulospessoa;
	}

	public void setGruposmodulospessoa(List<GruposModulos> gruposmodulospessoa) {
		this.gruposmodulospessoa = gruposmodulospessoa;
	}

	public List<GruposModulos> getGruposmodulos() {
		gruposmodulos = gruposmodulosint.recuperar();
		Iterator<GruposModulos> itgmp = gruposmodulos.iterator();
		while(itgmp.hasNext()){
			GruposModulos o = itgmp.next();
			if(getGruposmodulospessoa().contains(o)){
				o.setChecked(true);
			}
		}
		return gruposmodulos;
	}

	public void setGruposmodulos(List<GruposModulos> gruposmodulos) {
		this.gruposmodulos = gruposmodulos;
	}

	public boolean isExibepopupfunc() {
		return exibepopupfunc;
	}

	public void setExibepopupfunc(boolean exibepopupfunc) {
		this.exibepopupfunc = exibepopupfunc;
	}

	public OptionTreeNode getNosfunc() {
		if(exibepopupfunc){ // se n�o tiver esse IF ele vai tentar carregar antes do tempo
			initRichFacesTreeFunc();
		}
		return nosfunc;
	}

	public void setNosfunc(OptionTreeNode nosfunc) {
		this.nosfunc = nosfunc;
	}

	public List<Funcionalidades> getFuncionalidades() {
		return funcionalidades;
	}

	public void setFuncionalidades(List<Funcionalidades> funcionalidades) {
		this.funcionalidades = funcionalidades;
	}

	public List<Funcionalidades> getFuncionalidadespessoas() {
		if(pessoa!=null && pessoa.getIdpessoa()!=null){
			funcionalidadespessoas = funcionalidadesint.retornarFuncionalidadesPessoas(pessoa);
		}
		return funcionalidadespessoas;
	}

	public void setFuncionalidadespessoas(List<Funcionalidades> funcionalidadespessoas) {
		this.funcionalidadespessoas = funcionalidadespessoas;
	}

	public Modulos getModulofunc() {
		return modulofunc;
	}

	public void setModulofunc(Modulos modulofunc) {
		this.modulofunc = modulofunc;
	}

	public boolean isExibetreefunc() {
		return exibetreefunc;
	}

	public void setExibetreefunc(boolean exibetreefunc) {
		this.exibetreefunc = exibetreefunc;
	}

	public IndexMB getIndexmb() {
		return indexmb;
	}

	public void setIndexmb(IndexMB indexmb) {
		this.indexmb = indexmb;
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

	public List<String> getMsgserro() {
		return msgserro;
	}

	public void setMsgserro(List<String> msgserro) {
		this.msgserro = msgserro;
	}
}
