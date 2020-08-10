package br.com.antlr.mysql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.antlr.mysql.antlr.MySqlLexer;
import br.com.antlr.mysql.antlr.MySqlParser;
import br.com.antlr.mysql.dto.ArvoreSintaticaDto;
import br.com.antlr.mysql.dto.EntradaComandoDto;
import br.com.antlr.mysql.dto.RegraPraticaDto;
import br.com.antlr.mysql.dto.RegrasDto;

@Service
public class BoasPraticasService {
	private boolean ignoringWrappers = true;
	private List<ArvoreSintaticaDto> lista;
	private RegraPraticaDto regraPratica;

	public String preencheEntrada(EntradaComandoDto entrada) {
		lista = new ArrayList();
		if (StringUtils.trimAllWhitespace(entrada.getScript()).toUpperCase().contains("CREATEEXTERNALTABLE")
				|| StringUtils.trimAllWhitespace(entrada.getScript()).toUpperCase().contains("CREATETABLE")) {
			MySqlLexer lexer = new MySqlLexer(new ANTLRInputStream(entrada.getScript().toUpperCase()));
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			MySqlParser parser = new MySqlParser(tokens);
			return this.validarBoasPraticas(this.print(parser.root()));
		}
		return "Não existe regra para esse comando";
	}

	public List<ArvoreSintaticaDto> retornoArvoreSintatica(String a) {
		lista = new ArrayList();
		MySqlLexer lexer = new MySqlLexer(new ANTLRInputStream(a.toUpperCase()));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		MySqlParser parser = new MySqlParser(tokens);
		return this.print(parser.root());
	}

	public List<ArvoreSintaticaDto> print(RuleContext ctx) {
		return listaBoasPraticas(ctx, 0);
	}

	private List<ArvoreSintaticaDto> listaBoasPraticas(RuleContext ctx, int indentation) {
		ArvoreSintaticaDto boasPraticas = new ArvoreSintaticaDto();
		boolean toBeIgnored = ignoringWrappers && ctx.getChildCount() == 1
				&& ctx.getChild(0) instanceof ParserRuleContext;
		if (!toBeIgnored) {
			boasPraticas.setFragmento(ctx.getText());
			boasPraticas.setRegra(MySqlParser.ruleNames[ctx.getRuleIndex()]);
			lista.add(boasPraticas);
		}
		for (int i = 0; i < ctx.getChildCount(); i++) {
			ParseTree element = ctx.getChild(i);
			if (element instanceof RuleContext) {
				//System.out.println("RuleContext	" + MySqlParser.ruleNames[((RuleContext) element).getRuleIndex()]);
				//System.out.println("Context		" + MySqlParser.ruleNames[ctx.getRuleIndex()]);
				listaBoasPraticas((RuleContext) element, indentation + (toBeIgnored ? 0 : 1));
			}
		}
		return lista;
	}

	private String validarBoasPraticas(List<ArvoreSintaticaDto> listaArvoreSintatica) {
		regraPratica = new RegraPraticaDto();
		List<RegrasDto> retorno = regraPratica.getListaOrdenadaDeRegras().stream()
				.filter(x -> listaArvoreSintatica.stream()
						.filter(y -> y.getRegra().equalsIgnoreCase(x.getNomeRepresentativoRegra())).count() != 0)
				.collect(Collectors.toList());
		List<RegrasDto> retorno2 = regraPratica.getListaOrdenadaDeRegras().stream()
				.filter(x -> retorno.stream()
						.filter(y -> y.getNomeRepresentativoRegra().equalsIgnoreCase(x.getNomeRepresentativoRegra()))
						.count() == 0)
				.collect(Collectors.toList());
		if (retorno.size() != regraPratica.getListaOrdenadaDeRegras().size()) {
			return "Comando não cumpriu as boas praticas pois faltam as seguintes regras: " + retorno2.stream()
					.map(regrasDto -> regrasDto.getNomeRepresentativoRegra()).collect(Collectors.toList()).toString();
		}
		return "Comando cumpriu as boas praticas";
	}
}