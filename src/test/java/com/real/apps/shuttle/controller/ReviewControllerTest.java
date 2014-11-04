package com.real.apps.shuttle.controller;

import com.google.gson.Gson;
import com.real.apps.shuttle.config.MvcConfiguration;
import com.real.apps.shuttle.model.Review;
import com.real.apps.shuttle.respository.RepositoryConfig;
import com.real.apps.shuttle.service.ReviewService;
import com.real.apps.shuttle.service.ServiceConfig;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by zorodzayi on 14/10/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MvcConfiguration.class})
@WebAppConfiguration
public class ReviewControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ReviewController controller;
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    private MockMvc mockMvc;
    @Mock
    private ReviewService service;
    private Logger logger = Logger.getLogger(ReviewControllerTest.class);

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldRenderReviewPage() throws Exception {
        mockMvc.perform(get("/" + ReviewController.VIEW_PAGE)).andDo(print()).andExpect(status().isOk()).andExpect(view().name(ReviewController.VIEW_PAGE));
    }

    @Test
    public void shouldCallListOnTheServiceAndReturnAListOfReviews() throws Exception {
        final int skip = 1, limit = 10;
        final Review review = new Review();
        String text = "Test Review";
        ObjectId id = ObjectId.get();
        review.setId(id);
        review.setReviews(new String[]{text});

        logger.debug(String.format("Object Id {toSting:%s,toMongoString:%s} ", id.toString(), id.toStringMongod()));
        List<Review> reviews = Arrays.asList(review);
        final Page<Review> page = new PageImpl<Review>(reviews);
        controller.setService(service);

        context.checking(new Expectations() {{
            oneOf(service).list(skip, limit);
            will(returnValue(page));
        }});

        mockMvc.perform(get(String.format("/%s/%d/%d", ReviewController.VIEW_PAGE, skip, limit))).andDo(print()).andExpect(jsonPath("$.content[0].reviews[0]").value(text));
    }

    @Test
    public void shouldCallDriverInsertAndReturnTheCreatedReview() throws Exception {
        String text = "Test Review Text";
        final Review review = new Review();
        review.setReviews(new String[]{text});

        controller.setService(service);

        context.checking(new Expectations() {{
            oneOf(service).insert(with(any(Review.class)));
            will(returnValue(review));
        }});

        String reviewString = new Gson().toJson(review);
        mockMvc.perform(post("/" + ReviewController.VIEW_PAGE).contentType(MediaType.APPLICATION_JSON).content(reviewString)).
                andDo(print()).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.reviews[0]").value(text));
    }

    @Test
    public void shouldCallServiceDeleteAndReturnTheDeletedReview() throws Exception {
        String[] reviews = new String[]{"Test Review To Delete"};
        final Review review = new Review();
        review.setReviews(reviews);

        String reviewString = new Gson().toJson(review);
        controller.setService(service);
        context.checking(new Expectations(){{
            oneOf(service).delete(with(any(Review.class)));
            will(returnValue(review));
        }});

        mockMvc.perform(delete("/"+ReviewController.VIEW_PAGE).contentType(MediaType.APPLICATION_JSON).content(reviewString)).andDo(print()).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.reviews[0]").value(reviews[0]));
    }

    @Test
    public void shouldCallServiceUpdateAndReturnTheUpdateReview() throws Exception {
        String text = "Test Review To Put";
        String[] reviews = new String[]{text};
        final Review review = new Review();
        review.setReviews(reviews);

        String reviewString = new Gson().toJson(review);
        controller.setService(service);
        context.checking(new Expectations(){{
            oneOf(service).update(with(any(Review.class)));
            will(returnValue(review));
        }});

        mockMvc.perform(put("/"+ReviewController.VIEW_PAGE).contentType(MediaType.APPLICATION_JSON).content(reviewString)).andDo(print()).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.reviews[0]").value(text));
    }

    @Test
    public void shouldServiceFindOneAndReturnTheFoundReview() throws Exception {
        final ObjectId id = ObjectId.get();
        final Review review = new Review();
        review.setId(id);

        context.checking(new Expectations(){{
            oneOf(service).findOne(id);
            will(returnValue(review));
        }});
        controller.setService(service);
        mockMvc.perform(get("/"+ReviewController.VIEW_PAGE+"/one/"+id)).andDo(print()).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.id._time").value(id.getTimestamp()));
    }
}
