package com.dm.mes.showdata.servlet;

import com.dm.mes.showdata.bean.AuthItem;
import com.dm.mes.showdata.bean.Role;
import com.dm.mes.showdata.bean.User;
import com.dm.mes.showdata.constant.Constant;
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
 * Created by Administrator on 2017/2/14.
 */
@WebServlet(name = "GetUserRoleAuth",urlPatterns = "/GetUserRoleAuth")
public class GetUserRoleAuth extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        SoapObject authManagement = new SoapObject(Constant.SERVICE_NS, Constant.AuthorityManagementData);
        authManagement.addProperty("Type", 0);
        authManagement.addProperty("FormSYS", "BI");
        SoapObject data = GetDataFromWebservice.getData(authManagement);
        SoapObject detail1 = (SoapObject) data.getProperty(0);
        SoapObject detail2 = (SoapObject) detail1.getProperty(2);
        SoapObject detail3 = (SoapObject) detail2.getProperty(0);
        List<Role> roles = new ArrayList<>();
        for(int i=0;i<detail3.getPropertyCount();i++){
            SoapObject soapObject = (SoapObject) detail3.getProperty(i);
            Role role = new Role();
            role.setRole(soapObject.getProperty("RuleName").toString());
            role.setCreateTime(soapObject.getProperty("ActionDatetime").toString());
            if(soapObject.hasProperty("Account")){
                String[] account = soapObject.getProperty("Account").toString().split(",");
                String[] accountTime = soapObject.getProperty("AccountActiontime").toString().split(",");
                List<User> users = new ArrayList<>();
                for(int j=0;j<account.length;j++){
                    if(!account[j].equals("")){
                        User user = new User();
                        user.setUserName(account[j]);
                        user.setRoleName(role.getRole());
                        user.setCreateTime(accountTime[j]);
                        users.add(user);
                    }
                }
                role.setUsers(users);
            }

            if(soapObject.hasProperty("MenuName")){
                List<AuthItem> authItems = new ArrayList<>();
                String[] menuName = soapObject.getProperty("MenuName").toString().split(",");
                String[] menuId = soapObject.getProperty("MenuID").toString().split(",");
                String[] menuParentId = soapObject.getProperty("MenuParentID").toString().split(",");
                for(int j=0;j<menuName.length;j++){
                    if(!menuName[j].equals("")){
                        AuthItem authItem = new AuthItem();
                        authItem.setTitle(menuName[j]);
                        authItem.setId(menuId[j]);
                        authItem.setParentId(menuParentId[j]);
                        authItems.add(authItem);
                    }
                }
                role.setAuthItems(authItems);
            }
            roles.add(role);
        }

        Gson gson = new Gson();
        String jsonStr = gson.toJson(roles);
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
