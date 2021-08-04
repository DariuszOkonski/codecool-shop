--
-- DROP TABLE country;
-- DROP TABLE zip_code;
-- DROP TABLE city;
-- DROP TABLE street;
-- DROP TABLE address;
-- DROP TABLE "user";
-- DROP TABLE cart;
-- DROP TABLE product;
-- DROP TABLE cart_items;
-- DROP TABLE customer_data;
-- DROP TABLE department;
-- DROP TABLE order_status;
-- DROP TABLE "order";
-- DROP TABLE product_category;
-- DROP TABLE supplier;
-- DROP TABLE user_session;


ALTER TABLE IF EXISTS ONLY country DROP CONSTRAINT IF EXISTS PK_country CASCADE;

ALTER TABLE IF EXISTS ONLY zip_code DROP CONSTRAINT IF EXISTS PK_zip_code CASCADE;

ALTER TABLE IF EXISTS ONLY city DROP CONSTRAINT IF EXISTS PK_city CASCADE;
ALTER TABLE IF EXISTS ONLY city DROP CONSTRAINT IF EXISTS FK_163 CASCADE;
ALTER TABLE IF EXISTS ONLY city DROP CONSTRAINT IF EXISTS FK_201 CASCADE;

ALTER TABLE IF EXISTS ONLY street DROP CONSTRAINT IF EXISTS PK_street_number CASCADE;

ALTER TABLE IF EXISTS ONLY address DROP CONSTRAINT IF EXISTS PK_address CASCADE;
ALTER TABLE IF EXISTS ONLY address DROP CONSTRAINT IF EXISTS FK_236 CASCADE;
ALTER TABLE IF EXISTS ONLY address DROP CONSTRAINT IF EXISTS FK_245 CASCADE;
ALTER TABLE IF EXISTS ONLY address DROP CONSTRAINT IF EXISTS FK_248 CASCADE;
ALTER TABLE IF EXISTS ONLY address DROP CONSTRAINT IF EXISTS FK_251 CASCADE;

ALTER TABLE IF EXISTS ONLY "user" DROP CONSTRAINT IF EXISTS PK_user CASCADE;

ALTER TABLE IF EXISTS ONLY cart DROP CONSTRAINT IF EXISTS PK_cart CASCADE;
ALTER TABLE IF EXISTS ONLY cart DROP CONSTRAINT IF EXISTS FK_93 CASCADE;

ALTER TABLE IF EXISTS ONLY product DROP CONSTRAINT IF EXISTS PK_product CASCADE;
ALTER TABLE IF EXISTS ONLY product DROP CONSTRAINT IF EXISTS FK_55 CASCADE;
ALTER TABLE IF EXISTS ONLY product DROP CONSTRAINT IF EXISTS FK_63 CASCADE;

ALTER TABLE IF EXISTS ONLY cart_items DROP CONSTRAINT IF EXISTS PK_cartitems CASCADE;
ALTER TABLE IF EXISTS ONLY cart_items DROP CONSTRAINT IF EXISTS FK_78 CASCADE;
ALTER TABLE IF EXISTS ONLY cart_items DROP CONSTRAINT IF EXISTS FK_85 CASCADE;

ALTER TABLE IF EXISTS ONLY customer_data DROP CONSTRAINT IF EXISTS PK_customer_data CASCADE;
ALTER TABLE IF EXISTS ONLY customer_data DROP CONSTRAINT IF EXISTS FK_145 CASCADE;
ALTER TABLE IF EXISTS ONLY customer_data DROP CONSTRAINT IF EXISTS FK_254 CASCADE;
ALTER TABLE IF EXISTS ONLY customer_data DROP CONSTRAINT IF EXISTS FK_257 CASCADE;

ALTER TABLE IF EXISTS ONLY department DROP CONSTRAINT IF EXISTS PK_department CASCADE;

ALTER TABLE IF EXISTS ONLY order_status DROP CONSTRAINT IF EXISTS PK_order_status CASCADE;

ALTER TABLE IF EXISTS ONLY "order" DROP CONSTRAINT IF EXISTS PK_order CASCADE;
ALTER TABLE IF EXISTS ONLY "order" DROP CONSTRAINT IF EXISTS FK_106 CASCADE;
ALTER TABLE IF EXISTS ONLY "order" DROP CONSTRAINT IF EXISTS FK_109 CASCADE;
ALTER TABLE IF EXISTS ONLY "order" DROP CONSTRAINT IF EXISTS FK_116 CASCADE;


