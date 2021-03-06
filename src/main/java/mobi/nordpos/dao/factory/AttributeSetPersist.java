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

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import mobi.nordpos.dao.model.AttributeSet;
import mobi.nordpos.dao.ormlite.AttributeSetDao;

/**
 * @author Andrey Svininykh <svininykh@gmail.com>
 */
public class AttributeSetPersist implements PersistFactory {

    ConnectionSource connectionSource;
    AttributeSetDao attributeSetDao;

    @Override
    public void init(ConnectionSource connectionSource) throws SQLException {
        this.connectionSource = connectionSource;
        attributeSetDao = new AttributeSetDao(connectionSource);
    }

    @Override
    public AttributeSet read(Object id) throws SQLException {
        try {
            return attributeSetDao.queryForId((UUID) id);
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public List<AttributeSet> readList() throws SQLException {
        try {
            QueryBuilder qb = attributeSetDao.queryBuilder().orderBy(AttributeSet.NAME, true);
            qb.where().isNotNull(AttributeSet.ID);
            return qb.query();
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public AttributeSet find(String column, Object value) throws SQLException {
        try {
            QueryBuilder qb = attributeSetDao.queryBuilder();
            qb.where().like(column, value);
            return (AttributeSet) qb.queryForFirst();
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public AttributeSet add(Object attributeSet) throws SQLException {
        try {
            return attributeSetDao.createIfNotExists((AttributeSet) attributeSet);
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Boolean change(Object attributeSet) throws SQLException {
        try {
            return attributeSetDao.update((AttributeSet) attributeSet) > 0;
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    @Override
    public Boolean delete(Object id) throws SQLException {
        try {
            return attributeSetDao.deleteById((UUID) id) > 0;
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

}
