package com.oskarvos.cityweatherapp.validation.date;

import java.time.LocalDateTime;

public interface OutdatedChecker {

    boolean isOutdated(LocalDateTime createdAt);

}
