package com.bigshare;

import com.bigshare.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration
@WebAppConfiguration
class BigshareApplicationTests {

	@Autowired
	private UserService uService;

	@Test
	@WithMockUser(username="admin",  roles={"ADMIN"})
	public void getAdmin() {
		when(uService.getAdmin()).thenReturn(ResponseEntity.ok("ADMIN"));
	}

	@Test
	@WithMockUser(username="freelancer", roles={"FREELANCER"})
	public void getFreelancer() {
		when(uService.getFreelancer()).thenReturn(ResponseEntity.ok("FREELANCER"));
	}

	@Test
	@WithMockUser(username="client", roles={"CLIENT"})
	public void getUser() {
		when(uService.getClient()).thenReturn(ResponseEntity.ok("CLIENT"));
	}

	@Test
	@WithMockUser(username="moderator", roles={"MODERATOR"})
	public void getModerator() {
		when(uService.getModerator()).thenReturn(ResponseEntity.ok("MODERATOR"));
	}
}