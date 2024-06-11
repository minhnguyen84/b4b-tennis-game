package com.mng.b4b.tennis.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


class CommandLinePrintServiceTest {

    CommandLinePrintService commandLinePrintService = new CommandLinePrintService(new TennisGameComputer());

    @ParameterizedTest
    @MethodSource("fromString_validJdd")
    void fromString_with_valid_jdd(String input, List<String> expectedToString) {
        List<String> results = commandLinePrintService.decodeAndPrint(input);

        assertLinesMatch(expectedToString, results);
    }

    private static Stream<Arguments> fromString_validJdd() {
        return Stream.of(
                Arguments.of("ABABAA", List.of(
                        "Player A : 15 / Player B : 0",
                        "Player A : 15 / Player B : 15",
                        "Player A : 30 / Player B : 15",
                        "Player A : 30 / Player B : 30",
                        "Player A : 40 / Player B : 30",
                        "Player A wins the game")),
                Arguments.of("AAAA", List.of(
                        "Player A : 15 / Player B : 0",
                        "Player A : 30 / Player B : 0",
                        "Player A : 40 / Player B : 0",
                        "Player A wins the game")),
                Arguments.of("BBBB", List.of(
                        "Player A : 0 / Player B : 15",
                        "Player A : 0 / Player B : 30",
                        "Player A : 0 / Player B : 40",
                        "Player B wins the game")),
                Arguments.of("ABABABABAA", List.of(
                        "Player A : 15 / Player B : 0",
                        "Player A : 15 / Player B : 15",
                        "Player A : 30 / Player B : 15",
                        "Player A : 30 / Player B : 30",
                        "Player A : 40 / Player B : 30",
                        "Player A : 40 / Player B : 40",
                        "Player A : Advantage / Player B : 40",
                        "Player A : 40 / Player B : 40",
                        "Player A : Advantage / Player B : 40",
                        "Player A wins the game")),
                Arguments.of("ABABABABABBB", List.of(
                        "Player A : 15 / Player B : 0",
                        "Player A : 15 / Player B : 15",
                        "Player A : 30 / Player B : 15",
                        "Player A : 30 / Player B : 30",
                        "Player A : 40 / Player B : 30",
                        "Player A : 40 / Player B : 40",
                        "Player A : Advantage / Player B : 40",
                        "Player A : 40 / Player B : 40",
                        "Player A : Advantage / Player B : 40",
                        "Player A : 40 / Player B : 40",
                        "Player A : 40 / Player B : Advantage",
                        "Player B wins the game"))
        );
    }
}
