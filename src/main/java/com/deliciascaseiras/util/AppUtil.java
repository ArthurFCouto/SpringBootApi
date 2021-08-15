package com.deliciascaseiras.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

public class AppUtil {

    //Método para transformar uma frase em lista de palavras com mais de duas letras
    public List<String> stringForList(String name) {
        List<String> stringList = new ArrayList<>(); //Criando a lista que será retornada
        String nome = name.replaceAll("\\s|-|;", "");
        //Com o método replaceAll eu substitui todas as ocorrencias de espaços vazios(\\s), traços, ponto e virgula por vazio ("")
        //A barra funciona neste caso como um e/ou
        if (nome.length() >= 1) { //Verificando se a String não está vazia
            String[] stringArray = name.split("\\s|-|;"); //Criando um array/vetor do tipo string
            //O método split, separa uma string em um Array, dividindo pelos regex passados como parametros, no caso, espaço, traço e ponto e virgula
            //A barra funciona neste caso como um e/ou
            for (String stringAux : stringArray) { //Percorrendo a array formada
                if(stringAux.length() >= 2)
                    stringList.add(stringAux); //Adicionando a lista de retorno apenas as palavras da lista que sejam maiores que dois caracteres
            }
        } else {
            stringList.add(nome); //Se a frase estiver vazia a lista é retornada com apenas a frase vazia
        }
        return stringList;
    }

    /* //Método utilizado anteriormente
    public List<String> stringForList(String name) {
        List<String> stringList = new ArrayList<>(); //Criando a lista que será retornada
        String nome = name.replaceAll("\\s|-|;", "");
        //Com o método replaceAll eu substitui todas as ocorrencias de espaços vazios(\\s), traços, ponto e virgula por vazio ("")
        //A barra funciona neste caso como um e/ou
        if (nome.length() >= 1) { //Verificando se a String não está vazia
            String aux = ""; //Criando uma variável auxiliar que irá formar as palavras individualmente
            for (int i = 0; i < name.length(); i++) { //Percorrendo toda a String nome (frase)
                if (name.substring(i, i + 1).equals(" ")) { //Se na posição atual houver espaço vazio, criaremos uma palavra e vamos adicionar a lista de nomes
                    if (aux.length() > 2) { //Adicionando apenas palavras com mais de duas letras
                        stringList.add(aux);
                    }
                    aux = ""; //Após adicionada a variável auxiliar é limpa
                } else { //Enquanto não houver espaços vazios, vai adicionando os caracteres da frase na variável aux
                    aux = aux + name.substring(i, i + 1);
                    if (name.length()-i==1) //Verificando se chegamos na última posição da frase, se sim, adicionamos a última palavra formada
                        stringList.add(aux);
                }
            }

        } else {
            stringList.add(nome); //Se a frase estiver vazia a lista é retornada com apenas a frase vazia
        }
        return stringList;
    } */

    //Método booleano para verificar se a string contém algum valor da lista
    public boolean stringCompareList(String nameToCompare, List<String> listToCompare) {
        for (String aux : listToCompare) {
            if (nameToCompare.toUpperCase().contains(aux.toUpperCase()))
                return true;
        }
        return false;
    }

    //Retorna o Username do usuário logado
    public String userDetailUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    //Retorna a URL base da aplicação
    public String baseURL() {
        return "https://deliciascaseiras.herokuapp.com/";
    }

    //Método para efetuar a validação manual da categoria
    /* //Método utilizado anteriormente
    public void validCategoria(CategoriaProduto categoriaProduto) {
        List<ListError> error = new ArrayList<>(); //Cria uma lista para retornar os erros
        Conditions conditions = new Conditions(); //Cria um acesso a classe que contém os if's
        if(conditions.stringIsEmpty("nome_categoria", categoriaProduto.getNome_categoria()) != null)
            error.add(conditions.stringIsEmpty("nome_categoria", categoriaProduto.getNome_categoria()));
        if(conditions.lengthMaxException("nome_categoria", categoriaProduto.getNome_categoria(), 25) != null)
            error.add(conditions.lengthMaxException("nome_categoria", categoriaProduto.getNome_categoria(), 25));

        List<String> list = new ArrayList<>();
        if(!error.isEmpty()) {
            for (ListError listError : error) {
                list.add(listError.getTitle()+" "+listError.getError());
            }
            String errorString = String.join(",", list);
            throw new BadRequestException(errorString);
        }
    }*/

