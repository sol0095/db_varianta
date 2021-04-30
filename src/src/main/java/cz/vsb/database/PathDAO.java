package cz.vsb.database;

import java.sql.*;
import java.util.HashMap;

public class PathDAO {

    public static void insert(HashMap<String, Integer> pathsWithNums){
        System.out.println(pathsWithNums.size());
        Connection con = Database.getConnection();
        try {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO Paths VALUES(?,?);");

            pathsWithNums.forEach((k, v) -> {
                try {
                    stmt.setString(1, k);
                    stmt.setInt(2, v);
                    stmt.addBatch();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });

            stmt.executeBatch();
            stmt.close();
            con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static Path select(String strPath){
        Path path = null;
        Connection con = Database.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("SELECT PathID FROM Paths WHERE Path = ?;");
            stmt.setString(1, strPath);
            ResultSet rs = stmt.executeQuery();

            while ( rs.next() ) {
                path = new Path();
                path.setPathID(rs.getInt("PathID"));
            }

            stmt.close();
            con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return path;
    }

    public static Integer selectMaxID(){
        Integer max = null;
        Connection con = Database.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("SELECT MAX(PathID) FROM Paths;");
            ResultSet rs = stmt.executeQuery();

            while ( rs.next() ) {
                max = rs.getInt(1);
            }

            stmt.close();
            con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return max;
    }

    public static void clearTable(){
        Connection con = Database.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM Paths;");
            stmt.execute();


            stmt.close();
            con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
