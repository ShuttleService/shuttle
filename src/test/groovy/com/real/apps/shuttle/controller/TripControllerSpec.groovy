package com.real.apps.shuttle.controller

import com.real.apps.shuttle.config.MvcConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*

/**
 * Created by zorodzayi on 2016/07/18.
 */
@ContextConfiguration(classes = [MvcConfiguration])
@WebAppConfiguration
class TripControllerSpec extends spock.lang.Specification {
    @Autowired
    private WebApplicationContext webApplicationContext
    private MockMvc mockMvc

    def setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }

    def 'navigating to /trip-add returns trip-add.jsp'() {
        expect:
        mockMvc.perform(get('/trip-add')).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("trip-add"))
    }
}
