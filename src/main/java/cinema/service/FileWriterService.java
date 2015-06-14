package cinema.service;

import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

@Service
public class FileWriterService {

    public void writeFile(String path ,String content, int counter, String suffix) {
        try {

            File file = new File(path + counter + suffix);

            file.createNewFile();

            java.io.FileWriter fw = new java.io.FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();

            System.out.println("Wrote File");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
