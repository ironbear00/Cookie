package com.example.cookie.controller;

import com.example.cookie.service.TodoService;
import lombok.extern.log4j.Log4j2;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.example.cookie.dto.TodoDTO;
import java.util.List;

@WebServlet(name = "todoListController", value = "/todo/list")
@Log4j2
public class TodoListController extends HttpServlet {
    private TodoService todoService = TodoService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        log.info("todo list.................................");

        try
        {
            List<TodoDTO> dtoList=todoService.listAll();
            req.setAttribute("dtoList", dtoList);
            req.getRequestDispatcher("/WEB-INF/todo/list.jsp").forward(req, resp);
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            throw new ServletException("list error!");
        }
    }
}
