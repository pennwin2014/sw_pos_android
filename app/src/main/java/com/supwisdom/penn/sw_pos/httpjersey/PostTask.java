package com.supwisdom.penn.sw_pos.httpjersey;

/**
 * Created by linqing.he on 2015/1/9.
 */

import android.os.AsyncTask;

import com.sun.jersey.api.client.Client;
import com.supwisdom.penn.sw_pos.httpconnection.TransResp;


import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

public class PostTask extends AsyncTask<String, String, TransResp> {

    private final String url;
    private MultivaluedMap queryParams;
    private int timeout;
    private final Callback<TransResp> callback;
    private List<NameValuePair> paramspost;

    PostTask(String url, MultivaluedMap queryParams, List<NameValuePair> paramspost, int timeout, Callback<TransResp> callback) {
        this.url = url;
        this.timeout = timeout;
        this.queryParams = queryParams;
        this.callback = callback;
        this.paramspost = paramspost;
    }

    @Override
    protected TransResp doInBackground(String... params) {
        TransResp resp = new TransResp();
        final Client client = Client.create();
        client.setConnectTimeout(15000);
        client.setReadTimeout(10000);
        /*final WebResource resource = client.resource(url);
        final ClientResponse response = resource.type(MediaType.APPLICATION_FORM_URLENCODED).accept(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, queryParams);*/
        HttpPost post = new HttpPost(url);

        HttpResponse httpResponse = null;
        try {
            if(paramspost!=null){
                post.setEntity(new UrlEncodedFormEntity(paramspost, HTTP.UTF_8));
            }
            post.setHeader("Content-Type", MediaType.APPLICATION_FORM_URLENCODED);

            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeout * 1000);
            HttpConnectionParams.setSoTimeout(httpParameters, timeout * 1000);
            HttpClient httpClient = new DefaultHttpClient(httpParameters);
            /*httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, );
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout * 1000);*/
            httpResponse = httpClient.execute(post);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                resp.setRetcode(httpResponse.getStatusLine().getStatusCode());
                String retjson = EntityUtils.toString(httpResponse.getEntity());
                resp.setRetjson(retjson);
            } else {
                resp.setRetcode(httpResponse.getStatusLine().getStatusCode());
                String retmsg = EntityUtils.toString(httpResponse.getEntity());
                resp.setRetmsg(retmsg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*if (response == null) {
            resp.setRetcode(500);
            resp.setRetmsg("无法连接到服务器");
            return resp;
        }
        if (response.getStatus() == HttpStatus.SC_OK) {
            resp.setRetcode(response.getStatus());
            String retjson = response.getEntity(String.class);
            resp.setRetjson(retjson);
        } else {
            resp.setRetcode(response.getStatus());
            String retjson = response.getEntity(String.class);
            resp.setRetjson(retjson);
        }*/
        return resp;
    }

    @Override
    protected void onPostExecute(TransResp result) {
        callback.callback(result);
        super.onPostExecute(result);
    }
}