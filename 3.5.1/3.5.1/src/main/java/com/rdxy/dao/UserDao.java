package com.rdxy.dao;

import com.rdxy.entity.User;

import java.util.List;

public interface UserDao {

    public User getById(User user);

    List<User> getAll(String msg);

    boolean deleteById(int id);

    boolean deleteByIds(int[] ids);

    boolean insert(User user);

    boolean update(User user);
}
