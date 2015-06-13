package com.avol.service.impl;

import com.avol.domain.TweetDomain;
import com.avol.repository.TweetRepository;
import com.avol.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Lovababu on 6/13/2015.
 */

@Service
public class TweetServiceImpl implements TweetService {

    @Autowired
    private TweetRepository tweetRepository;

    @Override
    public Long post(TweetDomain tweetDomain) {
        return tweetRepository.post(tweetDomain);
    }

    @Override
    public TweetDomain get(Long id) {
        return tweetRepository.get(id);
    }

    @Override
    public List<TweetDomain> list() {
        return tweetRepository.list();
    }

    @Override
    public void update(TweetDomain tweetDomain) {
        tweetRepository.update(tweetDomain);
    }

    @Override
    public void delete(Long id) {
        tweetRepository.delete(id);
    }
}
