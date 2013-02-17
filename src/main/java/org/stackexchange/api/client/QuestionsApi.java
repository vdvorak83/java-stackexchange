package org.stackexchange.api.client;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.stackexchange.api.constants.Site;

public class QuestionsApi {
    public static final String LINK = "link";
    public static final String TITLE = "title";
    public static final String QUESTION_ID = "question_id";

    private HttpClient client;

    public QuestionsApi(final HttpClient client) {
        super();

        this.client = client;
    }

    // API

    public final String questions(final int min, final Site site) {
        HttpGet request = null;
        try {
            request = new HttpGet(ApiUris.getQuestionsUri(min, site));
            final HttpResponse httpResponse = client.execute(request);
            return IOUtils.toString(httpResponse.getEntity().getContent());
        } catch (final IOException ex) {
            throw new IllegalStateException(ex);
        } finally {
            if (request != null) {
                request.releaseConnection();
            }
        }
    }

    // non-API

    final HttpResponse questionsAsResponse(final int min, final Site site) {
        try {
            return client.execute(new HttpGet(ApiUris.getQuestionsUri(min, site)));
        } catch (final IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

}
