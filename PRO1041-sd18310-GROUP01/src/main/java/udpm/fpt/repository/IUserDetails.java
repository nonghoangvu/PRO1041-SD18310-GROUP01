package udpm.fpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import udpm.fpt.model.UserDetails;

/**
 *
 * @author BinhQuoc
 */
public interface IUserDetails extends JpaRepository<UserDetails, Integer>{
    UserDetails findByUser_Username(String username);
}
