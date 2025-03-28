-----------------------
Database name = bank
----------------------
Table list
---------------------

registration
+----------+----------------------------------+------+-----+---------+----------------+
| Field    | Type                             | Null | Key | Default | Extra          |
+----------+----------------------------------+------+-----+---------+----------------+
| id       | int                              | NO   | PRI | NULL    | auto_increment |
| name     | char(50)                         | NO   | UNI | NULL    |                |
| login    | tinyint(1)                       | YES  |     | 0       |                |
| status   | enum('pending','client','admin') | YES  |     | pending |                |
| username | char(50)                         | NO   | UNI | NULL    |                |
| password | char(50)                         | NO   | UNI | NULL    |                |
+----------+----------------------------------+------+-----+---------+----------------+

account
+---------------+----------------------------+------+-----+---------+----------------+
| Field         | Type                       | Null | Key | Default | Extra          |
+---------------+----------------------------+------+-----+---------+----------------+
| id            | int                        | NO   | PRI | NULL    | auto_increment |
| balance       | double                     | YES  |     | NULL    |                |
| ownerId       | int                        | YES  | MUL | NULL    |                |
| ownerName     | char(50)                   | YES  |     | NULL    |                |
| accountStatus | enum('pending','approved') | YES  |     | pending |                |
+---------------+----------------------------+------+-----+---------+----------------+

transaction
+--------------+----------+------+-----+---------+----------------+
| Field        | Type     | Null | Key | Default | Extra          |
+--------------+----------+------+-----+---------+----------------+
| id           | int      | NO   | PRI | NULL    | auto_increment |
| senderId     | int      | YES  |     | NULL    |                |
| senderName   | char(50) | YES  |     | NULL    |                |
| receiverId   | int      | YES  |     | NULL    |                |
| receiverName | char(50) | YES  |     | NULL    |                |
| amount       | double   | YES  |     | NULL    |                |
+--------------+----------+------+-----+---------+----------------+

-------------IMPORTANT-------------------

User or admin need to log in to access any of the feature
some features only available to admin
User account or ID need to get approved by admin to access any of features

----------------------------------------------