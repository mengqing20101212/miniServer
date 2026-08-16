ALTER TABLE `player` MODIFY COLUMN `modules` MEDIUMBLOB DEFAULT NULL;
ALTER TABLE `player_module`
  DROP PRIMARY KEY,
  ADD COLUMN `id` BIGINT NOT NULL AUTO_INCREMENT FIRST,
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uk_player_module_player_id_module_id` (`player_id`, `module_id`);
