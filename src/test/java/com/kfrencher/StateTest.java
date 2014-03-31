package com.kfrencher;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@SpringApplicationConfiguration(classes=TestSimpleJpaConfig.class)
public class StateTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	StateRepository repository;

	@Test
	public void testSaveState() throws Exception {
		State state = createState("Michigan");
		repository.save(state);
		assertThat(state, is(equalTo(repository.findAll().get(0))));
	}
	
	@Test
	public void testFindStateByName_WhenFindingStatesByName_ExpectedOnlyStatesWithTheNameToBeReturned() throws Exception {
		List<State> states = createStates("Virginia","New York", "Georigia");
		State expectedState = createState("Michigan");
		states.add(expectedState);

		repository.save(states);
		
		List<State> actualStates = repository.findByName("Michigan");
		
		assertThat(actualStates, hasItems(expectedState));
		
	}
	
	private List<State> createStates(String... stateNames){
		List<State> states = Arrays.asList(stateNames)
				.stream()
				.map(state -> createState(state))
				.collect(Collectors.toList());
		return states;
	}

	private State createState(String name){
		State state = new State();
		state.setName(name);
		return state;
	}
}
