package com.davidebelpanno.munrolibrary.model;

import com.davidebelpanno.munrolibrary.controller.MunroController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MunroDaoImpl {

    @Value("${app.query.filterByCategoryAndHeight}")
    private String filterByCategoryAndHeightQuery;

    @Value("${app.query.filterByHeight}")
    private String filterByHeightQuery;

    private Logger logger = LoggerFactory.getLogger(MunroController.class);

    @PersistenceContext
    private EntityManager em;

    public List findByCategoryAndHeight(String category, double maxHeight, double minHeight, String sortingCriteria,
            String sortingOrder, int maxResults) {
        String query = MessageFormat.format(filterByCategoryAndHeightQuery, category, Double.toString(maxHeight), Double.toString(minHeight), sortingCriteria, sortingOrder);
        logger.debug("Executing query: " + query);
        return em.createQuery(query).setMaxResults(maxResults).getResultList();
    }

    public List findByHeight(double maxHeight, double minHeight, String sortingCriteria,
            String sortingOrder, int maxResults) {
        String query = MessageFormat.format(filterByHeightQuery, Double.toString(maxHeight), Double.toString(minHeight), sortingCriteria, sortingOrder);
        logger.debug("Executing query: " + query);
        return em.createQuery(query).setMaxResults(maxResults).getResultList();
    }
}
