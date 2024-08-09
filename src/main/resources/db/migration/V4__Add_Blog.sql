CREATE TABLE blog
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    title      VARCHAR(255) NULL,
    content    LONGTEXT NULL,
    author     VARCHAR(255) NULL,
    created_at DATETIME NULL,
    updated_at DATETIME NULL,
    CONSTRAINT pk_blog PRIMARY KEY (id)
);

CREATE TABLE blog_post_content
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    text       LONGTEXT NULL,
    image_url  VARCHAR(255) NULL,
    created_at DATETIME NULL,
    updated_at DATETIME NULL,
    blog_id    BIGINT NULL,
    CONSTRAINT pk_blog_post_content PRIMARY KEY (id),
    CONSTRAINT FK_BLOG_POST_CONTENT_ON_BLOG FOREIGN KEY (blog_id) REFERENCES blog (id)
);
