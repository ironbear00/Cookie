package com.example.dao;

import com.example.cookie.dao.TodoDAO;
import com.example.cookie.domain.TodoVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.*;

public class TodoDAOTests {

    private TodoDAO dao;

    @BeforeEach
    public void ready()
    {
        dao=new TodoDAO();
    }

    @Test
    public void testTime() throws Exception
    {
        System.out.println(dao.getTime());
    }

    @Test
    public void testInsert() throws Exception{
        TodoVO vo=TodoVO.builder().title("Sample Title...").dueDate(LocalDate.of(2021, 12, 31)).build();

        dao.insert(vo);
    }

    @Test
    public void testList() throws Exception{
        List<TodoVO> list=dao.selectAll();
        list.forEach(vo -> System.out.println(vo));
    }

    @Test
    public void testSelectOne() throws Exception{
        Long tno=1L;
        TodoVO vo=dao.selectOne(tno);
        System.out.println(vo);
    }

    @Test
    public void testUpdateOne() throws Exception{
        TodoVO vo=TodoVO.builder()
                    .tno(1L)
                    .title("Sample Title...")
                    .dueDate(LocalDate.of(2021, 12, 31))
                    .finished(true)
                    .build();

        dao.updateOne(vo);
    }

    @Test
    public void testDeleteOne() throws Exception{
        Long tno=1L;
        dao.deleteOne(tno);
    }
}
