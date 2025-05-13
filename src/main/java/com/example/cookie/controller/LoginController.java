package com.example.cookie.controller;

import com.example.cookie.dto.MemberDTO;
import com.example.cookie.service.MemberService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/login")
@Log
public class LoginController extends HttpServlet {

    private int lifeTime=60*60*24*7;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        log.info("login get ...............");

        Cookie[] cookies = req.getCookies();
        if(cookies!=null)
        {
            for(Cookie cookie:cookies)
            {
                if("rememberMe".equals(cookie.getName()))
                {
                    String uuid=cookie.getValue();
                    try
                    {
                        MemberDTO dto=MemberService.INSTANCE.getByUuid(uuid);
                        if(dto!=null)
                        {
                            req.setAttribute("rememberMe", dto);
                        }
                    }
                    catch(Exception e)
                    {
                        log.warning("자동 로그인 정보 조회 실패!!" + e.getMessage());
                    }
                }
            }
        }

        req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        log.info("login post ...............");

        String mid=req.getParameter("mid");
        String mpw=req.getParameter("mpw");
        String auto=req.getParameter("auto");

        boolean isRememberd = auto!=null&&auto.equals("on");
        try
        {
            MemberDTO dto= MemberService.INSTANCE.login(mid, mpw);

            if(isRememberd)
            {
                String uuid= UUID.randomUUID().toString();
                MemberService.INSTANCE.updateUuid(mid, uuid);
                dto.setUuid(uuid);

                Cookie rememberCookie=new Cookie("rememberMe",uuid);
                rememberCookie.setMaxAge(lifeTime);
                rememberCookie.setPath("/");

                resp.addCookie(rememberCookie);
            }
            else
            {
                //자동 로그인 체크 안하면 쿠키 삭제
                Cookie deleteCookie = new Cookie("rememberMe", "");
                deleteCookie.setPath("/");
                deleteCookie.setMaxAge(0);
                resp.addCookie(deleteCookie);
            }

            HttpSession session=req.getSession();
            session.setAttribute("loginInfo",dto);
            resp.sendRedirect("/todo/list");
        }
        catch (Exception e)
        {
            resp.sendRedirect("/login?result=error");
        }
    }
}
