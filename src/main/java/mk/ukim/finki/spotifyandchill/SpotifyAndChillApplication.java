package mk.ukim.finki.spotifyandchill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class SpotifyAndChillApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpotifyAndChillApplication.class, args);
    }

}
