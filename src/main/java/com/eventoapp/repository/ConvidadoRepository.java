package com.eventoapp.repository;

import javax.swing.Spring;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eventoapp.models.Convidado;
import com.eventoapp.models.Evento;


@Repository
public interface ConvidadoRepository extends CrudRepository<Convidado, Spring>{

	Iterable<Convidado> findByEvento(Evento evento);

	Convidado findByRg(String rg);
	
}
