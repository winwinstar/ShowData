package com.dm.mes.showdata.servlet;

import com.dm.mes.showdata.constant.Constant;
import com.dm.mes.showdata.util.GetDataFromWebservice;
import org.ksoap2.serialization.SoapObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2017/2/15.
 */
@WebServlet(name = "RoleUserAction",urlPatterns = "/RoleUserAction")
public class RoleUserAction extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");

        if(request.getParameter("action") == null){
            return ;
        }
        SoapObject soapObject = null;
        String action = request.getParameter("action").toString();
        String parentName = "";
        String actionMethod = "";
        String linkURL = "";
        String childName = "";
        int signal = 0;
        int tran = 0;
        if(action.equals("delRole")){
            actionMethod = Constant.DeleteMenuTransaction_List;
            parentName = request.getParameter("parentName").toString();
            soapObject = new SoapObject(Constant.SERVICE_NS, actionMethod);
            signal = 0;
            tran = 2;
        }
        if(action.equals("addRole")){
            actionMethod = Constant.BuildMenuTransaction_List;
            parentName = request.getParameter("parentName").toString();
            signal = 0;
            linkURL = "";
            tran = 2;
            soapObject = new SoapObject(Constant.SERVICE_NS, actionMethod);
        }
        if(action.equals("addUser")){
            actionMethod = Constant.BuildMenuTransaction_List;
            parentName = request.getParameter("parentName").toString();
            childName = request.getParameter("childName").toString();
            soapObject = new SoapObject(Constant.SERVICE_NS, actionMethod);
            signal = 0;
            linkURL = "";
            tran = 3;
        }
        if(action.equals("delUser")){
            actionMethod = Constant.DeleteMenuTransaction_List;
            parentName = request.getParameter("parentName").toString();
            childName = request.getParameter("childName").toString();
            soapObject = new SoapObject(Constant.SERVICE_NS, actionMethod);
            signal = 0;
            linkURL = "";
            tran = 2;
        }
        if(action.equals("changePwd")){
            actionMethod = Constant.BuildMenuTransaction_List;
            parentName = request.getParameter("parentName").toString();
            childName = request.getParameter("childName").toString();
            soapObject = new SoapObject(Constant.SERVICE_NS, actionMethod);
            signal = 1;
            linkURL = request.getParameter("passWord").toString();
            tran = 3;
        }
        if(action.equals("addAuth")){
            actionMethod = Constant.BuildMenuTransaction_List;
            parentName = request.getParameter("parentName").toString();
            childName = request.getParameter("childName").toString();
            soapObject = new SoapObject(Constant.SERVICE_NS, actionMethod);
            signal = 1;
            linkURL = "";
            tran = 2;
        }
        if(action.equals("cancelAuth")){
            actionMethod = Constant.DeleteMenuTransaction_List;
            parentName = request.getParameter("parentName").toString();
            childName = request.getParameter("childName").toString();
            soapObject = new SoapObject(Constant.SERVICE_NS, actionMethod);
            signal = 1;
            linkURL = "";
            tran = 3;
        }

        soapObject.addProperty("ParentName", parentName);
        soapObject.addProperty("ChildName", childName);
        if(!action.equals("delRole") && !action.equals("delUser") && !action.equals("cancelAuth")){
            soapObject.addProperty("LinkURL",linkURL);
        }
        soapObject.addProperty("FromSYS", "BI");
        if(!action.equals("delRole") && !action.equals("delUser") && !action.equals("cancelAuth")){
            soapObject.addProperty("Signal",signal);
        }
        soapObject.addProperty("TransSignal", tran);
        SoapObject data = GetDataFromWebservice.getData(soapObject);
        SoapObject detail1 = (SoapObject) data.getProperty(0);
        String responseDetail = "";
        try {
            SoapObject detail2 = (SoapObject) detail1.getProperty(2);
            SoapObject detail3 = (SoapObject) detail2.getProperty(0);
            SoapObject item = (SoapObject) detail3.getProperty(0);
            responseDetail = item.getProperty("ReturnMsg").toString().split(":")[0];
        } catch (Exception e) {
            e.printStackTrace();
        }

        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(responseDetail);
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
            this.doGet(request,response);
    }
}
