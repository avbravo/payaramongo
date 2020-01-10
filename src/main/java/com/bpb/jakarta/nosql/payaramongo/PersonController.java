/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bpb.jakarta.nosql.payaramongo;

import com.bpb.jakarta.nosql.payaramongo.mongodb.PersonRepository;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;

import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author avbravo
 */
@Named(value = "personController")
@ViewScoped
public class PersonController implements Serializable {

    private static final long serialVersionUID = 1L;
    List<Person> personList = new ArrayList<>();
    Person person = new Person();

    @Inject
    PersonRepository personRepository;

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @PostConstruct
    public void init() {
        personList = personRepository.findAll();
    }

    /**
     * Creates a new instance of PersonController
     */
    public PersonController() {

    }

    public String findAll() {
        try {

            List<Person> list = personRepository.findAll();
            if (list == null || list.isEmpty()) {
                msg("not records....");

            } else {
                personList = list;
                for (Person p : list) {
                    System.out.println("=========================");
                    System.out.println(p.getName() + " " + p.getAge() + " " + p.getId());
                }
            }

        } catch (Exception e) {
            msg("findAll() " + e.getLocalizedMessage());
        }
        return "";
    }

    public String save() {
        try {
            System.out.println("a guardar...");
            System.out.println("---> " + person.getName());

            if (personRepository.create(person)) {
                Person p = new Person();
                p = person;
                personList.add(p);
                msg("save");
            } else {
                msg(" not save...");
            }

        } catch (Exception e) {
            msg("save() " + e.getLocalizedMessage());
        }
        return "";
    }

    public String delete() {
        try {
            if (personRepository.delete(person.getId())) {
                personList.remove(person);
                msg("Deleted...");
            } else {
                msg("Not Deleted....");
            }

        } catch (Exception e) {
            msg("delete() " + e.getLocalizedMessage());
        }
        return "";
    }

    public String find() {
        try {
         
            person = personRepository.findBy(person.getId());

            if (person == null || person.getId() == null) {
                msg("Not found..");
            } else {
                msg("Found..");
            }
         
        } catch (Exception e) {
            msg("find() " + e.getLocalizedMessage());
        }
        return "";
    }

    private void msg(String mensaje) {
        FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(null, new FacesMessage("Successful", mensaje));
    }

}
