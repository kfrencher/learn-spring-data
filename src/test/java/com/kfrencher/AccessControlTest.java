package com.kfrencher;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@SpringApplicationConfiguration(classes=TestAccessControlConfig.class)
public class AccessControlTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    StateRepository repository;

    @Test
    public void testAccessControl_WhenFindIsCalled_ExpectStatesMatchingTheFilterToBeReturned() throws Exception {
        String filterName = "Canada";
        State.setFilterName(filterName);
        State expectedState = createState(filterName,"Ontario");
        List<State> states = createStates("US", "Virginia", "North Carolina", "Pennsylvania", "Ohio");
        states.add(expectedState);
        repository.save(states);

        List<State> actualStates = repository.findAll();

        assertThat(actualStates, hasSize(1));
        assertThat(actualStates, hasItem(expectedState));

    }

    @Test
    public void testAccessControl_WhenFindByNameIsCalled_ExpectStatesMatchingTheFilterToBeReturned() throws Exception {
        State.setFilterName("Canada");
        State expectedState = createState("Canada","Michigan");
        List<State> states = createStates("US","Michigan","Virginia", "North Carolina", "Pennsylvania", "Ohio");
        states.add(expectedState);
        repository.save(states);

        List<State> actualStates = repository.findByName("Michigan");

        assertThat(actualStates, hasSize(1));
        assertThat(actualStates, hasItem(expectedState));

    }

    @Test
    public void testAccessControl_WhenNamedQueryIsCalled_ExpectStatesMatchingTheFilterToBeReturned() throws Exception {
        State.setFilterName("Canada");
        State expectedState = createState("Canada","Michigan");
        List<State> states = createStates("US","Michigan","Virginia", "North Carolina", "Pennsylvania", "Ohio");
        states.add(expectedState);
        repository.save(states);

        List<State> actualStates = repository.findCanadienStates();

        assertThat(actualStates, hasSize(1));
        assertThat(actualStates, hasItem(expectedState));

    }

    private List<State> createStates(String country, String... stateNames) {
        return Arrays.asList(stateNames)
                .stream()
                .map(state -> createState(country,state))
                .collect(Collectors.toList());
    }

    private State createState(String country, String name) {
        State state = new State();
        state.setName(name);
        state.setCountry(country);
        return state;
    }

}
