package com.ssi.kernel.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.ssi.kernel.compras.domain.Agendamentos;
import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.EventosAnaliseGerenteCompra;
import com.ssi.kernel.compras.domain.EventosIndicaAnalistaContrato;
import com.ssi.kernel.compras.domain.EventosIndicaNovoCompradores;
import com.ssi.kernel.compras.domain.RespostasQuestionamentos;
import com.ssi.kernel.redes.domain.DemandasRedes;


/** 
 * Classe que representa as pessoas que usam os sistemas SSI
 * 
 * @see Usuarios usuarios
 * @see Telefones pessoastelefones
 * @see Emails pessoasemails
 * @see Areas pessoasareas
 */

@Entity
@Cacheable
@Table(name="Pessoas")
public class Pessoas implements java.io.Serializable{

	private static final long serialVersionUID = 6732523674469817134L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idpessoa")
	private Long idpessoa;
	
	@OneToOne(mappedBy="pessoa", cascade=CascadeType.ALL)
	private Usuarios usuario;
//	
//	@Column(name="idgaus")
//	private Integer idgaus;
	
	@Column(name="cnmnome")
	private String cnmnome;
	
	@Column(name="flaggestor", columnDefinition="default false")
	private Boolean flaggestor;
	
	@Column(name="datacadastro", insertable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar datacadastro;
	
	@Version
	@Transient
	private Long version;
	
	@OneToMany(mappedBy="telefonespessoa", cascade=CascadeType.ALL)
	private Set<Telefones>pessoatelefones;
	
	@OneToMany(mappedBy="emailspessoa", cascade=CascadeType.ALL)
	private Set<Emails>pessoaemails;
	
	@ManyToMany(cascade=CascadeType.PERSIST)
	@JoinTable(name="Pessoas_Areas",
			   joinColumns={@JoinColumn(name="idpessoa", referencedColumnName="idpessoa")},
			   inverseJoinColumns={@JoinColumn(name="idarea", referencedColumnName="idarea")})
	private Set<Areas>pessoasareas;
	
	@ManyToMany(cascade=CascadeType.PERSIST)
	@JoinTable(name="Pessoas_GruposModulos",
			   joinColumns={@JoinColumn(name="idpessoa", referencedColumnName="idpessoa")},
			   inverseJoinColumns={@JoinColumn(name="idgrupomodulo", referencedColumnName="idgrupomodulo")})
	private Set<GruposModulos>pessoasgruposmodulos;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="Funcionalidades_Pessoas",
			   joinColumns={@JoinColumn(name="idpessoa", referencedColumnName="idpessoa")},
			   inverseJoinColumns={@JoinColumn(name="idfuncionalidade", referencedColumnName="idfuncionalidade")})
	private Set<Funcionalidades> pessoasfuncionalidades;
	
	@OneToMany(mappedBy = "analistaDeContratoResponsavel", cascade = CascadeType.ALL)
	private Set<Demandas> demandasAnalistaDeContratoresponsavel;
	
	@OneToMany(mappedBy = "analistaDeAquisicaoResponsavel", cascade = CascadeType.ALL)
	private Set<Demandas> demandasAnalistaDeAquisicaoResponsavel;

	@OneToMany(mappedBy = "comprador", cascade = CascadeType.ALL)
	private Set<EventosAnaliseGerenteCompra> eventosanalisegerentecompra;
		
	@OneToMany(mappedBy = "novocomprador", cascade = CascadeType.ALL)
	private Set<EventosIndicaNovoCompradores> eventosindicanovocomprador;
	
	@OneToMany(mappedBy = "pessoaindicada", cascade = CascadeType.ALL)
	private Set<EventosIndicaAnalistaContrato> eventosindicaanalistacontrato ;
	
	@OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
	private Set<Agendamentos> agendamentos ;
	
	@OneToMany(mappedBy = "autorresposta", cascade = CascadeType.ALL)
	private Set<RespostasQuestionamentos> respostasquestinamentos ;
	
	@Column(name="cdscpf")
	private String cdscpf;
	
	@Transient
	private boolean checked;
	
	@Column(name="flagativo", columnDefinition="default true")
	private boolean flagativo;
	
	@Column(name="cnmlocalizacao")
	private String cnmlocalizacao;
	
	@ManyToMany(mappedBy="copiados", cascade = CascadeType.REFRESH)
	private List<DemandasRedes> demandacopiados;
	
	@ManyToMany(mappedBy="copiadosatividades", cascade=CascadeType.ALL)
	private List<Atividades> pessoasatividades;
	
