package com.eventoapp.repository;

import javax.swing.Spring;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.eventoapp.models.Evento;

@Repository
public interface EventoRepository extends CrudRepository<Evento, Spring>{
	Evento findByCodigo(long codigo);
}
