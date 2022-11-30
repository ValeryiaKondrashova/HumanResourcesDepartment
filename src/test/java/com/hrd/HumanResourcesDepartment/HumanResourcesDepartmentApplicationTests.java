package com.hrd.HumanResourcesDepartment;

import com.hrd.HumanResourcesDepartment.controllers.RegistrationController;
import com.hrd.HumanResourcesDepartment.models.User;
import com.hrd.HumanResourcesDepartment.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static com.hrd.HumanResourcesDepartment.models.Role.USER;
import static org.springframework.test.util.AssertionErrors.assertNotNull;
import static org.springframework.test.util.AssertionErrors.fail;

@SpringBootTest
class HumanResourcesDepartmentApplicationTests {

//	@Autowired
//	private UserRepo userRepo;

	/*

	* This test need for check user in Database and use in addUser(registration user).
	* 	Note! Two person don't have equals username!
	*
	* 	If user will be in Database, so Test return result - failed. (because: Two person don't have equals username!)
	* 	And
	* 	If we don't find user in Database, so Test return result - passed.
	*
	*/

	@Test
	public void contextLoads() {

//		User user = new User("userLogin");
//		User userFromDb = userRepo.findByUsername(user.getUsername());
//
//		assertsEquals(user, userFromDb);

	}

//	private String assertsEquals(User user, User userFromDb) {
//		if(user.equals(userFromDb)){
//			fail("User already exists!");
//			return "User already exists!";
//		}
//		else{
//			return "This is a new user! Good!";
//		}
//
//	}

}
