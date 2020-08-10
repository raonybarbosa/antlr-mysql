package br.com.antlr.mysql.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "boa_pratica")
public class BoaPratica {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "codigo_boa_pratica")
	private int codigoBoaPratica;
	@Column(name = "nome_boa_pratica")
	private String nomeBoaPratica;
	@Column(name = "codigo_comando_boa_pratica")
	private int codigoComandoBoaPratica;
	@Column(name = "indicador_comportamento")
	private int indicadorComportamento;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ManyToMany
	private List<ComandoEstrutura> comandoEstrutura;
}