ALTER TABLE IF EXISTS ONLY product_category DROP CONSTRAINT IF EXISTS PK_productcategory CASCADE;
ALTER TABLE IF EXISTS ONLY product_category DROP CONSTRAINT IF EXISTS FK_71 CASCADE;

ALTER TABLE IF EXISTS ONLY supplier DROP CONSTRAINT IF EXISTS PK_supplier CASCADE;

ALTER TABLE IF EXISTS ONLY user_session DROP CONSTRAINT IF EXISTS PK_user_session CASCADE;
ALTER TABLE IF EXISTS ONLY user_session DROP CONSTRAINT IF EXISTS FK_123 CASCADE;





-- *************** SqlDBM: PostgreSQL ****************;
-- ***************************************************;

-- DROP TABLE country;



-- ************************************** country


DROP TABLE IF EXISTS country;
CREATE TABLE country
(
    "id"           serial NOT NULL,
    country_name varchar(50) NOT NULL,
    CONSTRAINT PK_country PRIMARY KEY ( "id" )
);


-- *************** SqlDBM: PostgreSQL ****************;
-- ***************************************************;

-- DROP TABLE zip_code;



-- ************************************** zip_code




DROP TABLE IF EXISTS zip_code;
CREATE TABLE zip_code
(
    "id"   serial NOT NULL,
    code varchar(50) NOT NULL,
    CONSTRAINT PK_zip_code PRIMARY KEY ( "id" )
);




-- *************** SqlDBM: PostgreSQL ****************;
-- ***************************************************;

-- DROP TABLE city;



-- ************************************** city
DROP TABLE IF EXISTS city;
CREATE TABLE city
(
    "id"          serial NOT NULL,
    country_id  integer NOT NULL,
    zip_code_id integer NOT NULL,
    city_name   varchar(50) NOT NULL,
    CONSTRAINT PK_city PRIMARY KEY ( "id" ),
    CONSTRAINT FK_163 FOREIGN KEY ( zip_code_id ) REFERENCES zip_code ( "id" ),
    CONSTRAINT FK_201 FOREIGN KEY ( country_id ) REFERENCES country ( "id" )
);

CREATE INDEX fkIdx_164 ON city
    (
     zip_code_id
        );

CREATE INDEX fkIdx_202 ON city
    (
     country_id
        );



-- *************** SqlDBM: PostgreSQL ****************;
-- ***************************************************;

-- DROP TABLE street;



-- ************************************** street
DROP TABLE IF EXISTS street;
CREATE TABLE street
(
    "id"              serial NOT NULL,
    street_name     text NOT NULL,
    CONSTRAINT PK_street_number PRIMARY KEY ( "id" )
);





-- *************** SqlDBM: PostgreSQL ****************;
-- ***************************************************;

-- DROP TABLE address;



-- ************************************** address
DROP TABLE IF EXISTS address;
CREATE TABLE address
(
    "id"          serial NOT NULL,
    country_id  integer NOT NULL,
    zip_code_id integer NOT NULL,
    street_id   integer NOT NULL,
    city_id     integer NOT NULL,
    CONSTRAINT PK_address PRIMARY KEY ( "id" ),
    CONSTRAINT FK_236 FOREIGN KEY ( country_id ) REFERENCES country ( "id" ),
    CONSTRAINT FK_245 FOREIGN KEY ( zip_code_id ) REFERENCES zip_code ( "id" ),
    CONSTRAINT FK_248 FOREIGN KEY ( city_id ) REFERENCES city ( "id" ),
    CONSTRAINT FK_251 FOREIGN KEY ( street_id ) REFERENCES street ( "id" )
);

CREATE INDEX fkIdx_237 ON address
    (
     country_id
        );

CREATE INDEX fkIdx_246 ON address
    (
     zip_code_id
        );

CREATE INDEX fkIdx_249 ON address
    (
     city_id
        );

CREATE INDEX fkIdx_252 ON address
    (
     street_id
        );



-- *************** SqlDBM: PostgreSQL ****************;
-- ***************************************************;

-- DROP TABLE "user";



