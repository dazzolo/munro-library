package com.davidebelpanno.munrolibrary.model;

import com.davidebelpanno.munrolibrary.controller.MunroController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
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
        String queryTemplate = "SELECT m FROM Munro m WHERE m.category = ''{0}'' AND m.height <= {1} AND m.height >= {2} ORDER BY {3} {4}";
        String query = MessageFormat.format(queryTemplate, category, Double.toString(maxHeight), Double.toString(minHeight), sortingCriteria, sortingOrder);
        logger.debug("Executing query: " + query);
        return em.createQuery(query).setMaxResults(maxResults).getResultList();
    }

    public List findByHeight(double maxHeight, double minHeight, String sortingCriteria,
            String sortingOrder, int maxResults) {
        String queryTemplate = "SELECT m FROM Munro m WHERE m.category != '''' AND m.height <= {0} AND m.height >= {1} ORDER BY {2} {3}";
        String query = MessageFormat.format(queryTemplate, Double.toString(maxHeight), Double.toString(minHeight), sortingCriteria, sortingOrder);
        logger.debug("Executing query: " + query);
        return em.createQuery(query).setMaxResults(maxResults).getResultList();
    }
}
