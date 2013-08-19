
CREATE CACHED TABLE IBB_USER (
  USER_ID bigint generated by default as identity (start with 10001) not null primary key,
  EMAIL varchar(50) not null,
  USER_NAME varchar(50) not null,
  USER_HANDLE varchar(20) not null,
  HASH_PASSWORD varchar(100) not null,
  GENDER_CODE smallint default 2 not null,
  PASSWORD_SALT varchar(100) not null,
  MOBILE_NO varchar(15) default null,
  MOBILE_VERIFIED smallint default 0,
  AVATAR_URI varchar(200) default null,
  SMALL_AVATAR_URI varchar(200) default null,
  THUMB_AVATAR_URI varchar(200) default null,
  SPARE_EMAIL varchar(50) default null,
  ROLE_DATA varchar(50) default null,
  IS_ACTIVATED smallint default 0,
  IS_DISABLED smallint default 0,
  DISABLED_START datetime null,
  DISABLED_END datetime null,
  CREATED_TIME timestamp default '1970-01-01 00:00:00' not null,
  LAST_MODIFIED_TIME timestamp default CURRENT_TIMESTAMP
);

CREATE CACHED TABLE IBB_FORUM (
  ID INT GENERATED BY DEFAULT AS IDENTITY (START WITH 1) PRIMARY KEY,
  ID_ALIAS VARCHAR(50) NOT NULL ,
  TITLE VARCHAR(50) NOT NULL ,
  SUMMARY VARCHAR(500) NOT NULL ,
  SORT_ORDER INT DEFAULT 0 NOT NULL ,
  STATUS TINYINT DEFAULT 1 NOT NULL ,
  OPTIONS INT ,
  CREATED_TIME DATETIME DEFAULT '1970-01-01 00:00:00',
  LAST_MODIFIED_TIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE CACHED TABLE IBB_THREAD (
  ID BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1) PRIMARY KEY,
  ID_ALIAS VARCHAR(50) NOT NULL,
  USER_ID BIGINT NOT NULL,
  FORUM_ID INT NOT NULL,
  TYPE_ID TINYINT DEFAULT 0,
  TITLE VARCHAR(50) NOT NULL,
  CONTENT LONGVARCHAR(120000) NOT NULL,
  TAGS VARCHAR(50),
  SOURCE_URL VARCHAR(255),
  VIEW_COUNT INT DEFAULT 0,
  REPLY_COUNT INT DEFAULT 0,
  LAST_POST_ID BIGINT DEFAULT 0,
  LAST_POST_USER_ID BIGINT DEFAULT 0,
  LAST_POST_TIME DATETIME DEFAULT NULL,
  IS_TOP TINYINT DEFAULT 0,
  IS_GOOD TINYINT DEFAULT 0,
  CLIENT_CODE TINYINT DEFAULT 0,
  CLIENT_IP VARCHAR(20) DEFAULT NULL,
  CREATED_TIME DATETIME DEFAULT '1970-01-01 00:00:00' NOT NULL,
  LAST_MODIFIED_TIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  OPTIONS INT DEFAULT 0,
  STATUS SMALLINT DEFAULT 0
);

CREATE CACHED TABLE IBB_USER_ONLINE (
  USER_ID BIGINT NOT NULL PRIMARY KEY,
  SESSION_ID VARCHAR(50) NOT NULL,
  ACCESS_TOKEN VARCHAR(50) NOT NULL,
  PREV_CLIENT_IP VARCHAR(20),
  LAST_CLIENT_IP VARCHAR(20),
  PREV_LOGIN_TIME DATETIME,
  LAST_LOGIN_TIME DATETIME,
  TOTAL_LOGIN_COUNT INT DEFAULT 0,
  THAT_LOGIN_COUNT INT DEFAULT 0,
  TOTAL_ONLINE_TIME BIGINT DEFAULT 0,
  THAT_ONLINE_TIME BIGINT DEFAULT 0,
  LAST_ACCESSED_TIME BIGINT DEFAULT 0
);

CREATE CACHED TABLE IBB_API_RESOURCE (
  ID INT GENERATED BY DEFAULT AS IDENTITY (START WITH 1) NOT NULL PRIMARY KEY,
  PARENT_ID INT DEFAULT 0 NOT NULL,
  BASE_PATH VARCHAR(100) NOT NULL,
  DESCRIPTION VARCHAR(100) NOT NULL,
  VERSION VARCHAR(10) NOT NULL,
  GRADE_CODE TINYINT DEFAULT 1 NOT NULL,
  HTTP_METHOD VARCHAR(10) DEFAULT 'GET' NOT NULL,
  DATA_TYPE VARCHAR(30) DEFAULT 'JSON' NOT NULL,
  OTHERS VARCHAR(500),
  REQUIRE_LOGIN TINYINT DEFAULT 1 NOT NULL,
  IS_ENABLED TINYINT DEFAULT 1 NOT NULL
);

CREATE CACHED TABLE IBB_API_PARAM (
  ID INT GENERATED BY DEFAULT AS IDENTITY (START WITH 1) NOT NULL PRIMARY KEY,
  RESOURCE_ID INT NOT NULL,
  PARAM_NAME VARCHAR(50) NOT NULL,
  PARAM_RULE VARCHAR(200),
  PARAM_TYPE VARCHAR(20),
  DEFAULT_VALUE VARCHAR(50),
  DESCRIPTION VARCHAR(500),
  SINCE VARCHAR(10),
  SORT_ORDER INT DEFAULT 0 NOT NULL,
  IS_REQUIRED TINYINT DEFAULT 1 NOT NULL,
  IS_DEPRECATED TINYINT DEFAULT 0 NOT NULL
);