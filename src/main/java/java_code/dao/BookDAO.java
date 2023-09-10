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
public class BookDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> showBooks() {
        return jdbcTemplate.query("select * from book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book showBook(int id) {
        return jdbcTemplate.query("select * from book where id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public void updateBook(int id, Book updatedBook) {
        jdbcTemplate.update("update book set name=?,author=?,year=? where id=?",
                updatedBook.getName(), updatedBook.getAuthor(), updatedBook.getYear(), id);
    }

    public void addBook(Book book) {
        jdbcTemplate.update("insert into book(name,author,year) values(?,?,?)",
                book.getName(), book.getAuthor(), book.getYear());
    }

    public void deleteBook(int id) {
        jdbcTemplate.update("delete from book where id=?", id);
    }

    public Optional<Person> getOwnerOfBook(int id) {
        String SQL = "select distinct p.id, p.name, date_of_birth from person p join book b on(p.id =\n" +
                "                (select user_id from book where id = ?))";
        return jdbcTemplate.query(SQL, new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    public void releaseBook(int id) {
        String SQL = "update book set user_id = null where id = ?";
        jdbcTemplate.update(SQL, id);
    }

    public void assignBook(int user_id, int id) {
        String SQl = "update book set user_id = ? where id = ?";
        jdbcTemplate.update(SQl, user_id, id);
    }
}
