package com.zhita.controller.operator.demo;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
class EmergencyContact{
    private String contact_name;
    private String contact_phone;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Contact{
    private String contact_name;
    private String contact_phone;
    private String update_time;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class ZhimiRiskRequest {
    private String model_name;
    private String product;
    private String channel;
    private String apply_time;
    private String mobile;
    private String name;
    private String idcard;
    private String phone_os;
    private String user_address;
    private List<EmergencyContact> e_contacts;
    private Map<String, String> carrier_data;
    private List<Contact> contact;
}

public class ZhimiRiskDemo {
    public String getzhimi() {
        for (int i = 0; i < 1; i++){
            String fileContent = null;
            try {
                fileContent = IOUtils.toString(new FileInputStream("sample_data"), StandardCharsets.UTF_8);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JSONObject sampleObject = JSON.parseObject(String.valueOf(fileContent));
            JSONObject responseObject = sampleObject.getJSONObject("wd_api_mobilephone_getdatav2_response");
            JSONObject dataObject = responseObject.getJSONObject("data");
            JSONArray dataListArray = dataObject.getJSONArray("data_list");
            JSONObject userdataObject = dataListArray.getJSONObject(0).getJSONObject("userdata");
            String addr = userdataObject.getString("addr");//地址
            String phone = userdataObject.getString("phone");//电话
            String id_card = userdataObject.getString("id_card");//身份证
            String real_name = userdataObject.getString("real_name");//姓名
            Date t = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String model_name = "dingkun_v1";
            String apply_time = df.format(t);
            String mobile = phone;
            String name = real_name;
            String idcard = id_card;
            String phone_os = "android";
            String user_address = addr;
            List<EmergencyContact> e_contacts = new ArrayList<EmergencyContact>();
            e_contacts.add(new EmergencyContact("张文豪", "18270683860"));
            e_contacts.add(new EmergencyContact("吕文杰", "17764520865"));
            
            
//            String model_name = "dingkun_v1";
//            String apply_time = df.format(t);
//            String mobile = "13486070402";
//            String name = "陈峥";
//            String idcard = "330225199507155112";
//            String phone_os = "android";
//            String user_address = "浙江省宁波市象山县茅洋乡花墙村";
//            List<EmergencyContact> e_contacts = new ArrayList<EmergencyContact>();
//            e_contacts.add(new EmergencyContact("张文豪", "18270683860"));
//            e_contacts.add(new EmergencyContact("吕文杰", "17764520865"));

            Map<String, String> carrier_data = new HashMap<String, String>();
//            JSONObject carrierDataObject = sampleObject.getJSONObject("carrier_data");
//            carrier_data.put("mx_report", JSON.toJSONString(carrierDataObject.getJSONObject("mx_report"), SerializerFeature.WriteMapNullValue));
//            carrier_data.put("mx_raw", JSON.toJSONString(carrierDataObject.getJSONObject("mx_raw"), SerializerFeature.WriteMapNullValue));
//            carrier_data.put("rong360_report", JSON.toJSONString(carrierDataObject.getJSONObject("rong360_report"), SerializerFeature.WriteMapNullValue));
            carrier_data.put("rong360_raw",fileContent);
//
            List<Contact> contact = new ArrayList<Contact>();
//            JSONArray contactArray = sampleObject.getJSONArray("contact");
//            for (int j = 0; j < contactArray.size(); j++) {
//                JSONObject contactItemObject = contactArray.getJSONObject(j);
//                contact.add(new Contact(contactItemObject.getString("contact_name"), contactItemObject.getString("contact_phone"), contactItemObject.getString("update_time")));
//            }
            contact.add(new Contact("张靓雯","15013825354","2018-06-10 09:00:00"));
            ZhimiRiskRequest request = new ZhimiRiskRequest();
            request.setModel_name(model_name);
//            request.setProduct(product);
//            request.setChannel(channel);
            request.setApply_time(apply_time);
            request.setMobile(mobile);
            request.setName(name);
            request.setIdcard(idcard);
            request.setPhone_os(phone_os);
            request.setUser_address(user_address);
            request.setCarrier_data(carrier_data);
            request.setE_contacts(e_contacts);
            request.setContact(contact);

            String requestStr = JSON.toJSONString(request, SerializerFeature.WriteMapNullValue);

//            String url = "http://47.94.221.154:8003/risk/gzip/";
            String url = "http://47.93.185.26/risk/gzip/";
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(url);
            post.setEntity(new ByteArrayEntity(gzip(requestStr)));
            try {
                HttpResponse response = client.execute(post);
                String responseStr = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
                System.out.println(responseStr);
                return responseStr;
            } catch(IOException e){
                e.printStackTrace();
            }
        }
		return null;
    }

    public static byte[] gzip(String str){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = null;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes("UTF-8"));
        } catch (IOException e){
            e.printStackTrace();
        } finally{
            if(gzip != null){
                try{
                    gzip.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return out.toByteArray();
    }
}


