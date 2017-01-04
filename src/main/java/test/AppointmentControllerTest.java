package test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)

// TODO: Unit Tests ( maybe with Mockito? No clue... )
public class AppointmentControllerTest {

    private MockMvc mockMvc;

    private com.controller.AppointmentController controller;

    @Before
    public void setUp() {

        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    }



}
