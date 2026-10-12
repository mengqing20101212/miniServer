-- Run against the intended miniServer database before starting the upgraded services.
-- Existing IDs are preserved. AUTO_INCREMENT remains for old schema compatibility;
-- RocksService always supplies application-generated IDs.
ALTER TABLE login MODIFY COLUMN id BIGINT NOT NULL;
ALTER TABLE share_daily MODIFY COLUMN id BIGINT NOT NULL AUTO_INCREMENT;
ALTER TABLE share_week MODIFY COLUMN id BIGINT NOT NULL AUTO_INCREMENT;
ALTER TABLE share_month MODIFY COLUMN id BIGINT NOT NULL AUTO_INCREMENT;
ALTER TABLE share_enum_config MODIFY COLUMN id BIGINT NOT NULL AUTO_INCREMENT;
-- GM tables: run these statements only in databases containing the GM schema.
ALTER TABLE gm_menu MODIFY COLUMN id BIGINT NOT NULL AUTO_INCREMENT,
    MODIFY COLUMN parent_id BIGINT DEFAULT NULL;
ALTER TABLE gm_role MODIFY COLUMN id BIGINT NOT NULL AUTO_INCREMENT;
ALTER TABLE gm_role_menu MODIFY COLUMN id BIGINT NOT NULL AUTO_INCREMENT,
    MODIFY COLUMN role_id BIGINT DEFAULT NULL, MODIFY COLUMN menu_id BIGINT DEFAULT NULL;
ALTER TABLE gm_role_permission MODIFY COLUMN id BIGINT NOT NULL AUTO_INCREMENT,
    MODIFY COLUMN role_id BIGINT DEFAULT NULL;
ALTER TABLE gm_admin MODIFY COLUMN role_id BIGINT DEFAULT NULL;
CREATE TABLE IF NOT EXISTS rocks_entity_commit (
    owner_id VARCHAR(36) NOT NULL,
    entity_key VARCHAR(64) NOT NULL,
    version BIGINT NOT NULL,
    PRIMARY KEY (owner_id, entity_key)
) ENGINE=InnoDB;
