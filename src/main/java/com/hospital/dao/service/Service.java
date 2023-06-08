package com.hospital.dao.service;

import java.util.List;

public interface Service<T> {

    T findById(int id);
    List<T> findAll();
    void save(T entity);
    void update(T entity);
    void delete(T entity);
}
