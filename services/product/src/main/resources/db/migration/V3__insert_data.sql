/*A little bit risky, since currval of sequence id could be modified before second insert*/
insert into category(description, name) values ('category', 'category');
insert into product(description, name, available_quantity, price, category_id) values ('product', 'product', 4, 45.3, currval("category_seq"));


/*Safer approach, mostly fail-safe against sequence inconsistency*/
with c as (
    insert into category(description, name) values('category', 'category');
)
insert into product(description, name, available_quantity, price, category_id)
select 'product' as name,
       'product' as description,
       4 as available_quantity,
       45.3 as price,
       c.id as category_id
from c;