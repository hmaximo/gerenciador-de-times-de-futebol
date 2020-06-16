package br.com.codenation;

import java.util.Comparator;

public class ComparadorJogadoresId implements Comparator<Jogador> {

    public int compare(Jogador j1, Jogador j2) {
        return j1.getId().compareTo(j2.getId());
    }

}