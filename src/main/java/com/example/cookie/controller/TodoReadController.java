package com.example.cookie.controller;

import com.example.cookie.service.TodoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import com.example.cookie.dto.TodoDTO;
import java.io.IOException;

@WebServlet(name="todoReadController", value = "/todo/read")
@Log4j2
public class TodoReadController extends HttpServlet {

    private TodoService todoService=TodoService.INSTANCE;
    private int lifeTime=60*60*24;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        try
        {
            Long tno=Long.parseLong(req.getParameter("tno"));
            TodoDTO dto=todoService.get(tno);
            req.setAttribute("dto", dto);

            //finding cookie
            Cookie viewTodoCookie=findCookie(req.getCookies(), "viewTodos");
            String todoListStr=viewTodoCookie.getValue();
            boolean exist=false;

            if(todoListStr!=null && todoListStr.indexOf(tno+"-")>=0)
            {
                exist=true;
            }
            log.info("exist: "+exist);
            if(!exist)
            {
                todoListStr+=tno+"-";
                viewTodoCookie.setValue(todoListStr);
                viewTodoCookie.setMaxAge(lifeTime);
                viewTodoCookie.setPath("/");
                resp.addCookie(viewTodoCookie);
            }
            req.getRequestDispatcher("/WEB-INF/todo/read.jsp").forward(req, resp);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.error(e.getMessage());
            throw  new ServletException("read error");
        }
    }

    private Cookie findCookie(Cookie[] cookies, String cookieName)
    {
        Cookie targetCookie=null;

        if(cookies!=null && cookies.length>0)
        {
            for(Cookie ck : cookies)
            {
                if(ck.getName().equals(cookieName))
                {
                    targetCookie=ck;
                    break;
                }
            }
        }

        if(targetCookie==null)
        {
            targetCookie=new Cookie(cookieName, "");
            targetCookie.setPath("/");
            targetCookie.setMaxAge(lifeTime);
        }

        return targetCookie;
     }

}
