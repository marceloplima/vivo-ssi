package com.ssi.kernel.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/** 
 * Classe de controle das Atividades relacionadas as �reas existentes
 * 
 * @see Areas atividadesareas
 */

@Entity
@Cacheable
@Table(name="Atividades")
public class Atividades implements java.io.Serializable{

	private static final long serialVersionUID = 654326624378892218L;
	
	public static final long ID_ADJUDICACAO = 10L;
	public static final long ID_APRESENTACAO_EM_MESA = 8L;
	public static final long ID_APROVACAO_RC = 7L;
	public static final long ID_EMISSAO_LAUDO_TECNICO = 5L;
	public static final long ID_EMISSAO_SSI = 1L;
	public static final long ID_ENVIO_RFP_AO_MERCADO = 3L;
	public static final long ID_LEILAO_FATOR_VARIAVEL = 9L;
	public static final long ID_RECEBIMENTO_PROPOSTA = 4L;
	public static final long ID_VALIDACAO_LAUDO_TECNICO = 6L;
	public static final long ID_VALIDACAO_SSI = 2L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idatividade")
	private Long idatividade;
	
	@Column(name="cnmatividade")
	private String cnmatividade;
	
	@Column(name="datacadastro", insertable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar datacadastro;
	
	@ManyToMany
	@JoinTable(name="Atividades_Areas",
			   joinColumns={@JoinColumn(name="idatividade", referencedColumnName="idatividade")},
			   inverseJoinColumns={@JoinColumn(name="idarea", referencedColumnName="idarea")})
	private Set<Areas> atividadesareas;
	
	@Column(name="flagativo")
	private boolean flagativo;
	
	@Column(name="flagtemrota")
	private boolean flagtemrota;
	
	@Transient
	private boolean checked;
	
	@ManyToMany
	@JoinTable(name="Copiados_Atividades",
			   joinColumns={@JoinColumn(name="idatividade", referencedColumnName="idatividade")},
			   inverseJoinColumns={@JoinColumn(name="idpessoa", referencedColumnName="idpessoa")})
	private Set<Pessoas> copiadosatividades;
	
	public Atividades() {
	}

	public Atividades(Long idatividade) {
		this.idatividade = idatividade;
	}
	
	public Long getIdatividade() {
		return idatividade;
	}
	public void setIdatividade(Long idatividade) {
		this.idatividade = idatividade;
	}
	
	public String getCnmatividade() {
		return cnmatividade;
	}
	public void setCnmatividade(String cnmatividade) {
		this.cnmatividade = cnmatividade;
	}
	public Calendar getDatacadastro() {
		return datacadastro;
	}
	public void setDatacadastro(Calendar datacadastro) {
		this.datacadastro = datacadastro;
	}
	public List<Areas> getAtividadesareas() {
		if(atividadesareas!=null)
			return new ArrayList<Areas>(atividadesareas);
		else
			return new ArrayList<Areas>();
	}
	public void setAtividadesareas(Set<Areas> areasaalt) {
		this.atividadesareas = areasaalt;
	}
	public boolean isFlagativo() {
		return flagativo;
	}
	public void setFlagativo(boolean flagativo) {
		this.flagativo = flagativo;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	public boolean isFlagtemrota() {
		return flagtemrota;
	}
	public void setFlagtemrota(boolean flagtemrota) {
		this.flagtemrota = flagtemrota;
	}
	
	@Override
	public int hashCode() {
		return this.idatividade != null ? 
		this.getClass().hashCode() + this.idatividade.hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		Atividades objint = (Atividades)obj;
		
		if(this.getIdatividade() != null && objint.getIdatividade() != null){
			if(this.getIdatividade().equals(objint.getIdatividade())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

	public List<Pessoas> getCopiadosatividades() {
		if(copiadosatividades!=null)
			return new ArrayList<Pessoas>(copiadosatividades);
		else
			return new ArrayList<Pessoas>();
	}

	public void setCopiadosatividades(Set<Pessoas> copiadosatividades) {
		this.copiadosatividades = copiadosatividades;
	}
	
}
