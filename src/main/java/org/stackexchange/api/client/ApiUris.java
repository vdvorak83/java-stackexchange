package org.stackexchange.api.client;

public class ApiUris {
    private static final String API_2_1 = "https://api.stackexchange.com/2.1";

    private ApiUris() {
        throw new AssertionError();
    }

    // API

    public static String getQuestionsUri() {
        return API_2_1 + "/questions?order=desc&sort=activity&site=stackoverflow";
    }

}
