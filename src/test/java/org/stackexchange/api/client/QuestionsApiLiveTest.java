package org.stackexchange.api.client;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
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
        client.execute(new HttpGet(ApiConstants.API_2_1 + "/questions?order=desc&sort=activity&site=stackoverflow"), new BasicResponseHandler());
    }

}