	@ManyToOne
	@JoinColumn(name="idcargo", referencedColumnName="idcargo")
	private Cargos cargo;
	
	@ManyToOne
	@JoinColumn(name="idarea", referencedColumnName="idarea")
	private Areas area;
	
	public Pessoas getByid(Long id){
		Pessoas obj = new Pessoas();
		obj.setIdpessoa(id);
		return obj;
	}
	
	public Pessoas(){
		if(pessoaemails == null)
			pessoaemails = new HashSet<Emails>();
		
		if(pessoatelefones == null)
			pessoatelefones = new HashSet<Telefones>();
		
		if(usuario == null){
			usuario = new Usuarios();
		}
	}
	
	public void remapearItens(Pessoas pessoa){
		if(!this.getPessoaemails().isEmpty()){
			Iterator<Emails>itmail = this.getPessoaemails().iterator();
		
			while(itmail.hasNext()){
				Emails em = itmail.next();
				em.setEmailspessoa(pessoa);
			}
		}
		
		
		if(!this.getPessoatelefones().isEmpty()){
			Iterator<Telefones>ittel = this.getPessoatelefones().iterator();
			
			while(ittel.hasNext()){
				Telefones tel = ittel.next();
				tel.setTelefonespessoa(pessoa);
			}
		}
		
		if(!this.getPessoasareas().isEmpty()){
			Iterator<Areas>itarea = this.getPessoasareas().iterator();
			while(itarea.hasNext()){
				Areas area = itarea.next();
				List<Pessoas>lp = new ArrayList<Pessoas>();
				lp.add(pessoa);
				area.setAreaspessoas(lp);
			}
		}
		
		if(this.getPessoasgruposmodulos()!=null && this.getPessoasgruposmodulos().isEmpty()){
			Iterator<GruposModulos>itgm = this.getPessoasgruposmodulos().iterator();
			while(itgm.hasNext()){
				GruposModulos gm = itgm.next();
				List<Pessoas>lp = new ArrayList<Pessoas>();
				lp.add(pessoa);
				gm.setGruposmodulospessoas(lp);
			}
		}
		
		if(this.getPessoasfuncionalidades()!=null && !this.getPessoasfuncionalidades().isEmpty()){
			Iterator<Funcionalidades>it = this.getPessoasfuncionalidades().iterator();
			while(it.hasNext()){
				Funcionalidades obj = it.next();
				List<Pessoas>lista = new ArrayList<Pessoas>();
				lista.add(pessoa);
				obj.setPessoas(new HashSet<Pessoas>(lista));
			}
		}
	}
	
	
	
	public List<DemandasRedes> getDemandacopiados() {
		return demandacopiados;
	}

	public void setDemandacopiados(List<DemandasRedes> demandacopiados) {
		this.demandacopiados = demandacopiados;
	}

	public Long getIdpessoa() {
		return idpessoa;
	}
	public void setIdpessoa(Long idpessoa) {
		this.idpessoa = idpessoa;
	}
	public Usuarios getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}
