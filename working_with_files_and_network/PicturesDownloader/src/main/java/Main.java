import java.util.Scanner;

public class Main {

    public static void main (String ... a){
        PictureDownloader downloader = new PictureDownloader();
        downloader.start(new Scanner(System.in));
    }
}
