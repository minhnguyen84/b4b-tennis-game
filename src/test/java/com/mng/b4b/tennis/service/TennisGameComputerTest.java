package com.mng.b4b.tennis.service;

import com.mng.b4b.tennis.domain.Player;
import com.mng.b4b.tennis.domain.TennisGame;
import com.mng.b4b.tennis.domain.TennisPoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.mng.b4b.tennis.domain.Player.A;
import static com.mng.b4b.tennis.domain.Player.B;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TennisGameComputerTest {

    TennisGameComputer tennisGameComputer = new TennisGameComputer();

    @ParameterizedTest
    @MethodSource("fromString_validJdd")
    void fromString_with_valid_jdd(String input, List<TennisPoint> expected) {
        TennisGame tennisGame = tennisGameComputer.fromString(input);
        assertThat(tennisGame.getPoints()).isEqualTo(expected);
    }

    @Test
    void fromString_with_invalid_jdd() {
        String input = "ABJBA";
        Throwable exception = assertThrows(
                IllegalArgumentException.class, () -> tennisGameComputer.fromString(input)
        );
        assertEquals("Invalid character in game results: J", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("fromPlayerSequence_validJdd")
    void fromPlayerSequence_with_valid_jdd(List<Player> input, List<TennisPoint> expected) {
        TennisGame tennisGame = tennisGameComputer.fromPlayerSequence(input);
        assertThat(tennisGame.getPoints()).isEqualTo(expected);
    }

    @Test
    void fromPlayerSequence_with_tooMuchValues() {
        List<Player> input = List.of(A,B,A,B,A,A,B);
        TennisGame tennisGame = tennisGameComputer.fromPlayerSequence(input);
        assertTrue(tennisGame.isWarning());
        assertEquals("Too much values in game results : [B]", tennisGame.getWarningMessage());
    }

    private static Stream<Arguments> fromPlayerSequence_validJdd() {
        TennisPoint previousPoint = TennisPoint.builder().build();
        return Stream.of(
                Arguments.of(List.of(A,B,A,B,A,A), List.of(
                        previousPoint.toBuilder().AScore(15).build(),
                        previousPoint.toBuilder().AScore(15).BScore(15).build(),
                        previousPoint.toBuilder().AScore(30).BScore(15).build(),
                        previousPoint.toBuilder().AScore(30).BScore(30).build(),
                        previousPoint.toBuilder().AScore(40).BScore(30).build(),
                        previousPoint.toBuilder().AScore(41).BScore(30).winner(A).build())),
                Arguments.of(List.of(A,A,A,A), List.of(
                        previousPoint.toBuilder().AScore(15).build(),
                        previousPoint.toBuilder().AScore(30).build(),
                        previousPoint.toBuilder().AScore(40).build(),
                        previousPoint.toBuilder().AScore(41).winner(A).build())),
                Arguments.of(List.of(B,B,B,B), List.of(
                        previousPoint.toBuilder().BScore(15).build(),
                        previousPoint.toBuilder().BScore(30).build(),
                        previousPoint.toBuilder().BScore(40).build(),
                        previousPoint.toBuilder().BScore(41).winner(Player.B).build())),
                Arguments.of(List.of(A,B,A,B,A,B,A,B,A,A), List.of(
                        previousPoint.toBuilder().AScore(15).build(),
                        previousPoint.toBuilder().AScore(15).BScore(15).build(),
                        previousPoint.toBuilder().AScore(30).BScore(15).build(),
                        previousPoint.toBuilder().AScore(30).BScore(30).build(),
                        previousPoint.toBuilder().AScore(40).BScore(30).build(),
                        previousPoint.toBuilder().AScore(40).BScore(40).build(),
                        previousPoint.toBuilder().AScore(40).BScore(40).AAdvantage(true).build(),
                        previousPoint.toBuilder().AScore(40).BScore(40).build(),
                        previousPoint.toBuilder().AScore(40).BScore(40).AAdvantage(true).build(),
                        previousPoint.toBuilder().AScore(40).BScore(40).AAdvantage(true).winner(A).build()))

        );
    }

    private static Stream<Arguments> fromString_validJdd() {
        TennisPoint previousPoint = TennisPoint.builder().build();
        return Stream.of(
                Arguments.of("ABABABABAA", List.of(
                        previousPoint.toBuilder().AScore(15).build(),
                        previousPoint.toBuilder().AScore(15).BScore(15).build(),
                        previousPoint.toBuilder().AScore(30).BScore(15).build(),
                        previousPoint.toBuilder().AScore(30).BScore(30).build(),
                        previousPoint.toBuilder().AScore(40).BScore(30).build(),
                        previousPoint.toBuilder().AScore(40).BScore(40).build(),
                        previousPoint.toBuilder().AScore(40).BScore(40).AAdvantage(true).build(),
                        previousPoint.toBuilder().AScore(40).BScore(40).build(),
                        previousPoint.toBuilder().AScore(40).BScore(40).AAdvantage(true).build(),
                        previousPoint.toBuilder().AScore(40).BScore(40).AAdvantage(true).winner(A).build()))
        );
    }
}
