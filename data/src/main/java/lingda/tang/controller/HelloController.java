package lingda.tang.controller;

import lingda.tang.pojo.Person;
import lingda.tang.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public
    @ResponseBody
    String printWelcome() {
        return "hello";
    }

    @RequestMapping(value = "/person/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    Person getPersonById(@PathVariable("id") int id) {
        return personService.getPersonById(id);
    }
}