import static db.Tables.USER;
import java.sql.*;
import java.util.Optional;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

public class Main {

	public static void main(String[] args) {

		String url = "jdbc:h2:mem:test1;DB_CLOSE_DELAY=-1;INIT=runscript from 'src/main/resources/create.sql'";
		try {
			Connection conn = DriverManager.getConnection(url);
			DSLContext create = DSL.using(conn, SQLDialect.H2);
			Optional<User> user = create.select(USER.ID, USER.NAME, USER.ADDRESS).from(USER).fetchOptionalInto(User.class);
			user.map(u -> {
				System.out.println(u);
				return null;
			});
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}