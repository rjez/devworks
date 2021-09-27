package rj.bdt.routing.dto;

import java.util.Objects;

/**
 * Minimalistic REST json data object for country.
 */
public class CountryDto {

    private String cca3;
    private String[] borders;

    public String getCca3() {
        return cca3;
    }

    public void setCca3(String cca3) {
        this.cca3 = cca3;
    }

    public String[] getBorders() {
        return borders;
    }

    public void setBorders(String[] borders) {
        this.borders = borders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryDto that = (CountryDto) o;
        return cca3.equals(that.cca3);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cca3);
    }
}
