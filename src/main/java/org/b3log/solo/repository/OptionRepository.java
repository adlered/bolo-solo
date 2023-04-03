/*
 * Bolo - A stable and beautiful blogging system based in Solo.
 * Copyright (c) 2020, https://github.com/adlered
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package org.b3log.solo.repository;

import org.b3log.latke.Keys;
import org.b3log.latke.ioc.Inject;
import org.b3log.latke.repository.*;
import org.b3log.latke.repository.annotation.Repository;
import org.b3log.solo.cache.OptionCache;
import org.b3log.solo.model.Option;
import org.json.JSONObject;

import java.util.List;

/**
 * Option repository.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @since 0.6.0
 */
@Repository
public class OptionRepository extends AbstractRepository {

    /**
     * Option cache.
     */
    @Inject
    private OptionCache optionCache;

    /**
     * Public constructor.
     */
    public OptionRepository() {
        super(Option.OPTION);
    }

    @Override
    public void remove(final String id) throws RepositoryException {
        final JSONObject option = get(id);
        if (null == option) {
            return;
        }

        super.remove(id);
        optionCache.removeOption(id);

        final String category = option.optString(Option.OPTION_CATEGORY);
        optionCache.removeCategory(category);
    }

    @Override
    public JSONObject get(final String id) throws RepositoryException {
        JSONObject ret = optionCache.getOption(id);
        if (null != ret) {
            return ret;
        }

        ret = super.get(id);
        if (null == ret) {
            return null;
        }

        optionCache.putOption(ret);

        return ret;
    }

    @Override
    public void update(final String id, final JSONObject option, final String... propertyNames) throws RepositoryException {
        super.update(id, option, propertyNames);

        option.put(Keys.OBJECT_ID, id);
        optionCache.putOption(option);
    }


}
