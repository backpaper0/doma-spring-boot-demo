package com.example;

import java.util.List;

import org.seasar.doma.jdbc.criteria.QueryDsl;
import org.seasar.doma.jdbc.criteria.option.LikeOption;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ReservationDao {

    private final QueryDsl queryDsl;

    public ReservationDao(QueryDsl queryDsl) {
        this.queryDsl = queryDsl;
    }

    public List<Reservation> selectAll() {
        var r = new Reservation_();
        return queryDsl.from(r).fetch();
    }

    public List<Reservation> selectByName(String name) {
        var r = new Reservation_();
        return queryDsl.from(r).where(c -> c.like(r.name, name, LikeOption.prefix())).fetch();
    }

    @Transactional
    public int insert(Reservation reservation) {
        var r = new Reservation_();
        return queryDsl.insert(r).single(reservation).execute().getCount();
    }
}
