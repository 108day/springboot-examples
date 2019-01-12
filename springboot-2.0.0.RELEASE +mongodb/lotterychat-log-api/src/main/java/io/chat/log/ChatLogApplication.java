package io.chat.log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages= {"io.chat.common"})
public class ChatLogApplication  extends SpringBootServletInitializer {

		public static void main(String[] args) {
			SpringApplication.run(ChatLogApplication.class, args);
		}

		@Override
		protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
			return application.sources(ChatLogApplication.class);
		}
}