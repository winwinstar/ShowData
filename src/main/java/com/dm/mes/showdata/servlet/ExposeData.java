package com.dm.mes.showdata.servlet;

import com.dm.mes.showdata.bean.Data;
import com.dm.mes.showdata.util.GetDataFromWebservice;
import com.google.gson.Gson;
import org.ksoap2.serialization.SoapObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/24.
 */
@WebServlet(name = "ExposeData",urlPatterns = "/ExposeData")
public class ExposeData extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        if(request.getParameter("getDataWay") == null || request.getParameter("getDataWay").equals("")){
            return ;
        }
        String getDataWay = request.getParameter("getDataWay");
        SoapObject soapObject = GetDataFromWebservice.GetData(getDataWay);

        if(soapObject.equals("") || soapObject == null){
            return ;
        }

        SoapObject detail1 = (SoapObject) soapObject.getProperty(0);
        SoapObject detail2 = (SoapObject) detail1.getProperty(2);
        SoapObject detail3 = (SoapObject) detail2.getProperty(0);

        List<Data> datas = new ArrayList<Data>();

        for (int i = 0; i <detail3.getPropertyCount(); i++) {
            SoapObject item = (SoapObject) detail3.getProperty(i);
            String x = item.toString().substring(item.toString().indexOf("=")+1,item.toString().indexOf(";"));
            int y = Integer.parseInt(item.toString().substring(item.toString().lastIndexOf("=")+1,item.toString().lastIndexOf(";")));

            Data data = new Data();
            data.setName(x);
            data.setNum(y);
            datas.add(data);
        }

        Gson gson = new Gson();
        String jsonStr = gson.toJson(datas);
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }
}
