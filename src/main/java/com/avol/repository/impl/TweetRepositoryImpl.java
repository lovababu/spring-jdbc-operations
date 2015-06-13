package com.avol.repository.impl;

import com.avol.domain.TweetDomain;
import com.avol.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Lovababu on 6/13/2015.
 */

@Repository
public class TweetRepositoryImpl implements TweetRepository{

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TweetRepositoryImpl(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Long post(final TweetDomain tweetDomain) {
        final String insertQuery = "INSERT INTO TWEET(MESSAGE, TIME) VALUES (?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preparedStatement = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                //preparedStatement.setLong(1, tweetDomain.getId());
                preparedStatement.setString(1, tweetDomain.getMessage());
                preparedStatement.setTimestamp(2, tweetDomain.getTime());
                return preparedStatement;
            }
        }, keyHolder);
        //Keyholder holds the generated key.
        return keyHolder.getKey().longValue();
    }

    @Override
    public TweetDomain get(Long id) {
        final String selectQuery = "SELECT * FROM TWEET WHERE ID= ? ";
        try {
            return (TweetDomain)jdbcTemplate.queryForObject(selectQuery, new Object[]{id}, new RowMapper<TweetDomain>() {
                @Override
                public TweetDomain mapRow(ResultSet rs, int rowNum) throws SQLException {
                    if(rs != null ){
                        TweetDomain tweetDomain = new TweetDomain();
                        tweetDomain.setId(rs.getLong("ID"));
                        tweetDomain.setMessage(rs.getString("MESSAGE"));
                        tweetDomain.setTime(rs.getTimestamp("TIME"));
                        return tweetDomain;
                    } else {
                        return null;
                    }
                }
            });
        } catch (Exception e) {
            //Since QueryForObject throws EmptyResultDataAccessException if no data found.
            return null;
        }
    }

    @Override
    public List<TweetDomain> list() {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM TWEET");
        List<TweetDomain> tweetDomainList = new ArrayList<>();
        TweetDomain tweetDomain = null;
        for (Map<String, Object> row : rows) {
            tweetDomain = new TweetDomain();
            tweetDomain.setId((Long)row.get("ID"));
            tweetDomain.setMessage((String)row.get("MESSAGE"));
            tweetDomain.setTime((Timestamp)row.get("TIME"));
            tweetDomainList.add(tweetDomain);
        }
        return tweetDomainList;
    }

    @Override
    public void update(final TweetDomain tweetDomain) {

        final String updateQuery = "UPDATE TWEET SET MESSAGE = ?, TIME = ? WHERE ID = ?";
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preparedStatement = con.prepareStatement(updateQuery);
                preparedStatement.setString(1, tweetDomain.getMessage());
                preparedStatement.setTimestamp(2, tweetDomain.getTime());
                preparedStatement.setLong(3, tweetDomain.getId());
                return preparedStatement;
            }
        });
    }

    @Override
    public void delete(Long id) {
        final String deleteQuery = "DELETE FROM TWEET WHERE ID = '" + id +"'";
        jdbcTemplate.execute(deleteQuery);
    }
}
