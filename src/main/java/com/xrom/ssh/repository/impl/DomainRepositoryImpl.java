package com.xrom.ssh.repository.impl;


import com.xrom.ssh.repository.DomainRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by XRom
 * On 11/16/2017.11:55 PM
 */
@Repository
public class DomainRepositoryImpl<T extends Serializable, PK extends Serializable> implements DomainRepository< T, PK> {
    // 实体类类型(由构造方法自己主动赋值)
    private Class<T> entityClass;

    // 构造方法，依据实例类自己主动获取实体类类型
    public DomainRepositoryImpl() {
        this.entityClass = null;
        Class c = getClass();
        Type t = c.getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            Type[] p = ((ParameterizedType) t).getActualTypeArguments();
            this.entityClass = (Class<T>) p[0];
        }
    }
    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return this.sessionFactory.openSession();
    }

    @Override
    public T load(PK id) {
        return (T) getCurrentSession().load(entityClass, id);
    }

    @Override
    public T get(PK id) {
        return (T) getCurrentSession().get(entityClass, id);
    }

    @Override
    public List<T> findAll() {
        return null;
    }

    @Override
    public void persist(T entity) {
        getCurrentSession().persist(entity);
    }

    @Override
    public PK save(T entity) {
        return (PK) getCurrentSession().save(entity);
    }

    @Override
    public void saveOrUpdate(T entity) {
        getCurrentSession().saveOrUpdate(entity);
    }

    @Override
    public void delete(PK id) {
        T T = load(id);
        getCurrentSession().delete(T);
    }

    @Override
    public void flush() {
        getCurrentSession().flush();
    }
}
