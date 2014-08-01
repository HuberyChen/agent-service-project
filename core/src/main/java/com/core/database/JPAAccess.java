package com.core.database;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Map;

public class JPAAccess {

    private EntityManager entityManager;

    public <T> T get(Class<T> entityClass, Object id) {
        return entityManager.find(entityClass, id);
    }

    public void save(Object entity) {
        entityManager.persist(entity);
    }

    public void update(Object entity) {
        entityManager.merge(entity);
    }

    public void delete(Object entity) {
        entityManager.remove(entity);
    }

    public void refresh(Object entity) {
        entityManager.refresh(entity);
    }

    public void detach(Object entity) {
        entityManager.detach(entity);
    }

    public <T> List<T> find(CriteriaQuery<T> query) {
        return entityManager.createQuery(query).getResultList();
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> find(String queryString, Map<String, Object> params) {
        Query query = entityManager.createQuery(queryString);
        if (params != null)
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> find(String queryString, Map<String, Object> params, int offset, int fetchSize) {
        Query query = entityManager.createQuery(queryString);
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        query.setFirstResult(offset);
        query.setMaxResults(fetchSize);
        return query.getResultList();
    }


    public <T> T findUniqueResult(CriteriaQuery<T> query) {
        List<T> results = entityManager.createQuery(query).getResultList();
        return getUniqueResult(results);
    }

    @SuppressWarnings("unchecked")
    public <T> T findUniqueResult(String queryString, Map<String, Object> params) {
        Query query = entityManager.createQuery(queryString);
        if (params != null)
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        List<T> results = query.getResultList();
        return getUniqueResult(results);
    }

    private <T> T getUniqueResult(List<T> results) {
        if (results.isEmpty()) return null;
        if (results.size() > 1) {
            throw new NonUniqueResultException("result returned more than one element, returnedSize=" + results.size());
        }
        return results.get(0);
    }

    public int update(String queryString, Map<String, Object> params) {
        Query query = entityManager.createQuery(queryString);
        if (params != null)
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        return query.executeUpdate();
    }

    public CriteriaBuilder criteriaBuilder() {
        return entityManager.getCriteriaBuilder();
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
