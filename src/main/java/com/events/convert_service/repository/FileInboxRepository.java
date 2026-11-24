package com.events.convert_service.repository;

import com.events.convert_service.entity.FileInbox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileInboxRepository extends JpaRepository<FileInbox, UUID> {

}
