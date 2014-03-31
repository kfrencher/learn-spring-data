package com.kfrencher;

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.LockMetadataProvider;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.util.Assert;

public class AccessControlRepositoryImpl<T, ID extends Serializable> extends
		SimpleJpaRepository<T, ID> implements AccessControlRepository<T, ID> {

	private EntityManager em;
	private LockMetadataProvider lockMetadataProvider;

	public void setLockMetadataProvider(LockMetadataProvider lockMetadataProvider) {
        this.lockMetadataProvider = lockMetadataProvider;
    }

    // There are two constructors to choose from, either can be used.
	public AccessControlRepositoryImpl(Class<T> domainClass,
			EntityManager entityManager) {
		super(domainClass, entityManager);

		// This is the recommended method for accessing inherited class
		// dependencies.
		this.em = entityManager;
	}

	/**
	 * Creates a {@link TypedQuery} for the given {@link Specification} and {@link Sort}.
	 * 
	 * @param spec can be {@literal null}.
	 * @param sort can be {@literal null}.
	 * @return
	 */
	@Override
	protected TypedQuery<T> getQuery(Specification<T> spec, Sort sort) {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(getDomainClass());

		Root<T> root = applySpecificationToCriteria(spec, query);
		query.select(root);
		query.where(builder.equal(root.get("country"), getFilter()));

		if (sort != null) {
			query.orderBy(toOrders(sort, root, builder));
		}

		return applyLockMode(em.createQuery(query));
	}
	
	private String getFilter(){
	    return State.getFilterName();
	}

	/**
	 * Applies the given {@link Specification} to the given {@link CriteriaQuery}.
	 * 
	 * @param spec can be {@literal null}.
	 * @param query must not be {@literal null}.
	 * @return
	 */
	private <S> Root<T> applySpecificationToCriteria(Specification<T> spec, CriteriaQuery<S> query) {

		Assert.notNull(query);
		Root<T> root = query.from(getDomainClass());

		if (spec == null) {
			return root;
		}

		CriteriaBuilder builder = em.getCriteriaBuilder();
		Predicate predicate = spec.toPredicate(root, query, builder);

		if (predicate != null) {
			query.where(predicate);
		}

		return root;
	}

	private TypedQuery<T> applyLockMode(TypedQuery<T> query) {

		LockModeType type = lockMetadataProvider == null ? null : lockMetadataProvider.getLockModeType();
		return type == null ? query : query.setLockMode(type);
	}
}