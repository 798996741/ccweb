package com.yulun.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

/**
 * @author huang
 * @email
 * @date 2020/09/28
 */
public class DesCrypt {
    private String KEY = "795caaca35536eba43bff4fba1285316";
    private String CODE_TYPE = "UTF-8";

    /**
     * DES加密
     * @param datasource
     * @return
     */
    public String encode(String datasource){
        try{
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(KEY.getBytes(CODE_TYPE));
            //创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            //Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            //用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            //现在，获取数据并加密
            byte[] temp = Base64.encodeBase64(cipher.doFinal(datasource.getBytes()));
            return IOUtils.toString(temp,"UTF-8");
        }catch(Throwable e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * DES解密
     * @return
     */
    public String decode(String src) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom random = new SecureRandom();
        // 创建一个DESKeySpec对象
        DESKeySpec desKey = new DESKeySpec(KEY.getBytes(CODE_TYPE));
        // 创建一个密匙工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        // 将DESKeySpec对象转换成SecretKey对象
        SecretKey securekey = keyFactory.generateSecret(desKey);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, random);
        // 真正开始解密操作
        return IOUtils.toString(cipher.doFinal(Base64.decodeBase64(src)),"UTF-8");
    }


    public static void main(String[] args){
        //String data = "{\"data\":{\"ucode\":\"admin\",\"dialphone\":\"15606063662\",\"upwd\":\"123456\"},\"cmd\":\"DIAL\"}";
        //String data ="{\"cmd\":\"LOGIN\",\"data\":{\"gid\":\"3829322B-ABB5-E089-5C19-B79615AE2DC5\",\"devid\":\"358240051111110\",\"upwd\":\"123456\",\"ucode\":\"admin\"}}";
        //String data="{cmd:'SYNC',data:{devid:'358240051111110',gid:'04eb6c65-0a75-4701-9ec9-ef262a3dce23',uid:'1'}}";
        DesCrypt desCrypt=new DesCrypt();
        //String data="{\"msg\":\"保存成功\",\"data\":\"oImE34xZFV_OG1pDs8_7QOKLj8Is\",\"success\":\"true\"}";
        String data="{\"api\":\"com.yulun.Video\",\"data\":{\"pageIndex\":1,\"pageSize\":10," +
                "\"tokenid\":\"69bb689d78e94d4b90b331ee415bf8ed\",\"stattime\":\"\",\"endtime\":\"\",\"zxid\":\"\"," +
                "\"keywords\":\"\"},\"cmd\":\"videolist\"}";
      //  String key =DesUtil.defaultKey;
        System.out.println("加密前===>"+data);
        try {
            System.out.println(desCrypt.encode(data));
           // System.out.println(decrypt(encrypt(data, key), key));
            //String jiamihou = encrypt(data);
            String jiami="mZOkRbKearmUiGCFxK9cy98mDN5+9ErcUdoPQVtHS4dXyG3s+t4siQ==";
            // jiami="MYVQNQ7h8cs=";
            //System.out.println("加密后===>"+jiamihou);
//            System.out.println("解密后===>"+desCrypt.decode(.toString()));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getKEY() {
        return KEY;
    }

    public void setKEY(String KEY) {
        this.KEY = KEY;
    }

}
