//package com.teamrandom.softproj;
//
//import java.util.List;
//
//import org.junit.Test;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNotEquals;
//
//import businessObject.Team;
//import businessObject.User;
//import exception.RandomException;
//import role.Role;
//import role.Submitter;
//
//public class UserTest {
//
//	@Test(expected=RandomException.class)
//	public void testCannotSetNullUserName() throws RandomException {
//		new User(null, "1");
//	}
//
//	@Test(expected=RandomException.class)
//	public void testCannotSetNullUserId() throws RandomException {
//		new User("Randy", null);
//	}
//
//	@Test(expected=RandomException.class)
//	public void testCannotSetEmptyUserName() throws RandomException {
//		new User("", "1");
//	}
//
//	@Test(expected=RandomException.class)
//	public void testCannotSetEmptyUserId() throws RandomException {
//		new User("Randy", "");
//	}
//
//	@Test
//	public void testRoleAddedSuccessfully() throws RandomException {
//		User randy = new User("Randy", "1");
//		Role role = new Submitter();
//		randy.addRole(role);
//		assertEquals(randy.getRoles().size(), 1);
//		assertEquals(randy.getRoles().get(0), role);
//	}
//}
