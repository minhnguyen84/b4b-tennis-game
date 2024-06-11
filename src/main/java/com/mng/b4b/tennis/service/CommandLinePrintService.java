package com.mng.b4b.tennis.service;

import com.mng.b4b.tennis.domain.TennisGame;
import com.mng.b4b.tennis.domain.TennisPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommandLinePrintService {
    private final TennisGameComputer tennisGameComputer;

    public List<String> decodeAndPrint(String gameResult) {
        try {
            TennisGame tennisGame = tennisGameComputer.fromString(gameResult);
            List<String> toPrint = tennisGame.getPoints().stream().map(this::toPrintedString).toList();
            if (tennisGame.isWarning()) {
                toPrint = new ArrayList<>(toPrint);
                toPrint.add("WARNING : " + tennisGame.getWarningMessage());
            }
            return toPrint;
        } catch (Exception exception) {
            return List.of("ERROR :" + exception.getMessage());
        }
    }

    public String toPrintedString(TennisPoint tennisPoint){
        if (tennisPoint.getWinner() != null) {
            return "Player " + tennisPoint.getWinner() + " wins the game";
        }

        if (tennisPoint.isAAdvantage()) {
            return "Player A : Advantage / Player B : " + translateScore(tennisPoint.getBScore());
        }

        if (tennisPoint.isBAdvantage()) {
            return "Player A : " + translateScore(tennisPoint.getAScore()) + " / Player B : Advantage";
        }

        return "Player A : " + translateScore(tennisPoint.getAScore()) + " / Player B : " + translateScore(tennisPoint.getBScore());

    }

    private String translateScore(int score) {
        if (score == 0) {
            return "0";
        } else if (score == 15) {
            return "15";
        } else if (score == 30) {
            return "30";
        } else {
            return "40";
        }
    }
}
