package com.example;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.springframework.transaction.annotation.Transactional;

@ConfigAutowireable
@Dao
public interface ReservationDao {
	@Select
	List<Reservation> selectAll();

	@Select
	List<Reservation> selectByName(String name);

	@Insert
	@Transactional
	int insert(Reservation reservation);
}
