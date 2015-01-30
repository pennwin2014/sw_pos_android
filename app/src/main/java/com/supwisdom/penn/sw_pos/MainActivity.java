package com.supwisdom.penn.sw_pos;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.supwisdom.penn.sw_pos.conn_service.online_consume;


public class MainActivity extends ActionBarActivity {
    EditText edt_amount;
    Button btn_consume;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edt_amount = (EditText)findViewById(R.id.edit_amount);
        btn_consume = (Button)findViewById(R.id.btn_consume);
    }

    public void clickHandler(View v){
        if(btn_consume == v) {
            Intent intent = new Intent(this, QRCameraActivity.class);
            //startActivity(intent);
            startActivityForResult(intent, 1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id==R.id.action_saoma){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        int ret;
        //表示收到二维码扫描的结果
        if(resultCode == 1){
            String barcode = data.getStringExtra("barcode");
            int amount = Integer.valueOf(edt_amount.getText().toString());
            ret = online_consume.online_pay(barcode, amount);
            if(ret!=0) {
                new AlertDialog.Builder(this).setTitle("树维pos").setMessage("消费失败!!")
                        .setPositiveButton("确定", null).show();
            }
            else if(0 == ret) {
                new AlertDialog.Builder(this).setTitle("树维pos").setMessage("消费成功!!")
                        .setPositiveButton("确定", null).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
