package lc.cy.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private DefaultListableBeanFactory beanFactory;


    @RequestMapping("/test/{password}")
    public String test(@PathVariable String password){
        logger.info("Passwork:[" + passwordEncoder.encode(password) + "]");
        return "See Logger";
    }
}
