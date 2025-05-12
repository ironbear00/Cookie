package com.example.cookie.controller;

import com.example.cookie.dto.TodoDTO;
import com.example.cookie.service.TodoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Log4j2
@WebServlet(name="todoRegisterController", value="/todo/register")
public class TodoRegisterController extends HttpServlet {

    private TodoService todoService= TodoService.INSTANCE;
    private final DateTimeFormatter DATETIMEFORMATTER= DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        log.info(("/todo/register GET ........."));

        HttpSession session =req.getSession();

        if(session.isNew())
        {
            log.info("JSession ID 쿠키가 새로 만들어진 사용자");
            resp.sendRedirect("/login");
        }

        if(session.getAttribute("loginInfo")==null)
        {
            log.info("로그인 정보가 없는 사용자");
            resp.sendRedirect("/login");
            return;
        }

        req.getRequestDispatcher(("/WEB-INF/todo/register.jsp")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TodoDTO dto=TodoDTO.builder()
                    .title(req.getParameter("title"))
                    .dueDate(LocalDate.parse(req.getParameter("dueDate"), DATETIMEFORMATTER))
                    .build();

        log.info("/todo/register POST .........");
        log.info(dto);

        try
        {
            todoService.register(dto);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        resp.sendRedirect("/todo/list");
    }

}
