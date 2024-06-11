package com.mng.b4b.tennis.service;

import com.mng.b4b.tennis.domain.Player;
import com.mng.b4b.tennis.domain.TennisGame;
import com.mng.b4b.tennis.domain.TennisPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TennisGameComputer {
    private static final String INVALID_PLAYER_ERROR = "Too much values in game results : ";

    public TennisGame fromString(String playersString){
        log.debug("playersString = {}", playersString);
        List<Player> playerSequence = playerFromString(playersString);
        return fromPlayerSequence(playerSequence);
    }

    public TennisGame fromPlayerSequence(List<Player> playerSequence) {
        TennisPoint previousPoint = TennisPoint.builder().build();
        TennisGame tennisGame = new TennisGame();
        log.debug("playerSequence = {}", playerSequence);
        for (int i = 0; i < playerSequence.size(); i++) {
            log.debug("previousPoint = {}", previousPoint);
            previousPoint = calculateScore(previousPoint, playerSequence.get(i));
            tennisGame.addPoint(previousPoint);
            if (previousPoint.getWinner() != null && i < playerSequence.size() - 1) {
                tennisGame.setWarning(true);
                tennisGame.setWarningMessage(INVALID_PLAYER_ERROR + playerSequence.subList(i + 1, playerSequence.size()));
                break;
            }
            log.debug("currentPoint = {}", previousPoint);
        }
        return tennisGame;
    }

    private TennisPoint calculateScore(TennisPoint previousPoint, Player player) {
        TennisPoint.TennisPointBuilder builder = previousPoint.toBuilder();
        if (player == Player.A) {
            if (previousPoint.isAAdvantage()) {
                builder.winner(Player.A);
            } else if (previousPoint.isBAdvantage()) {
                builder.BAdvantage(false);
            } else if (previousPoint.getAScore() == 40 && previousPoint.getBScore() == 40) {
                builder.AAdvantage(true);
            } else {
                int newScore = incrementScore(previousPoint.getAScore());
                builder.AScore(newScore).winner(newScore > 40 ? Player.A : null);
            }
        } else if (player == Player.B) {
            if (previousPoint.isBAdvantage()) {
                builder.winner(Player.B);
            } else if (previousPoint.isAAdvantage()) {
                builder.AAdvantage(false);
            } else if (previousPoint.getAScore() == 40 && previousPoint.getBScore() == 40) {
                builder.BAdvantage(true);
            } else {
                int newScore = incrementScore(previousPoint.getBScore());
                builder.BScore(newScore).winner(newScore > 40 ? Player.B : null);
            }
        }

        return builder.build();
    }

    private List<Player> playerFromString(String playerSequence) {
        List<Player> players = new ArrayList<>();
        for (char p : playerSequence.toCharArray()){
            players.add(Player.fromCharacter(p));
        }
        return players;
    }

    private int incrementScore(int currentScore) {
        return switch (currentScore) {
            case 0 -> 15;
            case 15 -> 30;
            case 30 -> 40;
            case 40 -> 41;
            default -> currentScore;
        };
    }
}
