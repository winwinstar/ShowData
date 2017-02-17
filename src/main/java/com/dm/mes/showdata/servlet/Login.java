package com.dm.mes.showdata.servlet;

import com.dm.mes.showdata.bean.AuthItem;
import com.dm.mes.showdata.constant.Constant;
import com.dm.mes.showdata.util.GetDataFromWebservice;
import org.ksoap2.serialization.SoapObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/2/13.
 */
@WebServlet(name = "Login",urlPatterns = "/Login")
public class Login extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");

        GetDataFromWebservice getDataFromWebservice = new GetDataFromWebservice();
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        SoapObject soapObject = new SoapObject(Constant.SERVICE_NS, Constant.LoadGetData);
        soapObject.addProperty("userName", account);
        soapObject.addProperty("passWord", password);
        soapObject.addProperty("FormSYS", "BI");
        SoapObject data = GetDataFromWebservice.getData(soapObject);
        List<AuthItem> authItemList = getDataFromWebservice.parseData(data);

        List<AuthItem> parent = new ArrayList<>();
        List<String> params = new ArrayList<>();
        if(authItemList!=null){

            Iterator<AuthItem> it = authItemList.iterator();
            while(it.hasNext()){
                System.out.println(it.next().toString());
            }

            for(int i=0;i<authItemList.size();i++){
                if(authItemList.get(i).getParentId().equals("-1")){
                    parent.add(authItemList.get(i));
                }
            }

            String param = "";
            for(int i=0;i<parent.size();i++){
                for(int j=0;j<authItemList.size();j++){
                    if(parent.get(i).getId().equals(authItemList.get(j).getParentId())){
                        param += authItemList.get(j).getTitle() + "&";
                    }
                }
                params.add(parent.get(i).getTitle()+"&"+param+";");
                param = "";
            }

            for(int i=0;i<params.size();i++){
                param += params.get(i);
            }

            System.out.println(param);

            HttpSession session=request.getSession(true);
            session.setMaxInactiveInterval(10);
            session.setAttribute("user",account);

            request.setAttribute("param",param);
            request.setAttribute("user",account);
            getServletContext().getRequestDispatcher("/main.jsp").forward(
                    request, response);

        }else{
            response.sendRedirect("/html/model/404.html");
        }
    }
}
