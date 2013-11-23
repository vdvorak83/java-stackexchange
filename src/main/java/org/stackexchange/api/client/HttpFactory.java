package org.stackexchange.api.client;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HttpFactory {
    private static final Logger logger = LoggerFactory.getLogger(HttpFactory.class);
    private static int count = 0;

    private HttpFactory() {
        throw new AssertionError();
    }

    // API

    public static CloseableHttpClient httpClient(final boolean followRedirects) {
        final PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(100); // Increase max total connection to 200
        cm.setDefaultMaxPerRoute(20); // Increase default max connection per route to 20

        final RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(60 * 1000).setConnectTimeout(60 * 1000).setSocketTimeout(60 * 1000).build();
        HttpClientBuilder builder = HttpClientBuilder.create().setConnectionManager(cm).setDefaultRequestConfig(requestConfig);
        if (!followRedirects) {
            builder = builder.disableRedirectHandling();
        }
        try {
            configureSSLHandlingNew(builder);
        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException ex) {
            logger.error("Failed to initialize SSL handling.", ex);
        }

        final CloseableHttpClient rawHttpClient = builder.build();

        // configureSSLHandling(rawHttpClient);

        logger.info("Created new Http Client; count: " + ++count);
        return rawHttpClient;
    }

    private static void configureSSLHandlingNew(final HttpClientBuilder builder) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        final SSLContextBuilder sslcb = new SSLContextBuilder();
        sslcb.loadTrustMaterial(KeyStore.getInstance(KeyStore.getDefaultType()), new TrustSelfSignedStrategy());
        builder.setSslcontext(sslcb.build());
    }

}
