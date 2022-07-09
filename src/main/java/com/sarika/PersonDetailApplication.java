package com.sarika;

import com.sarika.config.AppConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class PersonDetailApplication {

    public static void main(String[] args) throws IOException {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        PersonProcessor processor = context.getBean(PersonProcessor.class);
        processor.process();

        context.close();
    }
}
