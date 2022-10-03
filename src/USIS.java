import com.mysql.cj.protocol.Resultset;

import javax.management.Query;
import java.sql.*;
import java.util.Queue;
import java.util.Scanner;

public class USIS {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);



        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/usis", "root", "root");
            Statement statement = connection.createStatement();


/*
            // Login

            ResultSet resultSet = statement.executeQuery("select * from student");

            int access = 0;

            while (resultSet.next()){
                System.out.print(resultSet.getInt("sid")+" ");
                System.out.print(resultSet.getString("name")+" ");
                System.out.print(resultSet.getString("password")+" ");
                System.out.print(resultSet.getString("email")+" ");
                System.out.println();

                if(resultSet.getString("email").equals("tishan@gmail.com") && resultSet.getString("password").equals("1234")){
                    System.out.println("access granted");
                    access = 1;
                    break;
                }
            }

            if(access == 0){
                System.out.println("Please enter e correct login creadential");
            }

            // login end
*/

/*
            // Data show

            ResultSet resultSet = statement.executeQuery("select * from student");

            while (resultSet.next()){
                System.out.print(resultSet.getInt("sid")+" ");
                System.out.print(resultSet.getString("name")+" ");
                System.out.print(resultSet.getString("password")+" ");
                System.out.print(resultSet.getString("email")+" ");
                System.out.println();

            }

*/

/*

            System.out.println("\n\n :: REGISTRATION :: ");

            PreparedStatement preparedStatement;

            System.out.print("Please enter your name : ");
            String name = sc.next();
            System.out.print("Please enter your id : ");
            String id = sc.next();
            System.out.print("Please enter your email : ");
            String email = sc.next();
            System.out.print("Plesae enter your password : ");
            String password = sc.next();

            System.out.println("\n\n");

            String sql = "INSERT INTO students(name, sid, email, password) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, Integer.parseInt(id));
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);
            preparedStatement.executeUpdate();

            // registration end


            resultSet = statement.executeQuery("SELECT * FROM students");

            while(resultSet.next()){
                System.out.print(resultSet.getString("name")+ " ");
                System.out.print(resultSet.getInt("sid")+ " ");
                System.out.print(resultSet.getString("email")+ " ");
                System.out.print(resultSet.getString("password")+ " ");
                System.out.println();
            }

*/


            PreparedStatement preparedStatement;
            ResultSet resultSet;

            int sec1_remaining_seats = 0;
            int sec2_remaining_seats = 0;


            resultSet = statement.executeQuery("SELECT seats FROM remaining_seats WHERE section = '01'");
            if(resultSet.next()){
                sec1_remaining_seats = resultSet.getInt("seats");  //0 er bodole database er section 1 theke remaining seat fetch kore dibo eikhane
            }


            resultSet = statement.executeQuery("SELECT seats FROM remaining_seats WHERE section = '02'");
            if(resultSet.next()){
                sec2_remaining_seats = resultSet.getInt("seats");  //0 er bodole database er section 2 theke remaining seat fetch kore dibo eikhane
            }

