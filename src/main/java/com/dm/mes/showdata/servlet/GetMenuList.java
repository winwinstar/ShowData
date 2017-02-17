package com.dm.mes.showdata.servlet;

import com.dm.mes.showdata.bean.AuthItem;
import com.dm.mes.showdata.constant.Constant;
import com.dm.mes.showdata.util.GetDataFromWebservice;
import com.google.gson.Gson;
import org.ksoap2.serialization.SoapObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/14.
 */
@WebServlet(name = "GetMenuList",urlPatterns = "/GetMenuList")
public class GetMenuList extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        List<AuthItem> authItemList = new ArrayList<AuthItem>();
        SoapObject authManagement = new SoapObject(Constant.SERVICE_NS, Constant.AuthorityManagementData);
        authManagement.addProperty("Type", 1);
        authManagement.addProperty("FormSYS", "BI");
        SoapObject data = GetDataFromWebservice.getData(authManagement);
        SoapObject detail1 = (SoapObject) data.getProperty(0);
        SoapObject detail2 = (SoapObject) detail1.getProperty(2);
        SoapObject detail3 = (SoapObject) detail2.getProperty(0);
        SoapObject item = null;
        String name = "";
        String ID = "";
        String parentID = "";
        AuthItem authItem = null;
        for (int i = 0; i < detail3.getPropertyCount(); i++) {
            item = (SoapObject) detail3.getProperty(i);
            name = item.getProperty("MenuName").toString();
            ID = item.getProperty("ID").toString();
            parentID = item.getProperty("ParentID").toString();
            authItem = new AuthItem(name, ID, parentID, false);
            authItemList.add(authItem);
        }

        Gson gson = new Gson();
        String jsonStr = gson.toJson(authItemList);
        jsonStr = jsonStr.replace("title","name");
        jsonStr = jsonStr.replace("Id","id");
        jsonStr = jsonStr.replace("parentid","pId");
        jsonStr = jsonStr.replace("wether","checked");
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
        this.doGet(request,response);
    }
}
