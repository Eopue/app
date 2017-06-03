package com.example.sanstoi.connet;

import com.example.sanstoi.bean.ParentScenic;
import com.example.sanstoi.bean.User;
import com.example.sanstoi.constants.Constants;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by Sans toi on 2016/12/14.
 */

public class ConnectToWebService {

    private String SERVICE_NS = null;
    private String SERVICE_URL = null;
    //创建httpTransportSE传输对象
    private HttpTransportSE ht = null;
    private SoapSerializationEnvelope envelope = null;
    private SoapObject request = null;
    private Gson gson = null;

    private void init(){
        if(SERVICE_NS == null){
            SERVICE_NS = "http://webservice.eopue.com/";
        }
        if(SERVICE_URL == null){
            SERVICE_URL = Constants.url+"/iView/ws/clientService";
        }
        if(ht == null){
            ht = new HttpTransportSE(SERVICE_URL);
            ht.debug = true;
        }
        if(envelope == null) {
            //使用soap1.1协议创建Envelop对象
            envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        }
    }

    public User login(String nickname,String userpwd ) throws Exception {
        init();
        String methodName = "userLogin";
        //实例化SoapObject对象
        request = new SoapObject(SERVICE_NS, methodName);
        /**
         * 设置参数，参数名不一定需要跟调用的服务器端的参数名相同，只需要对应的顺序相同即可
         * */
        request.addProperty("nickname", nickname);
        request.addProperty("userpwd", userpwd);
        //将SoapObject对象设置为SoapSerializationEnvelope对象的传出SOAP消息
        envelope.bodyOut = request;
        //调用webService
        ht.call(null, envelope);
        if (envelope.getResponse() != null) {
            SoapObject soap = (SoapObject) envelope.bodyIn;
            JSONObject obj = new JSONObject(soap.getProperty(0).toString());
            User user = new User();
            user.setNickname(obj.getString("nickname"));
            user.setUserpwd(obj.getString("userpwd"));
            user.setUserId(obj.getInt("userId"));
            if (user!=null) {
                return user;
            }
        }
        return null;
    }

    public boolean saveUser(User user) throws Exception{
        init();
        String methodName = "userSave";
        //实例化SoapObject对象
        request = new SoapObject(SERVICE_NS, methodName);
        /**
         * 设置参数，参数名不一定需要跟调用的服务器端的参数名相同，只需要对应的顺序相同即可
         * */
        gson = new Gson();
        String userinfo = gson.toJson(user);
        request.addProperty("userinfo",userinfo);
        //将SoapObject对象设置为SoapSerializationEnvelope对象的传出SOAP消息
        envelope.bodyOut = request;
        //调用webService
        ht.call(null, envelope);
        if (envelope.getResponse() != null) {
            SoapObject soap = (SoapObject) envelope.bodyIn;
            JSONObject obj = new JSONObject(soap.getProperty(0).toString());
            String result = obj.getString("status");
            if (result.equals("SUCCESS")) {
                return true;
            }
        }
        return false;
    }

    public boolean saveScenic(ParentScenic parentScenic) throws Exception{
        init();
        String methodName = "parentScenicSave";
        //实例化SoapObject对象
        request = new SoapObject(SERVICE_NS, methodName);
        /**
         * 设置参数，参数名不一定需要跟调用的服务器端的参数名相同，只需要对应的顺序相同即可
         * */
        gson = new Gson();
        String scenicinfo = gson.toJson(parentScenic);
        request.addProperty("parentScenicInfo",scenicinfo);
        //将SoapObject对象设置为SoapSerializationEnvelope对象的传出SOAP消息
        envelope.bodyOut = request;
        //调用webService
        ht.call(null, envelope);
        if (envelope.getResponse() != null) {
            SoapObject soap = (SoapObject) envelope.bodyIn;
            JSONObject obj = new JSONObject(soap.getProperty(0).toString());
            String result = obj.getString("status");
            if (result.equals("SUCCESS")) {
                return true;
            }
        }
        return false;
    }

    public JSONArray queryAllParentScenic() throws Exception{
        init();
        String methodName = "parentScenicQueryAll";
        //实例化SoapObject对象
        request = new SoapObject(SERVICE_NS, methodName);
        envelope.bodyOut = request;
        ht.call(null, envelope);
        if (envelope.getResponse() != null) {
            SoapObject soap = (SoapObject) envelope.bodyIn;
            JSONArray obj = new JSONArray(soap.getProperty(0).toString());
            if (null != obj) {
                return obj;
            }
        }
        return null;
    }

    public JSONArray queryChildScenicByParentScenicId(int ID) throws  Exception{
        init();
        String methodName = "childScenicQueryByParentScenicId";
        //实例化SoapObject对象
        request = new SoapObject(SERVICE_NS, methodName);
        /**
         * 设置参数，参数名不一定需要跟调用的服务器端的参数名相同，只需要对应的顺序相同即可
         * */
        gson = new Gson();
        request.addProperty("parentScenicID",ID);
        //将SoapObject对象设置为SoapSerializationEnvelope对象的传出SOAP消息
        envelope.bodyOut = request;
        //调用webService
        ht.call(null, envelope);
        if (envelope.getResponse() != null) {
            SoapObject soap = (SoapObject) envelope.bodyIn;
            JSONArray obj = new JSONArray(soap.getProperty(0).toString());
            if (null != obj) {
                return obj;
            }
        }
        return null;
    }
}
