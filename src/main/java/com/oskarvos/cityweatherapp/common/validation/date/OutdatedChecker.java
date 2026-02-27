package com.oskarvos.cityweatherapp.common.validation.date;

import java.time.LocalDateTime;

public interface OutdatedChecker {

    boolean isOutdated(LocalDateTime createdAt);

}
