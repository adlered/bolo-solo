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
package org.b3log.solo.service;

import org.b3log.latke.Keys;
import org.b3log.latke.ioc.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.repository.Transaction;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.service.annotation.Service;
import org.b3log.solo.model.Link;
import org.b3log.solo.repository.LinkRepository;
import org.json.JSONObject;

/**
 * Link management service.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @since 0.4.0
 */
@Service
public class LinkMgmtService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(LinkMgmtService.class);

    /**
     * Link repository.
     */
    @Inject
    private LinkRepository linkRepository;

    /**
     * Removes a link specified by the given link id.
     *
     * @param linkId the given link id
     * @throws ServiceException service exception
     */
    public void removeLink(final String linkId) throws ServiceException {
        final Transaction transaction = linkRepository.beginTransaction();

        try {
            linkRepository.remove(linkId);

            transaction.commit();
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.log(Level.ERROR, "Removes a link[id=" + linkId + "] failed", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Updates a link by the specified request json object.
     *
     * @param requestJSONObject the specified request json object, for example,
     *                          "link": {
     *                          "oId": "",
     *                          "linkTitle": "",
     *                          "linkAddress": "",
     *                          "linkDescription": "",
     *                          "linkIcon": ""
     *                          }
     *                          see {@link Link} for more details
     * @throws ServiceException service exception
     */
    public void updateLink(final JSONObject requestJSONObject) throws ServiceException {
        final Transaction transaction = linkRepository.beginTransaction();

        try {
            final JSONObject link = requestJSONObject.getJSONObject(Link.LINK);
            final String linkId = link.getString(Keys.OBJECT_ID);
            final JSONObject oldLink = linkRepository.get(linkId);

            link.put(Link.LINK_ORDER, oldLink.getInt(Link.LINK_ORDER));

            linkRepository.update(linkId, link);

            transaction.commit();
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.log(Level.ERROR, e.getMessage(), e);

            throw new ServiceException(e);
        }
    }

    /**
     * Changes the order of a link specified by the given link id with the
     * specified direction.
     *
     * @param linkId    the given link id
     * @param direction the specified direction, "up"/"down"
     * @throws ServiceException service exception
     */
    public void changeOrder(final String linkId, final String direction) throws ServiceException {
        final Transaction transaction = linkRepository.beginTransaction();

        try {
            final JSONObject srcLink = linkRepository.get(linkId);
            final int srcLinkOrder = srcLink.getInt(Link.LINK_ORDER);

            JSONObject targetLink = null;

            if ("up".equals(direction)) {
                targetLink = linkRepository.getUpper(linkId);
            } else { // Down
                targetLink = linkRepository.getUnder(linkId);
            }

            if (null == targetLink) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }

                LOGGER.log(Level.WARN, "Cant not find the target link of source link[order={0}]", srcLinkOrder);
                return;
            }

            // Swaps
            srcLink.put(Link.LINK_ORDER, targetLink.getInt(Link.LINK_ORDER));
            targetLink.put(Link.LINK_ORDER, srcLinkOrder);

            linkRepository.update(srcLink.getString(Keys.OBJECT_ID), srcLink);
            linkRepository.update(targetLink.getString(Keys.OBJECT_ID), targetLink);

            transaction.commit();
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.log(Level.ERROR, "Changes link's order failed", e);

            throw new ServiceException(e);
        }
    }

    /**
     * Adds a link with the specified request json object.
     *
     * @param requestJSONObject the specified request json object, for example,
     *                          {
     *                          "link": {
     *                          "linkTitle": "",
     *                          "linkAddress": "",
     *                          "linkDescription": "",
     *                          "linkIcon": ""
     *                          }
     *                          }, see {@link Link} for more details
     * @return generated link id
     * @throws ServiceException service exception
     */
    public String addLink(final JSONObject requestJSONObject) throws ServiceException {
        final Transaction transaction = linkRepository.beginTransaction();

        try {
            final JSONObject link = requestJSONObject.getJSONObject(Link.LINK);
            final int maxOrder = linkRepository.getMaxOrder();

            link.put(Link.LINK_ORDER, maxOrder + 1);
            final String ret = linkRepository.add(link);

            transaction.commit();

            return ret;
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.log(Level.ERROR, "Adds a link failed", e);
            throw new ServiceException(e);
        }
    }
}
