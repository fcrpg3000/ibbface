/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) ForumServiceImpl.java 2013-08-03 23:55
 */

package com.ibbface.service.internal;

import com.google.common.collect.Range;
import com.ibbface.service.ForumService;
import com.ibbface.domain.exception.EntityNotFoundException;
import com.ibbface.domain.model.forum.Forum;
import com.ibbface.domain.model.forum.ForumField;
import com.ibbface.repository.forum.ForumRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static com.google.common.base.Preconditions.checkArgument;
import static com.ibbface.domain.model.forum.Forum.STATUS_DELETED;
import static com.ibbface.domain.model.forum.Forum.STATUS_PUBLISHED;
import static com.ibbface.domain.model.forum.Forum.STATUS_UNPUBLISHED;

/**
 * {@link ForumService} interface default implementation.
 *
 * @author Fuchun
 * @since 1.0
 */
@Service("forumService")
public class ForumServiceImpl implements ForumService {

    private ForumRepository forumRepository;


    private void checkForumId(Integer forumId) {
        checkArgument(forumId != null && forumId > 0,
                "The given `forumId` must be not null and greater than 0.");
    }

    private void checkStatus(Short status) {
        checkArgument(status != null && (STATUS_DELETED.equals(status)
                || STATUS_PUBLISHED.equals(status)
                || STATUS_UNPUBLISHED.equals(status)),
                String.format("The given `status` must be not null, and values in (%s, %s, %s)",
                        STATUS_DELETED, STATUS_PUBLISHED, STATUS_UNPUBLISHED));
    }

    /**
     * @see ForumService#addForum(Forum)
     */
    @Override
    @Transactional
    public void addForum(Forum forum) {
        Integer maxSortOrder = forumRepository.getMaxSortOrder();
        if (maxSortOrder == null) { // query error (assert not null)
            maxSortOrder = (int) forumRepository.count();
        }
        forum.setSortOrder(maxSortOrder + 1);
        forum.preInsert();
        forumRepository.save(forum);
    }

    /**
     * @see ForumService#changeSortOrder(Integer, Integer)
     */
    @Override
    @Transactional
    public void changeSortOrder(Integer forumId, Integer newSortOrder) {
        checkForumId(forumId);
        checkArgument(newSortOrder != null && newSortOrder > 0,
                "The given `newSortOrder` must be not null and greater than 0.");
        assert newSortOrder != null && newSortOrder > 0;
        Integer sortOrder = forumRepository.getSortOrder(forumId);
        if (sortOrder == null) { // Forum not found
            throw new EntityNotFoundException(forumId, Forum.class);
        }
        if (sortOrder.intValue() == newSortOrder.intValue()) {
            return;
        }
        if (newSortOrder > sortOrder) {
            forumRepository.subtractSortOrder(Range.closed(sortOrder + 1, newSortOrder));
        } else {
            forumRepository.addSortOrder(Range.closed(newSortOrder, sortOrder - newSortOrder));
        }
        forumRepository.updateSortOrder(forumId, newSortOrder);
    }

    public void modifyForumStatus(Integer forumId, Short status) {
        checkForumId(forumId);
        checkStatus(status);
        if (forumRepository.updateStatus(forumId, status) <= 0) {
            throw new EntityNotFoundException(forumId, Forum.class);
        }
    }

    /**
     * @see ForumService#logicDeleteForum(Integer)
     */
    @Override
    public void logicDeleteForum(Integer forumId) {
        modifyForumStatus(forumId, STATUS_DELETED);
    }

    /**
     * @see ForumService#publishForum(Integer)
     */
    @Override
    public void publishForum(Integer forumId) {
        modifyForumStatus(forumId, STATUS_PUBLISHED);
    }

    /**
     * @see ForumService#unpublishForum(Integer)
     */
    @Override
    public void unpublishForum(Integer forumId) {
        modifyForumStatus(forumId, STATUS_UNPUBLISHED);
    }

    /**
     * Add {@link com.ibbface.domain.model.forum.ForumField}s to the specified forum ({@link com.ibbface.domain.model.forum.ForumField#getForumId()}).
     *
     * @param forumFields the new {@link com.ibbface.domain.model.forum.ForumField}s.
     */
    @Override
    public void addForumFields(Iterable<ForumField> forumFields) {
        //To change body newError implemented methods use File | Settings | File Templates.
    }

    @Resource
    public void setForumRepository(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }
}
