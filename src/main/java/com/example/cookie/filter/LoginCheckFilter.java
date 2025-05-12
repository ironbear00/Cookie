package com.example.cookie.filter;

import com.example.cookie.dto.MemberDTO;
import com.example.cookie.service.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.Filter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@WebFilter(urlPatterns = {"/todo/*"})
@Log4j2
public class LoginCheckFilter implements Filter
{
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        log.info("Login check filter ........ ");

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();

        // 1. 세션에 로그인 정보가 있으면 바로 통과
        if (session.getAttribute("loginInfo") != null) {
            chain.doFilter(req, resp);
            return;
        }

        // 2. 쿠키로 자동 로그인 시도
        Cookie cookie = findCookie(request.getCookies(), "remember-me");

        if (cookie != null) {
            String uuid = cookie.getValue();
            log.info("자동 로그인용 쿠키 발견: " + uuid);

            try {
                MemberDTO dto = MemberService.INSTANCE.getByUuid(uuid);
                log.info("쿠키의 값으로 조회한 사용자 정보: " + dto);

                if (dto != null) {
                    session.setAttribute("loginInfo", dto);
                    chain.doFilter(req, resp); // 통과
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace(); // 로그로 남겨도 좋음
            }
        }

        // 자동 로그인 실패 → 로그인 페이지로
        response.sendRedirect("/login");
    }



    private Cookie findCookie(Cookie[] cookies, String name)
    {
        if(cookies==null || cookies.length==0)
        {
            return null;
        }

        Optional<Cookie> result= Arrays.stream(cookies).filter(ck->ck.getName().equals(name)).findFirst();

        return result.isPresent()?result.get():null;
    }

}
