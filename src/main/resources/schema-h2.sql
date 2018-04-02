CREATE TABLE CONTACT (
  CONTACT_ID BIGINT AUTO_INCREMENT);

CREATE TABLE APPLICATION (
  APPLICATION_ID BIGINT AUTO_INCREMENT,
  CONTACT_ID BIGINT NOT NULL,
  PRODUCT_NAME VARCHAR(100) NOT NULL,
  DT_CREATED TIMESTAMP NOT NULL);
