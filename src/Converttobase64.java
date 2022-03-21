import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

public class Converttobase64 {

    public static String Convert(String Picturepath){

        String base64 = null;

        File picture = new File(Picturepath);


        try{
            FileInputStream reader = new FileInputStream(picture);
            byte[] bytes = new byte[(int)picture.length()];
            reader.read(bytes);
            base64 = Base64.getEncoder().encodeToString(bytes);


        } catch (FileNotFoundException e) {
            System.out.println("No file found");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Wrong input");
            e.printStackTrace();
        }
        return base64;
    }

    public static String getName(String Picturepath){
        File picture = new File(Picturepath);

        String name = picture.getName();
        return name;
    }
}
