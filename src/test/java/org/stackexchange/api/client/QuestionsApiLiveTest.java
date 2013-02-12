package org.stackexchange.api.client;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

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
    public final void whenRequestIsPerformed_thenOutputIsJson() throws ClientProtocolException, IOException {
        final CloseableHttpResponse httpResponse = questionsApi.questionsAsResponse();
        assertThat(httpResponse.getHeaders(HttpHeaders.CONTENT_TYPE)[0].getValue(), containsString("application/json"));
    }

    @Test
    public final void whenRequestIsPerformed_thenOutputIsCorrect() throws ClientProtocolException, IOException {
        final String responseBody = questionsApi.questions();
        assertThat(responseBody, notNullValue());
    }

    @Test
    public final void whenParsingOutputFromQuestionsApi_thenOutputIsParsable() throws ClientProtocolException, IOException {
        final String questionsAsJson = questionsApi.questions();
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode rootNode = mapper.readTree(questionsAsJson);
        final ArrayNode questionsArray = (ArrayNode) rootNode.get("items");
        System.out.println(questionsArray.get(0));
    }

}
