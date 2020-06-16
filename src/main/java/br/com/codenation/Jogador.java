package br.com.codenation;

import java.time.LocalDate;
import java.math.BigDecimal;

public class Jogador {

    private Long id;
    private String nome;
    private LocalDate dataNascimento;
    private Integer nivelHabilidade;
    private BigDecimal salario;
    private boolean ehCapitao = false;
    
    public Jogador (Long id, String nome, LocalDate dataNascimento, Integer nivelHabilidade, BigDecimal salario) {
        setId(id);
        setNome(nome);
        setDataNascimento(dataNascimento);
        setNivelHabilidade(nivelHabilidade);
        setSalario(salario);
    }

    public void setId(Long id) {
        if (id == null)
            throw new IllegalArgumentException("O id do jogador não pode ser nulo.");
        this.id = id;
    }
    
    public Long getId() {
        return Long.valueOf(id);
    }

    public void setNome(String nome) {
        if (nome == null)
            throw new IllegalArgumentException("O nome do jogador não pode ser nulo.");
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setEhCapitao() {
        this.ehCapitao = true;
    }

    public void setNaoEhCapitao() {
        this.ehCapitao = false;
    }

    public boolean getEhCapitao() {
        return ehCapitao;
    }

    public void setNivelHabilidade(Integer nivelHabilidade) {
        if (nivelHabilidade == null) 
            throw new IllegalArgumentException("O nível de habilidade do jogador não pode ser nulo.");
        if ((int) nivelHabilidade < 0 || (int) nivelHabilidade > 100) {
            throw new IllegalArgumentException("O nível de habilidade é inválido. Entre com um nível de habilidade entre 0 e 100.");
        }
        this.nivelHabilidade = nivelHabilidade;
    }
    
    public Integer getNivelHabilidade() {
        return nivelHabilidade;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        if (dataNascimento == null)
            throw new IllegalArgumentException("A data de nascimento do jogador não pode ser nula.");
        if (dataNascimento.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("A data de nascimento do jogador não pode ser futura.");
        this.dataNascimento = dataNascimento;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setSalario(BigDecimal salario) {
        if (salario == null)
            throw new IllegalArgumentException("O salário do jogador não pode ser nulo.");
        if (salario.doubleValue() < 0)
            throw new IllegalArgumentException("O salário do jogador não pode ser menor que zero.");
        this.salario = salario;
    }
    
    public BigDecimal getSalario() {
        return salario;
    }
}