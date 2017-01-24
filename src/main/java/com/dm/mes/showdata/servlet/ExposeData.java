package com.dm.mes.showdata.servlet;

import com.dm.mes.showdata.bean.Data;
import com.google.gson.Gson;

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
        List<Data> datas = new ArrayList<Data>();
        for(int i=1;i<6;i++){
            Data data = new Data();
            data.setName("数据"+i);
            data.setNum(i*10-5*i);
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
