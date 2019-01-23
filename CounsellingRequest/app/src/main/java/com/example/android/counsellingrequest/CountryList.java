package com.example.android.counsellingrequest;

import java.text.Collator;
import java.util.*;

public class CountryList {
    public static ArrayList<Country> getCountryList() {
        // A collection to store our country object
        ArrayList<Country> countries = new ArrayList<Country>();

        // Get ISO countries, create Country object and
        // store in the collection.
        String[] isoCountries = Locale.getISOCountries();
        for (String country : isoCountries) {
            Locale locale = new Locale("en", country);
            String iso = locale.getISO3Country();
            String code = locale.getCountry();
            String name = locale.getDisplayCountry();

            if (!"".equals(iso) && !"".equals(code) && !"".equals(name)) {
                countries.add(new Country(iso, code, name));
            }
        }

        // Sort the country by their name and then return the content
        // of countries collection object.
        Collections.sort(countries, new CountryComparator());

        return countries;
    }

    /**
     * Country class.
     */
    static class Country {
        private String iso;
        private String code;
        private String name;

        Country(String iso, String code, String name) {
            this.iso = iso;
            this.code = code;
            this.name = name;
        }

        public String toString() {
            return iso + " - " + code + " - " + name.toUpperCase();
        }
    }

    /**
     * CountryComparator class.
     */
    private static class CountryComparator implements Comparator<Country> {
        private Comparator<Object> comparator;

        CountryComparator() {
            comparator = Collator.getInstance();
        }

        public int compare(Country c1, Country c2) {
            return comparator.compare(c1.name, c2.name);
        }
    }
}
