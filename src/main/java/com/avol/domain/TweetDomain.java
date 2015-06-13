package com.avol.domain;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by Lovababu on 6/13/2015.
 */

@Data
public class TweetDomain {

    private Long id;
    private String message;
    private Timestamp time;
}
