package lc.cy.core.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@PropertySource("classpath:properties/security.properties")
@ConfigurationProperties(prefix = "login")
public class SecurityProperties {

    private String initUrl;

    private String processerUrl;

    private String withoutlogin;
}
