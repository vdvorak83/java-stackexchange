package org.stackexchange.api.client;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

public class QuestionsApi {
    private CloseableHttpClient client;

    public QuestionsApi(final CloseableHttpClient client) {
        super();

        this.client = client;
    }

    // API

    public final String questions() {
        try (final CloseableHttpResponse httpResponse = client.execute(new HttpGet(ApiUris.getQuestionsUri()))) {
            return IOUtils.toString(httpResponse.getEntity().getContent());
        } catch (final IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    public final CloseableHttpResponse questionsAsResponse() {
        try {
            return client.execute(new HttpGet(ApiUris.getQuestionsUri()));
        } catch (final IOException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
