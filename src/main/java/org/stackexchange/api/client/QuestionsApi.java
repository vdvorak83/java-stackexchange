package org.stackexchange.api.client;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stackexchange.api.constants.Site;

public class QuestionsApi {
    private final Logger logger = LoggerFactory.getLogger(getClass());

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
        final String questionsUri = ApiUris.getQuestionsUri(min, site);
        logger.debug("Retrieving Questions of site = {} via URI = {}", site.name(), questionsUri);
        return questions(min, questionsUri);
    }

    public final String questions(final int min, final String questionsUri) {
        HttpGet request = null;
        try {
            request = new HttpGet(questionsUri);
            final HttpResponse httpResponse = client.execute(request);
            // String contentType = httpResponse.getHeaders(HttpHeaders.CONTENT_TYPE)[0].toString();
            final String escapedHtml = IOUtils.toString(httpResponse.getEntity().getContent(), Charset.forName("utf-8"));
            return escapedHtml;
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
