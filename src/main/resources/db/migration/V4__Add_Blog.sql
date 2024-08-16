CREATE TABLE image (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       type VARCHAR(50) NOT NULL,
                       data LONGBLOB NOT NULL
);
CREATE TABLE author (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        image_id BIGINT,
                        FOREIGN KEY (image_id) REFERENCES image(id) ON DELETE SET NULL
);

CREATE TABLE blog
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    title      VARCHAR(255) NOT NULL,
    content    LONGTEXT NOT NULL,
    posted     BOOLEAN DEFAULT FALSE,
    author_id  BIGINT NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    CONSTRAINT pk_blog PRIMARY KEY (id),
    CONSTRAINT FK_AUTHOR FOREIGN KEY (author_id) REFERENCES author(id)
);

CREATE TABLE blog_post_content
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    title      VARCHAR(255) NOT NULL,
    text       LONGTEXT NOT NULL,
    image_url  VARCHAR(255) NULL,
    blog_id    BIGINT,
    CONSTRAINT pk_blog_post_content PRIMARY KEY (id),
    CONSTRAINT FK_BLOG_POST_CONTENT_ON_BLOG FOREIGN KEY (blog_id) REFERENCES blog (id)
);

