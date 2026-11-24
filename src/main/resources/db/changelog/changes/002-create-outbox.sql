CREATE TABLE convert_file_outbox
(
    event_id     UUID PRIMARY KEY,
    event_type   VARCHAR(100) NOT NULL,
    payload      JSONB        NOT NULL,
    created_at   TIMESTAMP    NOT NULL DEFAULT now(),
    published_at TIMESTAMP
);