public class Main { 
    public static void main(String[] args) { 
        TabelaHash tabela = new TabelaHash(10, 2); //cria a instancia da tabela hash com capacidade 10 e fator de carga 2. Insere 10 elementos, usando o método inserir. 

        tabela.inserir(1, "Valor 1");
        tabela.inserir(2, "Valor 2");
        tabela.inserir(3, "Valor 3");
        tabela.inserir(4, "Valor 4");
        tabela.inserir(5, "Valor 5");
        tabela.inserir(6, "Valor 6");
        tabela.inserir(7, "Valor 7");
        tabela.inserir(8, "Valor 8");
        tabela.inserir(9, "Valor 9");
        tabela.inserir(10, "Valor 10");

        long inicio = System.nanoTime(); //mede o tempo de busca
        System.out.println(tabela.buscar(5));
        long fim = System.nanoTime();
        System.out.println("Tempo de busca por encadeamento: " + (fim - inicio) / 1000000.0 + " ms");

        inicio = System.nanoTime();
        System.out.println(tabela.buscarEnderecamentoAberto(5));
        fim = System.nanoTime();
        System.out.println("Tempo de busca por endereçamento aberto: " + (fim - inicio) / 1000000.0 + " ms");
    }
}