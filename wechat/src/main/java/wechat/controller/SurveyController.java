package wechat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by darlingtld on 2015/2/10.
 */
@Controller
@RequestMapping("survey")
public class SurveyController {

    private static final Logger logger = LoggerFactory.getLogger(SurveyController.class);

    @RequestMapping(value = "/answer", method = RequestMethod.POST)
    public void submitAnswers(@RequestParam("answers") String answer, HttpServletRequest request, HttpServletResponse response) {
        logger.info(answer);

    }

}

