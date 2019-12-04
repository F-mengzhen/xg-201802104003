package cn.edu.sdjzu.xg.bysj.controller;

import cn.edu.sdjzu.xg.bysj.domain.User;
import cn.edu.sdjzu.xg.bysj.service.UserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.deserializer.SqlDateDeserializer;
import util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

@WebServlet("/user.ctl")
public class UserController extends HttpServlet {

    protected void doPut(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
      //根据前台的请求，获得代表参数的json对象
      String user_json=JSONUtil.getJSON(request);
      //将json解析成user对象
      User userToUpade=JSON.parseObject(user_json,User.class);
      //用大于4的随机数给userToUpdate的id赋值
      userToUpade.setId(4+(int)(100*Math.random()));
      //创建json对象，以便向前台做出响应
      JSONObject resp=new JSONObject();
      try{
        UserService.getInstance().changePassword(userToUpade);
        resp.put("message","更新成功");
      }catch (SQLException e){
        resp.put("message","数据库异常");
      }catch (Exception e){
        resp.put("message","网络异常");
      }
      //响应
      response.getWriter().println(resp);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String uaername = request.getParameter("username");
      String password = request.getParameter("password");
      try {
        User user = UserService.getInstance().login(uaername, password);
        if (user!=null){
          HttpSession session=request.getSession();
          session.setAttribute("currentUser",user);
        }else {
          System.out.println("登录失败");
        }
      }catch (SQLException e){
      }
    }


    }
