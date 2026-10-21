-- varchar(36) plutôt que uuid : le type uuid n'existe pas dans MySQL
CREATE TABLE `personal_access_tokens` (
                                          `id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
                                          `token` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
                                          `user_id` int(11) NOT NULL,
                                          `expires_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

ALTER TABLE `personal_access_tokens`
    ADD UNIQUE KEY `token` (`token`),
    ADD KEY `user_id` (`user_id`);