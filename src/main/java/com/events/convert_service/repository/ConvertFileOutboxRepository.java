package com.events.convert_service.repository;

import com.events.convert_service.entity.ConvertFileOutbox;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ConvertFileOutboxRepository extends JpaRepository<ConvertFileOutbox, UUID> {
	List<ConvertFileOutbox> findByPublishedAtIsNullOrderByCreatedAtAsc(Pageable pageable);

	@Modifying(clearAutomatically = true)
	@Query(value = """
				update ConvertFileOutbox t
					 set t.publishedAt = :publishedAt
				 where t.eventId in :eventIds
				""")
	void updatePublishedAt(@Param(value = "eventIds") List<UUID> eventIds,
	                       @Param(value = "publishedAt") LocalDateTime publishedAt);
}