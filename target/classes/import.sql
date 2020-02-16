//Bootstrap WEB_CAM Table
INSERT INTO WEB_CAM (ID, NAME, COUNTRY, LOCATION, IS_VIP, OWNER) VALUES (1, 'Webcam Sattel', 'CH', 'Sattel', 0, 'user1@bfh.ch');
INSERT INTO WEB_CAM (ID, NAME, COUNTRY, LOCATION, IS_VIP, OWNER) VALUES (2, 'Siedlung Holliger', 'CH', 'Bern', 0, 'user1@bfh.ch');
INSERT INTO WEB_CAM (ID, NAME, COUNTRY, LOCATION, IS_VIP, OWNER) VALUES (3, 'Bielersee', 'CH', 'Biel', 0, 'user1@bfh.ch');
INSERT INTO WEB_CAM (ID, NAME, COUNTRY, LOCATION, IS_VIP, OWNER) VALUES (4, 'Berlin', 'DE', 'Fernsehturm', 0, 'user2@bfh.ch');
INSERT INTO WEB_CAM (ID, NAME, COUNTRY, LOCATION, IS_VIP, OWNER) VALUES (5, 'Matterhorn', 'CH', 'Hotel Palace', 1, 'user2@bfh.ch');

//Bootstrap User & Authority
INSERT INTO AUTHORITIES(ID, USERNAME, AUTHORITY ) VALUES (1, 'root@bfh.ch', 'ROLE_ADMINISTRATOR');
INSERT INTO USERS (ID, USERNAME, PASSWORD, ENABLED, AUTHORITIES_ID) VALUES (1, 'root@bfh.ch', '$2a$12$jnLQ2danaIveJLCiw7RzLef3k3.n0UyZhGmxmX2OBnWx3LRMUnoeW', 1, 1);
