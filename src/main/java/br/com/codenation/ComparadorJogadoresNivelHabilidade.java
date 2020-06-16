package br.com.codenation;

import java.util.Comparator;

public class ComparadorJogadoresNivelHabilidade implements Comparator<Jogador> {

    @Override
    public int compare(Jogador j1, Jogador j2) {
        return j2.getNivelHabilidade().compareTo(j1.getNivelHabilidade());
    }
}