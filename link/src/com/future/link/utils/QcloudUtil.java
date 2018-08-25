package com.future.link.utils;


import com.jfinal.kit.PropKit;
import com.qcloud.sign.FileCloudSign;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


/**
 * Created by zhengbingbing on 2016/3/22.
 */
public class QcloudUtil {

    private static String qcloud_url = PropKit.use("fileUploadConfig.properties").get("qcloud_upload_url"); //InitParam.get(InitParam.QCLOUD_UPLOAD_URL);

    private static String qcloud_appid = PropKit.use("fileUploadConfig.properties").get("qcloud_appid"); //InitParam.get(InitParam.QCLOUD_APPID);

    private static String qcloud_secretid = PropKit.use("fileUploadConfig.properties").get("qcloud_si"); //InitParam.get(InitParam.QCLOUD_SI);

    private static String qcloud_secretkey = PropKit.use("fileUploadConfig.properties").get("qcloud_sk"); //InitParam.get(InitParam.QCLOUD_SK);

    private static String qcloud_bucket = PropKit.use("fileUploadConfig.properties").get("qcloud_bucket"); //InitParam.get(InitParam.QCLOUD_BUCKET);

    /**
     * 万象优图上传接口，参数为图片数据流
     */
    public static String upload(InputStream inputStream, String fileId) {

        try {
            fileId = URLEncoder.encode(fileId, "utf-8");
        } catch (UnsupportedEncodingException e1) {

            e1.printStackTrace();
        }
        //上传路径
        String reqUrl = qcloud_url + qcloud_appid + "/" + qcloud_bucket + "/" + 0 + "/" + fileId;

        String BOUNDARY = "---------------------------abcdefg1234567";

        //万象优图返回的图片唯一标示，在我们系统称为相对路径
        fileId = "";

        String rsp;
        // create sign
        long expired = System.currentTimeMillis() / 1000 + 2592000;
        //鉴权
        String sign = FileCloudSign.appSignV2(Integer.parseInt(qcloud_appid), qcloud_secretid, qcloud_secretkey, qcloud_bucket, expired);
        if (null == sign) {
            return "create app sign failed";
        }
        //数据请求以及返回数据的处理
        try {

            URL realUrl = new URL(reqUrl);
            HttpURLConnection connection = (HttpURLConnection) realUrl
                    .openConnection();
            // set header
            connection.setRequestMethod("POST");
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("Host", "web.image.myqcloud.com");
            connection.setRequestProperty("user-agent", "qcloud-java-sdk");
            connection.setRequestProperty("Authorization", sign);

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            OutputStream out = new DataOutputStream(connection.getOutputStream());
            StringBuilder strBuf = new StringBuilder();
            strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
            strBuf.append("Content-Disposition: form-data; name=\"FileContent\"\r\n\r\n");
            out.write(strBuf.toString().getBytes());

            int bytes;
            byte[] bufferOut = new byte[1024];
            while((bytes = inputStream.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }

            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();

            connection.connect();
            rsp = getResponse(connection);
        } catch (Exception e) {
            return  "url exception, e=" + e.toString();
        }
        try{
            JSONObject jsonObject = new JSONObject(rsp);
            int code = jsonObject.getInt("code");
            String msg = jsonObject.getString("message");
            if(0 != code){
                return  msg;
            }

            //url = jsonObject.getJSONObject("data").getString("url");
            //downloadUrl = jsonObject.getJSONObject("data").getString(
            //               "download_url");
            fileId = jsonObject.getJSONObject("data").getString("fileid");
        }catch(JSONException e){
            return "json exception, e=" + e.toString();
        }
        return  fileId;
    }

    public static String getResponse(HttpURLConnection connection) throws IOException {
        String rsp = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));

        String line;
        while((line = in.readLine()) != null){
            rsp += line;
        }
        return rsp;
    }

    /**
     * 绝对路径
     * @param url
     * @return
     */
    public static String transformToAbsolute(String url){
        String bsUrl = PropKit.use("fileUploadConfig.properties").get("qcloud_down_url");
        return bsUrl+ url;
    }
}
