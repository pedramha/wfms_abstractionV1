
import static org.springframework.boot.SpringApplication.run;

public class Application {
    public static void main(String[] args) throws Exception {
        /*run(Application.class,args);*/
        ClientAbstractor.initClient();
    }
}
