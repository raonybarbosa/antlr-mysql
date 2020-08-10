package br.com.antlr.mysql.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.antlr.mysql.dto.ArvoreSintaticaDto;
import br.com.antlr.mysql.dto.EntradaComandoDto;
import br.com.antlr.mysql.service.BoasPraticasService;

@RestController
@RequestMapping("/boaspraticas")
public class BoasPraticasController {

	@Autowired
	private BoasPraticasService boasPraticasService;

	@PostMapping
	public String validaoBoasPraticas(@RequestBody EntradaComandoDto entrada) {
		return boasPraticasService.preencheEntrada(entrada);
	}

	@PostMapping("/retornaarvore")
	public List<ArvoreSintaticaDto> retornoArvoreSintatica(@RequestBody EntradaComandoDto entrada) {
		return boasPraticasService.retornoArvoreSintatica(entrada.getScript());
	}

}
