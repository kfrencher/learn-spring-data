package com.kfrencher;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.AccessControlQueryLookupStrategy;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.PersistenceProvider;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.QueryLookupStrategy.Key;

public class AccessControlRepositoryFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable>
        extends JpaRepositoryFactoryBean<R, T, I> {

    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {

        return new AccessControlRepositoryFactory(entityManager);
    }

    private static class AccessControlRepositoryFactory<T, I extends Serializable> extends JpaRepositoryFactory {

        private EntityManager entityManager;
        private PersistenceProvider extractor;

        public AccessControlRepositoryFactory(EntityManager entityManager) {
            super(entityManager);

            this.extractor = PersistenceProvider.fromEntityManager(entityManager);
            this.entityManager = entityManager;
        }

        @Override
        protected Object getTargetRepository(RepositoryMetadata metadata) {

            return new AccessControlRepositoryImpl<T, I>((Class<T>) metadata.getDomainType(), entityManager);
        }

        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {

            // The RepositoryMetadata can be safely ignored, it is used by the
            // JpaRepositoryFactory
            // to check for QueryDslJpaRepository's which is out of scope.
            return AccessControlRepository.class;
        }

        @Override
        protected QueryLookupStrategy getQueryLookupStrategy(Key key) {
            return AccessControlQueryLookupStrategy.create(entityManager, key, extractor);
        }

    }
}