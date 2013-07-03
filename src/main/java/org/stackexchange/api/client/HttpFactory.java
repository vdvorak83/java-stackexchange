package org.stackexchange.api.client;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.SchemeRegistryFactory;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HttpFactory {
    private static final Logger logger = LoggerFactory.getLogger(HttpFactory.class);
    private static int count = 0;

    private HttpFactory() {
        throw new AssertionError();
    }

    // API

    public static DefaultHttpClient httpClient(final boolean followRedirects) {
        final PoolingClientConnectionManager cxMgr = new PoolingClientConnectionManager(SchemeRegistryFactory.createDefault());
        cxMgr.setMaxTotal(100);
        cxMgr.setDefaultMaxPerRoute(20);

        final HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, 60 * 1000); // connect to
        HttpConnectionParams.setSoTimeout(httpParameters, 60 * 1000); // receive data from
        httpParameters.setParameter("http.connection-manager.timeout", 60 * 1000); // wait for a connection from the pool
        httpParameters.setParameter("http.protocol.handle-redirects", followRedirects);

        final DefaultHttpClient rawHttpClient = new DefaultHttpClient(cxMgr, httpParameters);

        logger.info("Created new Http Client; count: " + ++count);
        return rawHttpClient;
    }

}
