/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.forwardit.dao;

import br.com.forwardit.model.Product;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mauri
 */
@Repository
public class ProductDAO {

    @PersistenceContext
    private EntityManager manager;

    public void save(Product product) {
        manager.persist(product);
    }

    public List<Product> list() {
        return manager.createQuery("select distinct(p) from Product p join fetch p.prices", Product.class).getResultList();
    }

    public Product find(Integer id) {
        TypedQuery<Product> query = manager
                .createQuery(
                        "select distinct(p) from Product p join fetch p.prices where p.id=:id",
                        Product.class).setParameter("id", id);
        return query.getSingleResult();
    }

}
