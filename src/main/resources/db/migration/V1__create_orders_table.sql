CREATE TABLE orders (
                        id UUID PRIMARY KEY,
                        customer_id VARCHAR(100) NOT NULL,
                        total_price DECIMAL(19, 2) NOT NULL,
                        status VARCHAR(20) NOT NULL,
                        created_at TIMESTAMP NOT NULL
);