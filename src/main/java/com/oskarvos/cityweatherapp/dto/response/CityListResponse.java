package com.oskarvos.cityweatherapp.dto.response;

import java.util.List;
import java.util.Objects;

public class CityListResponse {

    private final List<CityResponse> favorite;
    private final List<CityResponse> noFavorite;

    public CityListResponse(List<CityResponse> favorite, List<CityResponse> noFavorite) {
        this.favorite = favorite;
        this.noFavorite = noFavorite;
    }

    public List<CityResponse> getFavorite() {
        return favorite;
    }

    public List<CityResponse> getNoFavorite() {
        return noFavorite;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CityListResponse that = (CityListResponse) o;
        return Objects.equals(favorite, that.favorite)
                && Objects.equals(noFavorite, that.noFavorite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(favorite, noFavorite);
    }
}
