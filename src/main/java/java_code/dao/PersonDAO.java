package java_code.dao;

import java_code.models.Book;
import java_code.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> showPeople(){
        return jdbcTemplate.query("select * from person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person showPerson(int id){
        return jdbcTemplate.query("select * from person where id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void updatePerson(int id, Person updatedPerson) {
        jdbcTemplate.update("update person set name=?, birthdate=? where id=?", updatedPerson.getName(),
                updatedPerson.getBirthdate(), id);
    }

    public void addPerson(Person person){
        jdbcTemplate.update("insert into person(name, birthdate) values(?,?)", person.getName(), person.getBirthdate());
    }

    public void deletePerson(int id){
        jdbcTemplate.update("delete from person where id =?", id);
    }

    public List<Book> getBooks(int id){
    String SQl ="select * from book where user_id =?";
        return jdbcTemplate.query(SQl, new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }


    ///////////////////////
    ///////PersonValidator
    //////////////////////

    public Optional<Person> ifNameUnique(String email){
        return jdbcTemplate.query("select * from person where name=?", new Object[]{email}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }
}
