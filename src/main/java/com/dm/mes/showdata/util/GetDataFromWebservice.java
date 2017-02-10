package com.dm.mes.showdata.util;

import com.dm.mes.showdata.bean.Data;
import com.dm.mes.showdata.constant.Constant;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.NtlmTransport;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/2/4.
 */
public class GetDataFromWebservice {

    public static SoapObject GetData(String getDataWay){

        SoapObject soapObject = new SoapObject(Constant.SERVICE_NS,getDataWay);
        NtlmTransport ntlmTransport = new NtlmTransport(Constant.SERVICE_URL);
        ntlmTransport.setCredentials(Constant.username, Constant.password, "", "");
        ntlmTransport.debug = true;
        //使用SOAP1.2协议创建Envelop对象
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        //设置与.Net提供的Web Service保持较好的兼容性
        envelope.dotNet = true;
        envelope.implicitTypes = true;
        envelope.bodyOut = soapObject;
        try {
            ntlmTransport.call(null, envelope);
            SoapObject response = (SoapObject) envelope.bodyIn;
            return response;
        } catch (IOException e) {
            e.printStackTrace();

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Data> ParaseData(SoapObject soapObject){

        if(soapObject.equals("") || soapObject == null){
            return null;
        }

        SoapObject detail1 = (SoapObject) soapObject.getProperty(0);
        SoapObject detail2 = (SoapObject) detail1.getProperty(2);
        SoapObject detail3 = (SoapObject) detail2.getProperty(0);

        List<Data> datas = new ArrayList<Data>();

        for (int i = 0; i <detail3.getPropertyCount(); i++) {
            SoapObject item = (SoapObject) detail3.getProperty(i);
            String x ="";
            String y = "0";
            if(item.getPropertyCount()<2){
                y = item.getProperty(0).toString();
            }else{
                x = item.getProperty(0).toString();
                y = item.getProperty(1).toString();
            }
            Data data = new Data();
            data.setName(x);
            data.setNum(y);
            datas.add(data);
        }
        return datas;
    }
}
