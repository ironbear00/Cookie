package com.example.cookie.controller;

import com.example.cookie.service.TodoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@WebServlet(name="todoRemoveController", value="/todo/remove")
@Log4j2
public class TodoRemoveController extends HttpServlet {

    private TodoService todoService=TodoService.INSTANCE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Long tno=Long.parseLong(req.getParameter("tno"));
        log.info("tno="+tno);

        try
        {
            todoService.remove(tno);
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            throw new ServletException("read error!!");
        }

        resp.sendRedirect("/todo/list");
    }
}