-- ************************************** "user"
DROP TABLE IF EXISTS "user";
CREATE TABLE "user"
(
    "id"            serial NOT NULL,
    username      varchar(50) UNIQUE NOT NULL,
    password_hash text NOT NULL,
    email         varchar (50) UNIQUE NOT NULL,
    CONSTRAINT PK_user PRIMARY KEY ( "id" )
);










-- *************** SqlDBM: PostgreSQL ****************;
-- ***************************************************;

-- DROP TABLE cart;



-- ************************************** cart
DROP TABLE IF EXISTS cart;
CREATE TABLE cart
(
    "id"      serial NOT NULL,
    user_id integer NOT NULL,
    session_id text NOT NULL,
    CONSTRAINT PK_cart PRIMARY KEY ( "id" ),
    CONSTRAINT FK_93 FOREIGN KEY ( user_id ) REFERENCES "user" ( "id" )
);

CREATE INDEX fkIdx_94 ON cart
    (
     user_id
        );






-- *************** SqlDBM: PostgreSQL ****************;
-- ***************************************************;

-- DROP TABLE department;



-- ************************************** department
DROP TABLE IF EXISTS department;
CREATE TABLE department
(
    "id"   serial NOT NULL,
    name varchar(50) NOT NULL,
    CONSTRAINT PK_department PRIMARY KEY ( "id" )
);






-- *************** SqlDBM: PostgreSQL ****************;
-- ***************************************************;

-- DROP TABLE supplier;



-- ************************************** supplier
DROP TABLE IF EXISTS supplier;
CREATE TABLE supplier
(
    "id"          serial NOT NULL,
    name        varchar(50) NOT NULL,
    description text,
    CONSTRAINT PK_supplier PRIMARY KEY ( "id" )
);







-- *************** SqlDBM: PostgreSQL ****************;
-- ***************************************************;

-- DROP TABLE product_category;



-- ************************************** "public".product_category
DROP TABLE IF EXISTS product_category;
CREATE TABLE product_category
(
    "id"             serial NOT NULL,
    department_id integer NOT NULL,
    name           varchar(50) NOT NULL,
    description text,
    CONSTRAINT PK_productcategory PRIMARY KEY ( "id" ),
    CONSTRAINT FK_71 FOREIGN KEY ( department_id ) REFERENCES department ( "id" )
);

CREATE INDEX fkIdx_72 ON product_category
    (
     department_id
        );






-- *************** SqlDBM: PostgreSQL ****************;
-- ***************************************************;

-- DROP TABLE product;



-- ************************************** product
DROP TABLE IF EXISTS product;
CREATE TABLE product
(
    "id"               serial NOT NULL,
    category_id      integer NOT NULL,
    supplier_id      integer NOT NULL,
    name             varchar(50) NOT NULL,
    description      text NOT NULL,
    default_price    decimal NOT NULL,
    default_currency varchar(50) NOT NULL,
    CONSTRAINT PK_product PRIMARY KEY ( "id" ),
    CONSTRAINT FK_55 FOREIGN KEY ( category_id ) REFERENCES product_category ( "id" ),
    CONSTRAINT FK_63 FOREIGN KEY ( supplier_id ) REFERENCES supplier ( "id" )
);

CREATE INDEX fkIdx_56 ON product
    (
     category_id
        );

CREATE INDEX fkIdx_64 ON product
    (
     supplier_id
        );








-- *************** SqlDBM: PostgreSQL ****************;
-- ***************************************************;

-- DROP TABLE cart_items;



-- ************************************** cart_items
DROP TABLE IF EXISTS cart_items;
CREATE TABLE cart_items
(
    "id"               serial NOT NULL,
    product_id       integer NOT NULL,
    cart_id          integer NOT NULL,
    product_quantity int NOT NULL,
    CONSTRAINT PK_cartitems PRIMARY KEY ( "id" ),
    CONSTRAINT FK_78 FOREIGN KEY ( product_id ) REFERENCES product ( "id" ),
    CONSTRAINT FK_85 FOREIGN KEY ( cart_id ) REFERENCES cart ( "id" )
);

CREATE INDEX fkIdx_79 ON cart_items
    (
     product_id
        );

CREATE INDEX fkIdx_86 ON cart_items
    (
     cart_id
        );









