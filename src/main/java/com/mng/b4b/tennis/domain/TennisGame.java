package com.mng.b4b.tennis.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TennisGame {
    List<TennisPoint> points = new ArrayList<>();
    boolean warning;
    String warningMessage;

    public TennisGame addPoint(TennisPoint point) {
        points.add(point);
        return this;
    }
}
