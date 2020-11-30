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

    private Logger logger = LoggerFactory.getLogger(MunroController.class);

    @PersistenceContext
    private EntityManager em;

    public List findByCategoryAndHeight(String category, double maxHeight, double minHeight, String sortingCriteria,
            String sortingOrder, int maxResults) {
        String query =
                "SELECT m FROM Munro m WHERE m.category = '" + category + "' AND m.height <= " + maxHeight + " AND m.height >= "
                        + minHeight + " ORDER BY " + sortingCriteria + " " + sortingOrder;
        logger.debug("Executing query: " + query);
        return em.createQuery(query).setMaxResults(maxResults).getResultList();
    }

    public List findByHeight(double maxHeight, double minHeight, String sortingCriteria,
            String sortingOrder, int maxResults) {
        String query =
                "SELECT m FROM Munro m WHERE m.category != '' AND m.height <= " + maxHeight + " AND m.height >= "
                        + minHeight + " ORDER BY " + sortingCriteria + " " + sortingOrder;
        logger.debug("Executing query: " + query);
        return em.createQuery(query).setMaxResults(maxResults).getResultList();
    }
}
