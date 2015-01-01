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
import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mobi.nordpos.dao.model.Product;
import mobi.nordpos.dao.model.ProductCategory;

/**
 * @author Andrey Svininykh <svininykh@gmail.com>
 */
public class ProductCategoryPersist extends BaseDaoImpl<ProductCategory, String> {

    Dao<ProductCategory, String> productCategoryDao;

    public ProductCategoryPersist(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, ProductCategory.class);
    }

    public List<ProductCategory> readList() throws SQLException {
        try {
            productCategoryDao = new ProductCategoryPersist(connectionSource);
            QueryBuilder qb = productCategoryDao.queryBuilder().orderBy(ProductCategory.NAME, true);
            qb.where().isNotNull(ProductCategory.ID);
            return qb.query();
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    public List<Product> readProductList(ProductCategory category) throws SQLException {
        CloseableWrappedIterable<Product> iterator = category.getProductCollection().getWrappedIterable();
        List<Product> list = new ArrayList<>();
        try {
            for (Product product : iterator) {
                list.add(product);
            }
            return list;
        } finally {
            iterator.close();
        }
    }

    public ProductCategory read(String id) throws SQLException {
        try {
            productCategoryDao = new ProductCategoryPersist(connectionSource);
            return productCategoryDao.queryForId(id);
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    public ProductCategory find(String column, String value) throws SQLException {
        try {
            productCategoryDao = new ProductCategoryPersist(connectionSource);
            QueryBuilder qb = productCategoryDao.queryBuilder();
            qb.where().like(column, value);
            return (ProductCategory) qb.queryForFirst();
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    public ProductCategory add(ProductCategory category) throws SQLException {
        try {
            productCategoryDao = new ProductCategoryPersist(connectionSource);
            return productCategoryDao.createIfNotExists(category);
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    public Boolean change(ProductCategory category) throws SQLException {
        try {
            productCategoryDao = new ProductCategoryPersist(connectionSource);
            return productCategoryDao.update(category) > 0;
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    public Boolean delete(String id) throws SQLException {
        try {
            productCategoryDao = new ProductCategoryPersist(connectionSource);
            return productCategoryDao.deleteById(id) > 0;
        } finally {
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }
}