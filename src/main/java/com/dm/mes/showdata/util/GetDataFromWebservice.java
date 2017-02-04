package com.dm.mes.showdata.util;

import com.dm.mes.showdata.constant.Constant;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.NtlmTransport;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;


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
}
