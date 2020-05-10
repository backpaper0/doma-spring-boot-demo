package com.example;

import java.util.List;

import org.seasar.doma.jdbc.criteria.Entityql;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ReservationDao {

    private final Entityql entityql;

    public ReservationDao(Entityql entityql) {
        this.entityql = entityql;
    }

    public List<Reservation> selectAll() {
        final Reservation_ r = new Reservation_();
        return entityql.from(r).fetch();
    }

    public List<Reservation> selectByName(String name) {
        final Reservation_ r = new Reservation_();
        return entityql.from(r).where(c -> c.like(r.name, name + "%")).fetch();
    }

    @Transactional
    public Reservation insert(Reservation reservation) {
        final Reservation_ r = new Reservation_();
        return entityql.insert(r, reservation).execute();
    }
}
