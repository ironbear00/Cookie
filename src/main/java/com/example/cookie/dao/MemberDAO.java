package com.example.cookie.dao;

import com.example.cookie.domain.MemberVO;
import lombok.Cleanup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MemberDAO {

    private String query;

    public MemberVO getWithPassword(String mid, String mpw) throws Exception
    {
        query="";
        query+=" select mid, mpw, mname from tbl_member ";
        query+=" where mid=? and mpw=? ";

        MemberVO vo=null;

        @Cleanup Connection conn=ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement psmt=conn.prepareStatement(query);
        psmt.setString(1, mid);
        psmt.setString(2, mpw);
        @Cleanup ResultSet rs=psmt.executeQuery();
        rs.next();

         vo= MemberVO.builder()
                     .mid(rs.getString(1))
                     .mpw(rs.getString(2))
                     .mname(rs.getString(3))
                     .build();

         return vo;
    }

    public void updateUuid(String mid, String uuid) throws Exception
    {
        query="";
        query+=" update tbl_member set uuid =? where mid=? ";

        @Cleanup Connection conn=ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement psmt=conn.prepareStatement(query);
        psmt.setString(1, uuid);
        psmt.setString(2, mid);
        @Cleanup ResultSet rs=psmt.executeQuery();
    }

    public MemberVO selectUUID(String uuid) throws Exception
    {
        query="";
        query+=" select mid, mpw, mname from tbl_member where uuid=? ";

        @Cleanup Connection conn=ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement psmt=conn.prepareStatement(query);
        psmt.setString(1, uuid);
        @Cleanup ResultSet rs=psmt.executeQuery();
        rs.next();

        MemberVO vo=MemberVO.builder()
                    .mid(rs.getString(1))
                    .mpw(rs.getString(2))
                    .mname(rs.getString(3))
                    .uuid(rs.getString(4))
                    .build();

        return vo;
    }

}
