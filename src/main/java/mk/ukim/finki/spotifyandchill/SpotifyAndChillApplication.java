package mk.ukim.finki.spotifyandchill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.*;

@SpringBootApplication
public class SpotifyAndChillApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpotifyAndChillApplication.class, args);
    }

}
