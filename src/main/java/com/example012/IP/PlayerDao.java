package com.example012.IP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;


@Repository
public class PlayerDao {

    private static final class PlayerMapper implements RowMapper<Player> {

        @Override
        public Player mapRow(ResultSet rs, int rowNum) throws SQLException {
            Player player = new Player();
            player.setId(rs.getInt("id"));
            player.setName(rs.getString("name"));
            player.setBirthDate(rs.getTime("birth_date"));
            player.setNationality(rs.getString("nationality"));
            player.setTitles(rs.getInt("titles"));
            return player;
        }
    }

    public List<Player> getPlayerByNationality(String nationality) {
        String sql = "SELECT * FROM PLAYER WHERE NATIONALITY = ?";
        return jdbcTemplate.query(sql, new PlayerMapper(), new Object[] {nationality});
    }

    @Autowired
    JdbcTemplate jdbcTemplate;
    public List<Player> getAllPlayers() {
        String sql = "SELECT * FROM PLAYER";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Player>(Player.class));
    }
    public Player getPlayerById(int id){
        String sql = "SELECT * FROM PLAYER WHERE ID = ?";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Player>(Player.class), new Object[] {id});
    }

    public void createTournamentTable() {
        String sql = "CREATE TABLE TOURNAMENT (ID INTEGER, NAME VARCHAR(50), LOCATION VARCHAR(50), PRIMARY KEY (ID))";

        jdbcTemplate.execute(sql);
        System.out.println("Table created.");
    }

    public int insertPlayer(Player player){
        String sql = "INSERT INTO PLAYER (ID, Name, Nationality, Birth_date, Titles) " + "VALUES (?,?,?,?,?)";
        return jdbcTemplate.update(sql , new Object[] {player.getId(), player.getName(),player.getNationality(), new Timestamp(player.getBirthDate().getTime()),player.getTitles()});
    }

    public int updatePlayer(Player player){
        String sql = "UPDATE PLAYER " + "SET Name = ?, Nationality = ?, Birth_date = ?, Titles = ? " + "WHERE ID = ?";

        return jdbcTemplate.update(sql , new Object[]{
                player.getName(),
                player.getNationality(),
                new Timestamp(player.getBirthDate().getTime()),
                player.getTitles(),
                player.getId()
        });

    }
    public int deletePlayerById(int id){
        String sql = "DELETE FROM PLAYER WHERE ID = ?";
        return jdbcTemplate.update(sql, new Object[]{id});
    }
}