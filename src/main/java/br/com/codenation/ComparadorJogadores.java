package br.com.codenation;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ComparadorJogadores implements Comparator<Jogador> {

    private List<Comparator<Jogador>> listComparators;

    @SafeVarargs
    public ComparadorJogadores (Comparator<Jogador>... comparators) {
        this.listComparators = Arrays.asList(comparators);
    }

    @Override
    public int compare(Jogador j1, Jogador j2) {
        for (Comparator<Jogador> comparator : listComparators) {
            int result = comparator.compare(j1, j2);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }
}