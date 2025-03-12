GRANT USAGE ON SCHEMA happy_db to happy_s;
GRANT USAGE ON SCHEMA happy_db to happy_w;

GRANT USAGE ON ALL SEQUENCES in SCHEMA happy_db to happy_w;
GRANT SELECT, INSERT, DELETE, UPDATE, TRUNCATE ON ALL TABLES in SCHEMA happy_db to happy_w;

GRANT ALL ON ALL TABLES in SCHEMA happy_db to happy_s;
GRANT USAGE ON SCHEMA public to happy_s;