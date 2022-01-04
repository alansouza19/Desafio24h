package com.desafio24h.aceito.domain.model;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Usuario {
	
	
	private String nome;
	private LocalDate dataNascimento;
	private String cep;
	private String cpf;
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(String dataNascimento ) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate date = LocalDate.parse(dataNascimento, formatter);
		this.dataNascimento = date;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	
	
}
