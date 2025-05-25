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
 * This class defines all archive date model relevant keys.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 */
public final class ArchiveDate {

    /**
     * Archive date.
     */
    public static final String ARCHIVE_DATE = "archiveDate";

    /**
     * Archive dates.
     */
    public static final String ARCHIVE_DATES = "archiveDates";

    /**
     * Archive time.
     */
    public static final String ARCHIVE_TIME = "archiveTime";

    //// Transient ////
    /**
     * Key of archive date article count.
     */
    public static final String ARCHIVE_DATE_T_PUBLISHED_ARTICLE_COUNT = "archiveDatePublishedArticleCount";

    /**
     * Archive date year.
     */
    public static final String ARCHIVE_DATE_YEAR = "archiveDateYear";

    /**
     * Archive date month.
     */
    public static final String ARCHIVE_DATE_MONTH = "archiveDateMonth";

    /**
     * Private constructor.
     */
    private ArchiveDate() {
    }
}
