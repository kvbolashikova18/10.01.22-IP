package com.example012.IP;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@SpringBootApplication
public class TennisPlayerApplication implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PlayerDao dao;

    public static void main(String[] args) {
        SpringApplication.run(TennisPlayerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Inserting Player 4: {}", dao.insertPlayer(new Player(4, "Thiem", "Austria", new Date(System.currentTimeMillis()), 17)));

        String Date1 = "02/12/1998";

        dao.createTournamentTable();
        logger.info("French Players: {}", dao.getPlayerByNationality("France"));
        logger.info("Updating Player with Id 1: {}", dao.updatePlayer(
                new Player(4, "Thiem", "Austria", new SimpleDateFormat("dd/MM/yyyy").parse(Date1), 17)));

        logger.info("Deleting Player with Id: 2 {}", dao.deletePlayerById(1));

        logger.info("All Players Data: {}", dao.getAllPlayers());
    }
}