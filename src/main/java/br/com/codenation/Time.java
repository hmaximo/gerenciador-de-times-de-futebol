package br.com.codenation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.codenation.desafio.exceptions.CapitaoNaoInformadoException;

public class Time {

    private Long idTime;
    private String nome;
    private LocalDate dataCriacao;
    private String corUniformePrincipal;
    private String corUniformeSecundario;
    private List<Jogador> jogadores = new ArrayList<Jogador>();

    public Time (Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal, String corUniformeSecundario) {
        setIdTime(id);
        setNome(nome);
        setDataCriacao(dataCriacao);
        setCorUniformePrincipal(corUniformePrincipal);
        setCorUniformeSecundario(corUniformeSecundario);    
    }
    
    public Long getIdTime() {
        return idTime;
    }

    public void setIdTime(Long idTime) {
        if (idTime == null)
            throw new IllegalArgumentException("O id do time não pode ser nulo.");
        this.idTime = idTime;
    }

    public void entrarNoTime(Jogador jogador) {
        jogadores.add(jogador);
    }

    public String getNomeTime() {
         return nome;
    }

    public void setNome(String nome) {
        if (nome == null)
            throw new IllegalArgumentException("O nome do time não pode ser nulo.");
        this.nome = nome;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        if (dataCriacao == null)
            throw new IllegalArgumentException("A data de criação do time não pode ser nula.");
        if (dataCriacao.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("A data de criação do time não pode ser futura.");
        this.dataCriacao = dataCriacao;
    }

    public String getCorUniformePrincipal() {
        return corUniformePrincipal;
    }

    public void setCorUniformePrincipal(String corUniformePrincipal) {
        if (corUniformePrincipal == null)
            throw new IllegalArgumentException("A cor do uniforme principal do time não pode ser nula.");
        this.corUniformePrincipal = corUniformePrincipal;
    }

    public String getCorUniformeSecundario() {
        return corUniformeSecundario;
    }

    public void setCorUniformeSecundario(String corUniformeSecundario) {
        if (corUniformeSecundario == null)
            throw new IllegalArgumentException("A cor do uniforme secundário do time não pode ser nula.");
        this.corUniformeSecundario = corUniformeSecundario;
    }

    public List<Long> listarJogadoresDoTime() {
        List<Long> listaJogadoresDoTime = new ArrayList<Long>();
        for (Jogador j : jogadores) {
            listaJogadoresDoTime.add(j.getId());
        }
        Collections.sort(listaJogadoresDoTime);
        return listaJogadoresDoTime;
    }

    public Jogador buscarCapitao() {
        for (Jogador j : jogadores) {
            if (j.getEhCapitao() == true)
                return j;
        }
        throw new CapitaoNaoInformadoException("Ainda não foi definido um capitão para o time.");
    }

    public boolean temCapitao() {
        for (Jogador j : jogadores) {
            if (j.getEhCapitao() == true)
                return true;
        }
        return false;
    }

    public Jogador melhorJogador() {
        Integer nivelHabilidadeMaisAlto = new Integer(0);
        Jogador melhorJogador = null;
        for (Jogador j : jogadores) {
            if (j.getNivelHabilidade().compareTo(nivelHabilidadeMaisAlto) > 0 || 
            (j.getNivelHabilidade().compareTo(nivelHabilidadeMaisAlto) == 0 && j.getId().compareTo(melhorJogador.getId()) < 0)) {
                nivelHabilidadeMaisAlto = Integer.valueOf(j.getNivelHabilidade());
                melhorJogador = j;
            }
        }
        return melhorJogador;
    }

    public Jogador buscarJogadorMaisVelho() {
        LocalDate dataMaisAntiga = LocalDate.now();
        Jogador jogadorMaisVelho = jogadores.get(0);
        for (Jogador j : jogadores) {
            if (j.getDataNascimento().isBefore(dataMaisAntiga) || 
            (j.getDataNascimento().isEqual(dataMaisAntiga) && j.getId().compareTo(jogadorMaisVelho.getId()) < 0)) {
                dataMaisAntiga = j.getDataNascimento();
                jogadorMaisVelho = j;
            }
        }
        return jogadorMaisVelho;
    }

    public Jogador maiorSalario() {
        BigDecimal maiorSalario = new BigDecimal(0);
        Jogador jogadorMaisCaro = jogadores.get(0);
        for (Jogador j : jogadores) {
            if (j.getSalario().compareTo(maiorSalario) > 0 ||
            (j.getSalario().equals(maiorSalario) && j.getId().compareTo(jogadorMaisCaro.getId()) < 0)) {
                maiorSalario = j.getSalario();
                jogadorMaisCaro = j;
            }
        }
        return jogadorMaisCaro;
    }

}