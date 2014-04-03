package com.kfrencher;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class QueryUtilsTest {

    @Test
    @Parameters({
            "country = 'Canada'",
            "country = 'USA'",
    })
    public void testInsertWhere_WhenThereIsNoWhereClause_ExpectWhereClauseToBeAdded(String predicate) throws Exception {
        String query = "select u from User u";
        String expectedQuery = String.format("%s where %s", query, predicate);

        String actualQuery = QueryUtils.insertPredicate(query, predicate);

        assertThat(actualQuery, is(equalTo(expectedQuery)));
    }

    @Test
    public void testInsertPredicate_WhenThereIsAWhereClause_ExpectPredicateToBeAndedWithWhereClause() throws Exception {
        String predicate = "country = 'Canada'";
        String query = "select u from User u where name = 'Kenneth'";
        String expectedQuery = "select u from User u where country = 'Canada' and (name = 'Kenneth')";

        String actualQuery = QueryUtils.insertPredicate(query, predicate);

        assertThat(actualQuery, is(equalTo(expectedQuery)));
    }

    @Test
    public void testInsertPredicate_WhenThereIsAGroupBy_ExpectPredicateToBeInsertedBeforeGroupBy() throws Exception {
        String predicate = "country = 'Canada'";
        String query = "select u from User u group by name";
        String expectedQuery = "select u from User u where country = 'Canada' group by name";

        String actualQuery = QueryUtils.insertPredicate(query, predicate);

        assertThat(actualQuery, is(equalTo(expectedQuery)));
    }
    
    @Test
    public void testInsertPredicate_WhenThereIsAGroupByAndWhereClause_ExpectPredicateToBeInsertedBetweenGroupByAndWhereClause() throws Exception {
        String predicate = "country = 'Canada'";
        String query = "select u from User u where name = 'John Smith' group by name";
        String expectedQuery = "select u from User u where country = 'Canada' and (name = 'John Smith') group by name";

        String actualQuery = QueryUtils.insertPredicate(query, predicate);

        assertThat(actualQuery, is(equalTo(expectedQuery)));       
    }

}
