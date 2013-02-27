package org.stackexchange.api.client;

import java.io.IOException;
import java.io.InputStream;
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

    public final String questions(final int minScore, final Site site, final int page) {
        final String questionsUri = ApiUris.getQuestionsUri(minScore, site, page);
        logger.debug("Retrieving Questions of site = {} via URI = {}", site.name(), questionsUri);
        return questions(minScore, questionsUri);
    }

    public final String questions(final int min, final String questionsUri) {
        HttpGet request = null;
        try {
            request = new HttpGet(questionsUri);
            final HttpResponse httpResponse = client.execute(request);
            // String contentType = httpResponse.getHeaders(HttpHeaders.CONTENT_TYPE)[0].toString();
            final InputStream entityContentStream = httpResponse.getEntity().getContent();
            final String outputAsEscapedHtml = IOUtils.toString(entityContentStream, Charset.forName("utf-8"));
            return outputAsEscapedHtml;
        } catch (final IOException ex) {
            throw new IllegalStateException(ex);
        } finally {
            if (request != null) {
                request.releaseConnection();
            }
        }
    }

    // non-API

    final String questions(final int min, final Site site) {
        return questions(min, site, 1);
    }

    final HttpResponse questionsAsResponse(final int min, final Site site) {
        try {
            return client.execute(new HttpGet(ApiUris.getQuestionsUri(min, site)));
        } catch (final IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

}
