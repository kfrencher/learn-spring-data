package com.kfrencher;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StateRepository extends JpaRepository<State, Long> {

	List<State> findByName(String string);

	@Query("select s from State s")
    List<State> findCanadienStates();

}