-- *************** SqlDBM: PostgreSQL ****************;
-- ***************************************************;

-- DROP TABLE customer_data;



-- ************************************** customer_data
DROP TABLE IF EXISTS customer_data;
CREATE TABLE customer_data
(
    "id"                    serial NOT NULL,
    user_id               integer NOT NULL,
    billing_address_id     integer NOT NULL,
    shipping_address_id    integer NOT NULL,
    customer_email        varchar(50) NOT NULL,
    customer_name         varchar(50) NOT NULL,
    customer_phone_number varchar(50) NOT NULL,
    session_id        text NOT NULL,
    CONSTRAINT PK_customer_data PRIMARY KEY ( "id" ),
    CONSTRAINT FK_145 FOREIGN KEY ( user_id ) REFERENCES "user" ( "id" ),
    CONSTRAINT FK_254 FOREIGN KEY ( billing_address_id ) REFERENCES address ( "id" ),
    CONSTRAINT FK_257 FOREIGN KEY ( shipping_address_id ) REFERENCES address ( "id" )
);

CREATE INDEX fkIdx_146 ON customer_data
    (
     user_id
        );

CREATE INDEX fkIdx_255 ON customer_data
    (
     billing_address_id
        );

CREATE INDEX fkIdx_258 ON customer_data
    (
     shipping_address_id
        );






-- *************** SqlDBM: PostgreSQL ****************;
-- ***************************************************;

-- DROP TABLE order_status;



-- ************************************** order_status
DROP TABLE IF EXISTS order_status;
CREATE TABLE order_status
(
    "id"   serial NOT NULL,
    name varchar(50) NOT NULL,
    CONSTRAINT PK_order_status PRIMARY KEY ( "id" )
);






-- *************** SqlDBM: PostgreSQL ****************;
-- ***************************************************;

-- DROP TABLE "order";



-- ************************************** "order"
DROP TABLE IF EXISTS "order";
CREATE TABLE "order"
(
    "id"              serial NOT NULL,
    cart_id         integer NOT NULL,
    order_status_id integer,
    user_id         integer NOT NULL,
    order_number    text NOT NULL,
    payment_method    varchar(20),
    order_date      timestamp NOT NULL default (now()),
    CONSTRAINT PK_order PRIMARY KEY ( "id" ),
    CONSTRAINT FK_106 FOREIGN KEY ( cart_id ) REFERENCES cart ( "id" ),
    CONSTRAINT FK_109 FOREIGN KEY ( user_id ) REFERENCES "user" ( "id" ),
    CONSTRAINT FK_116 FOREIGN KEY ( order_status_id ) REFERENCES order_status ( "id" )
);

CREATE INDEX fkIdx_107 ON "order"
    (
     cart_id
        );

CREATE INDEX fkIdx_110 ON "order"
    (
     user_id
        );

CREATE INDEX fkIdx_117 ON "order"
    (
     order_status_id
        );














-- *************** SqlDBM: PostgreSQL ****************;
-- ***************************************************;

-- DROP TABLE user_session;



-- ************************************** user_session
DROP TABLE IF EXISTS user_session;
CREATE TABLE user_session
(
    "id"                 serial NOT NULL,
    user_id            integer NOT NULL,
    session_expiration timestamp NOT NULL,
    session_token text UNIQUE,

    CONSTRAINT PK_user_session PRIMARY KEY ( "id" ),
    CONSTRAINT FK_123 FOREIGN KEY ( user_id ) REFERENCES "user" ( "id" )
);

CREATE INDEX fkIdx_124 ON user_session
    (
     user_id
        );













INSERT INTO department (id, name) VALUES (1, 'Hardware');
INSERT INTO "user" (id, username, email, password_hash) VALUES (1, 'guest', 'abc@abc', 'zaq');
INSERT INTO order_status VALUES (1, 'Not finished');
INSERT INTO order_status VALUES (2, 'Finished');
SELECT pg_catalog.setval('user_id_seq', 1, true);
SELECT pg_catalog.setval('order_status_id_seq', 3, true);


-- Prototypes queries
-- SELECT MAX(id) as id FROM public.user;

-- SELECT PRODUCT ITEMS FROM CART
-- select * from cart_items inner join cart on cart.id = cart_items.cart_id inner join product on product.id = cart_items.product_id;
