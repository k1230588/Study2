/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import object.UserInfo;

/**
 *
 * @author user
 */
public class DBConn {

    Connection conn = null;
    Statement stmt = null;
    ResultSet rset = null;
    String url = "jdbc:postgresql://localhost:5432/TestDB";
    String user = "postgres";
    String password = "KSadmin2020";

    public Statement getDBC() throws ClassNotFoundException {
        if (conn == null) {
            try {
                Class.forName("org.postgresql.Driver");
                //PostgreSQLへ接続
                conn = DriverManager.getConnection(url, user, password);
                //自動コミットOFF
                conn.setAutoCommit(false);
                stmt = conn.createStatement();
            } catch (SQLException e) {

            }
        }
        return stmt;
    }

    public void closeDBC() throws SQLException {
        if (conn != null) {
            stmt.close();
            conn.close();

        }
    }

    public void addUserInfo(UserInfo ui) throws SQLException {
        String sql = "insert into userinfo (userid, name, password, createdate)";
        sql += "VALUES (" + ui.getUserId() + ",";
        sql += "'" + ui.getName() + "',";
        sql += "'" + ui.getPassword() + "',";
        sql += "current_timestamp);";

//        System.out.println(sql);
        stmt.executeUpdate(sql);
        conn.commit();

    }

    public void DUserInfo(int DeleteL) throws SQLException {
        String sql = "delete from userinfo where userid = " + DeleteL + ";";
        stmt.executeUpdate(sql);
        conn.commit();
    }

    public void UpUserInfo(int Oid, UserInfo ui) throws SQLException {
        String sql = "update userinfo set userid = " + ui.getUserId() + ", name = '" + ui.getName() + "',";
        sql += " password = '" + ui.getPassword() + "' where userid = " + Oid + ";";
//        System.out.println(sql);
        stmt.executeUpdate(sql);
        conn.commit();
    }

    public List<UserInfo> userAll() throws SQLException {
        List<UserInfo> list = new ArrayList();
        String sql = "select * from userinfo;";
        rset = stmt.executeQuery(sql);
        if (rset != null) {
            while (rset.next()) {
                UserInfo ui = new UserInfo();
                ui.setUserId(rset.getInt("userid"));
                ui.setName(rset.getString("name"));
                ui.setPassword(rset.getString("password"));
                list.add(ui);
            }
        }
        return list;
    }

    public List<UserInfo> searchUI(int SIO, UserInfo ui) throws SQLException {
        List<UserInfo> list = new ArrayList();
        String sql;
        String sRange;

        if (ui.getUserId() != Integer.MIN_VALUE) {
            if (!ui.getName().equals("")) {
                sRange = "11";
            } else {
                sRange = "10";
            }
        } else {
            if (!ui.getName().equals("")) {
                sRange = "01";
            } else {
                sRange = "00";
            }
        }
        switch (sRange) {
            case "11":
                if (SIO == 1) {
                    //あいまい検索
                    sql = "select * from userinfo where cast(userid as varchar(255)) like '%" + ui.getUserId() + "%' AND name like '%" + ui.getName() + "%';";
                } else {
                    sql = "select * from userinfo where userid = " + ui.getUserId() + " AND name = '" + ui.getName() + "'";
                }
                break;
            case "10":
                if (SIO == 1) {
                    //あいまい検索
                    sql = "select * from userinfo where cast(userid as varchar(255)) like '%" + ui.getUserId() + "%';";
                } else {
                    sql = "select * from userinfo where userid = " + ui.getUserId();
                }
                break;
            case "01":
                if (SIO == 1) {
                    //あいまい検索
                    sql = "select * from userinfo where name like '%" + ui.getName() + "%'";
                } else {
                    sql = "select * from userinfo where name = '" + ui.getName() + "'";
                }
                break;
            default:
                sql = "select * from userinfo";
        }

        System.out.println(sql);
        rset = stmt.executeQuery(sql);
        if (rset != null) {
            while (rset.next()) {
                UserInfo ui1 = new UserInfo();
                ui1.setUserId(rset.getInt("userid"));
                ui1.setName(rset.getString("name"));
                ui1.setPassword(rset.getString("password"));
                list.add(ui1);
            }
        }
        return list;
    }

}
