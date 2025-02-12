package com.dipankarbhatia.HireMe.ConnectionsService.service;

import com.dipankarbhatia.HireMe.ConnectionsService.entity.Person;
import com.dipankarbhatia.HireMe.ConnectionsService.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionsService {

    private final PersonRepository personRepository;

    public List<Person> getFirstDegreeConnectionsOfUser(Long userId){
        log.info("getting first degree connectiions of user with id: " + userId);

        return personRepository.getFirstDegreeConnections(userId);
    }

}
