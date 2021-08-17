package com.example.watchbox.testutils;

public class TestConstants {

    public static final String VALID_ID = "id";
    public static final String VALID_ID_2 = "id2";
    public static final String VALID_POSTER_URL = "https://m.media-amazon.com/images/M/MV5BZGZkMDc1M2QtOWYyYy00ODZjLTk5OWQtOTI5NDVlYTRkY2FiXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg";
    public static final String VALID_CAST = "William Katt, Kay Lenz, George Wendt";
    public static final String VALID_DIRECTOR = "Steve Miner";
    public static final String VALID_WRITERS = "Fred Dekker, Ethan Wiley";
    public static final String VALID_TITLE ="House";
    public static final String VALID_PLOT ="A troubled writer moves into a haunted house after inheriting it from his aunt.";
    public static final String VALID_TYPE = "movie";
    public static final String VALID_RELEASED = "1985";
    public static final String VALID_RATING = "6.2/10";
    public static final String VALID_SHORT_QUERY = "query";
    public static final boolean POSITIVE_FAVOURITES = true;
    public static final boolean NEGATIVE_FAVOURITES = false;
    public static final String ERROR_STRING = "error";
    public static final String ERROR_MESSAGE = "Incorrect IMDb ID.";

    public static final String ERROR_API_RESPONSE = "{\"Response\":\"False\",\"Error\":\"Incorrect IMDb ID.\"}";
    public static final String SUCCESS_SEARCH_API_RESPONSE = "{\"Search\":[{\"Title\":\""+ VALID_TITLE + "\",\"Year\":\""+ VALID_RELEASED +"\",\"imdbID\":\""+ VALID_ID + "\",\"Type\":\""+ VALID_TYPE + "\",\"Poster\":\"" + VALID_POSTER_URL + "\"},{\"Title\":\""+ VALID_TITLE + "\",\"Year\":\""+ VALID_RELEASED +"\",\"imdbID\":\""+ VALID_ID_2 + "\",\"Type\":\""+ VALID_TYPE + "\",\"Poster\":\"" + VALID_POSTER_URL + "\"}],\"totalResults\":\"5222\",\"Response\":\"True\"}";
    public static final String SUCCESS_SINGLE_API_RESPONSE = "{\"Title\":\"House\",\"Year\":\"1985\",\"Rated\":\"R\",\"Released\":\"28 Feb 1986\",\"Runtime\":\"93 min\",\"Genre\":\"Comedy, Fantasy, Horror\",\"Director\":\"Steve Miner\",\"Writer\":\"Fred Dekker, Ethan Wiley\",\"Actors\":\"William Katt, Kay Lenz, George Wendt\",\"Plot\":\"A troubled writer moves into a haunted house after inheriting it from his aunt.\",\"Language\":\"English\",\"Country\":\"United States\",\"Awards\":\"1 win & 4 nominations\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BZGZkMDc1M2QtOWYyYy00ODZjLTk5OWQtOTI5NDVlYTRkY2FiXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg\",\"Ratings\":[{\"Source\":\"Internet Movie Database\",\"Value\":\"6.2/10\"},{\"Source\":\"Rotten Tomatoes\",\"Value\":\"57%\"},{\"Source\":\"Metacritic\",\"Value\":\"44/100\"}],\"Metascore\":\"44\",\"imdbRating\":\"6.2\",\"imdbVotes\":\"24,537\",\"imdbID\":\"tt0091223\",\"Type\":\"movie\",\"DVD\":\"14 Jan 2015\",\"BoxOffice\":\"$19,444,631\",\"Production\":\"New World Pictures, Sean S. Cunningham Films\",\"Website\":\"N/A\",\"Response\":\"True\"}";

    public static final String VALID_JSON_STRING = "{\"JsonParam\":\"StringValue\"}";
    public static final String VALID_JSON_PARAM = "JsonParam";
    public static final String VALID_JSON_VALUE = "StringValue";
}
