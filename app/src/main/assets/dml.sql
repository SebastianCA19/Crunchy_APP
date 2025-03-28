INSERT INTO estados_pedido ( nombre_estado_pedido)
VALUES ('encargada'),
('preparando'),
('pagado'),
('en camino');

/*QUEDA PENDIENTE LOCACIONES */

INSERT INTO metodos_pago (nombre_metodo_pago)
VALUES ('efectivo'),
       ('transferencia'),
       ('mixto');


INSERT INTO tipos_producto (nombre_tipo_producto)
VALUES ('combo'),/*1*/
('picada'),/*2*/
('bebida'),/*3*/
('personalizado');/*4*/



INSERT INTO infos_producto (cantidad_chicharron_gramos,cantidad_chorizo,cantidad_bollo)
VALUES (0,1,1),/*id=1 ;chorizo artesanal */
(215,1,1.5),/*id=2 ; combo 1*/
(360,1,2),/*id=3 ; combo 2*/
(480,2,3),/*id=4 ; combo 3*/
(695,2,4),/*id=5 ; combo 4*/
(820,3,5),/*id=6 ; combo 5*/
(215,0,1),/*id=7 ;chicharron pesonal */
(290,0,1.5),/*id=8 ; picada 1 */
(430,0,2),/*id=9 ; picada 2 */
(605,0,3),/*id=10 ; picada 3 */
(820,0,4),/*id=11; picada 4 */
(1000,0,5);/*id=12 ; kilo */



INSERT INTO productos (nombre_producto,id_tipo_producto,id_info_producto,precio)
VALUES ('chorizo artesanal',1,1,7000),/*id= 1*/
('combo #1',1,2,22000),/*id= 2 ; combo 1*/
('combo #2',1,3,34000),/*id= 3 ; combo 2*/
('combo #3',1,4,48000),/*id= 4 ; combo 3*/
('combo #4',1,5,65000),/*id= 5 ; combo 4*/
('combo #5',1,6,80000),/*id= 6 ; combo 5*/
('chicharron personal',2,7,17000),/*id= 7 */
('picada #1',2,8,23000),/*id= 8 ;picada #1*/
('picada #2',2,9,34000),/*id= 9 ;picada #2*/
('picada #3',2,10,48000),/*id= 10 ;picada #3*/
('picada #4',2,11,65000),/*id= 11;picada #4*/
('kilo',2,12,80000),/*id= 12 ;pound*/
('coca-cola 237ml',3,NULL,2000),
('quatro 237ml',3,NULL,2000),
('sprite 237ml',3,NULL,2000),
('manzana-postobon 250ml',3,NULL,2000),
('naranja-postobon 250ml',3,NULL,2000),
('cola-postobon 250ml',3,NULL,2000),
('colombiana 250ml',3,NULL,2000),
('agua-manzana 280ml',3,NULL,2000),
('agua 600ml',3,NULL,2000),
('jugo-hit-mora 500ml',3,NULL,3000),
('jugo-hit-tropical 500ml',3,NULL,3000),
('jugo-hit-lulo 500ml',3,NULL,3000),
('jugo-hit-narajaPiña 500ml',3,NULL,3000),
('bretaña 500ml',3,NULL,3000),
('coca-cola-normal 400ml',3,NULL,3500),
('coca-cola-zero 400ml',3,NULL,3500),
('pony-malta 1l',3,NULL,5000),
('coca-cola 1l',3,NULL,5000),
('coca-cola 1.5l',3,NULL,7500),
('coca-cola 2l',3,NULL,7500),
('costeñita',3,NULL,3000),
('miller-lite',3,NULL,3000),
('budweiser',3,NULL,3500),
('aguila-negra',3,NULL,3500),
('poker',3,NULL,3500),
('heineken',3,NULL,4000),
('coronita',3,NULL,4000),
('club-colombia',3,NULL,4000);




