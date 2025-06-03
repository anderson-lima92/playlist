package com.lima.api.playlist.adapter.out.persistence.repository;

import com.lima.api.playlist.adapter.out.persistence.entity.PlaylistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<PlaylistEntity, String> {
}