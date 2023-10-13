import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Siswa {

    private static Connection connection;
    private static Statement statement;
    private static Scanner scanner = new Scanner(System.in);

    static {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sekolah", "root", "");
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void matikanKoneksi() {
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void queryDB(String query) {
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void allSiswa() {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM siswa");

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String nis = resultSet.getString("nis");
                String nama = resultSet.getString("nama");
                String jurusan = resultSet.getString("jurusan");
                System.out.println("==================================");
                System.out.println("ID     :" + id);
                System.out.println("NIS    :" + nis);
                System.out.println("NAMA   :" + nama);
                System.out.println("JURUSAN:" + jurusan);
                System.out.println("==================================");
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void buatSiswa(String nis, String nama, String jurusan) {
        queryDB("INSERT INTO siswa VALUES (NULL, '" + nis + "', '" + nama + "', '" + jurusan + "')");
    }

    public static void hapusSiswa(String id) {
        queryDB("DELETE FROM siswa WHERE `siswa`.`id` = " + id);
    }

    public static void ubahSiswa(String id, String nis, String nama, String jurusan) {
        queryDB("UPDATE `siswa` SET `nis` = '" + nis + "', `nama` = '" + nama + "', `jurusan` = '" + jurusan
                + "' WHERE `siswa`.`id` =" + id);
    }

    public static void main(String[] args) {
        boolean loop = true;
        int pilihan;
        while (loop) {
            System.out.println("[1] Semua  Siswa");
            System.out.println("[2] Buat   Siswa");
            System.out.println("[3] Edit   Siswa");
            System.out.println("[4] Hapus  Siswa");
            System.out.println("[5] Keluar Siswa");
            System.out.print("Pilih salah satu :");
            pilihan = scanner.nextInt();
            if (pilihan == 1) {
                allSiswa();
            } else if (pilihan == 2 || pilihan == 3) {
                System.out.print("Nis     :");
                String nis = scanner.next();
                System.out.print("Nama    :");
                String nama = scanner.next();
                System.out.print("Jurusan :");
                String jurusan = scanner.next();
                if(pilihan == 2){
                    buatSiswa(nis,nama,jurusan);
                }else{
                    System.out.print("Id      :");
                    String id = scanner.next();
                    ubahSiswa(id,nis,nama,jurusan);
                }
            } else if(pilihan == 4){
                System.out.print("Id      :");
                String id = scanner.next();
                hapusSiswa(id);
            }else if (pilihan == 5) {
                loop = false;
                System.out.println("==================================");
                System.out.println("            Thanks :)           ");
                System.out.println("==================================");
            } else {
                System.out.println("==================================");
                System.out.println("             Failed :(              ");
                System.out.println("==================================");
            }
        }

        matikanKoneksi();
    }
}
