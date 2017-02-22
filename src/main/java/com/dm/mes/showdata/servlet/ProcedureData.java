package com.dm.mes.showdata.servlet;

import com.dm.mes.showdata.bean.Procedure;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by Administrator on 2017/2/21.
 */
@WebServlet(name="ProcedureData",urlPatterns="/ProcedureData")
public class ProcedureData extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        File file = new File("C:\\Users\\Administrator\\Desktop\\dataview\\d3\\shu_tu\\city_tree.json");
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file),"UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = null;
        String res = "";
        while((line = bufferedReader.readLine())!=null){
            if(line != "\n") res += line;
        }
        Gson gson = new Gson();
        Procedure procedure = gson.fromJson(res,Procedure.class);

        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(res);
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
