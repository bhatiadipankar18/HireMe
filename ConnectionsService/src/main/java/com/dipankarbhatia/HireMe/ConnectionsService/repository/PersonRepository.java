package com.dipankarbhatia.HireMe.ConnectionsService.repository;

import com.dipankarbhatia.HireMe.ConnectionsService.entity.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends Neo4jRepository<Person, Long> {

    Optional<Person> findByUserId(Long userId);

    @Query("match (personA:PersonB - [:CONNECTED_TO]- (personB:Person) " +
    "where personA.userId = $userId " +
            "retrun personB")
    List<Person> getFirstDegreeConnections(Long userId);
}
