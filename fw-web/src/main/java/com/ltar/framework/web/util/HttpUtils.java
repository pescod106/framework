package com.ltar.framework.web.util;

import com.ltar.framework.web.constant.HttpStatusEnum;
import com.ltar.framework.web.dto.HttpResponseEntity;
import lombok.NoArgsConstructor;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/14
 * @version: 1.0.0
 */
@NoArgsConstructor
public class HttpUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);

    private static PoolingHttpClientConnectionManager connectionManager;
    private static RequestConfig requestConfig;

    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private static final Charset GBK = Charset.forName("GBK");

    private static final String HTTPS = "https";

    private static String path;

    static {
        connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(50);
        connectionManager.setDefaultMaxPerRoute(5);

        requestConfig = RequestConfig.custom()
                .setConnectTimeout(5 * 1000)
                .setConnectionRequestTimeout(1 * 1000)
                .setSocketTimeout(25 * 1000)
                .build();

        File location = new File(HttpUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        path = location.isDirectory() ? location.getAbsolutePath() : location.getParentFile().getAbsolutePath();
    }

    /**
     * http post请求
     *
     * @param url           请求URL，不能为空
     * @param headers       请求header 可以为空
     * @param params        请求参数 可以为空
     * @param encodeCharset 编码字符 可以为空
     * @param decodeCharset 解码字符 可以为空
     * @return
     */
    public static HttpResponseEntity sendPostRequest(@NonNull String url,
                                                     Map<String, String> headers, Map<String, String> params,
                                                     Charset encodeCharset, Charset decodeCharset) {
        return execute(generateHttpPost(url, params, headers, encodeCharset), decodeCharset, null, null);
    }

    /**
     * https post请求
     *
     * @param url           请求URL，不能为空
     * @param headers       请求header 可以为空
     * @param params        请求参数 可以为空
     * @param encodeCharset 编码字符 可以为空
     * @param decodeCharset 解码字符 可以为空
     * @param keyStorePath  证书路径，相对于类路径，不能为空
     * @param keyPassword   证书密码，不能为空
     * @return
     */
    public static HttpResponseEntity sendPostRequest(String url,
                                                     Map<String, String> headers, Map<String, String> params,
                                                     Charset encodeCharset, Charset decodeCharset,
                                                     @NonNull String keyStorePath, @NonNull String keyPassword) {
        if (url.startsWith(HTTPS)) {
            if (StringUtils.isEmpty(keyStorePath) || StringUtils.isEmpty(keyPassword)) {
                throw new IllegalArgumentException("keyStorePath and keyPassword can not be null");
            }
            return execute(generateHttpPost(url, params, headers, encodeCharset), decodeCharset, keyStorePath, keyPassword);
        } else {
            throw new IllegalArgumentException("url must be start with https, please check!");
        }


    }

    public static HttpResponseEntity sendGetRequest(String url) {
        return sendGetRequest(url, null, null, UTF_8);
    }

    public static HttpResponseEntity sendGetRequest(String url, Map<String, String> params) {
        return sendGetRequest(url, params, null, UTF_8);
    }

    public static HttpResponseEntity sendGetRequest(String url, Map<String, String> params, Map<String, String> headers, Charset decodeCharset) {
        URIBuilder ub = new URIBuilder();
        ub.setPath(url);
        if (null != params) {
            List<NameValuePair> pairs = convertMap2NVPS(params);
            ub.setParameters(pairs);
        }
        HttpResponseEntity responseEntity = new HttpResponseEntity();
        HttpGet httpGet = null;
        try {
            httpGet = new HttpGet(ub.build());
        } catch (URISyntaxException e) {
            LOGGER.error(e.getMessage(), e);
        }
        httpGet.setConfig(requestConfig);
        if (null != headers) {
            Header[] headerArr = new Header[headers.size()];
            convertMap2NVPS(headers).toArray(headerArr);
            httpGet.setHeaders(headerArr);
        }
        return execute(httpGet, decodeCharset, null, null);
    }

    private static CloseableHttpClient getHttpClient()
            throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            public boolean isTrusted(X509Certificate[] chain, String authType) {
                return true;
            }
        }).build();

        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setSSLSocketFactory(sslsf)
                .build();
        return httpClient;
    }

    private static CloseableHttpClient getHttpClient(String keyStorePath, String keyPwd)
            throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
        InputStream in = null;
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            in = new FileInputStream(new File(path + keyStorePath));
            keyStore.load(in, keyPwd.toCharArray());
            SSLContext sslContext = new SSLContextBuilder()
                    .loadKeyMaterial(keyStore, keyPwd.toCharArray())
                    .build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            CloseableHttpClient httpClient = HttpClients.custom()
                    .setConnectionManager(connectionManager)
                    .setSSLSocketFactory(sslsf)
                    .build();
            return httpClient;
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }
    }

    private static HttpResponseEntity execute(HttpRequestBase request, Charset charset, String keyStorePath, String keyPwd) {
        CloseableHttpClient httpClient = null;
        HttpResponseEntity responseEntity = new HttpResponseEntity();
        try {
            httpClient = StringUtils.isEmpty(keyStorePath) || StringUtils.isEmpty(keyPwd) ? getHttpClient() : getHttpClient(keyStorePath, keyPwd);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return responseEntity;
        }
        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(request);

            responseEntity.setSuccess(true);
            responseEntity.setStatusCode(HttpStatusEnum.valueOf(response.getStatusLine().getStatusCode()));
            responseEntity.setHeaders(response.getAllHeaders());
            HttpEntity httpEntity = response.getEntity();
            String content = null;
            if (null != httpEntity) {
                content = EntityUtils.toString(httpEntity, null == charset ? UTF_8 : charset);
                EntityUtils.consume(httpEntity);
            }
            responseEntity.setBody(content);

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
        return responseEntity;
    }

    private static HttpPost generateHttpPost(String url, Map<String, String> params, Map<String, String> headers, Charset encodeCharset) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        if (null != headers) {
            Header[] headerArr = new Header[headers.size()];
            convertMap2NVPS(headers).toArray(headerArr);
            httpPost.setHeaders(headerArr);
        }

        if (null != params) {
            httpPost.setEntity(new UrlEncodedFormEntity(convertMap2NVPS(params),
                    null == encodeCharset ? UTF_8 : encodeCharset));
        }
        return httpPost;
    }

    private static List<NameValuePair> convertMap2NVPS(Map<String, String> params) {
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> param : params.entrySet()) {
            pairs.add(new BasicNameValuePair(param.getKey(), param.getValue()));
        }
        return pairs;
    }
}