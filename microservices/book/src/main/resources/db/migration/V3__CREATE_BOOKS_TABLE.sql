CREATE TABLE IF NOT EXISTS books(
    id VARCHAR(255) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    gender VARCHAR(50) NOT NULL,
    author VARCHAR(255) NOT NULL,
    content_path VARCHAR(300) NOT NULL
);