Kenshin-System

System to store and validate monthly utility readings of a building for each floor,such as single phase electrical meter reading,three phase electrical meter reading,water meter reading,gas meter reading. 
The main goal is to create Meter Reading System(検針システム) for multiple buildings. Users can input monthly readings for a building and can check whether readins are valid, any irregular changes from last month or last year. Lastly, all the datas will be managed and stored inside a database. 
The system will reduce the use of paper and analog management, drastically improve the efficiency,reliablity and availability.

#Technologies Used

Client Side (View): Java Swing(Java Version 19) Java built in Http library and Apache Http Library to connect to Server Ehcache to store image files to be attached,temporarily

Server: Spring Boot(Java Version 17) 
Framework:Spring Boot 3.1.3
Security:Spring Boot Starter Security with Jwt Authentication,Role based Authorization 
Build Tool: Maven 
Database: PostgreSQL15 Spring Data Jpa for database schema
Packaging: Docker

#Features

User Input: Users can input monthly readings ,write comments and attach photos for each building.
Data Validation: The system validates input readings for accuracy. 
Irregularity Detection: Detects irregular changes in meter usage comparing it from last month or last year.
Data Management: All data is efficiently managed and stored in a PostgreSQL database.
Role base authorization so that only Manager can approve data. Paperless: Reduces paper usage and analog management. 
Efficiency: Improves efficiency, reliability, and availability of the meter reading process.

#How to start server app. 

1.Go to project dierctory and run the container
  - Docker compose up
2.Download csv files from github project repository and place them inside /postgres-kenshin directory so that server can start with pre populated data.
3.Run this psql command line to copy csv files into db.
  - docker exec -it postgres-kenshin psql -U ADMIN -d kenshin(Enter postgres container and do client side operation)
  - \COPY Building(name) FROM '/data/postgres/Building.csv' CSV HEADER;
  - \COPY Reading_Date(date) FROM '/data/postgres/Reading_Date.csv' CSV HEADER;
  - \COPY Reading_Date_Building(reading_date_id,building_id) FROM '/data/postgres/Reading_Date_Building.csv' CSV HEADER;
  - \COPY Floor(name,area,building_id,is_parent) FROM '/data/postgres/Floor.csv' CSV HEADER;
  - \COPY Tenant(name,floor_id,area) FROM '/data/postgres/Tenant.csv' CSV HEADER;
  - \COPY Readings(lighting_reading,power_reading,water_reading,gas_reading,lighting_reading_before_change,power_reading_before_change,water_reading_before_change,gas_reading_before_change,floor_id,reading_date_id,comment) FROM '/data/postgres/Readings.csv' DELIMITER ',' CSV HEADER;
  - \COPY _User(id,username,password,role) FROM '/data/postgres/_User.csv' CSV HEADER;

日本語

検針システム
単相電気検針、三相電気検針、水道検針、ガス検針など、ビルの毎月の公共料金の検針値を各階ごとに保存し、検証するシステム。
主な目的は、複数のビルの検針システムを構築することです。ユーザーは毎月の検針票を入力することができ、検針票が適切かどうか、先月や昨年からのデータと比較しながら異常な変化の有無をチェックすることができる。 最後に、すべてのデータはデータベースで管理・保存される。このシステムにより、紙の使用やアナログ管理が削減され、効率性、信頼性、可用性が飛躍的に向上する。

#使用技術

クライアントサイド（ビュー）： Java Swing(Java Version 19) Java組み込みのHttpライブラリとApache Httpライブラリでサーバーに接続 添付する画像ファイルを一時的に保存するEhcache

サーバー Spring Boot(Java Version 17) 
Framework:Spring Boot 3.1.5 
Security:Spring Boot Starter Security with Jwt Authentication（認証）, Role based Authorization（認可） 
ビルドツール： Maven 
データベース & データベーススキーマ：PostgreSQL15 Spring Data Jpa
パッケージン：Docker

#特徴

ユーザー入力： 毎月の測定値を入力したり、必要に応じてコメントを入れたり、写真を添付したりすることができます。
データの検証： 入力された測定値が正確かどうかを検証します。
不規則性の検出： メーター使用量の不規則な変化を検出し、先月または昨年と比較します。データ管理 すべてのデータは効率的に管理され、PostgreSQLデータベースに保存されます。マネージャーだけがデータを承認できるように、役割ベースの承認。
ペーパーレス： 紙の使用量とアナログ管理を削減します。
効率化： 検針プロセスの効率性、信頼性、可用性を向上させます。

#クライアント側デスクトップアプリの起動方法

#サーバーアプリの起動方法 

1.プロジェクトのディレクトリに移動し、コンテナを起動する
  - Docker compose up
2.githubのプロジェクト・リポジトリからcsvファイルをダウンロードし、/postgres-kenshinディレクトリに置く。
3.psqlコマンドラインを実行して、csvファイルをデータベースにコピーする。
  - docker exec -it postgres-kenshin psql -U ADMIN -d kenshin(コンテナー内入り、クライアントサイド操作する)
  - \COPY Building(name) FROM '/data/postgres/Building.csv' CSV HEADER;
  - \COPY Reading_Date(date) FROM '/data/postgres/Reading_Date.csv' CSV HEADER;
  - \COPY Reading_Date_Building(reading_date_id,building_id) FROM '/data/postgres/Reading_Date_Building.csv' CSV HEADER;
  - \COPY Floor(name,area,building_id,is_parent) FROM '/data/postgres/Floor.csv' CSV HEADER;
  - \COPY Tenant(name,floor_id,area) FROM '/data/postgres/Tenant.csv' CSV HEADER;
  - \COPY Readings(lighting_reading,power_reading,water_reading,gas_reading,lighting_reading_before_change,power_reading_before_change,water_reading_before_change,gas_reading_before_change,floor_id,reading_date_id,comment) FROM '/data/postgres/Readings.csv' DELIMITER ',' CSV HEADER;
  - \COPY _User(id,username,password,role) FROM '/data/postgres/_User.csv' CSV HEADER;
