package org.stackexchange.api.client;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.Test;

public class QuestionsApiLiveTest {
    private QuestionsApi questionsApi;

    // fixtures

    @Before
    public final void before() {
        questionsApi = new QuestionsApi(HttpClientBuilder.create().build());
    }

    // tests

    @Test
    public final void whenInitialRequestIsPerformed_thenNoExceptions() throws ClientProtocolException, IOException {
        questionsApi.questions();
    }

    @Test
    public final void whenRequestIsPerformed_thenSuccess() throws ClientProtocolException, IOException {
        final CloseableHttpResponse httpResponse = questionsApi.questionsAsResponse();
        assertThat(httpResponse.getStatusLine().getStatusCode(), equalTo(200));
    }

    @Test
    public final void whenRequestIsPerformed_thenOutputIsCorrect() throws ClientProtocolException, IOException {
        final String responseBody = questionsApi.questions();
        assertThat(responseBody, notNullValue());
    }

}
