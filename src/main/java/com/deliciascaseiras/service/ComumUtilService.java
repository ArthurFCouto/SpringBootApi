package com.deliciascaseiras.service;

//Interface de serviços uteis que podem ser utilizados de forma genérica
public interface ComumUtilService {
    void verifyIfCategoriaExists(Long id);
    void verifyIfProdutoExists(Long id);
    void verifyIfUsuarioExists(Long id);
    void resourceNotFoundException(String message);
    void badRequestException(String message);
    void forbiddenException();
    void verifyIfBeingUsedCategory(Long id);
}
