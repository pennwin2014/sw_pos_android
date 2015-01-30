package com.supwisdom.penn.sw_pos.httpjersey;

import android.os.AsyncTask;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.supwisdom.penn.sw_pos.httpconnection.TransResp;

import org.apache.http.HttpStatus;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

public class GetTask extends AsyncTask<String, Long, TransResp> {

    private final String url;
    private MultivaluedMap queryParams;
    private int timeout;
    private final Callback<TransResp> callback;

    GetTask(String url, MultivaluedMap queryParams, int timeout, Callback<TransResp> callback) {
        this.url = url;
        this.timeout = timeout;
        this.queryParams = queryParams;
        this.callback = callback;
    }

    @Override
    protected TransResp doInBackground(String... params) {

        TransResp resp = new TransResp();
        try {
            final Client client = Client.create();
            client.setConnectTimeout(timeout < 10 ? 10 * 1000 : timeout * 1000);

            final WebResource resource = client.resource(url);
            if (queryParams != null) {
                resource.queryParams(queryParams);
            }

            final ClientResponse response = resource.accept(MediaType.APPLICATION_JSON)
                    .get(ClientResponse.class);
            if (response.getStatus() == HttpStatus.SC_OK) {
                resp.setRetcode(response.getStatus());
                String retjson = response.getEntity(String.class);
                resp.setRetjson(retjson);
            } else {
                resp.setRetcode(response.getStatus());
                String retmsg = response.getEntity(String.class);
                resp.setRetmsg(retmsg);
            }
        } catch (Exception e) {
            resp.setRetcode(HttpStatus.SC_NOT_FOUND);
            String retmsg = "";
            resp.setRetmsg(retmsg);
        }

        return resp;
    }

    @Override
    protected void onPostExecute(TransResp result) {
        callback.callback(result);
        super.onPostExecute(result);
    }
}
