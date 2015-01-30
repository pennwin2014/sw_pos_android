package com.supwisdom.penn.sw_pos.conn_service;

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

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

/**
 * Created by Penn on 2015-01-30.
 */
public class online_consume {

    /**
     * 对外提供的消费接口
     * @param barcode
     * @param amount
     * @return
     */
    public static int online_pay(String barcode, int amount){
        int ret = 0;
        String refno = "";
        ret = online_payinit(barcode, amount, refno);
        if(ret!=0)
            return ret;
        ret = online_payconfirm(refno);
        if(ret!=0)
            return ret;
        return 0;
    }

    /**
     * 联机消费初始化
     * @param barcode 二维码扫出来的串
     * @param amount 金额，分为单位
     * @return
     */
    private static int online_payinit(String barcode, int amount, String refno)
    {
        int retcode = 0;
        MultivaluedMap params = new MultivaluedMapImpl();
        List<NameValuePair> paramspost = new ArrayList<NameValuePair>();
        paramspost.add(new BasicNameValuePair("barcode", barcode));
        paramspost.add(new BasicNameValuePair("amount", Integer.toString(amount)));

        NetworkHandler handler = NetworkHandler.getInstance();
        handler.post(Constants.URI_DOMAIN + "/services/consume/pos/init/", null, paramspost, 15, new com.supwisdom.penn.sw_pos.httpjersey.Callback<TransResp>() {
            @Override
            public void callback(TransResp transResp) {
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


    /**
     * 联机消费确认
     * @param refno
     * @return
     */
    private static int online_payconfirm(String refno){
        return 0;
    }
}
