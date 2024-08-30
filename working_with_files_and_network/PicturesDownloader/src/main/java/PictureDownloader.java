import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;

public class PictureDownloader {
   private final Logger logger = LoggerFactory.getLogger(PictureDownloader.class);
    private String url;
    private String directory;

    /**
     * Supported formats
     */
    enum ImageFormat {
        JPG, JPEG, PNG, GIF, BMP, WBMP, TIFF, TIF
    }


    public void start (Scanner scanner){
        if (initialization(scanner)){
            try {
                picturesSearch();
            } catch (IOException e){
                logger.debug(e.getMessage());
                logger.info("Unexpected error");
            }
        } else {
            logger.debug("Initialization failed");
            logger.info("Try again");
        }
    }

    private void picturesSearch() throws IOException {
        Document document = Jsoup.connect(url).get();
        Elements images = document.getElementsByTag("img");

        int imgNum = 0;
        for (Element el : images){
            String src = el.absUrl("src");
            Optional<String> optionFormat = takeFormat(src);

            if (optionFormat.isPresent()){
                String format = optionFormat.get();
                URL urlImg = new URL(src);
                BufferedImage bufImg = ImageIO.read(urlImg);
                File file = new File(directory + "img" + imgNum + "." + format);
                ImageIO.write(bufImg,format,file);
                logger.info("loading " + src);
                imgNum ++;
            }
        }
    }

    public boolean initialization(Scanner scanner){
        logger.info("Enter the URL of the site from which you want to save images");
        String urlInput = scanner.nextLine();
        if (urlValidator(urlInput)) {
            url = urlInput;
            logger.info("Enter the directory");
            String folder = scanner.nextLine();
            if (folderValidator(folder)){
                logger.debug("Specified corrected directory");
                directory = folder + "\\";
                return true;
            }
        } return false;
    }

    private Optional<String> takeFormat (String url){
        String urlLow = url.toLowerCase(Locale.ROOT);
        for (ImageFormat format : ImageFormat.values()){
            if (urlLow.contains("." + format.toString().toLowerCase(Locale.ROOT))){
                return Optional.of(format.toString().toLowerCase(Locale.ROOT));
            }
        } return Optional.empty();
    }

    private boolean folderValidator(String folder){
        return Files.exists(Path.of(folder)) && !Files.isRegularFile(Path.of(folder));
    }

    private boolean urlValidator(String url){
        if (url.startsWith("http://") || url.startsWith("https://")){
            try {
                Connection.Response response = Jsoup.connect(url).execute();
                if (response.statusCode() == 200){
                    logger.debug("connection " + url + " was successfully established");
                    return true;
                } else {
                    logger.warn(url + " -  " + response.statusCode() + " " + response.statusMessage());
                    logger.info("Connection is failed " + response.statusCode());
                }
            } catch (IOException e){
                logger.error(e.getMessage());
                logger.info("Failed connection");
            }

        } return  false;

    }
}
