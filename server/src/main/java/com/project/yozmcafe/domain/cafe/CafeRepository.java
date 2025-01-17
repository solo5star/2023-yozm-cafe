package com.project.yozmcafe.domain.cafe;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CafeRepository extends JpaRepository<Cafe, Long> {

    Slice<Cafe> findSliceBy(Pageable pageable);

    @Query("SELECT c.id FROM Cafe c ORDER BY c.likeCount DESC")
    List<Long> findCafeIdsOrderByLikeCount(Pageable pageable);

    @Query("SELECT c FROM Cafe c " +
            "JOIN FETCH c.images.urls " +
            "WHERE c.id IN :ids " +
            "ORDER BY c.likeCount DESC ")
    List<Cafe> findCafesByIdsOrderByLikeCount(@Param("ids") List<Long> ids);
  
}
