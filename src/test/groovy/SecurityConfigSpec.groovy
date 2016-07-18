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

    @Unroll("user #subjectUser must give http status code #expectedStatus on url /agent-add")
    def 'only admins can access /agent-add to add agents'() {

        expect:
        mockMvc.perform(get('/agent-add').with(user(subjectUser))).andDo(print()).
                andExpect(status().is(expectedStatus))

        where: 'the expected statuses for the different roles are'
        subjectUser     || expectedStatus
        admin(id)       || 200
        agent(id)       || 403
        world(id)       || 403
        companyUser(id) || 403
        anonymous(id)   || 403
    }

    @Unroll("user #subjectUser must give http status code #expectedStatus on url /company-add")
    def 'only admins and agents can access /company-add to add companies'() {
        expect:
        mockMvc.perform(get('/company-add').with(user(subjectUser))).andDo(print()).
                andExpect(status().is(expectedStatus))
        where: 'the expected statuses for the different roles are'
        subjectUser     || expectedStatus
        admin(id)       || 200
        agent(id)       || 200
        world(id)       || 403
        companyUser(id) || 403
        anonymous(id)   || 403
    }


    @Unroll("user #subjectUser must give http status code #expectedStatus on url /driver-add")
    def 'only company users and admins  can access the /driver-add url'() {
        expect:
        mockMvc.perform(get("/driver-add").with(user(subjectUser))).andDo(print())
                .andExpect(status().is(expectedStatus))
        where: 'the expected http status codes for the different roles are'
        subjectUser     || expectedStatus
        admin(id)       || 200
        companyUser(id) || 200
        agent(id)       || 403
        world(id)       || 403
        anonymous(id)   || 403
    }

    @Unroll("user #subjectUser must give http status code #expectedStatus on urls #url")
    def 'only logged in users can navigate to to the /review and /review-add pages'() {
        expect:
        mockMvc.perform(get(url).with(user(subjectUser))).andDo(print())
                .andExpect(status().is(expectedStatus))
        where: 'the expected http status code for the different urls for each roles are'
        url           | subjectUser     || expectedStatus
        '/review'     | admin(id)       || 200
        '/review'     | agent(id)       || 200
        '/review'     | anonymous(id)   || 403
        '/review'     | companyUser(id) || 200
        '/review'     | world(id)       || 200
        '/review-add' | admin(id)       || 200
        '/review-add' | agent(id)       || 200
        '/review-add' | anonymous(id)   || 403
        '/review-add' | companyUser(id) || 200
        '/review-add' | world(id)       || 200
    }

    @Unroll("user #subjectUser must give http status code #expectedStatus on /trip-add")
    def 'only logged in users can navigate to the url /trip-add'() {
        expect:
        mockMvc.perform(get('/trip-add').with(user(subjectUser))).andDo(print())
                .andExpect(status().is(expectedStatus))

        where: 'the expected http status codes for the roles are below'
        subjectUser     || expectedStatus
        admin(id)       || 200
        agent(id)       || 200
        anonymous(id)   || 403
        companyUser(id) || 200
        world(id)       || 200
    }

    @Unroll("user #subjectUser must give http status code #expectedStatusCode on /vehicle-add page")
    def 'only admins and company user can access the vehicle-add page'() {
        expect:
        mockMvc.perform(get('/vehicle-add').with(user(subjectUser))).andDo(print())
                .andExpect(status().is(expectedStatusCode))
        where: 'the expected http status code for the roles are as below'
        subjectUser     || expectedStatusCode
        admin(id)       || 200
        companyUser(id) || 200
        anonymous(id)   || 403
        agent(id)       || 403
        world(id)       || 403
    }
}
