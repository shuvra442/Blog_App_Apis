package com.blog.app.apis;

import com.blog.app.apis.config.AppConstant;
import com.blog.app.apis.entityes.Role;
import com.blog.app.apis.repository.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepo roleRepo;
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
       System.out.println(this.passwordEncoder.encode("shuvra14"));

	   try{
		   Role role=new Role();
		   role.setId(AppConstant.ADMIN_USER);
		   role.setName("ROLE_ADMIN");

		   Role role1=new Role();
		   role1.setId(AppConstant.NORMAL_USER);
		   role1.setName("ROLE_NORMAL");
		   List<Role>roles=List.of(role,role1);
		   List<Role> result=this.roleRepo.saveAll(roles);
		   result.forEach(r->{
			   System.out.println(r.getName());
		   });
	   }catch (Exception e){
		   e.printStackTrace();
	   }

	}
}