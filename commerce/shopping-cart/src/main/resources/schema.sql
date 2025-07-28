CREATE TABLE IF NOT EXISTS shopping_carts (
    cart_id UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    state VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS products_in_carts (
    cart_id UUID REFERENCES shopping_carts (cart_id),
    product_id UUID,
    quantity INTEGER
);
