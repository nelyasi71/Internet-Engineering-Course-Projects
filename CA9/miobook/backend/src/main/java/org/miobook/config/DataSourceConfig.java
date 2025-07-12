import javax.sql.DataSource;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public DataSource dataSource() throws Exception {

        return DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(password)
                .build();
    }