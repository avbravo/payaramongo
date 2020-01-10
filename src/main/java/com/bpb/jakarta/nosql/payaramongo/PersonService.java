/*
 * Copyright (c) 2019 IBM Corporation and others
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bpb.jakarta.nosql.payaramongo;

import com.bpb.jakarta.nosql.payaramongo.mongodb.PersonRepository;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/people")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonService {

    @Inject
    PersonRepository personRepository;

  

    @PostConstruct
    public void initPeople() {

    }

    @GET
    public Collection<Person> findAll() {
        return personRepository.findAll();
    }

    @GET
    @Path("/{personId}")
    public Person getPerson(@PathParam("personId") Integer id) {
        Person foundPerson = personRepository.findBy(id);
        if (foundPerson == null) {
            personNotFound(id);
        }
        return foundPerson;
    }

    @POST
    public Integer createPerson(@QueryParam("name") @NotEmpty @Size(min = 2, max = 50) String name,
            @QueryParam("age") @PositiveOrZero int age, @QueryParam("id") @PositiveOrZero int id) {
        Person person = new Person(name, age);
        personRepository.create(person);
        return person.getId();
    }

//    @POST
//    @Path("/{personId}")
//    public void updatePerson(@PathParam("personId") long id, @Valid Person p) {
//        Person toUpdate = getPerson(id);
//        if (toUpdate == null) {
//            personNotFound(id);
//        }
//        personRepo.put(id, p);
//    }

    @DELETE
    @Path("/{personId}")
    public void removePerson(@PathParam("personId") Integer id) {
      Boolean toDelete = personRepository.delete(id);
//        if (toDelete == null) {
//            personNotFound(id);
//        }
//        personRepo.remove(id);
    }

    public void nonJaxrsMethod() {
        System.out.println("This is a non-JAXRS method and should not be accessible with a REST client!");
    }

    private void personNotFound(long id) {
        throw new NotFoundException("Person with id " + id + " not found.");
    }

}
