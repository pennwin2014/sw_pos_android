package com.supwisdom.penn.sw_pos.conn_service;

import android.app.AlertDialog;
import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.supwisdom.penn.sw_pos.bean.RetCodeMsgBean;
import com.supwisdom.penn.sw_pos.httpconnection.TransResp;
import com.supwisdom.penn.sw_pos.httpjersey.NetworkHandler;
import com.supwisdom.penn.sw_pos.util.CommonUtil;
import com.supwisdom.penn.sw_pos.util.Constants;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

/**
 * Created by Penn on 2015-01-30.
 */
public class online_consume {
    private static String refno;
    private static String term_id = "01";
    private static String term_no = "10001";
    private static String app_id = "15089039087";
    private static String merchaccno = "09893092";
    private static String sign = "a910bba901d9919c";
    private static String sing_method = "HMAC";
    private Context context;
    public online_consume(Context context){
        this.context =context;
    }
    /**
     * 对外提供的消费接口
     * @param barcode
     * @param amount
     * @return
     */


   /* public int online_pay(String barcode, int amount){
        int ret = 0;
        online_payinit(barcode, amount);



    }*/

    /**
     * 联机消费初始化
     * @param barcode 二维码扫出来的串
     * @param amount 金额，分为单位
     * @return
     */
    public  void online_payinit(String barcode, int amount)
    {
        int retcode = 0;
        MultivaluedMap params = new MultivaluedMapImpl();
        List<NameValuePair> paramspost = new ArrayList<NameValuePair>();
        paramspost.add(new BasicNameValuePair("barcode", barcode));
        paramspost.add(new BasicNameValuePair("amount", Integer.toString(amount)));
        paramspost.add(new BasicNameValuePair("term_id", "01"/*online_consume.term_id*/));
        paramspost.add(new BasicNameValuePair("term_no", online_consume.term_no));
        paramspost.add(new BasicNameValuePair("app_id", online_consume.app_id));
        paramspost.add(new BasicNameValuePair("merchaccno", online_consume.merchaccno));
        paramspost.add(new BasicNameValuePair("sign", online_consume.sign));
        paramspost.add(new BasicNameValuePair("sing_method", online_consume.sing_method));
        //获取时间
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String timestamp = sDateFormat.format(new java.util.Date());
        paramspost.add(new BasicNameValuePair("timestamp", timestamp));
        NetworkHandler handler = NetworkHandler.getInstance();
        handler.post(Constants.URI_DOMAIN + "init/", null, paramspost, 15, new com.supwisdom.penn.sw_pos.httpjersey.Callback<TransResp>() {
            @Override
            public void callback(TransResp transResp) {
                Log.d("retcode", String.valueOf(transResp.getRetcode()));
                if (transResp.getRetcode() == HttpStatus.SC_OK) {
                    String ret = transResp.getRetjson();
                    if (CommonUtil.isEmpty(ret)) {
                        return;
                    }
                    Gson gson = new Gson();
                    RetCodeMsgBean retBean = gson.fromJson(ret, RetCodeMsgBean.class);
                    if (retBean != null) {
                        if ("0".equals(retBean.getRetcode())) {
                            //表示成功
                            online_consume.refno = transResp.toString();
                            online_payconfirm(online_consume.refno);
                            System.out.println("init success");
                            return;
                        } else {
                            Log.d("init fail,ret=", retBean.getRetcode());
                        }
                        return;
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
        });

    }


    /**
     * 联机消费确认
     * @param refno
     * @return
     */
    private  int online_payconfirm(String refno){
        int retcode = 0;
        MultivaluedMap params = new MultivaluedMapImpl();
        List<NameValuePair> paramspost = new ArrayList<NameValuePair>();
        paramspost.add(new BasicNameValuePair("refno", refno));
        NetworkHandler handler = NetworkHandler.getInstance();
        handler.post(Constants.URI_DOMAIN + "confirm/", null, paramspost, 15, new com.supwisdom.penn.sw_pos.httpjersey.Callback<TransResp>() {
            @Override
            public void callback(TransResp transResp) {
                Log.d("retcode1", String.valueOf(transResp.getRetcode()));
                if (transResp.getRetcode() == HttpStatus.SC_OK) {
                    String ret = transResp.getRetjson();
                    if (CommonUtil.isEmpty(ret)) {
                        return;
                    }
                    Gson gson = new Gson();
                    RetCodeMsgBean retBean = gson.fromJson(ret, RetCodeMsgBean.class);
                    if (retBean != null) {
                        if ("0".equals(retBean.getRetcode())) {
                            //表示成功
                            new AlertDialog.Builder(context).setTitle("树维pos").setMessage("消费成功!!")
                                    .setPositiveButton("确定", null).show();
                            return;
                        } else {

                        }
                        return;
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
        });
        return retcode;
    }
}
