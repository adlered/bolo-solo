/*
 * Bolo - A stable and beautiful blogging system based in Solo.
 * Copyright (c) 2020-present, https://github.com/bolo-blog
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
package org.b3log.solo.model;

/**
 * This class defines all link model relevant keys.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @since 0.3.1
 */
public final class Link {

    /**
     * Link.
     */
    public static final String LINK = "link";

    /**
     * Links.
     */
    public static final String LINKS = "links";

    /**
     * Key of title.
     */
    public static final String LINK_TITLE = "linkTitle";

    /**
     * Key of address.
     */
    public static final String LINK_ADDRESS = "linkAddress";

    /**
     * Key of description.
     */
    public static final String LINK_DESCRIPTION = "linkDescription";

    /**
     * Key of icon URL.
     */
    public static final String LINK_ICON = "linkIcon";

    /**
     * Key of order.
     */
    public static final String LINK_ORDER = "linkOrder";

    /**
     * Private constructor.
     */
    private Link() {
    }
}
