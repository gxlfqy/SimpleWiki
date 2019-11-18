package org.gx.simplewiki.crud.hibernate;

import org.gx.simplewiki.crud.PageRepository;
import org.gx.simplewiki.exception.DataBaseError;
import org.gx.simplewiki.exception.page.PageNoneFound;
import org.gx.simplewiki.model.Page;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class HibernatePageRepository implements PageRepository {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void update(Page page) throws DataBaseError {
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(page);
        } catch (HibernateException e) {
            throw new DataBaseError();
        }

    }

    @Override
    public void delete(Page page) throws DataBaseError {
        try {
            sessionFactory.getCurrentSession().delete(page);
        } catch (HibernateException e) {
            throw new DataBaseError();
        }
    }

    @Override
    public Page getPage(String page_id) throws DataBaseError, PageNoneFound {
        try {
            List pages = sessionFactory.getCurrentSession().createCriteria(Page.class).add(Restrictions.eq("id", page_id)).list();
            if (pages.isEmpty())
                throw new PageNoneFound();

            return (Page) pages.get(0);
        } catch (HibernateException e) {
            throw new DataBaseError();
        }
    }


}
