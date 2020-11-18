package cn.edu.gdut.douyintoutiao.view.user.resign;

import android.os.StrictMode;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import cn.edu.gdut.douyintoutiao.entity.Result;
import cn.edu.gdut.douyintoutiao.entity.User;
import cn.edu.gdut.douyintoutiao.net.UserApi;
import cn.edu.gdut.douyintoutiao.util.EncrypRSA;
import retrofit2.Call;
import retrofit2.Response;

/**
 * @author hudp
 */
public class ResignModel {
    private static ResignModel instance;
    private static final String TAG = "loginUserModel";
    private UserApi api;

    public static ResignModel getInstance(){
        if(instance == null){
            instance = new ResignModel();
        }
        return instance;
    }


    //检查该用户是否注册过
    public MutableLiveData<Result<User>> check_user(User user){
        //解决网络通讯不能在主线程运行的问题
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        //调用api
        api = UserApi.getUserApi();
        Call<Result<User>> check = api.check_Resign(user);
        MutableLiveData<Result<User>> mutableLiveData = new MutableLiveData<>();
        Log.d(TAG, "注册数据: " + user.toString());
        //开始调用
        try {
            Response<Result<User>> response = check.execute();
            mutableLiveData.setValue(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mutableLiveData;
    }

    //插入该用户的注册数据
    public MutableLiveData<Result<User>> insert_user(User user){
        //解决网络通讯不能在主线程运行的问题
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        //调用api
        api = UserApi.getUserApi();
        Call<Result<User>> insert= api.insertUser(user);
        MutableLiveData<Result<User>> mutableLiveData = new MutableLiveData<>();
        Log.d(TAG, "数据: " + user.toString());
        //开始调用
        try {
            Response<Result<User>> response = insert.execute();
            mutableLiveData.setValue(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mutableLiveData;
    }
    public byte[] encrypt(String password){
        EncrypRSA rsa = new  EncrypRSA();
        byte[] resultBytes = new byte[0];
        String msg = password ;
        try {
            //KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
            KeyPairGenerator keyPairGen = null;
            keyPairGen = KeyPairGenerator.getInstance("RSA" );
            //初始化密钥对生成器，密钥大小为1024位
            keyPairGen.initialize(1024 );
            //生成一个密钥对公钥和私钥，保存在keyPair中
            KeyPair keyPair = keyPairGen.generateKeyPair();
            //得到公钥
            RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
            //用公钥加密
            byte [] srcBytes = msg.getBytes("UTF-8");
            resultBytes = rsa.encrypt(publicKey, srcBytes);

            Log.d(TAG, "明文是: " + msg);
            Log.d(TAG, "加密后是: " + resultBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return resultBytes;
    }
    public String decrypt(byte[] password){
        EncrypRSA rsa = new  EncrypRSA();
        byte[] msg = password ;
        try {
            //KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
            KeyPairGenerator keyPairGen = null;
            keyPairGen = KeyPairGenerator.getInstance("RSA" );
            //初始化密钥对生成器，密钥大小为1024位
            keyPairGen.initialize(1024 );
            //生成一个密钥对公钥和私钥，保存在keyPair中
            KeyPair keyPair = keyPairGen.generateKeyPair();
            //得到私钥
            RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();

            //用私钥解密
            byte [] decBytes = rsa.decrypt(privateKey,password);
            Log.d(TAG, "密文是: " + msg);
            Log.d(TAG, "解密后是: " + new  String(decBytes));
            return new  String(decBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
