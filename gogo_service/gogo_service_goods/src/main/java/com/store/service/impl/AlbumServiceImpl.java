package com.store.service.impl;

import com.store.mapper.AlbumMapper;
import com.store.pojo.Album;
import com.store.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: gogo-store
 * @description:
 * @author: xiaozhang6666
 * @create: 2020-10-27 16:39
 **/
@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    AlbumMapper albumMapper;

    @Override
    public List<Album> findAll() {
        final List<Album> albums = albumMapper.selectAll();
        return albums;
    }

    @Override
    public Integer add(Album album) {
        final int insert = albumMapper.insert(album);
        return insert;
    }
}
