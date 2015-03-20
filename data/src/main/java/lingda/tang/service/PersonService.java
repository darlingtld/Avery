package lingda.tang.service;

import lingda.tang.dao.PersonDao;
import lingda.tang.pojo.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by tangld on 2015/3/20.
 */
@Service
public class PersonService {

    @Autowired
    private PersonDao personDao;

    @Transactional
    public Person getPersonById(int id){
        return personDao.getPersonById(id);
    }
}
