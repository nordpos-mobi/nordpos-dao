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
package mobi.nordpos.dao.ormlite;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;
import mobi.nordpos.dao.model.Product;

/**
 * @author Andrey Svininykh <svininykh@gmail.com>
 */
public class ProductPersist extends BaseDaoImpl<Product, String> {

    Dao<Product, String> productDao;

    public ProductPersist(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Product.class);
    }

    public Product read(String id) throws SQLException {
        try {
            productDao = new ProductPersist(connectionSource);
            return productDao.queryForId(id);
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    public Product find(String table, String value) throws SQLException {
        try {
            productDao = new ProductPersist(connectionSource);
            QueryBuilder qb = productDao.queryBuilder();
            qb.where().like(table, value);
            return (Product) qb.queryForFirst();
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }
}