/*

//            eta bar bar cholbe na. just ekbar seat gula fix kore eta off kore dibo othoba mysql workbench er remaining seats table e just query 2 ta run
//            kore 2 ta section e entry diye dibo. that's it. taile ar eikhane kisui kora lagbena


            String s1_query = "INSERT INTO remaining_seats" + "(section, seats)" +  "VALUES ('01', '8')";
            preparedStatement = connection.prepareStatement(s1_query);
            preparedStatement.executeUpdate();


            String s2_query = "INSERT INTO remaining_seats" + "(section, seats)" +  "VALUES ('02', '7')";
            preparedStatement = connection.prepareStatement(s2_query);
            preparedStatement.executeUpdate();


*/



            int logout = 0;

            while(true){

                if(logout == 0){
                    System.out.println("\n");
                    System.out.println("1. Register as a student");
                    System.out.println("2. Login as a student");
                    System.out.println("3. Login as a faculty");
                    System.out.println("Please choose one option");
                }

                if(logout == 1){
                    System.out.println("4. Log out");
                    System.out.println("Press 4 to logout.");
                }



                int choose_option = sc.nextInt();

                if(choose_option == 1){
                    //logut => 0
                    //Register as a student

                    if(sec1_remaining_seats+sec2_remaining_seats == 15){
                        System.out.println("Class is full. No registration allowed now.");
                        continue;
                    }

                    System.out.print("Name : ");
                    String name = sc.next();

                    System.out.print("Student ID : ");
                    String id = sc.next();

                    System.out.print("Email : ");
                    String email = sc.next();

                    System.out.print("Password : ");
                    String password = sc.next();


                    // eikhane students table e entry dicchi database e

                    String sql = "INSERT INTO students" + "(name , sid, email, password)" +  "VALUES ('"+name+"', '"+id+"', '"+email+"', '"+password+"' )";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.executeUpdate();

                    System.out.println("Registration Successful!");

                    System.out.println("\n\n");
                    logout = 0;

                }
                else if(choose_option == 2){
                    // logout => 1
                    // Login as a student
                    System.out.println("\n --------- Login --------\n");


                    System.out.print("Email : ");
                    String given_email = sc.next();

                    System.out.print("Password : ");
                    String given_password = sc.next();


                    // eikhane check korbo je given_name and given_password er kuno id khola ase kina database e
                    // jodi database e pai taile access = 1 hobe and vhitore dhukte dibo

                    resultSet = statement.executeQuery("select * from students");

                    int access = 0;
                    String logged_in_student_id = "";
                    String logged_in_student_name = "";

                    while (resultSet.next()){

                        if(resultSet.getString("email").equals(given_email) && resultSet.getString("password").equals(given_password)){
                            System.out.println("\nLogin Successfull\n");
                            logged_in_student_id = resultSet.getString("sid");
                            logged_in_student_name = resultSet.getString("name");
                            access = 1;
                            break;
                        }
                    }

                    resultSet = statement.executeQuery("SELECT seats FROM remaining_seats WHERE section = '01'");
                    if(resultSet.next()){
                        sec1_remaining_seats = resultSet.getInt("seats");  //0 er bodole database er section 1 theke remaining seat fetch kore dibo eikhane
                    }


                    resultSet = statement.executeQuery("SELECT seats FROM remaining_seats WHERE section = '02'");
                    if(resultSet.next()){
                        sec2_remaining_seats = resultSet.getInt("seats");  //0 er bodole database er section 2 theke remaining seat fetch kore dibo eikhane
                    }



                    if(access == 1){
                        //logout => 1

                        System.out.println("----------------- Advising portal -----------------\n");

                        System.out.println("1. Section-01   Sunday: 12:30 pm 1:00 pm  " + sec1_remaining_seats);
                        System.out.println("2. Section-02   Sunday: 2:30  pm 4:00 pm  " + sec2_remaining_seats);
                        System.out.print("Which section do you want to choose : ");

                        int choosen_section = sc.nextInt();


                        if(choosen_section == 1){

                            // logged_in_student_id and name diye ei student ke enrolled_students table e section 01 diye entry dibo
                            String sql = "INSERT INTO enrolled_students" + "(sid, name, section)" +  "VALUES ('"+logged_in_student_id+"', '"+logged_in_student_name+"', '"+choosen_section+"')";
                            preparedStatement = connection.prepareStatement(sql);
                            preparedStatement.executeUpdate();


                            // eikhane section er remaining seat status change kortesi

                            sec1_remaining_seats--;

                            String s = "UPDATE remaining_seats SET seats = '"+sec1_remaining_seats+"' WHERE section = '1'";
                            preparedStatement = connection.prepareStatement(s);
                            preparedStatement.executeUpdate();


                        }
                        else if(choosen_section == 2){

                            // logged_in_student_id and name diye ei student ke enrolled_students table e section 02 diye entry dibo
                            String sql = "INSERT INTO enrolled_students" + "(sid, name, section)" +  "VALUES ('"+logged_in_student_id+"', '"+logged_in_student_name+"', '"+choosen_section+"')";
                            preparedStatement = connection.prepareStatement(sql);
                            preparedStatement.executeUpdate();


                            // eikhane section er remaining seat status change kortesi

                            sec2_remaining_seats--;

                            String s = "UPDATE remaining_seats SET seats = '"+sec2_remaining_seats+"' WHERE section = '2'";
                            preparedStatement = connection.prepareStatement(s);
                            preparedStatement.executeUpdate();

                        }
                        else{
                            System.out.println("\nPlease give a valid section number.\n");
                        }

                        logout = 1;
                    }
                    else{
                        System.out.println("\n\nSorry, this email is not registered or the credentials is not right.\n\n");
                        logout = 0;
                        continue;
                    }
                }
                else if(choose_option == 3){
                    //logout => 1
                    // login as a faculty

                    System.out.println("\n --------- Faculty Login --------\n");

                    System.out.print("Faculty Name : ");
                    String faculty_name = sc.next();

                    System.out.print("Faculty password : ");
                    String faculty_password = sc.next();

                    int access = 0;

                    if(faculty_name.equals("310") && faculty_password.equals("310")){
                        access = 1;
                    }


                    if(access == 1){


                        while (true){

                            System.out.println("\n---------Sections-------\n");

                            System.out.println("1. Section-01   Sunday: 12:30 pm 1:00 pm  " + sec1_remaining_seats);
                            System.out.println("2. Section-02   Sunday: 2:30  pm 4:00 pm  " + sec2_remaining_seats);
                            System.out.print("Choose one to see the enrolled students of that section: ");

                            int choose_section = sc.nextInt();

                            System.out.println("Name     "+ "     SID ");

                            if(choose_section == 1){

                                // enrolled_students table er jegula sectino 01 ase oigula shob gula ke * diye fetch kore show kore dibo


                                String sql = "select * from enrolled_students where section='1'";
                                resultSet = statement.executeQuery(sql);

                                while (resultSet.next()){
                                    System.out.print(resultSet.getString("name")+"          ");
                                    System.out.print(resultSet.getInt("sid")+"  ");
                                    System.out.println();
                                }

                            }
                            else if(choose_section == 2){

                                // enrolled_students table er jegula sectino 02 ase oigula shob gula ke * diye fetch kore show kore dibo

                                String sql = "select * from enrolled_students where section='2'";
                                resultSet = statement.executeQuery(sql);

                                while (resultSet.next()){
                                    System.out.print(resultSet.getString("name")+"          ");
                                    System.out.print(resultSet.getInt("sid")+"  ");
                                    System.out.println();
                                }

                            }

                            System.out.println("want to choose another section?");
                            System.out.println("1. Yes          2. NO");
                            int option = sc.nextInt();
                            if(option == 1){
                                continue;
                            }
                            else if(option == 2){
                                break;
                            }
                        }

                        logout = 1;
                    }
                    else{
                        System.out.println("Please give me a valid faculty name and password.");
                        logout = 0;
                    }
                }
                else if(choose_option == 4){
                    //logout => 0
                    logout = 0;
                    continue;
                }
                else
                    System.out.println("PLease choose a valid option");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
