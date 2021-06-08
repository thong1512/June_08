import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static Connection connectDatabase(){
        Connection databaseConnect = null;
        try{
            String dbURL = "jdbc:mysql://localhost:3306/db2";
            //Class.forName("com.mysql.cj.jdbc.Driver");
            databaseConnect = DriverManager.getConnection(dbURL,"root","undeadworld");
        } catch (SQLException e){
            e.printStackTrace();
        }
        return databaseConnect;
    }

    public static void main(String[] args){
        int port = 9999;
        ServerSocket server = null;
        
        try{
            server = new ServerSocket(port);
            System.out.println("Server running on port: " + server);

            while(true){
                System.out.println("Waiting for client... ");
                Socket client = server.accept();
                System.out.println("Client accepted on port: " + client);

                DataInputStream in = new DataInputStream(client.getInputStream());

                //Get student list from database
                List<Student> students = new ArrayList<Student>();
                Connection con = connectDatabase();
                PreparedStatement st = con.prepareStatement("select * from student");
                ResultSet rs = st.executeQuery();
                while(rs.next()){
                    students.add(new Student(rs.getInt("id"),rs.getString("name"),rs.getDouble("grade")));
                }
                //Get student list from socket
                String xmlData = in.readUTF();
                List<Student> clientStudents = Utils.convertXMLString2Object(xmlData);
                //Synchronize
                boolean sameId = false;
                for (Student cstd : clientStudents){
                    for (Student std : students){
                        if(std.getId() == cstd.getId()){
                            sameId = true;
                        }
                    }
                    if(!sameId){
                        st = con.prepareStatement("insert into student values (?, ?, ?)");
                        st.setInt(1, cstd.getId());
                        st.setString(2, cstd.getName());
                        st.setDouble(3, cstd.getGrade());
                        System.out.println(st);
                        st.executeUpdate();
                    }
                }

                client.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try{
                server.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
