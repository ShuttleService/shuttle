package com.real.apps.shuttle.controller

import com.real.apps.shuttle.config.MvcConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*

/**
 * Created by zorodzayi on 2016/07/16.
 */
@ContextConfiguration(classes = [MvcConfiguration])
@WebAppConfiguration
class UserControllerSpec extends spock.lang.Specification {

    @Autowired
    private WebApplicationContext applicationContext;
    MockMvc mockMvc;

    def setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build()
    }

    def 'bootstrap the test correctly'() {
        expect:
        applicationContext
    }

    def 'visiting /user-add should show user-add.html'() {
        expect:
        mockMvc.perform(get('/user/add')).andDo(print()).
                andExpect(status().isOk()).
                andExpect(view().name('user-add'))
    }
}
