/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bpb.jakarta.nosql.payaramongo.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import org.bson.Document;

/**
 *
 * @author avbravo
 */
@ApplicationScoped
public class MongodbProducer {

    @Produces
    public MongoClient getClient() {
        MongoClient mongoClient = null;
        System.out.println("intentando conectar");
        try {
            mongoClient = MongoClients.create();
            System.out.println("---> Se conecto");
        } catch (Exception e) {
            System.out.println("no se conecto " + e.getLocalizedMessage());
        }

        return mongoClient;
    }

    @Produces
    MongoCollection<Document> collection() {
        MongoDatabase database = getClient().getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("person");
        return collection;
    }

    public void destroy(@Disposes MongoClient mongoClient) {
        mongoClient.close();
    }
}
