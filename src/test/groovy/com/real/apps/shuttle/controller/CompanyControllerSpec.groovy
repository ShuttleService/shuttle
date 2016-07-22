package com.real.apps.shuttle.controller

import com.real.apps.shuttle.config.MvcConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view


/**
 * Created by zorodzayi on 2016/07/18.
 */
@WebAppConfiguration
@ContextConfiguration(classes = [MvcConfiguration])
class CompanyControllerSpec extends spock.lang.Specification {
    @Autowired
    private WebApplicationContext webApplicationContext
    private MockMvc mockMvc


    def setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }

    def 'navigating to /company-add returns the company-add.jsp page'() {
        expect:
        mockMvc.perform(get("/company-add")).andDo(print()).
                andExpect(status().isOk()).
                andExpect(view().name("company-add"))
    }
}
