/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bpb.jakarta.nosql.payaramongo.mongodb;

import com.bpb.jakarta.nosql.payaramongo.Person;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import org.bson.Document;

/**
 *
 * @author avbravo
 */
@Stateless
public class PersonRepository {

    @Inject
    MongoCollection<Document> collection;

    public Boolean create(Person person) {
        try {

            Document document = new Document().append("name", person.getName()).append("id", person.getId()).append("age", person.getAge());
            collection.insertOne(document);
            return true;
        } catch (Exception e) {
            System.out.println(" create() " + e.getLocalizedMessage());
        }
        return false;

    }

    public List<Person> findAll() {
        List<Person> list = new ArrayList<>();
        try {

            FindIterable<Document> iterable = collection.find();
            list = convert(iterable);
            return list;
        } catch (Exception e) {
            System.out.println(" findAll() " + e.getLocalizedMessage());
        }
        return list;

    }

    public Person findBy(Integer id) {
        List<Person> list = new ArrayList<>();
        Person person = new Person("", 0);
        try {

            FindIterable<Document> iterable = collection.find(eq("id", id));
            list = convert(iterable);
            person = list.get(0);

        } catch (Exception e) {
            System.out.println(" findBy " + e.getLocalizedMessage());
        }
        return person;

    }

    public Boolean delete(Integer id) {
        try {

            Document document = new Document().append("id", id);
            collection.deleteOne(document);
            return true;
        } catch (Exception e) {
            System.out.println(" delete() " + e.getLocalizedMessage());
        }
        return false;

    }

    public List<Person> convert(FindIterable<Document> iterable) {
        List<Person> list = new ArrayList<>();
        try {

            MongoCursor<Document> cursor = iterable.iterator();
//Use JsonB
            Jsonb jsonb = JsonbBuilder.create();
            while (cursor.hasNext()) {
                Person fromJson = jsonb.fromJson(cursor.next().toJson(), Person.class);
                list.add(fromJson);
            }

            return list;
        } catch (Exception e) {
            System.out.println(" convert() " + e.getLocalizedMessage());
        }
        return list;

    }

}
