package com.example.jack.demo;

import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
//import com.aliyuncs.exceptions.ClientException;
import com.chainshells.safeshells.client.ClientFingerprintManager;

import com.example.idshells.IDshellsApiTest;
//import com.example.idshells;
//import com.idshells.auth.IDshellsApi;

public class MainActivity extends AppCompatActivity {
    private TextView mTextView;
    //private TextView mTextView_token;
    //private Button mBtnGetToken;
    private Button mBtnRegister;
    private Button mBtnAuthenticater;
    //private Button mBtnDeregister;
    private Button mBtnSendMMS;
    private ClientFingerprintManager mManager;
    private ArrayAdapter<String> arrayAdapter;
    private AutoCompleteTextView autoCompleteTextView;
//    private Fingerprint_Register mFR;
//    private Fingerprint_Authenticate mFA;
//    private Fingerprint_Deregister mFD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        String data[] = {"15311446772", "18511868869"};
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        mTextView = findViewById(R.id.text_view);
        //mTextView_token=findViewById(R.id.tv_token);
        mBtnRegister=findViewById(R.id.btn_register);
        mBtnAuthenticater=findViewById(R.id.btn_authenticater);
        //mBtnDeregister=findViewById(R.id.btn_deregister);
        //mBtnGetToken=findViewById(R.id.btn_getToken);
        mBtnSendMMS=findViewById(R.id.btn_sendMNS);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        autoCompleteTextView.setAdapter(arrayAdapter);

        mManager = ClientFingerprintManager.from(this);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SDK version is "+ Build.VERSION.SDK_INT);
        stringBuilder.append("\n");
        stringBuilder.append("isHardwareDetected : "+mManager.isHardwareDetected());
        stringBuilder.append("\n");
        stringBuilder.append("hasEnrolledFingerprints : "+mManager.hasEnrolledFingerprints());
        stringBuilder.append("\n");
        stringBuilder.append("isKeyguardSecure : "+mManager.isKeyguardSecure());
        stringBuilder.append("\n");

        mTextView.setText(stringBuilder.toString());

        mBtnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (mManager.isFingerprintEnable()) {
                    mManager.register(new ClientFingerprintManager.OnBiometricIdentifyCallback_register() {

                        @Override
                        public void onSucceeded(String msg) {

                            //Toast.makeText(MainActivity.this, "onSucceeded", Toast.LENGTH_SHORT).show();
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailed(String msg) {

                            Toast.makeText(MainActivity.this, "onFailed", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(String msg) {

                            Toast.makeText(MainActivity.this, "onError", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancel(String msg) {

                            Toast.makeText(MainActivity.this, "onCancel", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        mBtnAuthenticater.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (mManager.isFingerprintEnable()) {
                    mManager.authenticate(new ClientFingerprintManager.OnBiometricIdentifyCallback_authenticate() {

                        @Override
                        public void onSucceeded(String msg) {
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailed(String msg) {
                            Toast.makeText(MainActivity.this, "onFailed", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(String msg) {
                            Toast.makeText(MainActivity.this, "onError", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancel(String msg) {
                            Toast.makeText(MainActivity.this, "onCancel", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

//        mBtnDeregister.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                if (mManager.isFingerprintEnable()) {
//                    mManager.deregister(new ClientFingerprintManager.OnBiometricIdentifyCallback_deregister() {
//
//                        @Override
//                        public void onSucceeded(String msg) {
//                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onFailed(String msg) {
//                            Toast.makeText(MainActivity.this, "onFailed", Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onError(String msg) {
//                            Toast.makeText(MainActivity.this, "onError", Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onCancel(String msg) {
//                            Toast.makeText(MainActivity.this, "onCancel", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            }
//        });
//        mBtnGetToken.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                getToken();
//            }
//        });
        mBtnSendMMS.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                try {
                    String sn=autoCompleteTextView.getText().toString();
                    if(TextUtils.isEmpty(sn) ){
                        Toast.makeText(MainActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    sendSms(sn);
                    Toast.makeText(MainActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){}
            }
        });
    }
    private void sendSms(String phoneSN) {

        IDshellsApiTest.init();
        //IDshellsApiTest.send(phoneSN);// 初始化
        //String userName = "test-user";
        //IDshellsApiTest.isUserExist(userName);

        //IDshellsApiTest.sendSmsOtp(userName,"18511868869");
    }
//    private void getToken(){
//        mManager.init(new ClientFingerprintManager.OnBiometricIdentifyCallback_init() {
//
//            @Override
//            public void onSucceeded(String msg) {
//                Toast.makeText(MainActivity.this, "get token successfully", Toast.LENGTH_SHORT).show();
//                mTextView_token.setText(msg);
//            }
//
//            @Override
//            public void onFailed(String msg) {
//                Toast.makeText(MainActivity.this, "onFailed", Toast.LENGTH_SHORT).show();
//                mTextView_token.setText("");
//            }
//
//
//        });
//    }
}
