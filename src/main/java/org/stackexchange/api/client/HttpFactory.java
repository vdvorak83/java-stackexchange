package org.stackexchange.api.client;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
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

        try {
            enableSSLOnHttpClient(cxMgr);
        } catch (KeyManagementException | NoSuchAlgorithmException | UnrecoverableKeyException | KeyStoreException e) {
            throw new IllegalStateException(e);
        }

        logger.info("Created new Http Client; count: " + ++count);
        return rawHttpClient;
    }

    public static final void enableSSLOnHttpClient(final ClientConnectionManager connectionManager) throws NoSuchAlgorithmException, KeyManagementException, UnrecoverableKeyException, KeyStoreException {
        final SSLSocketFactory sf = new SSLSocketFactory(new TrustStrategy() {
            @Override
            public boolean isTrusted(final X509Certificate[] certificate, final String authType) throws CertificateException {
                return true;
            }
        }, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        connectionManager.getSchemeRegistry().register(new Scheme("https", 443, sf));
    }

}
