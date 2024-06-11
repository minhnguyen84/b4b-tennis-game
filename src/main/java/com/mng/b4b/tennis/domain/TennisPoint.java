package com.mng.b4b.tennis.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder(toBuilder = true)
@EqualsAndHashCode
public class TennisPoint {
    int AScore;
    int BScore;
    boolean AAdvantage;
    boolean BAdvantage;
    Player winner;
}
