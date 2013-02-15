package org.stackexchange.api.client;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

public class QuestionsApi {
    private HttpClient client;

    public QuestionsApi(final HttpClient client) {
        super();

        this.client = client;
    }

    // API

    public final String questions() {
        HttpGet request = null;
        try {
            request = new HttpGet(ApiUris.getQuestionsUri());
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

    public final HttpResponse questionsAsResponse() {
        try {
            return client.execute(new HttpGet(ApiUris.getQuestionsUri()));
        } catch (final IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

}
