package org.springframework.data.jpa.repository.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.parser.PartTree;

public class AccessControlQueryCreator extends JpaQueryCreator {

    public AccessControlQueryCreator(PartTree tree, Class<?> domainClass, CriteriaBuilder builder, ParameterMetadataProvider provider) {
        super(tree, domainClass, builder, provider);
    }

    @Override
    protected CriteriaQuery<Object> complete(Predicate predicate, Sort sort, CriteriaQuery<Object> query, CriteriaBuilder builder, Root<?> root) {
		CriteriaQuery<Object> criteriaQuery = super.complete(predicate, sort, query, builder, root);
        Predicate hasAccess = builder.equal(root.get("country"), "Canada");
		if(predicate == null){
		    return criteriaQuery.where(hasAccess);
		}else{
		    return criteriaQuery.where(builder.and(predicate,hasAccess));
		}
    }

}
