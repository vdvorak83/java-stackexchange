package org.stackexchange.api.client;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.SchemeRegistryFactory;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public final class HttpFactory {

    private HttpFactory() {
        throw new AssertionError();
    }

    // API

    public static DefaultHttpClient httpClient() {
        return httpClient(true);
    }

    public static DefaultHttpClient httpClient(final boolean followRedirects) {
        final PoolingClientConnectionManager cxMgr = new PoolingClientConnectionManager(SchemeRegistryFactory.createDefault());
        cxMgr.setMaxTotal(100);
        cxMgr.setDefaultMaxPerRoute(20);

        final HttpParams httpParameters = new BasicHttpParams();
        final int timeoutConnection = 60000;
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        final int timeoutSocket = 60000;
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
        httpParameters.setParameter("http.protocol.handle-redirects", followRedirects);

        final DefaultHttpClient rawHttpClient = new DefaultHttpClient(cxMgr, httpParameters);
        return rawHttpClient;
    }

}
