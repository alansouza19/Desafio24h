package com.desafio24h.aceito.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.desafio24h.aceito.domain.model.EnderecoCep;
import com.desafio24h.aceito.domain.model.Usuario;
import com.desafio24h.aceito.model.UsuarioModel;
import com.desafio24h.aceito.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	public List<UsuarioModel> findAll(){
		return usuarioRepository.findAll();
	}
	public void criar(Usuario usuario) throws Exception{
		validaUsuario(usuario);
		validaCep(usuario);
	
	/**
	 * buscaCep(usuario.getCep());
	 * private EnderecoCep buscaCep(String cep) throw Exception {
	 * 
	 * }
	 * 
	 */
		String url = "http://viacep.com.br/ws/"+usuario.getCep()+"/json/";
		RestTemplate restTemplate = new RestTemplate();
		EnderecoCep cepResponse = restTemplate.getForObject(url,EnderecoCep.class);
		
		if(cepResponse.isErro()) {
			throw new Exception("Informe um CEP válido");
		}
		
		Random gerador = new Random();
		
		UsuarioModel user = new UsuarioModel();
		user.setId((long) gerador.nextInt(1500));
		user.setNome(usuario.getNome());
		user.setIdade(calcIdade(usuario.getDataNascimento().getYear()));
		user.setCidade(cepResponse.getLocalidade());
		user.setBairro(cepResponse.getBairro());
		user.setEstado(cepResponse.getUf());
		user.setCpf(usuario.getCpf());
		
		usuarioRepository.save(user);
	}
	

	private void validaCep(Usuario usuario) throws Exception{
		if(usuario.getCep().equals("00000000") || 
				usuario.getCep().equals("11111111") ||
				usuario.getCep().equals("22222222") || 
				usuario.getCep().equals("33333333") ||	
				usuario.getCep().equals("44444444") || 
				usuario.getCep().equals("55555555") ||
				usuario.getCep().equals("66666666") || 
				usuario.getCep().equals("77777777") ||
				usuario.getCep().equals("88888888") || 
				usuario.getCep().equals("99999999") ||
				usuario.getCep().length() != 8) {
			throw new Exception("Informe um CEP válido.");
		} 
	}
	
	private int calcIdade(int a){
		LocalDate anoatual = LocalDate.now();
		int ano = anoatual.getYear();
		return ano - a;
	}
	
	public UsuarioModel findByCpf(String cpf) throws Exception {
		UsuarioModel user = usuarioRepository.findByCpf(cpf);
		
		if(user == null) {
			throw new Exception("Usuário não encontrado");
		}
		
		return user;
	}
	
	private void validaUsuario(Usuario usuario) throws Exception {
		usuario.setNome(usuario.getNome().trim());
		usuario.setCpf(usuario.getCpf().trim());
		if(usuario.getNome() == null || usuario.getNome().isEmpty()
				|| usuario.getNome().length() > 15){
			throw new Exception("Nome não pode ser nulo ou vazio ou ter mais do que 15 caracteres.");
		}
		if(usuario.getDataNascimento() == null){
			throw new Exception("Data de nascimento não pode ser nulo ou vazio.");
		}
		if(usuario.getCpf() == null || usuario.getCpf().isEmpty() 
			|| usuario.getCpf().length() != 11){
			throw new Exception("CPF não pode ser nulo, vazio ou inválido.");
		}
	}
	

}
