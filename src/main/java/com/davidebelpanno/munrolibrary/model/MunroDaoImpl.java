package com.davidebelpanno.munrolibrary.model;

import org.springframework.stereotype.Repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MunroDaoImpl {

    @PersistenceContext
    private EntityManager em;

    public List<Munro> findByCategoryAndHeight(String category, Double maxHeight, Double minHeight, String sortingCriteria,
            String sortingOrder, Integer maxResults) {
        String query =
                "SELECT m FROM Munro m WHERE category = " + category + " AND m.height <= " + maxHeight + " AND m.height >= "
                        + minHeight + " ORDER BY " + sortingCriteria + " " + sortingOrder;
        return em.createQuery(query).setMaxResults(maxResults).getResultList();
    }

    public List<Munro> findByHeight(Double maxHeight, Double minHeight, String sortingCriteria,
            String sortingOrder, Integer maxResults) {
        String query =
                "SELECT m FROM Munro m WHERE category != '' AND m.height <= " + maxHeight + " AND m.height >= "
                        + minHeight + " ORDER BY " + sortingCriteria + " " + sortingOrder;
        return em.createQuery(query).setMaxResults(maxResults).getResultList();
    }
}
