package com.deliciascaseiras.service;

public interface ComumUtilService {
    void verifyIfCategoriaExists(Long id);
    void verifyIfProdutoExists(Long id);
    void verifyIfUsuarioExists(Long id);
    void acceptedException(String message);
    void badRequestException(String message);
    void unauthotizedException();
    void verifyIfBeingUsed(Long id);
}
