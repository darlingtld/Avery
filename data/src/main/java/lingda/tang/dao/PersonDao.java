package lingda.tang.dao;

import lingda.tang.pojo.Person;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by tangld on 2015/3/20.
 */
@Repository
public class PersonDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Person getPersonById(int id){
        return (Person) sessionFactory.getCurrentSession().get(Person.class, id);
    }
}
