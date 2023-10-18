public class TabelaHash {
    private int tamanho;
    private int capacidade;
    private int[] chaves;
    private Object[] valores;
    private int fatorCarga;
    private int tamanhoMaximo;

    public TabelaHash(int capacidade, int fatorCarga) {
        this.tamanho = 0;
        this.capacidade = capacidade;
        this.chaves = new int[capacidade];
        this.valores = new Object[capacidade];
        this.fatorCarga = fatorCarga;
        this.tamanhoMaximo = (int) (capacidade * fatorCarga);
    }

    public void inserir(int chave, Object valor) {
        if (tamanho >= tamanhoMaximo) {
            redimensionar();
        }

        int indice = hash(chave);

        if (chaves[indice] == 0) {
            chaves[indice] = chave;
            valores[indice] = valor;
            tamanho++;
        } else if (chaves[indice] == chave) {
            valores[indice] = valor;
        } else {
            // Tratamento de colisão por encadeamento
            int novoIndice = indice;
            while (chaves[novoIndice] != 0 && chaves[novoIndice] != chave) {
                novoIndice = (novoIndice + 1) % capacidade;
            }

            if (chaves[novoIndice] == 0) {
                chaves[novoIndice] = chave;
                valores[novoIndice] = valor;
                tamanho++;
            } else {
                valores[novoIndice] = valor;
            }

            // Tratamento de colisão por endereçamento aberto
            /*
            int i = 1;
            while (chaves[indice] != 0) {
                indice = (indice + i * i) % capacidade;
                i++;
            }

            chaves[indice] = chave;
            valores[indice] = valor;
            tamanho++;
            */
        }
    }

    public Object buscar(int chave) {
        int indice = hash(chave);

        while (chaves[indice] != 0) {
            if (chaves[indice] == chave) {
                return valores[indice];
            }

            indice = (indice + 1) % capacidade;
        }

        return null;
    }

    public Object buscarEnderecamentoAberto(int chave) {
        int indice = hash(chave);
        int i = 1;
        int iteracoes = 0;
        while (chaves[indice] != 0 && iteracoes < capacidade) {
            if (chaves[indice] == chave) {
                return valores[indice];
            }
            indice = (indice + i * i) % capacidade;
            i++;
            iteracoes++;
        }
        throw new RuntimeException("Limite de iterações excedido. Não foi possível encontrar a chave na tabela.");
    }

    public Object remover(int chave) {
        int indice = hash(chave);
        int i = 1;
        int iteracoes = 0;
        while (chaves[indice] != 0 && iteracoes < capacidade) {
            if (chaves[indice] == chave) {
                Object valor = valores[indice];
                chaves[indice] = -1; // Marca o slot como removido
                valores[indice] = null;
                tamanho--;
                return valor;
            }
            indice = (indice + i * i) % capacidade;
            i++;
            iteracoes++;
        }
        throw new RuntimeException("Limite de iterações excedido. Não foi possível encontrar a chave na tabela para remoção.");
    }

    private void redimensionar() {
        int novaCapacidade = capacidade * 2;
        int[] novasChaves = new int[novaCapacidade];
        Object[] novosValores = new Object[novaCapacidade];

        for (int i = 0; i < capacidade; i++) {
            if (chaves[i] != 0) {
                int indice = hash(chaves[i]);

                if (novasChaves[indice] == 0) {
                    novasChaves[indice] = chaves[i];
                    novosValores[indice] = valores[i];
                } else {
                    // Tratamento de colisão por encadeamento
                    int novoIndice = indice;
                    while (novasChaves[novoIndice] != 0) {
                        novoIndice = (novoIndice + 1) % novaCapacidade;
                    }

                    novasChaves[novoIndice] = chaves[i];
                    novosValores[novoIndice] = valores[i];

                    // Tratamento de colisão por endereçamento aberto
                    /*
                    int j = 1;
                    while (novasChaves[indice] != 0) {
                        indice = (indice + j * j) % novaCapacidade;
                        j++;
                    }

                    novasChaves[indice] = chaves[i];
                    novosValores[indice] = valores[i];
                    */
                }
            }
        }

        chaves = novasChaves;
        valores = novosValores;
        capacidade = novaCapacidade;
        tamanhoMaximo = (int) (capacidade * fatorCarga);
    }
}