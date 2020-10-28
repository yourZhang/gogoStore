package com.store.service;

import com.store.pojo.Album;

import java.util.List;

public interface AlbumService {

    List<Album> findAll();

    Integer add(Album album);

}
