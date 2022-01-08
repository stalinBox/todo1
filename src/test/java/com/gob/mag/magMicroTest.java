package com.gob.mag;

import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import todo1.com.domain.Cliente;
import todo1.com.repository.ClienteRepository;
import todo1.com.services.ClienteService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class magMicroTest {

	@Autowired
	ClienteService clienteService;

	@Test
	@Transactional
	public void test() {
		Cliente cliente = new Cliente();
		cliente.setCli_nombre("ALGO");
		cliente.setCli_apellido("apellido");
		cliente.setCli_dir("Direccion");
		cliente.setCli_telf("0998523644");
		this.clienteService.save(cliente);
	}
}
