package com.mng.b4b.tennis.domain;

public enum Player {
    A,
    B;

    private static final String INVALID_PLAYER_ERROR = "Invalid character in game results: ";

    public static Player fromCharacter(char playerChar) {
        return switch (playerChar) {
            case 'A' -> Player.A;
            case 'B' -> Player.B;
            default -> throw new IllegalArgumentException(INVALID_PLAYER_ERROR + playerChar);
        };
    }
}
