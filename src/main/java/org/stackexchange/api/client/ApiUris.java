package org.stackexchange.api.client;

import org.stackexchange.api.constants.ApiConstants.Questions;
import org.stackexchange.api.constants.Site;

public class ApiUris {
    private static final String API_2_1 = "https://api.stackexchange.com/2.1";

    private ApiUris() {
        throw new AssertionError();
    }

    // API

    public static String getQuestionsUri(final int min, final Site site) {
        return getUri(min, site, "/questions");
    }

    public static String getTagUri(final int min, final Site site, final String tag) {
        return getUri(min, site, "/tags/" + tag + "/faq");
    }

    static String getUri(final int min, final Site site, final String operation) {
        final String params = new RequestBuilder().add(Questions.order, "desc").add(Questions.sort, "votes").add(Questions.min, min).add(Questions.site, site).build();
        return API_2_1 + operation + params;
    }

}
