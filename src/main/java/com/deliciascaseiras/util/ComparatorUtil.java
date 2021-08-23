package com.deliciascaseiras.util;

import com.deliciascaseiras.entity.Produto;

import java.util.Comparator;

//Classe para ordenar as listas de produtos
public class ComparatorUtil {
    //Ordenando por nome, preço e data de criação
    public static class ProdutoNameComparator implements Comparator<Produto> {
        @Override
        public int compare(Produto produto, Produto produto2) {
            return produto.getNome_produto().toLowerCase().compareToIgnoreCase(produto2.getNome_produto().toLowerCase());
        }
    }

    public static class ProdutoPrecoComparator implements Comparator<Produto> {
        @Override
        public int compare(Produto produto, Produto produto2) {
            return Float.compare(produto.getPreco_produto(), produto2.getPreco_produto());
        }
    }

    public static class ProdutoDataComparator implements Comparator<Produto> {
        @Override
        public int compare(Produto produto, Produto produto2) {
            return produto.getData_produto().compareTo(produto2.getData_produto());
        }
    }

}
