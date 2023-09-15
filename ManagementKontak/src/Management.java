import java.io.FileInputStream;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Management extends BukuTelepon{

    Management(String email, String name, String phoneNumber) {
        super(email, name, phoneNumber);
    }
    public static void main(String[] args){
        Application startPoint = new Application("", "", "");
        try {
            FileInputStream fileInput = new FileInputStream("dataContact");
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);
            allContact = (ArrayList<BukuTelepon>) objectInput.readObject();
            objectInput.close();
            fileInput.close();
        } catch (IOException | ClassNotFoundException e) {
        }
        startPoint.application();
    }

    public void allContact(){
         if(allContact.size() > 0){
             for(int i = 0; i < allContact.size(); i++){
                 email = allContact.get(i).email;
                 name = allContact.get(i).name;
                 phoneNumber = allContact.get(i).phoneNumber;
                 
                if(i == 0){
                    System.out.println("=============================");     
                }
                System.out.println(String.format("(%s). Email: %s, Name: %s, Phone Number: %s", i + 1, email, name, phoneNumber));
                System.out.println("=============================");
             }
         }
        else{
            System.out.println("=========================");
            System.out.println("no data in here, please create data");
            System.out.println("=========================");
        }
    }
}
class Application extends Management{
    Scanner choose = new Scanner(System.in);
    Application(String email, String name, String phoneNumber) {
        super(email, name, phoneNumber);
    }
    
    public void application(){
        Management kontak = new Management("", "", "");
        
        System.out.println("\nWelcome To My Application!!");
        System.out.println("(1). Add kontak");
        System.out.println("(2). Show all kontak");
        System.out.println("(3). Delete kontak");
        System.out.println("(4). Close application");
        System.out.print("Choose number : ");
        int pilih = choose.nextInt();
        System.out.println("");

        switch (pilih) {
            case 1:
                kontak.addContact();
                kontak.save();
                application();
                break;
            case 2:
                kontak.allContact();
                application();
                break;
            case 3:
                kontak.allContact();
                kontak.delete();
                kontak.save();
                application();
                break;
            case 4:
                choose.close();
                inputContact.close();
                break;
            default:
                break;
        }
    }
}
class BukuTelepon implements Serializable{
    public static ArrayList<BukuTelepon> allContact = new ArrayList<>();
    String email;
    String name;
    String phoneNumber;
    transient Scanner inputContact = new Scanner(System.in);
    BukuTelepon(String email,String name, String phoneNumber){
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }


    public void save(){
        try {
            FileOutputStream fileOutput = new FileOutputStream("dataContact");
            ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
            objectOutput.writeObject(allContact);
            objectOutput.close();
            fileOutput.close();
        } catch (IOException e) {
            System.out.println("=====================");
            System.out.println("Error when save contact" + e);
            System.out.println("=====================\n");
        }
    }
    public void addContact(){
        System.out.print("Enter your email: ");
        String emailin = inputContact.nextLine();
        System.out.print("Enter your name: ");
        String namein = inputContact.nextLine();
        System.out.print("Enter your phone number: ");
        String phoneNumberin = inputContact.nextLine();
        
        allContact.add(new BukuTelepon(emailin, namein, phoneNumberin));

        System.out.println("\n================");
        System.out.println("Data success created!");
        System.out.println("================");
    }
    public void delete(){
        if(allContact.size() > 0){
            System.out.print("Choose contact to deleted: ");
            int delete = inputContact.nextInt() - 1;
            if(delete >= allContact.size()){
                System.out.println("\n================");
                System.out.println("Data not found!");
                System.out.println("================");
                delete();
            }
            else{
                allContact.remove(delete);
                
                System.out.println("");
                System.out.println("================");
                System.out.println("Contact success deleted!");
                System.out.println("================");
            }
        }
    }
}