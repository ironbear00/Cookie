package com.example.cookie.dao;

import com.example.cookie.domain.TodoVO;
import lombok.Cleanup;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TodoDAO {

    private String sql;

    public String getTime(){
        String now= null;

        try(Connection conn=ConnectionUtil.INSTANCE.getConnection();
            PreparedStatement psmt=conn.prepareStatement("select now()");
            ResultSet rs=psmt.executeQuery();
        )
        {
            rs.next();
            now=rs.getString(1);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return now;
    }


    public void insert(TodoVO vo) throws Exception
    {
        sql="";
        sql+=" insert into tbl_todo(title, dueDate, finished) ";
        sql+=" values(?,?,?) ";

        @Cleanup Connection conn=ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement psmt=conn.prepareStatement(sql);

        psmt.setString(1, vo.getTitle());
        psmt.setDate(2, Date.valueOf(vo.getDueDate()));
        psmt.setBoolean(3, vo.isFinished());

        psmt.executeUpdate();
    }

    public List<TodoVO> selectAll() throws Exception
    {
        sql="";
        sql+=" select * from tbl_todo ";

        @Cleanup Connection conn=ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement psmt=conn.prepareStatement(sql);
        @Cleanup ResultSet rs=psmt.executeQuery();

        List<TodoVO> list=new ArrayList<>();

        while(rs.next())
        {
            TodoVO vo=TodoVO.builder()
                    .tno(rs.getLong("tno"))
                    .title(rs.getString("title"))
                    .dueDate(rs.getDate("dueDate").toLocalDate())
                    .finished(rs.getBoolean("finished"))
                    .build();

            list.add(vo);
        }

        return list;
    }


    public TodoVO selectOne(Long tno) throws Exception
    {
        sql="";
        sql+=" select * from tbl_todo ";
        sql+=" where tno=? ";

        @Cleanup Connection conn=ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement psmt=conn.prepareStatement(sql);
        psmt.setLong(1, tno);
        @Cleanup ResultSet rs=psmt.executeQuery();

        rs.next();
        TodoVO vo= TodoVO.builder()
                    .tno(rs.getLong("tno"))
                    .title(rs.getString("title"))
                    .dueDate(rs.getDate("dueDate").toLocalDate())
                    .finished(rs.getBoolean("finished"))
                    .build();

        return vo;
    }

    public void deleteOne(Long tno) throws Exception
    {
        sql="";
        sql+=" delete from tbl_todo ";
        sql+=" where tno=? ";

        @Cleanup Connection conn=ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement psmt=conn.prepareStatement(sql);
        psmt.setLong(1, tno);
        psmt.executeUpdate();
    }

    public void updateOne(TodoVO vo) throws Exception
    {
        sql="";
        sql+=" update tbl_todo ";
        sql+=" set title=?,dueDate=?,finished=? where tno=? ";

        @Cleanup Connection conn=ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement psmt=conn.prepareStatement(sql);
        psmt.setString(1, vo.getTitle());
        psmt.setDate(2, Date.valueOf(vo.getDueDate()));
        psmt.setBoolean(3, vo.isFinished());
        psmt.setLong(4, vo.getTno());

        psmt.executeUpdate();
    }
}
