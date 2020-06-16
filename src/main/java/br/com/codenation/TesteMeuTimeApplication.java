package br.com.codenation;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.codenation.desafio.exceptions.CapitaoNaoInformadoException;
import br.com.codenation.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.codenation.desafio.exceptions.JogadorNaoEncontradoException;
import br.com.codenation.desafio.exceptions.TimeNaoEncontradoException;

public class TesteMeuTimeApplication {

    public static void main(String[] args) {
        DesafioMeuTimeApplication desafio1 = new DesafioMeuTimeApplication();

        desafio1.buscarTopJogadores(Integer.valueOf(5));

        System.out.println();
        System.out.println("Visualizando a lista de top 5 jogadores:\n");
        for (int i = 0; i < desafio1.buscarTopJogadores(Integer.valueOf(5)).size(); i++)
            System.out.println("Posição " + i + ", jogador " + desafio1.buscarTopJogadores(Integer.valueOf(5)).get(i)
             + ", com nível de habilidade " +  desafio1.encontraJogador(desafio1.buscarTopJogadores(Integer.valueOf(5)).get(i)).getNivelHabilidade() + ".");

        assertProperty(desafio1.buscarTimes().size(), 0, "#1 Quantidade de times");
  
        desafio1.incluirTime(Long.valueOf(000001), "Palmeiras", LocalDate.of(1998, 06, 13), "Verde", "Branco");

        assertProperty(desafio1.buscarTimes().size(), 1, "#2 Quantidade de times");

        try { 
            desafio1.incluirTime(Long.valueOf(000001), "Palmeiras", LocalDate.of(1998, 06, 13), "Verde", "Branco");
        } catch (IdentificadorUtilizadoException e) {
            assertProperty(e.getMessage(),"O id 1 já existe para um time.", "#3 Mensagem da Exception"); 
        }

        assertProperty(desafio1.buscarTimes().size(), 1, "#4 Quantidade de times = 1");

        desafio1.incluirTime(Long.valueOf(000002), "Corinthians", LocalDate.of(1997, 05, 12), "Preto", "Branco");

        assertProperty(desafio1.buscarTimes().size(), 2, "#5 Quantidade de times = 2");

        assertProperty(desafio1.buscarTimes().contains(Long.valueOf(000001)), true, "#6 Time 000001 está na lista");
    
        assertProperty(desafio1.buscarTimes().contains(Long.valueOf(000002)), true, "#7 Time 000002 está na lista");
  
        desafio1.incluirJogador(Long.valueOf(100), Long.valueOf(000001), "Danilo", LocalDate.of(1981, 01, 21), Integer.valueOf(100), new BigDecimal("15000.00"));

        assertProperty(desafio1.getListaJogadores().contains(Long.valueOf(100)), true, "#8 Jogador 100 está cadastrado");

        try {
            desafio1.incluirJogador(Long.valueOf(100), Long.valueOf(000001), "Danilo", LocalDate.of(1981, 01, 21), Integer.valueOf(100), BigDecimal.valueOf(15000));
        } catch (IdentificadorUtilizadoException e) {
            assertProperty(e.getMessage(),"O jogador Danilo de id 100 já existe.", "#9 Mensagem da Exception"); 
        }

        assertProperty(desafio1.buscarNomeJogador(Long.valueOf(100)), "Danilo", "#10 O jogador 100 foi encontrado com o nome Danilo.");

        try {
            desafio1.buscarNomeJogador(Long.valueOf(110));            
        } catch (JogadorNaoEncontradoException e) {
            assertProperty(e.getMessage(), "O jogador de id 110 não existe.", "#11 O jogador 110 não foi encontrado.");
        }

        assertProperty(desafio1.buscarNomeTime(Long.valueOf(1)), "Palmeiras", "#12 O time Palmeiras, de id 1, foi encontrado.");

        try {
            desafio1.buscarNomeTime(Long.valueOf(4));
        } catch (TimeNaoEncontradoException e) {
            assertProperty(e.getMessage(), "O Time de id 4 não existe.", "#13 O time de id 4 não foi encontrado.");
        }

        desafio1.incluirJogador(Long.valueOf(200), Long.valueOf(000001), "Diogo", LocalDate.of(1979, 03, 9), Integer.valueOf(98), BigDecimal.valueOf(13000));

        desafio1.incluirJogador(Long.valueOf(300), Long.valueOf(000002), "Tarcísio", LocalDate.of(1969, 10, 22), Integer.valueOf(100), BigDecimal.valueOf(17000));
        
        assertProperty(desafio1.buscarJogadoresDoTime(Long.valueOf(1)).size(), 2, "#14 Time 1 está com 2 jogadores.");

        assertProperty(desafio1.buscarJogadoresDoTime(Long.valueOf(2)).size(), 1, "#15 Time 2 está com 1 jogador.");

        try {
            desafio1.buscarJogadoresDoTime(Long.valueOf(3));
        } catch (TimeNaoEncontradoException e) {
            assertProperty(e.getMessage(), "O Time de id 3 não existe.", "#16 Time com id 3 não existe.");
        }

        desafio1.definirCapitao(Long.valueOf(100));

        assertProperty(desafio1.encontraJogador(Long.valueOf(100)).getEhCapitao(), true, "#17 O jogador de id 100 é capitão do time.");

        try {
            desafio1.buscarCapitaoDoTime(Long.valueOf(2));
        } catch (CapitaoNaoInformadoException e) {
            assertProperty(e.getMessage(), "Ainda não foi definido um capitão para o time.", "#18 Exception de time sem capitão.");
        }
        
        desafio1.definirCapitao(Long.valueOf(200));

        assertProperty(desafio1.buscarCapitaoDoTime(Long.valueOf(1)), Long.valueOf(200), "#19 O jogador de id 200 é capitão do time e o jogador 100 deixou de ser capitão.");

        assertProperty(desafio1.buscarCapitaoDoTime(Long.valueOf(1)), Long.valueOf(200), "#20 Busca de capitão no time.");

        assertProperty(desafio1.buscarMelhorJogadorDoTime(Long.valueOf(1)), Long.valueOf(100), "#21 Busca de melhor jogador.");

        assertProperty(desafio1.buscarJogadorMaisVelho(Long.valueOf(1)), Long.valueOf(200), "#22 Busca do jogador mais velho do time.");

        desafio1.incluirJogador(Long.valueOf(150), Long.valueOf(1), "Desconhecido", LocalDate.of(1979, 3, 9), Integer.valueOf(50), BigDecimal.valueOf(21000));

        assertProperty(desafio1.buscarJogadorMaisVelho(Long.valueOf(1)), Long.valueOf(150), "#23 Busca jogador mais velho e aplica desempate por menor id de jogador.");

        desafio1.incluirTime(Long.valueOf(7), "Portuguesa", LocalDate.of(1970, 5, 10), "Verde", "Vermelho");

        desafio1.incluirTime(Long.valueOf(4), "Flamengo", LocalDate.of(1950, 11, 30), "Vermelho", "Preto");

        System.out.println();
        System.out.println("Visualizando a ordenação da lista de times:\n");
        for (int i = 0; i < desafio1.buscarTimes().size(); i++)
            System.out.println("Posição " + i + ", time id " + desafio1.buscarTimes().get(i) + "." );

        assertProperty(desafio1.buscarJogadorMaiorSalario(Long.valueOf(1)), Long.valueOf(150), "#24 Busca jogador com maior salário no time.");

        assertProperty(desafio1.buscarSalarioDoJogador(Long.valueOf(200)), BigDecimal.valueOf(13000), "#25 Busca salário de jogador.");

        desafio1.buscarTopJogadores(Integer.valueOf(5));

        System.out.println();
        System.out.println("Visualizando a lista de top 5 jogadores:\n");
        for (int i = 0; i < desafio1.buscarTopJogadores(Integer.valueOf(5)).size(); i++)
            System.out.println("Posição " + i + ", jogador " + desafio1.buscarTopJogadores(Integer.valueOf(5)).get(i)
             + ", com nível de habilidade " +  desafio1.encontraJogador(desafio1.buscarTopJogadores(Integer.valueOf(5)).get(i)).getNivelHabilidade() + ".");

        System.out.println();
        System.out.println("Visualizando a lista de times com cor de camisa principal e secundária:\n");
            for (int i = 0; i < desafio1.buscarTimes().size(); i++)
                System.out.println("Time id: " + desafio1.buscarTimes().get(i)
                 + ", camisa principal (" + desafio1.encontraTime(desafio1.buscarTimes().get(i)).getCorUniformePrincipal() +
                "), camisa secundária (" + desafio1.encontraTime(desafio1.buscarTimes().get(i)).getCorUniformeSecundario() + ").");
        
        assertProperty(desafio1.buscarCorCamisaTimeDeFora(Long.valueOf(1), Long.valueOf(2)), "Preto", "#26 Busca camisa time de fora com cores diferentes.");

        assertProperty(desafio1.buscarCorCamisaTimeDeFora(Long.valueOf(1), Long.valueOf(7)), "Vermelho", "#27 Busca camisa time de forma com cores iguais.");

        try {
            desafio1.incluirJogador(Long.valueOf(175), Long.valueOf(1), "Epaminondas", LocalDate.of(1985, 5, 1), Integer.valueOf(150), BigDecimal.valueOf(15000));
        } catch (IllegalArgumentException e) {
            assertProperty(e.getMessage(), "O nível de habilidade é inválido. Entre com um nível de habilidade entre 0 e 100.", "#28 Erro de jogador com nível de habilidade inválido.");
        }

        try {
            desafio1.incluirJogador(Long.valueOf(500), Long.valueOf(10), "Carolina", LocalDate.of(1970, 12, 31), Integer.valueOf(100), BigDecimal.valueOf(20000));
        } catch (TimeNaoEncontradoException e) {
            assertProperty(e.getMessage(), "O Time de id 10 não existe.", "#29 Inclusão de jogador com id de time inexistente.");
        }

        try {
            desafio1.incluirJogador(null, Long.valueOf(1), "Odozires", LocalDate.of(1976, 3, 4), Integer.valueOf(10), BigDecimal.valueOf(15000));
        } catch (IllegalArgumentException e) {
            System.out.println("\nTeste Passou! #30 Id do jogador nula.\n");
        }

        try {
            desafio1.incluirJogador(Long.valueOf(500), null, "Odozires", LocalDate.of(1976, 3, 4), Integer.valueOf(10), BigDecimal.valueOf(15000));
        } catch (IllegalArgumentException e) {
            System.out.println("\nTeste Passou! #31 Id do time nula.\n");
        }

        try {
            desafio1.incluirJogador(Long.valueOf(500), Long.valueOf(1), null, LocalDate.of(1976, 3, 4), Integer.valueOf(10), BigDecimal.valueOf(15000));
        } catch (IllegalArgumentException e) {
            System.out.println("\nTeste Passou! #32 Nome do jogador nulo.\n");
        }

        try {
            desafio1.incluirJogador(Long.valueOf(500), Long.valueOf(1), "Odozires", null, Integer.valueOf(10), BigDecimal.valueOf(15000));
        } catch (IllegalArgumentException e) {
            System.out.println("\nTeste Passou! #33 Data de nascimento do jogador nula.\n");
        }

        try {
            desafio1.incluirJogador(Long.valueOf(500), Long.valueOf(1), "Odozires", LocalDate.of(1976, 3, 4), null, BigDecimal.valueOf(15000));
        } catch (IllegalArgumentException e) {
            System.out.println("\nTeste Passou! #34 Nível de habilidade do jogador nulo.\n");
        }

        try {
            desafio1.incluirJogador(Long.valueOf(500), Long.valueOf(1), "Odozires", LocalDate.of(1976, 3, 4), Integer.valueOf(10), null);
        } catch (IllegalArgumentException e) {
            System.out.println("\nTeste Passou! #35 Salário do jogador nulo.\n");
        }

        try {
            desafio1.incluirJogador(Long.valueOf(500), Long.valueOf(1), "Odozires", LocalDate.of(2021, 3, 4), Integer.valueOf(10), BigDecimal.valueOf(15000));
        } catch (IllegalArgumentException e) {
            System.out.println("\nTeste Passou! #36 Data de nascimento futura do jogador.\n");
        }

        try {
            desafio1.incluirJogador(Long.valueOf(500), Long.valueOf(1), "Odozires", LocalDate.of(1976, 3, 4), Integer.valueOf(10), BigDecimal.valueOf(-1000));
        } catch (IllegalArgumentException e) {
            System.out.println("\nTeste Passou! #37 Salário do jogador é menor que zero.\n");
        }

        try {
            desafio1.incluirTime(null, "Joinville", LocalDate.of(1965, 2, 23), "Preto", "Vermelho");
        } catch (IllegalArgumentException e) {
            System.out.println("\nTeste Passou! #38 Id do time é nulo.\n");
        }

        try {
            desafio1.incluirTime(Long.valueOf(15), null, LocalDate.of(1965, 2, 23), "Preto", "Vermelho");
        } catch (IllegalArgumentException e) {
            System.out.println("\nTeste Passou! #39 Nome do time é nulo.\n");
        }

        try {
            desafio1.incluirTime(Long.valueOf(15), "Joinville", null, "Preto", "Vermelho");
        } catch (IllegalArgumentException e) {
            System.out.println("\nTeste Passou! #40 Data de criação do time é nula.\n");
        }

        try {
            desafio1.incluirTime(Long.valueOf(15), "Joinville", LocalDate.of(1965, 2, 23), null, "Vermelho");
        } catch (IllegalArgumentException e) {
            System.out.println("\nTeste Passou! #41 Cor uniforme principal do time é nula.\n");
        }

        try {
            desafio1.incluirTime(Long.valueOf(15), "Joinville", LocalDate.of(1965, 2, 23), "Preto", null);
        } catch (IllegalArgumentException e) {
            System.out.println("\nTeste Passou! #42 Cor uniforme secundária do time é nula.\n");
        }

        try {
            desafio1.incluirTime(Long.valueOf(15), "Joinville", LocalDate.of(2023, 2, 23), null, "Vermelho");
        } catch (IllegalArgumentException e) {
            System.out.println("\nTeste Passou! #43 Data de criação do time é futura.\n");
        }

        try {
            desafio1.definirCapitao(null);
        } catch (IllegalArgumentException e) {
            System.out.println("\nTeste Passou! #44 Método definirCapitao com parâmetro nulo.\n");
        }

        try {
            desafio1.buscarCapitaoDoTime(null);
        } catch (IllegalArgumentException e) {
            System.out.println("\nTeste Passou! #45 Método buscarCapitaoDoTime com parâmetro nulo.\n");
        }

        try {
            desafio1.buscarNomeJogador(null);
        } catch (IllegalArgumentException e) {
            System.out.println("\nTeste Passou! #46 Método buscarNomeJogador com parâmetro nulo.\n");
        }
        
        try {
            desafio1.buscarNomeTime(null);
        } catch (IllegalArgumentException e) {
            System.out.println("\nTeste Passou! #47 Método buscarNomeTime com parâmetro nulo.\n");
        }

        try {
            desafio1.buscarJogadoresDoTime(null);
        } catch (IllegalArgumentException e) {
            System.out.println("\nTeste Passou! #48 Método buscarJogadoresDoTime com parâmetro nulo.\n");
        }

        try {
            desafio1.buscarMelhorJogadorDoTime(null);
        } catch (IllegalArgumentException e) {
            System.out.println("\nTeste Passou! #49 Método buscarMelhorJogadorDoTime com parâmetro nulo.\n");
        }

        try {
            desafio1.buscarJogadorMaisVelho(null);
        } catch (IllegalArgumentException e) {
            System.out.println("\nTeste Passou! #50 Método buscarJogadorMaisVelho com parâmetro nulo.\n");
        }

        try {
            desafio1.buscarJogadorMaiorSalario(null);
        } catch (IllegalArgumentException e) {
            System.out.println("\nTeste Passou! #51 Método buscarJogadorMaiorSalario com parâmetro nulo.\n");
        }

        try {
            desafio1.buscarSalarioDoJogador(null);
        } catch (IllegalArgumentException e) {
            System.out.println("\nTeste Passou! #52 Método buscarSalarioJogador com parâmetro nulo.\n");
        }

        try {
            desafio1.buscarTopJogadores(null);
        } catch (IllegalArgumentException e) {
            System.out.println("\nTeste Passou! #53 Método buscarTopJogadores com parâmetro nulo.\n");
        }

        try {
            desafio1.buscarCorCamisaTimeDeFora(null, Long.valueOf(1));
        } catch (IllegalArgumentException e) {
            System.out.println("\nTeste Passou! #54 Método buscarCorCamisaTimeDeFora idTimeDaCasa com parâmetro nulo.\n");
        }

        try {
            desafio1.buscarCorCamisaTimeDeFora(Long.valueOf(1), null);
        } catch (IllegalArgumentException e) {
            System.out.println("\nTeste Passou! #55 Método buscarCorCamisaTimeDeFora idTimeDeFora com parâmetro nulo.\n");
        }

        desafio1.incluirJogador(Long.valueOf(700), Long.valueOf(1), "X1", LocalDate.of(1980, 1, 2), Integer.valueOf(70), BigDecimal.valueOf(30000));

        desafio1.incluirJogador(Long.valueOf(13), Long.valueOf(1), "X2", LocalDate.of(1980, 1, 3), Integer.valueOf(97), BigDecimal.valueOf(50000));

        desafio1.incluirJogador(Long.valueOf(7), Long.valueOf(1), "X3", LocalDate.of(1980, 1, 4), Integer.valueOf(80), BigDecimal.valueOf(100000));

        desafio1.incluirJogador(Long.valueOf(23), Long.valueOf(1), "X4", LocalDate.of(1980, 1, 5), Integer.valueOf(84), BigDecimal.valueOf(100000));

        desafio1.imprimeListaJogadoresComSalarios();

        System.out.println("Jogador com maior salário no time 1: " + desafio1.buscarJogadorMaiorSalario(Long.valueOf(1)));
    }


    private static boolean assertProperty (Object value, Object expected, String property) {
        boolean success = value.equals(expected);
        System.out.println();
        if (success)
            System.out.println("Teste passou! " + property);
        else
            System.out.println("Teste falhou! " + property + " era esperado " + expected + " mas retornou " + value);

        return success;
    }


}