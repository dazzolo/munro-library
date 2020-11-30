package com.davidebelpanno.munrolibrary.model;

import com.davidebelpanno.munrolibrary.controller.MunroController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MunroDaoImpl {

    Logger logger = LoggerFactory.getLogger(MunroController.class);

    @PersistenceContext
    private EntityManager em;

    public List<Munro> findByCategoryAndHeight(String category, Double maxHeight, Double minHeight, String sortingCriteria,
            String sortingOrder, Integer maxResults) {
        String query =
                "SELECT m FROM Munro m WHERE m.category = '" + category + "' AND m.height <= " + maxHeight + " AND m.height >= "
                        + minHeight + " ORDER BY " + sortingCriteria + " " + sortingOrder;
        logger.debug("Executing query: " + query);
        return em.createQuery(query).setMaxResults(maxResults).getResultList();
    }

    public List<Munro> findByHeight(Double maxHeight, Double minHeight, String sortingCriteria,
            String sortingOrder, Integer maxResults) {
        String query =
                "SELECT m FROM Munro m WHERE m.category != '' AND m.height <= " + maxHeight + " AND m.height >= "
                        + minHeight + " ORDER BY " + sortingCriteria + " " + sortingOrder;
        logger.debug("Executing query: " + query);
        return em.createQuery(query).setMaxResults(maxResults).getResultList();
    }
}
