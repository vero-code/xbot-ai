CREATE TABLE IF NOT EXISTS categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code INT NOT NULL
);

INSERT INTO categories (name, code) VALUES
    ('Autos and Vehicles', 47),
    ('Beauty and Fashion', 22),
    ('Business and Finance', 7),
    ('Climate', 13),
    ('Entertainment', 3),
    ('Food and Drink', 71),
    ('Games', 8),
    ('Health', 45),
    ('Hobbies and Leisure', 29),
    ('Jobs and Education', 958),
    ('Law and Government', 65),
    ('Other', 41),
    ('Pets and Animals', 66),
    ('Politics', 396),
    ('Science', 174),
    ('Shopping', 18),
    ('Sports', 20),
    ('Technology', 5),
    ('Travel and Transportation', 67);