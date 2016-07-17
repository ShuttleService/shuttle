import com.real.apps.shuttle.config.MvcConfiguration
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Unroll

import javax.servlet.Filter

import static com.real.apps.shuttle.controller.UserDetailsUtils.*
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * Created by zorodzay on 2016/07/17.
 */
@ContextConfiguration(classes = [MvcConfiguration])
@WebAppConfiguration
class SecurityConfigSpec extends spock.lang.Specification {
    @Autowired
    WebApplicationContext applicationContext
    @Autowired
    Filter springSecurityFilterChain
    MockMvc mockMvc
    private static final ObjectId id = ObjectId.get()

    def setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).addFilter(springSecurityFilterChain).build()
    }

    def 'access to the signup page is open to all'() {
        expect:
        mockMvc.perform(get("/signup")).andDo(print()).andExpect(status().isOk())
    }

    @Unroll("user #subjectUser must give #expectedStatus on url /agent-add")
    def 'only admins can access /agent-add to add agents'() {

        expect:
        mockMvc.perform(get('/agent-add').with(user(subjectUser))).andDo(print()).
                andExpect(status().is(expectedStatus))

        where: 'the expected status for the different roles are'
        subjectUser     || expectedStatus
        admin(id)       || 200
        agent(id)       || 403
        world(id)       || 403
        companyUser(id) || 403
    }
}
