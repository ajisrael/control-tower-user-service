package control.tower.user.service;

import control.tower.user.service.command.interceptors.CreateUserCommandInterceptor;
import control.tower.user.service.command.interceptors.RemoveUserCommandInterceptor;
import control.tower.user.service.core.errorhandling.UserServiceEventsErrorHandler;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;

@EnableDiscoveryClient
@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Autowired
	public void registerUserCommandInterceptors(ApplicationContext context, CommandBus commandBus) {
		commandBus.registerDispatchInterceptor(
				context.getBean(CreateUserCommandInterceptor.class)
		);
		commandBus.registerDispatchInterceptor(
				context.getBean(RemoveUserCommandInterceptor.class)
		);
	}

	@Autowired
	public void configure(EventProcessingConfigurer configurer) {
		configurer.registerListenerInvocationErrorHandler("user-group",
				configuration -> new UserServiceEventsErrorHandler());
	}
}
