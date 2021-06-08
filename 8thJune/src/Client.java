import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    private static Connection connectDatabase(){
        Connection databaseConnect = null;
        try{
            String dbURL = "jdbc:mysql://localhost:3306/db1";
            //Class.forName("com.mysql.cj.jdbc.Driver");
            databaseConnect = DriverManager.getConnection(dbURL,"root","undeadworld");
        } catch (SQLException e){
            e.printStackTrace();
        }
        return databaseConnect;
    }

    public static void main(String[] args){
        String host = "localhost";
        int port = 9999;
        try{
            Socket socket = new Socket(host, port);
            System.out.println("Connected to: " + socket);
            
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in);
            //Get student list from database
            List<Student> students = new ArrayList<Student>();
            Connection con = connectDatabase();
            PreparedStatement st = con.prepareStatement("select * from student");
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                students.add(new Student(rs.getInt("id"),rs.getString("name"),rs.getDouble("grade")));
            }
            //Send list over socket
            out.writeUTF(Utils.convertObject2XMLString(students));
            

            socket.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
