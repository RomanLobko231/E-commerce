/*A little bit risky, since currval() is not exactly concurrent safe*/
insert into category(id, description, name)
values(
    nextval('category_seq'),
    'Includes various tea brands',
    'Tea'
);
insert into product(id, description, name, available_quantity, price, category_id)
values(
    nextval('product_seq'),
    'Chamomile tea grown and harvested in Galicia, Spain',
    'Chamomile tea',
    12,
    4.55,
    currval('category_seq')
);



/*Safer approach, mostly fail-safe against sequence inconsistency*/
with c as (
    insert into category(id, description, name)
    values(
        nextval('category_seq'),
        'Different sorts of coffee',
        'Coffee')
    returning id
)
insert into product(id, description, name, available_quantity, price, category_id)
select nextval('product_seq') as id,
       'Coffee' as name,
       'Pure arabica coffee, 100%' as description,
       20 as available_quantity,
       6.75 as price,
       c.id as category_id
from c;