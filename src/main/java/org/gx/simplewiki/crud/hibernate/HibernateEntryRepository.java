package org.gx.simplewiki.crud.hibernate;

import org.gx.simplewiki.crud.EntryRepository;
import org.gx.simplewiki.exception.DataBaseError;
import org.gx.simplewiki.exception.entry.EntryNoneFound;
import org.gx.simplewiki.model.*;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
@Transactional
public class HibernateEntryRepository implements EntryRepository {

    // HIbernate会话
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Entry getById(String entry_id) throws DataBaseError, EntryNoneFound {
        try {
            List entrys = sessionFactory.getCurrentSession().createCriteria(Page.class).add(Restrictions.eq("id", entry_id)).list();
            if (entrys.isEmpty())
                throw new EntryNoneFound();

            return (Entry) entrys.get(0);
        } catch (HibernateException e) {
            throw new DataBaseError();
        }
    }

    @Override
    public Entry getByTitle(String entry_title) throws DataBaseError, EntryNoneFound {
        try {
            List entrys = sessionFactory.getCurrentSession().createCriteria(Entry.class).add(Restrictions.eq("title", entry_title)).list();
            if (entrys.isEmpty())
                throw new EntryNoneFound();

            return (Entry) entrys.get(0);
        } catch (HibernateException e) {
            throw new DataBaseError();
        }

    }

    @Override
    public void update(Entry entry) throws DataBaseError {
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(entry);
        } catch (HibernateException e) {
            throw new DataBaseError();
        }
    }

    @Override
    public Label getByTitle(String title) throws DataBaseError  {
        try {
            List labels = sessionFactory.getCurrentSession().createCriteria(Label.class).add(Restrictions.eq("title", title)).list();
            if(labels.size() > 0) {
                return (Label) labels.get(0);
            }
        } catch (HibernateException e) {
            throw new DataBaseError();
        }

        return null;
    }

    @Override
    public List<Label> getLabels() throws DataBaseError {
        try {
            return sessionFactory.getCurrentSession().createQuery("from Label").list();
        } catch (HibernateException e) {
            throw new DataBaseError();
        }
    }

    @Override
    public void delete(Entry entry) {
        sessionFactory.getCurrentSession().delete(entry);
    }

    @Override
    public void delete(Label label) {
        sessionFactory.getCurrentSession().delete(label);
    }





    @Override
    public List<Entry> getList() throws DataBaseError {
        try {
            return sessionFactory.getCurrentSession().createQuery("from Entry").list();
        } catch (HibernateException e) {
            throw new DataBaseError();
        }
    }

    @Override
    public void update(Label label) throws DataBaseError {
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(label);
        } catch (HibernateException e) {
            throw new DataBaseError();
        }

    }


}
