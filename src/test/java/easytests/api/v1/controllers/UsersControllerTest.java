package easytests.api.v1.controllers;

import easytests.api.v1.mappers.UsersMapper;
import easytests.auth.services.SessionServiceInterface;
import easytests.config.SwaggerRequestValidationConfig;
import easytests.core.models.*;
import easytests.core.options.builder.UsersOptionsBuilder;
import easytests.core.services.UsersService;
import easytests.support.JsonSupport;
import easytests.support.SubjectsSupport;
import easytests.support.UsersSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * @author SvetlanaTselikova
 */
@Import({UsersMapper.class, SwaggerRequestValidationConfig.class})
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UsersController.class, secure = false)
public class UsersControllerTest {
    private static String id = "id";
    private static String firstName = "firstName";
    private static String lastName = "lastName";
    private static String surname = "surname";
    private static String email = "email";
    private static String isAdmin = "isAdmin";
    private static String state = "state";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UsersService usersService;

    @MockBean
    private UsersOptionsBuilder usersOptionsBuilder;

    private UsersSupport usersSupport = new UsersSupport();

    private SubjectsSupport subjectsSupport = new SubjectsSupport();

    @MockBean
    private SessionServiceInterface sessionService;

    @Test
    public void testListSuccess() throws Exception {
        //when(this.sessionService.getUserModel().getIsAdmin()).thenReturn(true);
        final List<UserModelInterface> usersModels = new ArrayList<>();
        IntStream.range(0, 2).forEach(idx -> {
            final UserModel userModel = new UserModel();
            userModel.map(this.usersSupport.getEntityFixtureMock(idx));
            usersModels.add(userModel);
        });
        when(this.usersService.findAll()).thenReturn(usersModels);

        mvc.perform(get("/v1/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(new JsonSupport()
                        .with(new JsonSupport()
                                .with(id, usersModels.get(0).getId())
                                .with(firstName, usersModels.get(0).getFirstName())
                                .with(lastName, usersModels.get(0).getLastName())
                                .with(surname, usersModels.get(0).getSurname())
                                .with(email, usersModels.get(0).getEmail())
                                .with(isAdmin, usersModels.get(0).getIsAdmin())
                                .with(state, usersModels.get(0).getState()))
                        .with(new JsonSupport()
                                .with(id, usersModels.get(1).getId())
                                .with(firstName, usersModels.get(1).getFirstName())
                                .with(lastName, usersModels.get(1).getLastName())
                                .with(surname, usersModels.get(1).getSurname())
                                .with(email, usersModels.get(1).getEmail())
                                .with(isAdmin, usersModels.get(1).getIsAdmin())
                                .with(state, usersModels.get(1).getState()))
                        .build()
                ))
                .andReturn();
    }

    @Test
    public void testListForbidden() throws Exception {
        when(this.sessionService.getUserModel().getIsAdmin()).thenReturn(false);

        mvc.perform(get("/v1/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(content().string(""))
                .andReturn();
    }

    /**
     * testCreateSuccess
     */
    /**
     * testCreateWithIdFailed
     */
    /**
     * testCreateWithSubjectsFailed
     */
    /**
     * testUpdateSuccess
     */
    /**
     * testUpdateWithoutIdFailed
     */
    /**
     * testUpdateWithSubjectsFailed
     */
    /**
     * testShowSuccess
     */
    /**
     * testShowFailed
     */
    /**
     * testShowWithSubjectsSuccess
     */
    /**
     * testDeleteSuccess
     */
    /**
     * testDeleteFailed
     */
    /**
     * testShowMeSuccess
     */
    /**
     * testShowMeFailed
     */
    /**
     * testShowMeWithSubjectsSuccess
     */
}
