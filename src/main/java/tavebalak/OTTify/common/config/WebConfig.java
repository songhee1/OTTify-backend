package tavebalak.OTTify.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // cors를 적용할 spring서버의 url 패턴.
            .allowedOrigins("http://ottify.kro.kr:3000") // cors를 허용할 도메인. 제한을 모두 해제하려면 "**"
            .allowedOrigins("http://ottify.kro.kr")//새로 추가된 부분 : 여기
            .allowedOrigins("http://localhost:3000")
            .allowedMethods("GET", "POST", "PUT", "PATCH",
                "DELETE"); // cors를 허용할 method + DELETE 추가
    }
}
