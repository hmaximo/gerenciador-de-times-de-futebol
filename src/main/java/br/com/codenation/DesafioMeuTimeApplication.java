package br.com.codenation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.codenation.desafio.annotation.Desafio;
import br.com.codenation.desafio.app.MeuTimeInterface;
import br.com.codenation.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.codenation.desafio.exceptions.JogadorNaoEncontradoException;
import br.com.codenation.desafio.exceptions.TimeNaoEncontradoException;

public class DesafioMeuTimeApplication implements MeuTimeInterface {

	private List<Time> listaTimes = new ArrayList<Time>();
	private List<Jogador> listaJogadores = new ArrayList<Jogador>();

	@Desafio("incluirTime")
	public void incluirTime(Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal, String corUniformeSecundario) {
		if (buscarTimes().contains(id)) 
			throw new IdentificadorUtilizadoException("O id " + id + " já existe para um time.");
		listaTimes.add(new Time(id, nome, dataCriacao, corUniformePrincipal, corUniformeSecundario));
	}

	@Desafio("incluirJogador")
	public void incluirJogador(Long id, Long idTime, String nome, LocalDate dataNascimento, Integer nivelHabilidade, BigDecimal salario) {
		Time time = encontraTime(idTime);
		
		if (id == null)
			throw new IllegalArgumentException("O id do jogador não pode ser nulo.");

		for (Jogador j : listaJogadores) {
			if (j.getId().equals(Long.valueOf(id)))
				throw new IdentificadorUtilizadoException("O jogador " + nome + " de id " + id + " já existe.");
		}
		Jogador novoJogador = new Jogador(id, nome, dataNascimento, nivelHabilidade, salario);
		listaJogadores.add(novoJogador);
		time.entrarNoTime(novoJogador);
	}	

	@Desafio("definirCapitao")
	public void definirCapitao(Long idJogador) {
		Jogador capitao = encontraJogador(idJogador);
		Time time = null;
		for (Time t : listaTimes) {
			if (t.listarJogadoresDoTime().contains(idJogador))
				time = t;
		}
		if (time.temCapitao()) {
			time.buscarCapitao().setNaoEhCapitao();
		}
		capitao.setEhCapitao();
	}

	@Desafio("buscarCapitaoDoTime")
	public Long buscarCapitaoDoTime(Long idTime) {
		return encontraTime(idTime).buscarCapitao().getId();
	}

	@Desafio("buscarNomeJogador")
	public String buscarNomeJogador(Long idJogador) {
		return encontraJogador(idJogador).getNome();
	}

	@Desafio("buscarNomeTime")
	public String buscarNomeTime(Long idTime) {
		return encontraTime(idTime).getNomeTime();
	}

	@Desafio("buscarJogadoresDoTime")
	public List<Long> buscarJogadoresDoTime(Long idTime) {
		return encontraTime(idTime).listarJogadoresDoTime();
	}

	@Desafio("buscarMelhorJogadorDoTime")
	public Long buscarMelhorJogadorDoTime(Long idTime) {
		return encontraTime(idTime).melhorJogador().getId();
	}

	@Desafio("buscarJogadorMaisVelho")
	public Long buscarJogadorMaisVelho(Long idTime) {
		return encontraTime(idTime).buscarJogadorMaisVelho().getId();
	}

	@Desafio("buscarTimes")
	public List<Long> buscarTimes() {
		List<Long> timesAtuais = new ArrayList<Long>();
		for (Time t : listaTimes)
			timesAtuais.add(t.getIdTime());
		Collections.sort(timesAtuais);
		return timesAtuais;
	}

	@Desafio("buscarJogadorMaiorSalario")
	public Long buscarJogadorMaiorSalario(Long idTime) {
		return encontraTime(idTime).maiorSalario().getId();
	}

	@Desafio("buscarSalarioDoJogador")
	public BigDecimal buscarSalarioDoJogador(Long idJogador) {
		return encontraJogador(idJogador).getSalario();
	}

	@Desafio("buscarTopJogadores")
	public List<Long> buscarTopJogadores(Integer top) {
		if (top == null)
			throw new IllegalArgumentException("A quantidade de jogadores na lista não pode ser nula.");
		
		List<Long> listaTops = new ArrayList<Long>();
		Collections.sort(listaJogadores, new ComparadorJogadores(
				new ComparadorJogadoresNivelHabilidade(), 
				new ComparadorJogadoresId()));
		for (int i = 0; i < listaJogadores.size() && i < (int) top ; i++) 
			listaTops.add(listaJogadores.get(i).getId());
		return listaTops;
	}

	@Desafio("buscarCorCamisaTimeDeFora")
	public String buscarCorCamisaTimeDeFora(Long timeDaCasa, Long timeDeFora) {
		String corCamisaTimeDeFora = encontraTime(timeDeFora).getCorUniformePrincipal();
		if (encontraTime(timeDaCasa).getCorUniformePrincipal() == encontraTime(timeDeFora).getCorUniformePrincipal())
			corCamisaTimeDeFora = encontraTime(timeDeFora).getCorUniformeSecundario();
		return corCamisaTimeDeFora;
	}

	//Método para operacionalizar os testes na classe TesteMeuTimeApplication
	public List<Long> getListaJogadores () { 
		List<Long> jogadoresAtuais = new ArrayList<Long>();
		for (Jogador j : listaJogadores)
			jogadoresAtuais.add(j.getId());
		return jogadoresAtuais;
	}

	public void imprimeListaJogadoresComSalarios() {
		System.out.println("Jogadores atuais com seus salários:\n");
		for (Jogador j : listaJogadores) {
			System.out.println("Id: " + j.getId() + ", Nome: " + j.getNome() + ", Salário: " + j.getSalario() + ".");
		}
	}

	//Método para facilitar a buscar de um objeto time a partir do id do time
	public Time encontraTime(Long idTime) {
		if (idTime == null) //o método já dá conta da entrada de parâmetro nulo
			throw new IllegalArgumentException("A id do time não pode ser nula.");

		for (Time t : listaTimes) {
			if (t.getIdTime().equals(Long.valueOf(idTime)))
				return t;
		}
		throw new TimeNaoEncontradoException("O Time de id " + idTime + " não existe.");
	}

	//Método para facilitar a busca de um objeto jogador a partir do id do jogador
	public Jogador encontraJogador(Long idJogador) {
		if (idJogador == null) //o método já dá conta da entrada de parâmetro nulo
			throw new IllegalArgumentException("O id do jogador não pode ser nulo.");

		for (Jogador j : listaJogadores) {
			if (j.getId().equals(Long.valueOf(idJogador)))
				return j;
		}
		throw new JogadorNaoEncontradoException("O jogador de id " + idJogador + " não existe.");
	}

}
