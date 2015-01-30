package com.supwisdom.penn.sw_pos.httpjersey;

import com.sun.jersey.spi.service.ServiceFinder;
import com.supwisdom.penn.sw_pos.httpconnection.TransResp;

import org.apache.http.NameValuePair;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;


public class NetworkHandler {

    private static NetworkHandler instance;

    public static synchronized NetworkHandler getInstance() {
        if (instance == null) {
            instance = new NetworkHandler();
        }
        return instance;
    }

    @SuppressWarnings("rawtypes")
    private NetworkHandler() {
        ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
    }

    public void get(final String url,final MultivaluedMap queryParams,int timeout, final Callback<TransResp> callback) {
        new GetTask(url,queryParams,15,callback).execute();
    }

    public void post(final String url, final MultivaluedMap queryParams,List<NameValuePair> paramspost,int timeout,final Callback<TransResp> callback) {
        new PostTask(url, queryParams,paramspost, timeout,callback).execute();
    }
}