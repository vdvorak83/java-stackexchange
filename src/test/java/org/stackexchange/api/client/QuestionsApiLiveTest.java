package org.stackexchange.api.client;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.Test;

public class QuestionsApiLiveTest {
    private CloseableHttpClient client;

    // fixtures

    @Before
    public final void before() {
        client = HttpClientBuilder.create().build();
    }

    // tests

    @Test
    public final void whenInitialRequestIsPerformed_thenNoExceptions() throws ClientProtocolException, IOException {
        client.execute(new HttpGet(ApiConstants.API_2_1 + "/questions?order=desc&sort=activity&site=stackoverflow"));
    }

    @Test
    public final void whenRequestIsPerformed_thenSuccess() throws ClientProtocolException, IOException {
        final CloseableHttpResponse httpResponse = client.execute(new HttpGet(ApiConstants.API_2_1 + "/questions?order=desc&sort=activity&site=stackoverflow"));
        assertThat(httpResponse.getStatusLine().getStatusCode(), equalTo(200));
    }

}
