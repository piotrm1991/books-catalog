CREATE TABLE IF NOT EXISTS test_entity
(
    id         BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `name`     VARCHAR(255)                               NOT NULL,
    created_at datetime(6)                                NOT NULL,
    updated_at datetime(6)                                NOT NULL,
    deleted_at datetime(6)                                NULL
);
