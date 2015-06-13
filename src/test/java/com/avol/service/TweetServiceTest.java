package com.avol.service;

import com.avol.config.AppConfig;
import com.avol.domain.TweetDomain;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Lovababu on 6/13/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class TweetServiceTest {

    @Autowired
    private TweetService tweetService;

    @Test
    public void testPostGetUpdateListDelete(){
        TweetDomain tweetDomain = getTweetDomain("Hello..");
        Long id = tweetService.post(tweetDomain);
        System.out.println("Tweet posted successfully: " + id);
        assertNotNull(id);
        //Get the tweet info.

        TweetDomain dbTweetDomain = tweetService.get(id);
        System.out.println("---------- TWEET INFO After Create ----------");
        System.out.println("ID            :" + dbTweetDomain.getId());
        System.out.println("Message       :" + dbTweetDomain.getMessage());
        System.out.println("Time          :" + dbTweetDomain.getTime());

        //Update TweetInfo.
        dbTweetDomain.setMessage("Hi..");
        tweetService.update(dbTweetDomain);

        //Get Tweet After update.
        dbTweetDomain = tweetService.get(id);
        System.out.println("---------- TWEET INFO After Update----------");
        System.out.println("ID            :" + dbTweetDomain.getId());
        System.out.println("Message       :" + dbTweetDomain.getMessage());
        System.out.println("Time          :" + dbTweetDomain.getTime());

        //Delete Tweet info.
        tweetService.delete(id);
        dbTweetDomain = tweetService.get(id);
        assertNull(dbTweetDomain);
    }

    @Test
    public void testList(){
        TweetDomain tweetDomain = getTweetDomain("Its cool.");
        tweetService.post(tweetDomain);
        tweetDomain = getTweetDomain("Feeling happy..");
        tweetService.post(tweetDomain);
        tweetDomain = getTweetDomain("Awesome day..");
        tweetService.post(tweetDomain);

        //Get Tweet After update.
        List<TweetDomain> list = tweetService.list();
        System.out.println("---------- TWEETS INFO----------");
        for (TweetDomain dbTweetDomain: list) {
            System.out.println("ID            :" + dbTweetDomain.getId());
            System.out.println("Message       :" + dbTweetDomain.getMessage());
            System.out.println("Time          :" + dbTweetDomain.getTime());
            System.out.println("------------------------------------------");
        }

    }

    private TweetDomain getTweetDomain(String s) {
        TweetDomain tweetDomain = new TweetDomain();
        tweetDomain.setMessage(s);
        tweetDomain.setTime(new Timestamp(System.currentTimeMillis()));
        return tweetDomain;
    }
}
