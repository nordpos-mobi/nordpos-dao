/**
 * Copyright (c) 2012-2015 Nord Trading Network.
 *
 * http://www.nordpos.mobi
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package mobi.nordpos.dao.factory;

import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mobi.nordpos.dao.model.Payment;
import mobi.nordpos.dao.model.Receipt;
import mobi.nordpos.dao.ormlite.PaymentDao;

/**
 * @author Andrey Svininykh <svininykh@gmail.com>
 */
public class PaymentPersist implements PersistFactory {

    ConnectionSource connectionSource;
    PaymentDao paymentDao;

    @Override
    public void init(ConnectionSource connectionSource) throws SQLException {
        this.connectionSource = connectionSource;
        paymentDao = new PaymentDao(connectionSource);
    }

    @Override
    public Payment read(Object id) throws SQLException {
        try {
            return paymentDao.queryForId((UUID) id);
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public List<Payment> readList() throws SQLException {
        try {
            return paymentDao.queryForAll();
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Payment find(String column, Object value) throws SQLException {
        try {
            QueryBuilder qb = paymentDao.queryBuilder();
            qb.where().like(column, value);
            return (Payment) qb.queryForFirst();
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Payment add(Object payment) throws SQLException {
        try {
            return paymentDao.createIfNotExists((Payment) payment);
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Boolean change(Object payment) throws SQLException {
        try {
            return paymentDao.update((Payment) payment) > 0;
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Boolean delete(Object id) throws SQLException {
        try {
            return paymentDao.deleteById((UUID) id) > 0;
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    public List<Payment> readPaymentList(Receipt receipt) throws SQLException {
        CloseableWrappedIterable<Payment> iterator = receipt.getPaymentCollection().getWrappedIterable();
        List<Payment> list = new ArrayList<>();
        try {
            for (Payment payment : iterator) {
                list.add(payment);
            }
            return list;
        } finally {
            iterator.close();
        }
    }
}
