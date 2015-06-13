package com.avol.repository;

import com.avol.domain.TweetDomain;

import java.util.List;

/**
 * Created by Lovababu on 6/13/2015.
 */

public interface TweetRepository {

    Long post(TweetDomain tweetDomain);

    TweetDomain get(Long id);

    List<TweetDomain> list();

    void update(TweetDomain tweetDomain);

    void delete(Long id);
}