//	public Integer getIdgaus() {
//		return idgaus;
//	}
//	public void setIdgaus(Integer idgaus) {
//		this.idgaus = idgaus;
//	}
	public String getCnmnome() {
		return cnmnome;
	}
	public void setCnmnome(String cnmnome) {
		this.cnmnome = cnmnome.toUpperCase();
	}
	public Calendar getDatacadastro() {
		return datacadastro;
	}
	public void setDatacadastro(Calendar datacadastro) {
		this.datacadastro = datacadastro;
	}
	
	public Set<EventosIndicaAnalistaContrato> getEventosindicaanalistacontrato() {
		return eventosindicaanalistacontrato;
	}

	public void setEventosindicaanalistacontrato(
			Set<EventosIndicaAnalistaContrato> eventosindicaanalistacontrato) {
		this.eventosindicaanalistacontrato = eventosindicaanalistacontrato;
	}		
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public List<Telefones> getPessoatelefones() {
		if(pessoatelefones!=null && pessoatelefones.size()>=1)
			return new ArrayList<Telefones>(pessoatelefones);
		else
			return new ArrayList<Telefones>();
		
	}
	public void setPessoatelefones(Set<Telefones> pessoatelefones) {
		this.pessoatelefones = pessoatelefones;
	}
	public List<Emails> getPessoaemails() {
		if(pessoaemails != null && pessoaemails.size() >= 1)
			return new ArrayList<Emails>(pessoaemails);
		else
			return new ArrayList<Emails>();
	}
	public void setPessoaemails(Set<Emails> pessoaemails) {
		this.pessoaemails = pessoaemails;
	}
	
	public List<Areas> getPessoasareas() {
		if(pessoasareas!=null)
			return new ArrayList<Areas>(pessoasareas);
		else
			return new ArrayList<Areas>();
	}
	public void setPessoasareas(Set<Areas> pessoasareas) {
		this.pessoasareas = pessoasareas;
	}
	
	public Boolean getFlaggestor() {
		return flaggestor;
	}
	public void setFlaggestor(Boolean flaggestor) {
		this.flaggestor = flaggestor;
	}
	public String getCdscpf() {
		return cdscpf;
	}
	public void setCdscpf(String cdscpf) {
		this.cdscpf = cdscpf;
	}
	
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	public boolean isFlagativo() {
		return flagativo;
	}

	public void setFlagativo(boolean flagativo) {
		this.flagativo = flagativo;
	}

	public Set<GruposModulos> getPessoasgruposmodulos() {
		return pessoasgruposmodulos;
	}

	public void setPessoasgruposmodulos(Set<GruposModulos> pessoasgruposmodulos) {
		this.pessoasgruposmodulos = pessoasgruposmodulos;
	}

	public List<Funcionalidades> getPessoasfuncionalidades() {
		if(pessoasfuncionalidades!=null){
			return new ArrayList<Funcionalidades>(pessoasfuncionalidades);
		}
		else{
			return new ArrayList<Funcionalidades>();
		}
	}

	public void setPessoasfuncionalidades(Set<Funcionalidades> pessoasfuncionalidades) {
		this.pessoasfuncionalidades = pessoasfuncionalidades;
	}
			
	public Set<Demandas> getDemandasAnalistaDeContratoresponsavel() {
		return demandasAnalistaDeContratoresponsavel;
	}

	public void setDemandasAnalistaDeContratoresponsavel(
			Set<Demandas> demandasAnalistaDeContratoresponsavel) {
		this.demandasAnalistaDeContratoresponsavel = demandasAnalistaDeContratoresponsavel;
	}

	public Set<Demandas> getDemandasAnalistaDeAquisicaoResponsavel() {
		return demandasAnalistaDeAquisicaoResponsavel;
	}

	public void setDemandasAnalistaDeAquisicaoResponsavel(
			Set<Demandas> demandasAnalistaDeAquisicaoResponsavel) {
		this.demandasAnalistaDeAquisicaoResponsavel = demandasAnalistaDeAquisicaoResponsavel;
	}
		
	public Set<EventosAnaliseGerenteCompra> getEventosanalisegerentecompra() {
		return eventosanalisegerentecompra;
	}

	public void setEventosanalisegerentecompra(
			Set<EventosAnaliseGerenteCompra> eventosanalisegerentecompra) {
		this.eventosanalisegerentecompra = eventosanalisegerentecompra;
	}
	
	

	public Set<EventosIndicaNovoCompradores> getEventosindicanovocomprador() {
		return eventosindicanovocomprador;
	}

	public void setEventosindicanovocomprador(
			Set<EventosIndicaNovoCompradores> eventosindicanovocomprador) {
		this.eventosindicanovocomprador = eventosindicanovocomprador;
	}
		
	public Set<Agendamentos> getAgendamentos() {
		return agendamentos;
	}

	public void setAgendamentos(Set<Agendamentos> agendamentos) {
		this.agendamentos = agendamentos;
	}

	@Override
	public int hashCode() {
		return this.idpessoa != null ? 
		this.getClass().hashCode() + this.idpessoa.hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		Pessoas objint = (Pessoas)obj;
		
		if(this.getIdpessoa() != null && objint.getIdpessoa() != null){
			if(this.getIdpessoa().equals(objint.getIdpessoa())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

	public String getCnmlocalizacao() {
		return cnmlocalizacao;
	}

	public void setCnmlocalizacao(String cnmlocalizacao) {
		this.cnmlocalizacao = cnmlocalizacao;
	}

	public Set<RespostasQuestionamentos> getRespostasquestinamentos() {
		return respostasquestinamentos;
	}

	public void setRespostasquestinamentos(
			Set<RespostasQuestionamentos> respostasquestinamentos) {
		this.respostasquestinamentos = respostasquestinamentos;
	}

	public List<Atividades> getPessoasatividades() {
		return pessoasatividades;
	}

	public void setPessoasatividades(List<Atividades> pessoasatividades) {
		this.pessoasatividades = pessoasatividades;
	}

	public Cargos getCargo() {
		return cargo;
	}

	public void setCargo(Cargos cargo) {
		this.cargo = cargo;
	}

	public Areas getArea() {
		return area;
	}

	public void setArea(Areas area) {
		this.area = area;
	}
	
	

}