    //Método para efetuar a validação manual do produto
    /* //Método utilizado anteriormente
    public void validProduto(Produto produto) {
        List<ListError> error = new ArrayList<>();
        Conditions conditions = new Conditions();
        if(conditions.stringIsEmpty("nome_produto", produto.getNome_produto()) != null)
            error.add(conditions.stringIsEmpty("nome_produto", produto.getNome_produto()));
        if(conditions.lengthMaxException("nome_produto", produto.getNome_produto(), 25) != null)
            error.add(conditions.lengthMaxException("nome_produto", produto.getNome_produto(), 25));
        if(conditions.stringIsEmpty("sabor_produto", produto.getSabor_produto()) != null)
            error.add(conditions.stringIsEmpty("sabor_produto", produto.getSabor_produto()));
        if(conditions.lengthMaxException("sabor_produto", produto.getSabor_produto(), 25) != null)
            error.add(conditions.lengthMaxException("sabor_produto", produto.getSabor_produto(), 25));
        if(conditions.floatIsEmpty("preco_produto", produto.getPreco_produto()) != null)
            error.add(conditions.floatIsEmpty("preco_produto", produto.getPreco_produto()));
        if(conditions.lengthMaxException("detalhe_produto", produto.getDetalhe_produto(), 120) != null)
            error.add(conditions.lengthMaxException("detalhe_produto", produto.getDetalhe_produto(), 120));

        if(!error.isEmpty()) {
            String titleString = error.stream().map(ListError::getTitle).collect(Collectors.joining(",")); //Estou pegando os campos de erros e separando por virgula, eram uma lista agora será apenas uma String
            String errorString = error.stream().map(ListError::getError).collect(Collectors.joining(",")); //Estou pegando as menssagens de erros e separando por virgula, eram uma lista agora será apenas uma String
            throw new BadRequestException(titleString + " - " + errorString);
        }
    }*/

    //Método para efetuar a validação manual do usuário
    /* //Método utilizado anteriormente
    public void validUsuario(Usuario usuario) {
        List<ListError> error = new ArrayList<>();
        Conditions conditions = new Conditions();
        String nome_usuario = usuario.getNome_usuario();
        String email_usuario = usuario.getEmail_usuario();
        LocalDate aniversario_usuario = usuario.getAniversario_usuario();
        long telefone_usuario = usuario.getTelefone_usuario();
        String senha_usuario = usuario.getSenha_usuario();

        if(conditions.stringIsEmpty("nome_usuario", nome_usuario) != null)
            error.add(conditions.stringIsEmpty("nome_usuario", nome_usuario));
        if(conditions.lengthMaxException("nome_usuario", nome_usuario, 42) != null)
            error.add(conditions.lengthMaxException("nome_usuario", nome_usuario, 42));
        if(conditions.stringIsEmpty("email_usuario", email_usuario) != null)
            error.add(conditions.stringIsEmpty("email_usuario", email_usuario));
        if(conditions.lengthMaxException("email_usuario", email_usuario, 60) != null)
            error.add(conditions.lengthMaxException("email_usuario", email_usuario, 60));
        if(conditions.localDateValid("aniversario_usuario", aniversario_usuario) != null)
            error.add(conditions.localDateValid("aniversario_usuario", aniversario_usuario));
        if(String.valueOf(telefone_usuario).length() != 11)
            error.add(new ListError("telefone_usuario", "Deve ser no formato xx xxxxx xxxx, apenas números"));
        if(conditions.stringIsEmpty("senha_usuario", senha_usuario) != null)
            error.add(conditions.stringIsEmpty("senha_usuario", senha_usuario));
        if(senha_usuario != null) {
            if(senha_usuario.length()<8)
                error.add(new ListError("senha_usuario", "Senha de no mínimo 8 digitos"));
        }

        List<String> list = new ArrayList<>();
        if(!error.isEmpty()) {
            for (ListError listError : error) {
                list.add(listError.getTitle()+" "+listError.getError());
            }
            String errorString = String.join(",", list); //Transformando uma lista de String em uma String com delimitação de vírgula
            throw new BadRequestException(errorString);
        }
    }*/

    //Classe para testar as condições
    /* //Classes utilizadas anteriormente
    class Conditions {

        public ListError stringIsEmpty(String title, String value) {
            if(value == null || value.trim().length() == 0)
                return new ListError(title, "Não pode ser vazio ou nulo");
            return null;
        }

        public ListError floatIsEmpty(String title, float value) {
            if(value == 0)
                return new ListError(title, "Não pode ser igual a 0");
            return null;
        }

        public ListError localDateValid(String title, LocalDate value) {
            if(value == null)
                return new ListError(title, "Não pode ser vazio");
            if(value.isAfter(LocalDate.now()))
                return new ListError(title, "Deve ser no passado");
            return null;
        }

        public ListError lengthMaxException(String title, String value, int param) {
            if(value != null)
                if(value.length()>param)
                    return new ListError(title, "No máximo "+param+" caracteres");
            return null;
        }
    }

    //Subclasse que servirá de modelo para nossa lista de erros.
    class ListError {
        private String title;
        private String error;

        public ListError(String title, String error) {
            this.title = title;
            this.error = error;
        }

        public String getTitle() {
            return title;
        }

        public String getError() {
            return error;
        }
    }*/
}
