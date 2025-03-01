package com.rdxy.servlet;

import com.rdxy.entity.User;
import com.rdxy.service.UserService;
import com.rdxy.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserServlet", value = "/users/UserServlet")
public class UserServlet extends HttpServlet {
    private UserService userService=new UserServiceImpl();
//444444444444444444444444444444444444444444444444444444444444444444
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        System.out.println("doGet");
        String method = request.getParameter("method");

        switch (method){
            case "add":
                String name=request.getParameter("name");
                String password=request.getParameter("password");
                System.out.println("name="+name+" password="+password);
                userService.insert(new User(name,password));
                response.sendRedirect("/3.5.1/users/UserServlet?method=select");
                break;
            case "delete":
                userService.delete(Integer.valueOf(request.getParameter("id")));
                response.sendRedirect("/3.5.1/users/UserServlet?method=select");
                break;
            case "update":
                String get=request.getParameter("get");
                Integer uid= Integer.valueOf(request.getParameter("id"));
                String uname=request.getParameter("name");
                if(uname!=null){
                    uname = new String(uname.getBytes("iso-8859-1"), "utf-8");
                }
                String upassword=request.getParameter("password");
                if(get!=null){
                    System.out.println("uid="+uid);

                    User byId = userService.getById(new User(uid, uname, upassword));
                    System.out.println("byId="+byId);
                    request.getSession().setAttribute("user",byId);
                    request.getRequestDispatcher("/page/update.jsp").forward(request,response);
                    break;
                }


                userService.update(new User(uid,uname,upassword));
                response.sendRedirect("/3.5.1/users/UserServlet?method=select");
                break;
            case "select":
                String msg=null;
                if(request.getParameter("msg")!=null){
                    msg = new String(request.getParameter("msg").getBytes("iso-8859-1"), "utf-8");
                }


                //String msg=request.getParameter("msg");
                System.out.println("msg="+msg);
                List<User> users=userService.getAll(msg);

                System.out.println("UserServlet");
                for (int i = 0; i < users.size(); i++) {
                    System.out.println(users.get(i));
                }
                //request.getSession().setAttribute("list",users);
                request.setAttribute("list",users);

                request.getRequestDispatcher("/page/select.jsp").forward(request,response);
                break;
            default:
                break;
        }



    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);

    }
}
