package lc.cy.core.dao;

import lc.cy.core.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface LoginDao {
	public User getUserByID(@Param("id") String userID) throws Exception;
}
