package com.deliciascaseiras.util;

import com.deliciascaseiras.entity.Produto;

import java.util.Comparator;

public class ComparatorUtil {
    public static class ProdutoNameComparator implements Comparator<Produto> {
        @Override
        public int compare(Produto produto, Produto produto2) {
            return produto.getNome_produto().compareToIgnoreCase(produto2.getNome_produto());
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
