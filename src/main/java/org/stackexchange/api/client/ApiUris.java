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
        return getQuestionsUri(min, site, 1);
    }

    public static String getTagUri(final int min, final Site site, final String tag) {
        return getTagUri(min, site, tag, 1);
    }

    public static String getQuestionsUri(final int min, final Site site, final int page) {
        return getUri(min, site, "/questions", page);
    }

    public static String getTagUri(final int min, final Site site, final String tag, final int page) {
        return getUri(min, site, "/tags/" + tag + "/faq", page);
    }

    static String getUri(final int min, final Site site, final String operation, final int page) {
        final String params = new RequestBuilder().add(Questions.order, "desc").add(Questions.sort, "votes").add(Questions.min, min).add(Questions.site, site).add(Questions.page, page).build();
        return API_2_1 + operation + params;
    }

}
