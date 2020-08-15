package com.eventoapp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eventoapp.models.Convidado;
import com.eventoapp.models.Evento;
import com.eventoapp.repository.ConvidadoRepository;
import com.eventoapp.repository.EventoRepository;

@Controller
public class EventoController {
	
	@Autowired
	private EventoRepository repositoryEvento;
	
	@Autowired
	private ConvidadoRepository repositoryConvidado;
	
	@RequestMapping(value="/cadastroEvento",method=RequestMethod.GET)
	public String form() {
		return "evento/formEvento";
	}
	
	@RequestMapping(value="/cadastroEvento",method=RequestMethod.POST)
	public String salvar(@Valid Evento evento, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique se os campos estão corretos");
			return "redirect:/cadastroEvento";
		}
		System.out.println(evento);
		repositoryEvento.save(evento);
		return "redirect:/eventos";
	}
	@RequestMapping(value="/eventos")
	public ModelAndView listar() {
		ModelAndView view = new ModelAndView("evento/listaEvento");
		view.addObject("eventos", repositoryEvento.findAll());
		return view;
	}
	@RequestMapping(value="/{codigo}", method=RequestMethod.GET)
	public ModelAndView detalheEvento(@PathVariable("codigo") long codigo) {
		
		Evento evento = repositoryEvento.findByCodigo(codigo);
		ModelAndView view = new ModelAndView("evento/detalheEvento");
		Iterable<Convidado> convidados = repositoryConvidado.findByEvento(evento);
		view.addObject("evento", evento);
		view.addObject("convidados", convidados);
		return view;
	}
	@RequestMapping(value="/{codigo}", method=RequestMethod.POST)
	public String convidadoEvento(@PathVariable("codigo") long codigo,@Valid  Convidado convidado,  BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique se os campos estão corretos");
			return "redirect:/{codigo}";
		}
		Evento evento = new Evento();
		evento.setCodigo(codigo);
		convidado.setEvento(evento);
		repositoryConvidado.save(convidado);
		attributes.addFlashAttribute("mensagem", "Sucesso!!!!!");
		return "redirect:/{codigo}";
	}
	@RequestMapping("/deletarEvento")
	public String deletarEvento(long codigo) {
		Evento evento = repositoryEvento.findByCodigo(codigo);
		repositoryEvento.delete(evento);
		return "redirect:/eventos";
	}
	@RequestMapping("/deletarConvidado")
	public String deletarConvidado(String rg) {
		Convidado convidado = repositoryConvidado.findByRg(rg);
		repositoryConvidado.delete(convidado);
		
		Evento evento = convidado.getEvento();
		long codigoLong = evento.getCodigo();
		String codigo = "" + codigoLong;
		
		return "redirect:/" + codigo;
	}
}
